import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Board grid tile that represents a room location. A single room often ignores all other
 * tiles in its area, and refers all back to one tile in that room.
 *
 * A room also contains a bunch of doors which a player can choose to
 * exit from.
 */
public class Room extends Tile{
    private String name;
    private ArrayList<Integer> Occupants; // Integer Placeholder
    private HashMap<Integer, Door> doors;

    public Room(String name, int x, int y) {
        super(x,y);
        this.name = name;
        Occupants = new ArrayList<>();
    }

    public void addDoor(int k, Door d) {
        doors.put(k, d);
    }

    public HashMap<Integer, Door> getDoors() {
        return doors;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Integer> getOccupants() {
        return Occupants;
    }

    public void addOccupant(int token) {
        Occupants.add(token);
    }
}
