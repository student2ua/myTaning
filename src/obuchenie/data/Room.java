package obuchenie.data;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 05.09.2008
 * Time: 17:03:24
 * To change this template use File | Settings | File Templates.
 */
public class Room {
    private static final Logger log = Logger.getLogger(Room.class);
    Integer roomNumber;
    Integer seatingCount;

    public Room(Integer roomNumber, Integer seatingCount) {
        this.roomNumber = roomNumber;
        this.seatingCount = seatingCount;
    }

    public Integer getRoomNumber() {
    return roomNumber;
}

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getSeatingCount() {
        return seatingCount;
    }

    public void setSeatingCount(Integer seatingCount) {
        this.seatingCount = seatingCount;
    }
    public String toString(){
        return "room ¹ "+roomNumber;
    }
}
