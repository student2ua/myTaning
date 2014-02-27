package com.tor.text;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 07.08.12
 * Time: 19:24
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * String.format
 * <pre>
 * d	 Decimal (ordinary) integer	 %5d %d
 * f	 Fixed-point (everyday notation) floating point	 %6.2f %f
 * e	 E-notation floating point	 %8.3e %e
 * g	 General floating point (Java decides whether to use E-notation or not)	 %8.3g %g
 * s	 String	 %12s %s
 * c	 Character	 %2c %c
 * </pre>
 */
public class FormatedOut_String_Format {
    public FormatedOut_String_Format() {

        String aString = "world";
        int aInt = 20;
        System.out.println(String.format("Hello, %s on line %d", aString, aInt));

        System.out.println(String.format("Line:%2$d. Value:%1$s. Result: Hello %1$s at line %2$d", aString, aInt));
/**@see Formatter
 * %[argument_index$][flags][width]conversion
 * */
        double double1 = 50.5;
        //"%8.3f" is format specifier
        //"8" is the total number of spaces used to output the value
        //"3" is the number of digits after the decimal point of the output
        //"f" is conversion character, means the output is a floating-point number
        System.out.printf("1 Begin %8.3f End", double1);
        System.out.println();
        //left justified, if you add a hyphen(-) after the %
        System.out.printf("2 Begin %-8.3f End", double1);
        System.out.println();

        double double2 = 50.987654321;
        //when digits are discarded, the output is rounded, not truncated
        //"%n" is is new line separator, platform-specific line separator
        System.out.printf("3 Begin %8.3f End %n", double2);

        int int1 = 50;
        //"d" is conversion character, means the output is a Decimal integer
        System.out.printf("4 Begin %8d End %n", int1);

        //"s" is conversion character, means the output is a String
        System.out.printf("5 Begin %8.3f End %s", double1, "How are you?");
    }

    public static void main(String[] args) {
        new FormatedOut_String_Format();
    }
}
