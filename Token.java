import java.awt.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;


/**
 * Tokens are a physical representation of how a scenario is played out when a player suggests the murder circumstances.
 */
public class Token {
    public String name;
    int x, y;
    Board board;
    Room room;

    public Token(String name, int x, int y, Board board) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.board = board;
    }

    public void move(int x, int y, Room room) {
        this.x = x;
        this.y = y;
        this.room = room;
    }

    public void tokenGUI(Graphics2D g, int x, int y) {}
}

/**
 * TokenWeapon represents the physical weapons on the board.
 * When mentioned in a suggestion, the token is moved to the suggested room.
 */
class TokenWeapon extends Token {
    public TokenWeapon(String name, int x, int y, Board board) {
        super(name, x, y, board);
    }
}

/**
 * The TokenChar is the player's piece on the board.
 * The human player will control the token character by moving it around the board.
 */
class TokenChar extends Token {

    Color c;

    public TokenChar(String name, int x, int y, Board board) {
        super(name, x, y, board);
        switch(name) {
            case "Miss Scarlett":
                c = new Color(150,0,0);
                break;
            case "Mr. Green":
                c = new Color(0, 150, 0);
                break;
            case "Colonel Mustard":
                c = new Color(149, 150, 36);
                break;
            case "Professor Plum":
                c = new Color(62, 9, 100);
                break;
            case "Mrs. Peacock":
                c = new Color(0, 1, 90);
                break;
            case "Mrs. White":
                c = new Color(255, 255,255);
        }
    }


    /**
     * Find the range of movement given a dice roll of the player.
     * Returns the rooms that can be visited if it is within the range of movement.
     *
     * @param row     -- The y coordinates
     * @param col     -- The x coordinates
     * @param dice    -- The dice roll
     * @param visited -- The range of movement of the player
     */
    public HashSet<Tile> getVisitableTiles(int row, int col, int dice, HashSet<Tile> visited) {

        //Check that the player is able to move and that the current tile has
        if (dice > -1 && (board.board[row][col] instanceof Hallway || board.board[row][col] instanceof Door)) {

            visited.add(board.board[row][col]);
            if (board.board[row][col] instanceof Hallway) {
                ((Hallway) board.board[row][col]).visited = true;
            }//not been visited

            if (board.board[row][col] instanceof Door && dice > 0) {
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

    /**
     * Return the fastest route from the player's visitable movement to the closest door of a given room.
     *
     * @param tile          -- The end of the route
     * @param movementRange -- The player's movement range
     * @return the edge of the player's movement closest to the door
     */
    public Tile unreachableMove(Tile tile, HashSet<Tile> movementRange) {
        Queue<Tile> queue = new LinkedList<>();
        HashSet<Tile> visited = new HashSet<>();

        queue.add(tile);
        visited.add(tile);

        while (!queue.isEmpty()) {
            Tile currentTile = queue.remove();
            if (movementRange.contains(currentTile) && !(currentTile instanceof Room)) {
                return currentTile;
            }    //Found the closest tile of the player's movement that is not a room


            if (currentTile instanceof Blocked || currentTile instanceof Room)
                continue;  //Do not find the neighbours of an obstacle

            Tile nextTile;

            //add to the queue if the left/right cells are valid
            for (int i = -1; i < 2; i += 2) {
                if (currentTile.getX() + i >= 0 && currentTile.getX() + i < board.board[0].length) {
                    nextTile = board.board[currentTile.getY()][currentTile.getX() + i];
                    if (!visited.contains(nextTile)) {
                        queue.add(nextTile);
                        visited.add(nextTile);
                    }
                }
            }

            //add to the queue if the top/down cells are valid
            for (int i = -1; i < 2; i += 2) {
                if (currentTile.getY() + i >= 0 && currentTile.getY() + i < board.board.length) {
                    nextTile = board.board[currentTile.getY() + i][currentTile.getX()];
                    if (!visited.contains(nextTile)) {
                        queue.add(nextTile);
                        visited.add(nextTile);
                    }
                }
            }
        }

        return null; //This usually means that a bug has occurred because the player should have a minimum movement range of 2.
    }

    public void tokenGUI(Graphics2D g, int x, int y) {
        g.setColor(Color.black);
        g.fillOval(10 + (x * 22), 10 + (y * 22), 20, 20);
        g.setColor(this.c);
        g.fillOval(10 + (x * 22), 10 + (y * 22), 18, 18);

    }

}