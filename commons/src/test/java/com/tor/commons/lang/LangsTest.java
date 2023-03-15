package com.tor.commons.lang;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.math.Fraction;
import org.apache.commons.lang.reflect.ConstructorUtils;
import org.apache.commons.lang.reflect.FieldUtils;
import org.apache.commons.lang.reflect.MethodUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.StopWatch;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 18.03.2010
 * Time: 16:03:47
 * test fore org.apache.commons.lang package
 */
public class LangsTest {

    @Test
    public void testStopWatch() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < 1000000; i++) {
            double result = (double) ((i - 10) * Math.sin(0.1) * 2);
        }
        stopWatch.stop();
        System.out.println("JDK time: " + stopWatch);

    }

    @Test
    public void testDateUtils() {

        assertTrue(DateUtils.isSameDay(new Date(), new Date()));
        //  assertTrue(DateUtils.getMillisPerUnit(Calendar.MINUTE)==3600);


    }

    @Test
    public void testArrayUtils() {

        String[][] colorsString = {
                {"RED", "#FF0000"},
                {"GREEN", "#00FF00"},
                {"BLUE", "#0000FF"}
        };
        assertTrue(ArrayUtils.isNotEmpty(colorsString));
        System.out.println("colorsString = " + ArrayUtils.toString(colorsString));

        Map colorMap = ArrayUtils.toMap(colorsString);
        assertTrue(colorMap.size() == 3);

        Object[] colors = colorMap.keySet().toArray();
        ArrayUtils.add(colors, "");
        System.out.println("colors = " + ArrayUtils.toString(colors));
        System.out.println("EMPTY_OBJECT_ARRAY = " + ArrayUtils.toString(ArrayUtils.EMPTY_OBJECT_ARRAY, "is null"));
        System.out.println("NULL_OBJECT_ARRAY = " + ArrayUtils.toString(null, "is null"));
    }

    @Test
    public void testStringUtils() {
        boolean isOk = true;
        String[] colorsString = StringUtils.split("RED #FF0000 RED #FF0000 GREEN #00FF00 BLUE #0000FF");
        assertTrue(ArrayUtils.isNotEmpty(colorsString));
        System.out.println("colorsString = " + ArrayUtils.toString(colorsString));
        StringUtils.split("ab:cd:ef", ":", 2);    //["ab", "cd:ef"]
        StringUtils.splitByCharacterType("number5");//["number", "5"]
        StringUtils.splitByCharacterType("fooBar");//["foo", "B", "ar"]
        StringUtils.splitByCharacterType("foo200Bar");//["foo", "200", "B", "ar"]
        assertTrue(StringUtils.countMatches("abba", "a") == 2);
        assertTrue(isOk);
    }

    @Test
    public void testSystemUtils() {
        boolean isOk = true;
        System.out.println("getJavaHome() " + SystemUtils.getJavaHome());
        System.out.println("getJavaIoTmpDir " + SystemUtils.getJavaIoTmpDir());
        System.out.println("getUserHome() " + SystemUtils.getUserHome());
        System.out.println("getUserDir() " + SystemUtils.getUserDir());
        assertTrue(SystemUtils.IS_JAVA_1_6);
        assertTrue(isOk);
    }

    @Test
    public void testReflectMetodUtils() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        boolean isOk = true;
        Object o = MethodUtils.invokeMethod("String", "length", null);
        Assert.assertNotNull(o);
        System.out.println("lenth = " + o);
    }

    @Test
    public void testReflectConstructorUtils() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Object o = ConstructorUtils.invokeConstructor(Integer.class, "12");
        Assert.assertNotNull(o);
        assertTrue((Integer) o == 12);
        System.out.println("lenth = " + o);
    }

    @Test
    public void testReflectFieldUtils() {
        Object o = FieldUtils.getDeclaredField(this.getClass(), "log", true);
        Assert.assertNotNull(o);
        System.out.println("o = " + o);
    }

    @Test
    public void testMahtFraction() {
        Fraction o = Fraction.getFraction("1/2");
        Assert.assertNotNull(o);
        System.out.println("o = " + o);
        assertTrue(o.doubleValue() == 0.5d);

    }

    @Test
    public void testBuilderEqualsBuilder() {
        FIO fio2 = new FIO("Пупс", "Первый", "Последний");
        fio2.setLastName("Xxx");
        System.out.println("fio2 = " + fio2.hashCode() + " " + fio2);
        FIO fio3 = new FIO("Xxx", "Первый", "Последний");
        System.out.println("fio3 = " + fio3.hashCode() + " " + fio3);
        assertNotEquals(fio2, fio3);
        assertTrue(EqualsBuilder.reflectionEquals(fio2, fio3));
    }

    private class FIO {
        private String lastName, s1, s2;

        FIO(String lastName, String s1, String s2) {
            this.lastName = lastName;
            this.s1 = s1;
            this.s2 = s2;
        }

        void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }
}
