import java.util.*;

/**
 * Player represents the user player and their functions in playing the game
 */
public class Player {

    //Player Attributes
    private String name;
    private boolean gameOver;

    //Player Associations
    private TokenChar tokenChar;
    private Game game;
    private Room currentRoom;
    private HashSet<Card> cards = new HashSet<>();              // the player's hand
    Map<String, HashSet<Card>> knownCards = new HashMap<>();    // all the card the player has found during the game


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
    }

    /**
     * Returns the name of this card.
     *
     * @return String -- name of this card.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the gameOver state to true.
     */
    public void setGameOver() {
        gameOver = true;
    }

    /**
     * Gets this player's state in the game (active or eliminated/game over).
     *
     * @return boolean -- true if Player has been eliminated. Otherwise, false.
     */
    public boolean getGameOver() {
        return gameOver;
    }

    /**
     * Gets the character token associated to this player.
     *
     * @return TokenChar -- this player's character token.
     */
    public TokenChar getToken() {
        return tokenChar;
    }

    /**
     * Gets the current room this Player is in.
     *
     * @return Room -- the current room this Player is in.
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Executes the suggestion process for this Player by asking players if they can refute a matching card.
     * Known cards are stored for future suggestions/accusations.
     *
     * @param character -- the Character card suggested by this Player.
     * @param room      -- the Room card suggested by this Player.
     * @param weapon    -- the Weapon card suggested by this Player.
     * @param other     -- the other player this player is suggesting to.
     * @return boolean -- true if a matching card is refuted. Otherwise, false.
     */
    public boolean suggest(CardChar character, CardRoom room, CardWeapon weapon, Player other) {
        Card card = other.refute(character, room, weapon, other);
        if (card != null) {
            knownCards.get(card.getType()).add(card);
            return true;
        }
        return false;
    }

    /**
     * Displays all the cards this Player has and has not found in the game.
     * Known cards are marked with an "X"
     */
    public void displayCards() {
        //convert all cards into a map
        HashMap<String, Card> charCards = new HashMap<>(game.charCards);
        HashMap<String, Card> weaponCards = new HashMap<>(game.weaponCards);
        HashMap<String, Card> roomCards = new HashMap<>(game.roomCards);
        HashMap<String, Card> allCard = new HashMap<>();
        int colSize = 3;

        String[][] printArray = new String[roomCards.size()][colSize]; //for printing everything in column
        makeArray(charCards, 0, printArray, allCard);
        makeArray(roomCards, 1, printArray, allCard);
        makeArray(weaponCards, 2, printArray, allCard);


        System.out.println(String.format("\n%-20s%-7s%15s%-20s%-7s%15s%-20s%-7s", "CHARACTER", "FOUND", "", "ROOM", "FOUND", "", "WEAPON", "FOUND"));
        for (int row = 0; row < roomCards.size(); row++) {
            for (int col = 0; col < colSize; col++) {
                String key = printArray[row][col];
                if (key == null) {
                    System.out.print(String.format("%-20s%-7s%15s", "", "", ""));
                    continue;
                }
                Card card = allCard.get(key);
                System.out.print(String.format("%-20s%-7s%15s",
                        card.getNameOfCard(),
                        knownCards.get(card.getType()).contains(card) ? "  X" : "", ""));
            }
            System.out.println();

        }
    }

    /**
     * A helper method to print the known cards in columns. @Phoebe TO CHECK !
     *
     * @param map        -- a map containing one of the three card categories (character, room, weapon).
     * @param col        -- the current column to print.
     * @param printArray -- the array containing the structured data to be printed.
     * @param allCard    -- all three card categories combined.
     */
    public void makeArray(HashMap<String, Card> map, int col, String[][] printArray, HashMap<String, Card> allCard) {
        allCard.putAll(map);
        int row = 0;
        for (String charKey : map.keySet()) {
            printArray[row][col] = charKey;
            row++;
        }
    }


    /**
     * After making a suggestion, the player being suggested to will refute a matching card.
     * If the player has exactly one matching card, it is automatically refuted.
     * If a player has multiple matching cards, they will be prompted to select one to refute.
     * If no matching cards are found, the suggestion passes on to the next player.
     *
     * @param character -- the suggested Character card.
     * @param room      -- the suggested Room card.
     * @param weapon    -- the suggested Weapon card.
     * @param other     -- the player being asked to refute.
     * @return Card -- the matching card the player has refuted. Null is returned if no matching card is found.
     */
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

            //keeps asking user until a valid index is inputted
            while (true) {
                try {
                    Scanner scan = new Scanner(System.in);
                    System.out.println(other.getName() + " these are your options: " + options.toString() + "\nWhat would you like to refute ? Please select an index [1 - " + options.size() + "] : ");
                    Card card = options.get(scan.nextInt() - 1);
                    return card;
                } catch (Exception e) {
                    System.out.println("Invalid input please enter a valid index.\n");
                }
            }
        }

    }

    /**
     * Checks if this player made a correct accusation by checking if all three accused
     * cards match the cards in the winning deck.
     *
     * @param character -- the accused Character card.
     * @param room      -- the accused Room card.
     * @param weapon    -- the accused Weapon card.
     * @return boolean -- true if this player has made a correct accusation. Otherwise, false.
     */
    public boolean accuse(CardChar character, CardRoom room, CardWeapon weapon) {
        return game.getWinningDeck().contains(character) && game.getWinningDeck().contains(room) && game.getWinningDeck().contains(weapon);
    }

    /**
     * Moves this Player and their character token to a new tile.
     *
     * @param tile -- the new tile to be moved to.
     */
    public void move(Tile tile) {
        if (tile instanceof Room) {
            currentRoom = (Room) tile;
            tokenChar.room = (Room) tile;
            Room room = (Room) tile;
            room.addOccupant(tokenChar);
            System.out.println(this.getName() + " is now in : " + currentRoom.getName());
        }
        tokenChar.x = tile.getX();
        tokenChar.y = tile.getY();
        System.out.println("current Position: x: " + tokenChar.x + "  y: " + tokenChar.y);
    }

    /**
     * Moves a Player outside of a Room.
     *
     * @param door -- the door tile the character token will be moved to.
     */
    public void leaveRoom(Door door) {
        currentRoom = null;
        tokenChar.x = door.getX();
        tokenChar.y = door.getY();
    }


    public String toString() {
        return "P";
    }

}