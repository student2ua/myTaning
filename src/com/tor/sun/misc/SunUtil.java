package com.tor.sun.misc;

import sun.misc.SharedSecrets;

import java.io.FileDescriptor;
import java.nio.MappedByteBuffer;

/**
 * User: tor
 * Date: 16.03.15
 * Time: 13:02
 * To change this template use File | Settings | File Templates.
 */
public class SunUtil {
    //  получение нативного (OS-level) дескриптора файла из Java-объекта FileDescriptor
    public static int getNativeFD(FileDescriptor fd) {
        return SharedSecrets.getJavaIOFileDescriptorAccess().get(fd);
    }

    //JavaIOAccess
    public void consoleCharset() {
        if (System.console() != null) {
            System.out.println(SharedSecrets.getJavaIOAccess().charset());
        }
    }

    /* Unmapping MappedByteBuffer

     А вот как раз и пример Cleaner'а в системных классах JDK.
     Кто пользовался в Java memory-mapped файлами посредством FileChannel.map(), вероятно, не раз посожалел, что у MappedByteBuffer'а нет метода unmap(). На то есть свои причины.
     Но, как в поговорке, если нельзя, но очень хочется — то можно. Реализация MappedByteBuffer создает Cleaner для выполнения unmap после сборки мусора, когда буфер становится недостижим. Так вот, этот Cleaner можно вызывать и вручную:
    */
    public static void unmap(MappedByteBuffer bb) {
        if (bb instanceof sun.nio.ch.DirectBuffer) {
            ((sun.nio.ch.DirectBuffer) bb).cleaner().clean();
        }
    }
}
