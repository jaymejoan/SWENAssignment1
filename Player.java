import java.util.*;

// line 8 "model.ump"
// line 140 "model.ump"
public class Player {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Player Attributes
    private String name;
    private boolean gameOver;

    //Player Associations
    private TokenChar tokenChar;
    private Game game;
    private Room currentRoom;
    private HashSet<Card> cards = new HashSet<>();
    Map<String, HashSet<Card>> knownCards = new HashMap<>();

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Player(String playerName, int x, int y, HashSet<Card> playerCards, Game GAME, TokenChar token) {
        tokenChar = token;
        name = playerName;
        gameOver = false;
        game = GAME;
        cards = playerCards;
        currentRoom = null;
        String[] cardsType = {"character", "room", "weapon"};


        for (String type : cardsType) {
            knownCards.put(type, new HashSet<>());
        }
        for (Card card : cards) {
            knownCards.get(card.getType()).add(card);
        }


        //@todo link this player obj back to card
//        tokenChar = new TokenChar(name,0,0); //number of player
        //@todo need to know where the player is going to start might need to add (x,y) in constructor
        //@todo add Game object
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setGameOver(boolean aGameOver) {
        boolean wasSet = false;
        gameOver = aGameOver;
        wasSet = true;
        return wasSet;
    }

    public String getName() {
        return name;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public TokenChar getToken(){
        return tokenChar;
    }

    /* Code from template association_GetOne */
//    public TokenChar getTokenChar()
//    {
//        return tokenChar;
//    }
    /* Code from template association_GetMany */
    public Game getGame() {
        return game;
    }


    /* Code from template association_GetOne */
    public HashSet<Card> getHand() {
        return cards;
    }

    /**
     * Checks if this player's hand contains any of the passed in cards.
     */
    public boolean checkHand(Card character, Card room, Card weapon) {
        for (Card c : cards) {
            if (c.getNameOfCard().toLowerCase().equals(character.getNameOfCard().toLowerCase())
                    || c.getNameOfCard().toLowerCase().equals(room.getNameOfCard().toLowerCase())
                    || c.getNameOfCard().toLowerCase().equals(weapon.getNameOfCard().toLowerCase()))
                return true;

        }

        return false;
    }

    // line 14 "model.ump"
    public boolean suggest(CardChar character, CardRoom room, CardWeapon weapon, Player other) {
        Card card = other.refute(character, room, weapon, other);
        if (card != null) {
            knownCards.get(card.getType()).add(card);
//            System.out.println(other.getName() + " has refuted: " + card.getNameOfCard());
            return true;
        }
        return false;
    }

    public void displayCards() {
        //convert to map card
        HashMap<String, Card> charCards = new HashMap<>(game.charCards);
        HashMap<String, Card> weaponCards = new HashMap<>(game.weaponCards);
        HashMap<String, Card> roomCards = new HashMap<>(game.roomCards);
        HashMap<String, Card> allCard = new HashMap<>();
        int colSize = 3;

        String[][] printArray = new String[roomCards.size()][colSize]; //for printing everything in column
        makeArray(charCards,0,printArray,allCard);
        makeArray(roomCards,1,printArray, allCard);
        makeArray(weaponCards,2,printArray, allCard);


        System.out.println(String.format("\n%-20s%-7s%15s%-20s%-7s%15s%-20s%-7s","CHARACTER","FOUND","","ROOM","FOUND","","WEAPON","FOUND"));
        for (int row = 0; row < roomCards.size(); row++){
            for(int col = 0; col < colSize; col++){
                String key = printArray[row][col];
                if(key == null){
                    System.out.print(String.format("%-20s%-7s%15s","","",""));
                    continue;
                }
                Card card = allCard.get(key);
                System.out.print(String.format("%-20s%-7s%15s",
                        card.getNameOfCard(),
                        knownCards.get(card.getType()).contains(card)?"  X":"",""));
            }
            System.out.println();

        }
    }
    public void makeArray(HashMap<String, Card> map, int col, String[][] printArray, HashMap<String, Card> allCard){
        allCard.putAll(map);
        int row = 0;
        for(String charKey : map.keySet()){
            printArray[row][col] = charKey;
            row++;
        }
    }


    // line 17 "model.ump"
    public Card refute(CardChar character, CardRoom room, CardWeapon weapon, Player other) {
        List<Card> options = new ArrayList<>();
        if (other.cards.contains(character)) {
            options.add(character);
        }
        if (other.cards.contains(room)) {
            options.add(room);
        }
        if (other.cards.contains(weapon)) {
            options.add(weapon);
        }

        int size = options.size();
        if (size == 1) {            // other player has exactly one match
            System.out.println(other.getName() + " has refuted " + options.get(0).getNameOfCard() + ".");
            return options.get(0);
        } else if (size == 0) {     // other players has no matches
            System.out.println(other.getName() + " does not have any matching cards.");
            return null;
        } else {                    // other player has multiple matches and must choose one to refute

            //keep asking the user until there's a valid index
            while (true) {
                try {
                    Scanner scan = new Scanner(System.in);
                    System.out.println(other.getName() + " these are your options: " + options.toString() + "\nWhat would you like to refute ? Please select an index [ 1 , 2 , 3 ] : ");
                    Card card = options.get(scan.nextInt() - 1);
//                    scan.close();
                    return card;
                } catch (Exception e) {
                    System.out.println("Invalid input please enter a valid index.\n");
                }
            }
        }

    }

    // line 20 "model.ump"
    public boolean accuse(CardChar character, CardRoom room, CardWeapon weapon) {
        return game.getWinningDeck().contains(character) && game.getWinningDeck().contains(room) && game.getWinningDeck().contains(weapon);
    }

    // line 23 "model.ump"
    public void move(Tile tile) {
        if(tile instanceof Room){
            currentRoom = (Room) tile;
            System.out.println(this.getName() + " is now in : " + currentRoom.getName());
        }
        tokenChar.x = tile.getX();
        tokenChar.y = tile.getY();
        System.out.println("current Position: x: " + tokenChar.x + "  y: " + tokenChar.y);
    }

    public void leaveRoom(Door door){
        currentRoom = null;
        tokenChar.x = door.getX();
        tokenChar.y = door.getY();
    }

    public void setGameOver() {
        gameOver = true;
    }

    public Room getCurrentRoom(){
        return currentRoom;
    }



    public String toString() {
        return "P";
    }

}