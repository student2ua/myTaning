package com.tor.commons.lang;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import obuchenie.data.FIO;
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
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 18.03.2010
 * Time: 16:03:47
 * To change this template use File | Settings | File Templates.
 */
public class LangsTest extends TestCase {
    private static final Logger log = Logger.getLogger(LangsTest.class);

    public LangsTest(String s) {
        super(s);
    }

    public static Test suite() throws FileNotFoundException {
        TestSuite suite = new TestSuite();
        suite.addTest(new LangsTest("testStopWatch"));
        suite.addTest(new LangsTest("testDateUtils"));
        suite.addTest(new LangsTest("testArrayUtils"));
        suite.addTest(new LangsTest("testStringUtils"));
        suite.addTest(new LangsTest("testSystemUtils"));
        suite.addTest(new LangsTest("testReflectMetodUtils"));
        suite.addTest(new LangsTest("testReflectConstructorUtils"));
        suite.addTest(new LangsTest("testReflectFieldUtils"));
        suite.addTest(new LangsTest("testMahtFraction"));
        suite.addTest(new LangsTest("testBuilderEqualsBuilder"));
        return suite;
    }

    public void testStopWatch() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < 1000000; i++) {
            double result = (double) ((i - 10) * Math.sin(0.1) * 2);
        }
        stopWatch.stop();
        System.out.println("JDK time: " + stopWatch);

    }

    public void testDateUtils() {

        assertTrue(DateUtils.isSameDay(new Date(), new Date()));
        //  assertTrue(DateUtils.getMillisPerUnit(Calendar.MINUTE)==3600);


    }

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

    public void testSystemUtils() {
        boolean isOk = true;
        System.out.println("getJavaHome() " + SystemUtils.getJavaHome());
        System.out.println("getJavaIoTmpDir " + SystemUtils.getJavaIoTmpDir());
        System.out.println("getUserHome() " + SystemUtils.getUserHome());
        System.out.println("getUserDir() " + SystemUtils.getUserDir());
        assertTrue(SystemUtils.IS_JAVA_1_4);
        assertTrue(isOk);
    }

    public void testReflectMetodUtils() {
        boolean isOk = true;
        try {
            Object o = MethodUtils.invokeMethod("String", "length", null);
            assertNotNull(o);
            System.out.println("lenth = " + o);

        } catch (NoSuchMethodException e) {
            isOk = false;
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            isOk = false;
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            isOk = false;
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        assertTrue(isOk);
    }

    public void testReflectConstructorUtils() {
        boolean isOk = true;
        try {
            Object o = ConstructorUtils.invokeConstructor(Integer.class, "12");
            assertNotNull(o);
            assertTrue(((Integer) o).intValue() == 12);
            System.out.println("lenth = " + o);

        } catch (NoSuchMethodException e) {
            isOk = false;
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            isOk = false;
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            isOk = false;
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            isOk = false;
        }
        assertTrue(isOk);
    }

    public void testReflectFieldUtils() {
        boolean isOk = true;
        Object o = FieldUtils.getDeclaredField(this.getClass(), "log", true);
        assertNotNull(o);
        System.out.println("o = " + o);


        assertTrue(isOk);
    }

    public void testMahtFraction() {
        boolean isOk = true;
        Fraction o = Fraction.getFraction("1/2");
        assertNotNull(o);
        System.out.println("o = " + o);
        assertTrue(o.doubleValue() == 0.5d);

        assertTrue(isOk);
    }

    public void testBuilderEqualsBuilder() {
        boolean isOk = true;
        FIO fio2 = new FIO("Пупс", "Первый", "Последний");
        fio2.setLastName("Xxx");
        System.out.println("fio2 = " + fio2.hashCode() + " " + fio2);
        FIO fio3 = new FIO("Xxx", "Первый", "Последний");
        System.out.println("fio3 = " + fio3.hashCode() + " " + fio3);
        assertFalse(fio2.equals(fio3));
        assertTrue(EqualsBuilder.reflectionEquals(fio2, fio3));

        assertTrue(isOk);
    }
}
