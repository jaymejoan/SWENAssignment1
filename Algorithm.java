import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.Queue;

public class Algorithm {
    public static Cell[][] cells = new Cell[25][100];   //actual board should be [25][24] (0-24, 0-23)
    public ArrayList<Cell> rooms = new ArrayList<>();   //The list of rooms
    public static HashSet<Cell> movementRange = new HashSet<>();
    public static Cell closestTile = new Cell(0, 0);


    public static void main(String... args) {
        Algorithm alg = new Algorithm();

        int die1 = (int) Math.ceil(Math.random() * 6);
        int die2 = (int) Math.ceil(Math.random() * 6);
        System.out.println("Total dice: " + (die1 + die2));

        alg.reset();

        cells[12][50].player = true;

        movementRange = alg.DF(12, 50, die1 + die2, new HashSet<Cell>());
        closestTile = alg.BF(new HashSet<>(), cells[10][54], movementRange);


        alg.print();
    }

    /**
     * Change a HashSet to a HashMap
     *
     * @param visitedCells
     * @return a hashmap
     */
    public HashMap<Integer, Cell> asHashMap(HashSet<Cell> visitedCells) {

        HashMap<Integer, Cell> hashMap = new HashMap<>();
        int i = 0;
        for (Cell cell : visitedCells) {
            if (rooms.contains(cell)) {
                hashMap.put(i++, cell);
            }
        }

        return hashMap;
    }

    /**
     * Reset the board so that the next player can move
     */
    public void reset() {
        for (int row = 0; row < 25; row++) {
            for (int col = 0; col < 100; col++) {
                cells[row][col] = new Cell(row, col);
            }
        }

        for (int i = 0; i < 3; i++) {
            cells[9 + i][52].obstacle = true;
        }

        for (int i = 0; i < 4; i++) {
            cells[12][52 + i].obstacle = true;
        }

        cells[15][50].obstacle = true;
        cells[16][50].obstacle = true;
        cells[17][50].obstacle = true;

        cells[10][54].room = true;  //The door should be the reference to the room, and if the player has a movement of
        //more than one, the player will be moved to the room.
        cells[10][54].name = "The Backyard";

        rooms.add(cells[10][54]);
    }


    /**
     * Find the range of movement given a dice roll of the player.
     * Returns the rooms that can be visited if it is within the range of movement.
     *
     * @param row   -- The y coordinates
     * @param col   -- The x coordinates
     * @param dice  -- The dice roll
     * @param visited   -- The range of movement of the player
     */
    public HashSet<Cell> DF(int row, int col, int dice, HashSet<Cell> visited) {
        if (dice > -1 && !cells[row][col].obstacle) {  //Check that the player is able to move and that the current tile has
            visited.add(cells[row][col]);                       //not been visited

            //Move in all directions
            if (row - 1 >= 0) DF(row - 1, col, dice - 1, visited);
            if (row + 1 < cells.length) DF(row + 1, col, dice - 1, visited);
            if (col - 1 >= 0) DF(row, col - 1, dice - 1, visited);
            if (col + 1 < cells[0].length) DF(row, col + 1, dice - 1, visited);
        }

        return visited;
    }


    /**
     * Breadth first search the fastest route back to the edge of a player's movement
     *
     * @param visited
     * @param room
     */
    public Cell BF(HashSet<Cell> visited, Cell room, HashSet<Cell> movementRange) {
        Queue<Cell> queue = new LinkedList();
        queue.add(room);
        visited.add(room);

        while (!queue.isEmpty()) {
            Cell currentCell = queue.remove();

            if (movementRange.contains(currentCell)) return currentCell;    //Found the closest tile of the player's movement

            if(currentCell.obstacle) continue;  //Do not find the neighbours of an obstacle

            //add to the queue if the left/right cells are valid
            for (int i = -1; i < 2; i += 2) {
                if (currentCell.x + i >= 0 && currentCell.x + i <= cells[0].length) {
                    queue.add(cells[currentCell.y][currentCell.x + i]);
                    visited.add(cells[currentCell.y][currentCell.x + i]);
                }
            }

            //add to the queue if the top/down cells are valid
            for (int i = -1; i < 2; i += 2) {
                if (currentCell.y + i >= 0 && currentCell.y + i <= cells.length) {
                    queue.add(cells[currentCell.y + i][currentCell.x]);
                    visited.add(cells[currentCell.y + i][currentCell.x]);
                }
            }
        }

        return null;
    }

    /**
     * Print the updated board
     */
    public void print() {
        boolean allRoomsVisited = true;

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (movementRange.contains(cells[i][j])) {
                    if (cells[i][j].player) System.out.print("P");
                    else if (cells[i][j] == closestTile) System.out.print("C");
                    else System.out.print("X");

                } else {

                    System.out.print(cells[i][j].toString());
                }
            }
            System.out.println();
        }

        System.out.println("You are able to reach: ");
        for (Cell room : rooms) {
            if (room.visited) System.out.println(room.name);
            else allRoomsVisited = false;
        }
        if (!allRoomsVisited) {
            System.out.println("You can get closer to: ");
            for (Cell room : rooms) {
                if (!room.visited) {
                    System.out.println(room.name);
                }
            }
        }
    }
}
