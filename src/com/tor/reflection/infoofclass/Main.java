package com.tor.reflection.infoofclass;

import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 13.01.2009
 * Time: 16:01:06
 * В примере для получения конструкторов, методов и полей класса используются методы
 * getDeclaredConstructors(), getDeclaredMethods(), getDeclaredFields().
 * Но, наряду с этими методами, объект типа Class имеет также методы
 * getConstructors(), getMethods(), getFields(). В чем их отличие?
 * Отличаются они тем, что методы getDeclaredXXX() возвращают элементы, объявленные в текущем классе
 * (в том числе элементы, имеющие модификаторы доступа protected и private), а методы getConstructors(),
 * getMethods(), getFields() возвращают все public элементы текущего и родительских классов.
 */
public class Main {
    private static final Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) throws NoSuchFieldException, NoSuchMethodException {
        infoPointClass();
        infoSomethingClass();
    }

    private static void infoSomethingClass() throws NoSuchMethodException, NoSuchFieldException {
        Class sourceClass = Something.class;
        int classModifire = sourceClass.getModifiers();
        System.out.println("Something.class is Final " + Modifier.isFinal(classModifire));

        Constructor constructor = sourceClass.getDeclaredConstructor(null);
        System.out.println(" Constructor is Private = " + Modifier.isPrivate(constructor.getModifiers()));

        Field varitable = sourceClass.getField("variable");
        System.out.println(" Field 'variable' is transient = " + Modifier.isTransient(varitable.getModifiers()));
        Method method = sourceClass.getMethod("getVariable", null);
        System.out.println(" getVariable is Synchronized = " + Modifier.isSynchronized(method.getModifiers()));
    }

    private static void infoPointClass() {
        Class clazz = Point.class;
        System.out.println("clazz = " + clazz.getName());

        Class superClass = clazz.getSuperclass();
        System.out.println("superClass = " + superClass.getName());

        System.out.println("\nAll implemented interfaces:");
        Class[] intrfaces = clazz.getInterfaces();
        for (int idx = 0; idx < intrfaces.length; idx++) {
            System.out.println("intrfaces[" + (idx + 1) + "] = " + intrfaces[idx].getName());
        }

        System.out.println("\nAll declared constructors of Point class:");
        Constructor[] constructorClass = clazz.getDeclaredConstructors();
        for (int index = 0; index < constructorClass.length; index++) {
            System.out.println("constructorClass[" + (index + 1) + "] = " + constructorClass[index]);
        }
        System.out.println("\nAll declared methods of Point class:");
        Method[] metods = clazz.getDeclaredMethods();
        for (int indMetod = 0; indMetod < metods.length; indMetod++) {
            System.out.println("metods [" + (indMetod + 1) + "] = " + metods[indMetod]);
        }
        System.out.println("\nAll declared fields of Point class:");
        Field[] fields = clazz.getDeclaredFields();
        for (int indMetod = 0; indMetod < fields.length; indMetod++) {
            System.out.println("fields [" + (indMetod + 1) + "] = " + fields[indMetod]);
        }
    }
}
