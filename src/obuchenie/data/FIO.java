package obuchenie.data;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 04.08.2008
 * Time: 18:19:56
 * To change this template use File | Settings | File Templates.
 */
public class FIO {
    public FIO(String lastName, String midllName, String firstName) {
        this.lastName = lastName;
        this.midllName = midllName;
        this.firstName = firstName;
    }

    private String lastName;
    private String midllName;
    private String firstName;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMidllName() {
        return midllName;
    }

    public void setMidllName(String midllName) {
        this.midllName = midllName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

   public String toString(){
       return getLastName()+", "+getFirstName()+" "+getMidllName();
   }
}
