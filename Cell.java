public class Cell {
    public boolean visited = false, obstacle = false, player = false, room = false;
    public int x, y;
    String name;    //The name of the cell - Mainly used as a centre reference point for the room names

    public Cell(int y, int x){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        if(obstacle) return "O";
        else if(player) return "P";
        else if(room)   return "R";
        return !visited ? "_" : "X";
    }
}
