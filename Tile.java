import java.util.ArrayList;
import java.util.HashMap;

/**
 * A tile represents a position on the grid board, where tokens can exist on.
 * <p>
 * This should probably be a functional abstract class.
 */
public class Tile {
    private int x, y;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the name for this Tile.
     *
     * @param name -- the name of this Tile.
     */
    public void setName(String name) {
    }

    /**
     * Adds a door to a Room.
     *
     * @param k -- the index for the door.
     * @param d -- the door to be added.
     */
    public void addDoor(int k, Door d) {
    }

    /**
     * Returns all the Doors inside a Room.
     *
     * @return HashMap -- map of all the doors with door indexes as the key.
     */
    public HashMap<Integer, Door> getDoors() {
        return null;
    }

    /**
     * Sets the centre tile for a Room.
     *
     * @param r -- the Room containing this centre tile.
     */
    public void setCentreTile(Room r) {
    }

    /**
     * Gets the centre tile representing a Room.
     *
     * @return Room -- the centre tile for this Room.
     */
    public Room getCentreTile() {
        return null;
    }

    /**
     * Gets the x position of a Tile.
     *
     * @return int - the x position of this Tile.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y position of a Tile.
     *
     * @return int - the y position of this Tile.
     */
    public int getY() {
        return y;
    }

    public String toString() {
        return null;
    }
}

/**
 * Blocked is a tile type that is completely inaccessible to any type of token.
 * Nothing may occupy it.
 */
class Blocked extends Tile {

    public Blocked(int x, int y) {
        super(x, y);
    }

    public String toString() {
        return "#";
    }
}

/**
 * Players on a Hallway tile will be unable to make a suggestion or accusation.
 */
class Hallway extends Tile {

    public boolean visited = false;

    public Hallway(int x, int y) {
        super(x, y);
    }

    public String toString() {
        return !visited ? "░" : "X";
    }
}

/**
 * Board grid tile that represents a room location. A single room often ignores all other
 * tiles in its area, and refers all back to one tile in that room.
 * <p>
 * A room also contains a bunch of doors which a player can choose to
 * exit from.
 */
class Room extends Tile {
    private String name;
    private ArrayList<Token> Occupants; // Integer Placeholder
    private HashMap<Integer, Door> doors = new HashMap<>();

    public Room(int x, int y) {
        super(x, y);
        //this.name = name;
        Occupants = new ArrayList<>();
    }

    public void addDoor(int k, Door d) {
        doors.put(k, d);
        d.setIndex(k);
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

    public ArrayList<Token> getOccupants() {
        return Occupants;
    }

    public void addOccupant(Token token) {
        Occupants.add(token);
    }

    public void removeOccupant(Token token) {
        Occupants.remove(token);
    }

    public String toString() {
        return "█";
    }
}

/**
 * Represents a hallway-type tile which allows the player token to move into
 * a room/room tile.
 */
class Door extends Tile {

    Room centerTile;

    int indexOfDoor = 0;

    public Door(int x, int y) {
        super(x, y);
    }

    public void setIndex(int index) {
        indexOfDoor = index;
    }

    public void setCenterTile(Room r) {
        this.centerTile = r;
    }

    public Room getCenterTile() {
        return centerTile;
    }

    public String toString() {
        return String.valueOf(indexOfDoor);
    }

}