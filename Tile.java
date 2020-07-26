/**
 * A tile represents a position on the grid board, where tokens can exist on.
 */
public class Tile {
    private int x, y;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
