package com.tor.reflection.bean;

import obuchenie.data.Person;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 27.05.13
 * Time: 18:43
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 */
public class BeanInfoDemo {
    public BeanInfoDemo() {
        BeanInfo info = null;
        try {
            info = Introspector.getBeanInfo(Person.class);
        } catch (IntrospectionException ex) {
            throw new RuntimeException("Unable to introspect dynamic table data class", ex);
        }
        PropertyDescriptor[] pds = info.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            System.out.printf("getName() %s, getDisplayName() %s, getPropertyType() %s, getPropertyEditorClass() %s, getReadMethod() %s, getShortDescription() %s, getWriteMethod()%s%n", pd.getName(), pd.getDisplayName(), pd.getPropertyType(), pd.getPropertyEditorClass(), pd.getReadMethod(), pd.getShortDescription(), pd.getWriteMethod());
        }
    }

    public static void main(String[] args) {
        new BeanInfoDemo();
    }
}
