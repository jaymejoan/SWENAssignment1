import java.util.ArrayList;
import java.util.HashMap;

/**
 * A tile represents a position on the grid board, where tokens can exist on.
 */
public class Tile {
    private int x, y;
    public Boolean visited = false;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setName(String name) {}

    public void addDoor(int k, Door d) {}

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

/**
 * Blocked is a tile type that is completely inaccessible to any type of token
 * Nothing may occupy it.
 */
class Blocked extends Tile {

    public Blocked(int x, int y) {
        super(x,y);
    }
}

class Hallway extends Tile {

    public Hallway(int x, int y) {
        super(x,y);
    }
}

/**
 * Board grid tile that represents a room location. A single room often ignores all other
 * tiles in its area, and refers all back to one tile in that room.
 *
 * A room also contains a bunch of doors which a player can choose to
 * exit from.
 */
class Room extends Tile{
    private String name;
    private ArrayList<Integer> Occupants; // Integer Placeholder
    private HashMap<Integer, Door> doors = new HashMap<>();

    public Room(int x, int y) {
        super(x,y);
        //this.name = name;
        Occupants = new ArrayList<>();
    }

    public void addDoor(int k, Door d) {
        doors.put(k, d);
    }

    public void setName(String name) {
        this.name = name;
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

/**
 * Represents a hallway-type tile which allows the player token to move into
 * a room/ room tile.
 */
class Door extends Tile{

    public Door(int x, int y) {
        super(x,y);
    }
}