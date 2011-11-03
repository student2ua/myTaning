package com.tor.reflection.runer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 1. При вызове статического метода или осуществлении доступа к статическому полю класса вместо
 * экземпляра класса в методы invoke(...), get(...) и set(...) передается null.
 * 2. Для нахождения/выполнения конструктора или метода необходимо знать, какие аргументы они имеют.
 * Если конструктор или метод не имеет таковых, тогда передается пустой массив или null.
 */

public class Main {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Class clazz = com.tor.reflection.runer.Something.class;

        Field staticVar = clazz.getField("STATIC_VARITABLE");
        staticVar.set(null, "Static Variable");
        System.out.println(" " + staticVar.getName() + " " + staticVar.get(null));

        Class[] staticMetodArgType = {String.class, int.class, Double.class};
        Method staticMetod = clazz.getMethod("concatenate", staticMetodArgType);
        Object[] statMetodArg = {new String("static"), new Integer(52), new Double(4.01)};
        System.out.println(" " + staticMetod.getName() + " " + staticMetod.invoke(null, statMetodArg));

        Class[] constructorMetodArgType = {String.class, String.class, boolean.class};
        Constructor constructor = clazz.getConstructor(constructorMetodArgType);
        Object[] constructorMetodArg = {new String("Main"), new String("Constructor"), new Boolean(true)};
        Object classInstance = constructor.newInstance(constructorMetodArg);

        Field strWord = clazz.getField("word");
        System.out.println(strWord.getName() + " : " + strWord.get(classInstance));
        Field strHellow = clazz.getField("hello");
        System.out.println(strHellow.getName() + " : " + strHellow.get(classInstance));

        Field boolCups = clazz.getField("isLoweCase");
        System.out.println(boolCups.getName() + " : " + boolCups.get(classInstance));

        Class[] argType = {String.class};
        Method prnHellow = clazz.getMethod("printHello", argType);
        Object[] prnHelliwArg = {new String("!!!")};
        System.out.println(" " + prnHellow.getName() + " : " + prnHellow.invoke(classInstance, prnHelliwArg));
    }

}
