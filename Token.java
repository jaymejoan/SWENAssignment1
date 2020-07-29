import java.util.HashSet;

public class Token {
    public String name;
    int x, y;
    Board board;
    Tile[][] tiles;
    Room room;

    public Token(String name, int x, int y, Board board){
        this.name = name;
        this.x = x;
        this.y = y;
        this.board = board;
        this.tiles = board.getTiles();

    }

    public void move(int x, int y, Room room){
        this.x = x;
        this.y = y;
        this.room = room;
    }
}


class TokenWeapon extends Token {
    public TokenWeapon(String name, int x, int y, Board board) {
        super(name, x, y, board);
    }
}

class TokenChar extends Token {

    public TokenChar(String name, int x, int y, Board board) { super(name, x, y, board); }

    /**
     * Find the range of movement given a dice roll of the player.
     * Returns the rooms that can be visited if it is within the range of movement.
     *
     * @param row   -- The y coordinates
     * @param col   -- The x coordinates
     * @param dice  -- The dice roll
     * @param visited   -- The range of movement of the player
     */
    public HashSet<Tile> getVisitableTiles(int row, int col, int dice, HashSet<Tile> visited) {
        if (dice > -1 && (tiles[row][col] instanceof Hallway || tiles[row][col] instanceof Door)) {  //Check that the player is able to move and that the current tile has
            visited.add(tiles[row][col]);                       //not been visited

            if(tiles[row][col] instanceof Door) visited.add((Room)((Door)tiles[row][col]).getCenterTile());

            //Move in all directions
            if (row - 1 >= 0) getVisitableTiles(row - 1, col, dice - 1, visited);
            if (row + 1 < tiles.length) getVisitableTiles(row + 1, col, dice - 1, visited);
            if (col - 1 >= 0) getVisitableTiles(row, col - 1, dice - 1, visited);
            if (col + 1 < tiles[0].length) getVisitableTiles(row, col + 1, dice - 1, visited);
        }

        return visited;
    }

}
