package com.tor.sun.misc;

import sun.misc.Cleaner;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * User: tor
 * Date: 16.03.15
 * Time: 15:31
 * http://wasm.ru/wault/article/show/unsjav1
 */
public class UnsafeUtil {
    public static Unsafe unsafe;
    private static long fieldOffset;
    private static UnsafeUtil instance = new UnsafeUtil();

    private Object obj;

    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);

            unsafe = (Unsafe) f.get(null);
            fieldOffset = unsafe.objectFieldOffset(UnsafeUtil.class.getDeclaredField("obj"));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static long ObjectToAddress(Object o) {
        instance.obj = o;
        return unsafe.getLong(instance, fieldOffset);
    }

    public static Object AddressToObject(long address) {
        unsafe.putLong(instance, fieldOffset, address);
        return instance.obj;
    }

    /**
     * Функция возвращает результат в DWORD'ах, если нужен в байтах не забудьте умножить на 4. С помощью sizeOf()
     * можно теперь копировать содержимое инстанций - так называемая "shallow copy". "Shallow" - так как в случае
     * внутренних переменных типа Object (и от него производных) копируются естественно только указатели, а не объекты целиком.
     */
    public static long sizeOf(Object object) {
        boolean vm1_5 = true;
        return unsafe.getAddress(unsafe.getAddress(ObjectToAddress(object) + 4) + (vm1_5 ? 12 : 56));
    }

    /**
     * copyObjectShallow бывает особенно полезна если иметь дело с объектами, содержащими большое количество переменных
     * примитивных типов. То есть когда класс используется в основном для хранения данных, как структура в С.
     * Копировать переменные по одной (единственный штатный способ Явы) - удовольствия мало.
     */
    public static void copyObjectShallow(Object objectSource, Object objectDest) {
        unsafe.copyMemory(ObjectToAddress(objectSource), ObjectToAddress(objectDest), sizeOf(objectSource) * 4);
    }

    public static long getByteBufferAddress(ByteBuffer buffer) {
        try {
            return getField(Buffer.class, "address").getLong(buffer);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    public static Field getField(Class cls, String name) {
        try {
            Field f = cls.getDeclaredField(name);
            f.setAccessible(true);
            return f;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static long allocateMemory(long size, Object holder) {
        final long address = unsafe.allocateMemory(size);
        Cleaner.create(holder, new Runnable() {
            @Override
            public void run() {
                unsafe.freeMemory(address);
            }
        });
        return address;
    }
}
