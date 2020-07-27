import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Algorithm {
    public static Cell[][] cells = new Cell[25][100];   //actual board should be [25][24] (0-24, 0-23)
    public ArrayList<Cell> rooms = new ArrayList<>();


    public static void main(String... args) {
        Algorithm alg = new Algorithm();

        int die1 = (int) Math.ceil(Math.random() * 6);
        int die2 = (int) Math.ceil(Math.random() * 6);
        System.out.println("Total dice: " + (die1 + die2));

        alg.reset();

        cells[12][50].player = true;

        alg.DF(12,50, 9);
        alg.print();
    }

    public void reset(){
        for (int row = 0; row < 25; row++) {
            for (int col = 0; col < 100; col++) {
                cells[row][col] = new Cell(row, col);
            }
        }

        for(int i = 0; i < 3; i++){
            cells[9 + i][52].obstacle = true;
        }

        for(int i = 0; i < 4; i++){
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



    public void DF(int row, int col, int dice) {

        if(cells[row][col].obstacle) return;

        if (dice > -1) {
            cells[row][col].visited = true;
            if (row - 1 >= 0)                   DF(row - 1, col, dice - 1);
            if (row + 1 < cells.length)         DF(row + 1, col, dice - 1);
            if (col - 1 >= 0)                   DF(row, col - 1, dice - 1);
            if (col + 1 < cells[0].length)      DF(row, col + 1, dice - 1);
        }
    }

    public void BF(PriorityQueue queue, HashSet<Cell> visited, Cell room){



    }

    public void print() {
        boolean allRoomsVisited = true;

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                System.out.print(cells[i][j].toString());
            }
            System.out.println();
        }

        System.out.println("You are able to reach: ");
        for (Cell room : rooms) {
            if (room.visited) System.out.println(room.name);
            else              allRoomsVisited = false;
        }
        if(!allRoomsVisited){
            System.out.println("You can get closer to: ");
            for (Cell room : rooms) {
                if (!room.visited) {
                    System.out.println(room.name);
                }
            }
        }
    }
}
