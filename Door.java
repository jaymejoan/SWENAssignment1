/**
 * Represents a hallway-type tile which allows the player token to move into
 * a room/ room tile.
 */
public class Door extends Tile{
    int exitNum;

    public Door(int x, int y, int exit) {
        super(x,y);
        exitNum = exit;
    }
}
