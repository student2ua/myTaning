package obuchenie;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 28.08.12
 * Time: 15:42
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 http://vanillajava.blogspot.gr/2012/08/uses-for-special-characters-in-java-code.html
 */
public class ConnectingCharacters {
   public static void main(String[] args) {
       for (int i = Character.MIN_CODE_POINT; i <= Character.MAX_CODE_POINT; i++)
              if (Character.isJavaIdentifierStart(i) && !Character.isAlphabetic(i))
                  System.out.println(i + " : " + (char) i);
       System.out.println('\u005F');
   }

}
