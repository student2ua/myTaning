package com.tor.reflection.access;

import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 14.01.2009
 * Time: 16:26:45
 * Если конструктор, метод или поле имеют модификаторы protected или private, это еще не значит,
 * что они не доступны. При помощи механизма reflection можно изменить уровень доступа к любому
 * элементу класса или объекта. Для этого у полученного элемента надо вызвать метод setAccessible(true).
 */
public class Main {
    private static final Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class clazz = Name.class;
        Field def = clazz.getField("DEFAULT_NAME");
        def.setAccessible(true);

        Class[] argTyp = {String.class};
        Constructor constructors = clazz.getConstructor(argTyp);
        constructors.setAccessible(true);

        Object[] constArg = {def.get(null)};
        Object instance = constructors.newInstance(constArg);

        Method getName = clazz.getDeclaredMethod("getName", null);
        getName.setAccessible(true);
        System.out.println(getName.getName() + " : " + getName.invoke(instance, null));

        Class[] argSetNameType = {String.class};
        Method setName = clazz.getDeclaredMethod("setName", argSetNameType);
        Object[] argMethot = {new String("dybl6 1")};
        setName.setAccessible(true);
        setName.invoke(instance, argMethot);

        System.out.println(getName.getName() + " : " + getName.invoke(instance, null));

        Method getDefaultName = clazz.getDeclaredMethod("getDEFAULT_NAME", null);
        getDefaultName.setAccessible(true);
        System.out.println(getDefaultName.getName() + " : " + getDefaultName.invoke(null, null));
    }
}
