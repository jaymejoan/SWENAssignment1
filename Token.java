import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

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
        this.tiles = board.board;
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
//    public HashSet<Tile> getVisitableTiles(int row, int col, int dice, HashSet<Tile> visited) {
//
//        if (dice > -1) {
//            // if(tiles[row][col] == null)
//            if (tiles[row][col] instanceof Hallway) {
//                visited.add(tiles[row][col]);
//                ((Hallway) tiles[row][col]).visited = true;
//            }
//            else if (tiles[row][col] instanceof Door) visited.add(tiles[row][col].getCentreTile());
//            //Move in all directions
//
//            if (!(tiles[row][col] instanceof Blocked || tiles[row][col] instanceof Room) && !visited.contains(tiles[row][col])) {
//                if (row - 1 >= 0) getVisitableTiles(row - 1, col, dice - 1, visited);
//                if (row + 1 < tiles.length) getVisitableTiles(row + 1, col, dice - 1, visited);
//                if (col - 1 >= 0) getVisitableTiles(row, col - 1, dice - 1, visited);
//                if (col + 1 < tiles[0].length) getVisitableTiles(row, col + 1, dice - 1, visited);
//            }
//        }
//
//        return visited;
//    }

    public HashSet<Tile> getVisitableTiles(int row, int col, int dice, HashSet<Tile> visited) {
        if (dice > -1 && (board.board[row][col] instanceof Hallway || board.board[row][col] instanceof Door)) {  //Check that the player is able to move and that the current tile has
            visited.add(board.board[row][col]);
            if (board.board[row][col] instanceof Hallway) {
                ((Hallway) board.board[row][col]).visited = true;
            }//not been visited

            if(board.board[row][col] instanceof Door && dice > 0) {
                visited.add((Room) ((Door) board.board[row][col]).getCenterTile());

            }
            //Move in all directions
            if (row - 1 >= 0) getVisitableTiles(row - 1, col, dice - 1, visited);
            if (row + 1 < board.board.length) getVisitableTiles(row + 1, col, dice - 1, visited);
            if (col - 1 >= 0) getVisitableTiles(row, col - 1, dice - 1, visited);
            if (col + 1 < board.board[0].length) getVisitableTiles(row, col + 1, dice - 1, visited);
        }

        return visited;
    }

    public Tile BF(HashSet<Tile> visited, Tile tile, HashSet<Tile> movementRange) {
        Queue<Tile> queue = new LinkedList<>();
        queue.add(tile);
        visited.add(tile);

        while (!queue.isEmpty()) {
            Tile currentTile = queue.remove();

            if (movementRange.contains(currentTile)) return currentTile;    //Found the closest tile of the player's movement

            if(currentTile instanceof Blocked) continue;  //Do not find the neighbours of an obstacle

            //add to the queue if the left/right cells are valid
            for (int i = -1; i < 2; i += 2) {
                if (currentTile.getY() + i >= 0 && currentTile.getY() + i <= tiles[0].length) {
                    queue.add(tiles[currentTile.getX()][currentTile.getY() + i]);
                    visited.add(tiles[currentTile.getX()][currentTile.getY() + i]);
                }
            }

            //add to the queue if the top/down cells are valid
            for (int i = -1; i < 2; i += 2) {
                if (currentTile.getX() + i >= 0 && currentTile.getX() + i <= tiles.length) {
                    queue.add(tiles[currentTile.getX() + i][currentTile.getY()]);
                    visited.add(tiles[currentTile.getX() + i][currentTile.getY()]);
                }
            }
        }

        return null;
    }


}
