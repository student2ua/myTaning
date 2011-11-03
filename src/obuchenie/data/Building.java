package obuchenie.data;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 05.09.2008
 * Time: 17:00:58
 * To change this template use File | Settings | File Templates.
 */
public class Building {
    private  String name;
private  Floor [] floor;
    public Building(String name, Floor[] floor) {
        this.name = name;
        this.floor = floor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Floor[] getFloor() {
        return floor;
    }

    public void setFloor(Floor[] floor) {
        this.floor = floor;
    }

   public String toString(){
       return name;
   }

}
