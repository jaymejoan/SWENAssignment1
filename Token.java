public class Token {
    public String name;
    int x, y;
    Room room;

    public Token(String name, int x, int y){
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public void move(int x, int y, Room room){
        this.x = x;
        this.y = y;
        this.room = room;
    }
}


class TokenWeapon extends Token {
    public TokenWeapon(String name, int x, int y) {
        super(name, x, y);
    }
}

class TokenChar extends Token {

    public TokenChar(String name, int x, int y) {
        super(name, x, y);
    }
}
