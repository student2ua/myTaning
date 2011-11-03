package obuchenie.data;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 05.09.2008
 * Time: 17:02:11
 * To change this template use File | Settings | File Templates.
 */
public class Floor {
    private static final Logger log = Logger.getLogger(Floor.class);

    public Floor(Integer numberFloor, Room[] room) {
        this.numberFloor = numberFloor;
        this.room = room;
    }

    public Integer getNumberFloor() {
        return numberFloor;
    }

    public void setNumberFloor(Integer numberFloor) {
        this.numberFloor = numberFloor;
    }

    public Room[] getRoom() {
        return room;
    }

    public void setRoom(Room[] room) {
        this.room = room;
    }
     public String toString(){
         return "floor ¹"+numberFloor;
     }
    Integer numberFloor;
    Room[] room;
}
