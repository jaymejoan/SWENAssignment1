import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Board {
    ArrayList<Integer> boardSetUp;
    HashSet<Room> rooms = new HashSet<>();
    Tile[][] board = new Tile[25][24]; //0-24[25] by 0-23[24]

    public Board() {
        // Setting board up as integer for tile type, (integer so it's shorthanded)
        // and then will go through this list and construct board with tiles based
        // on integer.

        // 1 = Blocked
        // 2 = Hallway
        // 3 = Room
        // 4 = Door
        boardSetUp = new ArrayList<Integer>(
                Arrays.asList(1,1,1,1,1,1,1,1,1,2,1,1,1,1,2,1,1,1,1,1,1,1,1,1,
                              3,3,3,3,3,3,1,2,2,2,3,3,3,3,2,2,2,1,3,3,3,3,3,3,
                              3,3,3,3,3,3,2,2,3,3,3,3,3,3,3,3,2,2,3,3,3,3,3,3,
                              3,3,3,3,3,3,2,2,3,3,3,3,3,3,3,3,2,2,3,3,3,3,3,3,
                              3,3,3,3,3,3,2,2,3,3,3,3,3,3,3,3,2,2,3,3,3,3,3,3,
                              3,3,3,3,3,3,2,4,3,3,3,3,3,3,3,3,4,2,4,3,3,3,3,1,
                              1,3,3,3,3,3,2,2,3,3,3,3,3,3,3,3,2,2,2,2,2,2,2,2,
                              2,2,2,2,4,2,2,2,3,3,3,3,3,3,3,3,2,2,2,2,2,2,2,1,
                              1,2,2,2,2,2,2,2,2,4,2,2,2,2,4,2,2,2,3,3,3,3,3,3,
                              3,3,3,3,3,2,2,2,2,2,2,2,2,2,2,2,2,4,3,3,3,3,3,3,
                              3,3,3,3,3,3,3,3,2,2,1,1,1,1,1,2,2,2,3,3,3,3,3,3,
                              3,3,3,3,3,3,3,3,2,2,1,1,1,1,1,2,2,2,3,3,3,3,3,3,
                              3,3,3,3,3,3,3,3,4,2,1,1,1,1,1,2,2,2,3,3,3,3,3,3,
                              3,3,3,3,3,3,3,3,2,2,1,1,1,1,1,2,2,2,2,2,4,2,4,1,
                              3,3,3,3,3,3,3,3,2,2,1,1,1,1,1,2,2,2,3,3,3,3,3,1,
                              3,3,3,3,3,3,3,3,2,2,1,1,1,1,1,2,2,3,3,3,3,3,3,3,
                              1,2,2,2,2,2,4,2,2,2,1,1,1,1,1,2,4,3,3,3,3,3,3,3,
                              2,2,2,2,2,2,2,2,2,2,2,4,4,2,2,2,2,3,3,3,3,3,3,3,
                              1,2,2,2,2,2,4,2,2,3,3,3,3,3,3,2,2,2,3,3,3,3,3,1,
                              3,3,3,3,3,3,3,2,2,3,3,3,3,3,3,2,2,2,2,2,2,2,2,2,
                              3,3,3,3,3,3,3,2,2,3,3,3,3,3,3,4,2,4,2,2,2,2,2,1,
                              3,3,3,3,3,3,3,2,2,3,3,3,3,3,3,2,2,3,3,3,3,3,3,3,
                              3,3,3,3,3,3,3,2,2,3,3,3,3,3,3,2,2,3,3,3,3,3,3,3,
                              3,3,3,3,3,3,3,2,2,3,3,3,3,3,3,2,2,3,3,3,3,3,3,3,
                              3,3,3,3,3,3,1,2,1,3,3,3,3,3,3,1,2,1,3,3,3,3,3,3)
        );

        // Set up the board of Tile objects based on the integer shorthand
        int x = 0;
        int y = 0;
        Tile newTile = null;
        for(int i = 0; i < 600; i++) {
            int tileType = boardSetUp.get(i);
            switch (tileType) {
                case 1:
                    newTile = new Blocked(x, y);
                    break;
                case 2:
                    newTile = new Hallway(x, y);
                    break;
                case 3:
                    newTile = new Room(x, y);
                    break;
                case 4:
                    newTile = new Door(x, y);
                    break;
            }

            board[y][x] = newTile;

            if (x == 23) { x = 0; y++;}
            else { x++;}
        }

        // Designating Room Tile that represents whole room:

        // Kitchen [4][2]
        // Door 1: [7][4]
        board[4][2].setName("Kitchen");
        board[4][2].addDoor(1, (Door)board[7][4]);

        // Ballroom [5][11]
        // Door 1: [5][7]
        // Door 2: [8][9]
        // Door 3: [8][14]
        // Door 4: [5][16]
        board[5][11].setName("Ballroom");
        board[5][11].addDoor(1, (Door)board[5][7]);
        board[5][11].addDoor(2, (Door)board[8][9]);
        board[5][11].addDoor(3, (Door)board[8][14]);
        board[5][11].addDoor(4, (Door)board[5][16]);
        rooms.add((Room)board[5][11]);

        // Conservatory [3][20]
        // Door 1: [5][18]
        board[3][20].setName("Conservatory");
        board[3][20].addDoor(1, (Door)board[5][18]);
        rooms.add((Room)board[3][20]);

        // Dining Room [12][3]
        // Door 1: [12][8]
        // Door 2: [16][6]
        board[12][3].setName("Dining Room");
        board[12][3].addDoor(1, (Door)board[12][8]);
        board[12][3].addDoor(2, (Door)board[16][6]);
        rooms.add((Room)board[12][3]);

        // Billiard Room [10][20]
        // Door 1: [9][17]
        // Door 2: [13][22]
        board[10][20].setName("Billiard Room");
        board[10][20].addDoor(1, (Door)board[9][17]);
        board[10][20].addDoor(2, (Door)board[12][8]);
        rooms.add((Room)board[10][20]);

        // Library [16][20]
        // Door 1: [13][20]
        // Door 2: [16][16]
        board[16][20].setName("Library");
        board[16][20].addDoor(1, (Door)board[13][20]);
        board[16][20].addDoor(2, (Door)board[16][16]);
        rooms.add((Room)board[16][20]);

        // Lounge [22][3]
        // Door 1: [18][6]
        board[22][3].setName("Lounge");
        board[22][3].addDoor(1, (Door)board[18][6]);
        rooms.add((Room)board[22][3]);

        // Hall [21][11]
        // Door 1: [17][11]
        // Door 2: [17][12]
        // Door 3: [20][15]
        board[21][11].setName("Hall");
        board[21][11].addDoor(1, (Door)board[13][20]);
        board[21][11].addDoor(1, (Door)board[13][20]);
        board[21][11].addDoor(1, (Door)board[13][20]);
        rooms.add((Room)board[21][11]);


        // Study [23][20]
        // Door 1: [20][17]
        board[23][20].setName("Study");
        board[23][20].addDoor(1, (Door)board[20][17]);
        rooms.add((Room)board[23][20]);

        int tes = 1;
        System.out.println("It works");
    }

    public static void main(String... args) {
        Board b = new Board();
    }
}


