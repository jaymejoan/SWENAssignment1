import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The Game Class initialises and runs the game sequence.
 */
public class Game {

    private String[] cardNames = new String[]{"Miss Scarlett", "Mr. Green", "Colonel Mustard", "Professor Plum", "Mrs. Peacock", "Mrs. White",
            "Candlestick", "Dagger", "Lead Pipe", "Revolver", "Rope", "Spanner",
            "Kitchen", "Ballroom", "Conservatory", "Dining Room", "Billiard Room", "Library", "Lounge", "Hall", "Study"};


    private final String[] playerNames = new String[]{"Miss Scarlett (P1)", "Mr. Green (P2)", "Colonel Mustard (P3)", "Professor Plum (P4)", "Mrs. Peacock (P5)", "Mrs. White (P6)"};
    private final ArrayList<Card> winningDeck = new ArrayList<>();                    // deck containing the three winning cards
    private boolean gameWon = false;
    private int currentPlayer = 0, nextPlayer, totalPlayers, eliminatedPlayers = 0;
    //HashSet<Room> rooms = new HashSet<>();
    Board board;
    //Tile[][] tiles = new Tile[25][24]; //0-24[25] by 0-23[24]
    //private ArrayList<Integer> boardSetUp;

    HashMap<String, CardChar> charCards = new HashMap<>();
    HashMap<String, CardWeapon> weaponCards = new HashMap<>();
    HashMap<String, CardRoom> roomCards = new HashMap<>();

    HashMap<String, TokenChar> charTokenMap = new HashMap<>();
    HashMap<String, TokenWeapon> weaponTokenMap = new HashMap<>();

    ArrayList<Card> fullDeck = new ArrayList<>();                       // deck containing the remaining 18 cards
    ArrayList<Player> players = new ArrayList<>();

    /**
     * Players, Cards and Tiles are constructed here
     * (as they only need to be initialised once).
     *
     * @param numOfPlayers -- number of players in this game.
     */
    public Game(int numOfPlayers) {
        board = new Board();
        totalPlayers = numOfPlayers;
        int[] xStart = {9, 14, 23, 23, 7, 0};
        int[] yStart = {0, 0, 6, 19, 24, 17};

        // initialise Character cards and tokens
        for (int i = 0; i <= 5; i++) {
            charCards.put(cardNames[i].toLowerCase(), new CardChar(cardNames[i]));
            charTokenMap.put(cardNames[i].toLowerCase(), new TokenChar(cardNames[i], xStart[i], yStart[i], board));
        }

        // initialise Weapon cards and tokens
        for (int i = 6; i <= 11; i++) {
            weaponCards.put(cardNames[i].toLowerCase(), new CardWeapon(cardNames[i]));
            weaponTokenMap.put(cardNames[i].toLowerCase(), new TokenWeapon(cardNames[i], -1, -1, board));
        }

        // initialise Room cards
        for (int i = 12; i < cardNames.length; i++)
            roomCards.put(cardNames[i].toLowerCase(), new CardRoom(cardNames[i]));

        createWinningDeck();

        // add non-winning cards to deck
        fullDeck.addAll(charCards.values());
        fullDeck.addAll(weaponCards.values());
        fullDeck.addAll(roomCards.values());

        ArrayList<HashSet<Card>> hand = dealCards(numOfPlayers);        // List of each player's hand of cards


        // initialise players
        for (int i = 0; i < numOfPlayers; i++) {
            players.add(new Player(playerNames[i], 0, 0, hand.get(i), this, charTokenMap.get(cardNames[i].toLowerCase())));     //TODO: double check fields for Board... what should x and y be ? do you want to create a list of starting positions and iterate through?
            System.out.println("player" + i + " deck: " + hand.get(i).toString() + " tokenChar: " + cardNames[i]);
        }

        // add winning cards back into original deck (needed for rest of gameplay)
        for (Card card : winningDeck) {
            if (card instanceof CardChar)
                charCards.put(card.getNameOfCard().toLowerCase(), (CardChar) card);
            else if (card instanceof CardWeapon)
                weaponCards.put(card.getNameOfCard().toLowerCase(), (CardWeapon) card);
            else if (card instanceof CardRoom)
                roomCards.put(card.getNameOfCard().toLowerCase(), (CardRoom) card);
        }


        for (int i = 0; i < cardNames.length; i++)
            cardNames[i] = cardNames[i].toLowerCase();

    }


    /**
     * Selects three random cards (one from each category) to be the winning deck.
     * These will be stored in the centre of the board and will only be revealed
     * if a Player correctly guesses all three cards.
     */
    public void createWinningDeck() {
        // select random winning cards
        int randomChar = ThreadLocalRandom.current().nextInt(0, 6);
        int randomWeapon = ThreadLocalRandom.current().nextInt(6, 12);
        int randomRoom = ThreadLocalRandom.current().nextInt(12, 21);

        // add cards to winning deck
        winningDeck.add(charCards.get(cardNames[randomChar].toLowerCase()));
        winningDeck.add(roomCards.get(cardNames[randomRoom].toLowerCase()));
        winningDeck.add(weaponCards.get(cardNames[randomWeapon].toLowerCase()));

        // remove winning cards from pack
        charCards.remove(cardNames[randomChar].toLowerCase());
        weaponCards.remove(cardNames[randomWeapon].toLowerCase());
        roomCards.remove(cardNames[randomRoom].toLowerCase());

        // testing
        for (Card card : winningDeck)
            System.out.println("type: " + card.getType() + " name: " + card.getNameOfCard());

    }

    /**
     * Evenly deals the remaining cards among the players.
     *
     * @param numOfPlayers -- the total number of players in the game.
     * @return ArrayList<HashSet < Card>> -- a List containing the deck of cards in each Player's hand.
     */
    public ArrayList<HashSet<Card>> dealCards(int numOfPlayers) {

        Collections.shuffle(fullDeck);
        ArrayList<HashSet<Card>> decks = new ArrayList<>();
        int count = 0;

        // initialize a deck of cards for each Player
        for (int i = 0; i < numOfPlayers; i++)
            decks.add(new HashSet<>());

        // distribute cards
        for (Card card : fullDeck) {
            count = count == numOfPlayers ? 0 : count;
            decks.get(count).add(card);
            count++;
        }

        System.out.println("Cards have been handed out... Let's start!");

        return decks;
    }

    /**
     * Resets the game to its initial starting state.
     */
    public void resetGame() {
        charCards.clear();
        weaponCards.clear();
        roomCards.clear();
        winningDeck.clear();
        fullDeck.clear();
        players.clear();
        gameWon = false;
    }

    /**
     * Sets gameWon to true if this Player has won the game.
     *
     * @return boolean -- true if Player has won game. Otherwise, false.
     */
    public void setGameWon() {
        gameWon = true;
    }

    // TODO: should we include a gameLost in the event NO players correctly guess the winning deck ?

    /**
     * This method rolls the dice for this Player.
     *
     * @return -- the number of steps this Player can move.
     */
    public Integer rollDice() {
        return (int) Math.ceil(Math.random() * 6) + (int) Math.ceil(Math.random() * 6);
    }

    /**
     * Finds the closest room this Player can reach.
     *
     * @param range -- the range of Tiles this Player can reach
     * @param scan  -- the current Scanner in use.
     * @return Room -- the Room this Player can reach.
     */
    public Tile getReachableRoom(HashSet<Tile> range, Scanner scan, Player p1) {
        ArrayList<Room> reachableRooms = new ArrayList<>();
        ArrayList<Room> unreachableRooms = new ArrayList<>(board.rooms);

        board.drawBoard(p1.getToken().x, p1.getToken().y);

        System.out.print("\nThese are the rooms you can reach: [");
        for (Room room : board.rooms) {
            //System.out.println(room.getName());
            if (range.contains(room)) {
                reachableRooms.add(room);
                System.out.print(room.getName() + ", ");
            }
        }
        System.out.println("]");


        //keep asking the user until there's a valid index
        while (true) {
            try {

                int num;
                if (!reachableRooms.isEmpty()) {
                    System.out.print("\naWhat room would you like to move to? [1 - " + reachableRooms.size() + "] or enter [0] for none of these: ");

//                    if (!scan.hasNextInt()) throw new Exception();

                    num = scan.nextInt();
                    System.out.println("'" + num + "'");

                    if (num < 0 || num > reachableRooms.size()) throw new Exception();

                    // move into reachable room
                    if (num != 0) {
                        p1.move(reachableRooms.get(num - 1));
                        return reachableRooms.get(num - 1);
                    }

                    unreachableRooms.removeAll(reachableRooms);
                }

                //move towards a room
                System.out.print("\nbWhat room would you like to move towards? [ ");
                for (Room room : unreachableRooms) {
                    System.out.print(room.getName() + ", ");
                }
                System.out.print("] enter [1 - " + unreachableRooms.size() + "]:");

                if (!scan.hasNextInt()) throw new Exception();

                num = scan.nextInt();
                System.out.println("'" + num + "'");


                if (num < 0 || num > unreachableRooms.size()) throw new Exception();

//                Room moveTo = unreachableRooms.get(scan.nextInt()-1);
                Room moveTo = unreachableRooms.get(num - 1);
                System.out.println("you choose to move towards " + moveTo.getName());
                System.out.println("moveto: " + moveTo + " position: " + p1.getToken().y + " " + p1.getToken().x);

                Door door = closetDoor(moveTo, p1.getToken());
                System.out.println("Door: " + door);
                Tile tile = p1.getToken().BF(new HashSet<>(), door, range);
                System.out.println("Tiles: " + tile.getX() + "  " + tile.getY());
                p1.move(tile);
                return tile;
            } catch (Exception e) {
//                e.printStackTrace();

                System.out.println("Invalid input please enter a valid index.\n");
                scan.next();
            }
        }
    }

    public Door closetDoor(Room room, TokenChar tokenChar) {

        if (room.getDoors().size() <= 1) {
            return room.getDoors().get(1);
        } else {
            double shortestLength = Double.MAX_VALUE;
            Door closetDoor = null;
            for (Door door : room.getDoors().values()) {
                double newLength = Math.sqrt((door.getX() - tokenChar.x) * (door.getX() - tokenChar.x) + (door.getY() - tokenChar.y) * (door.getY() - tokenChar.y));
                if (newLength < shortestLength) {
                    shortestLength = newLength;
                    closetDoor = door;
                }
            }
            return closetDoor;
        }

    }


    /**
     * Executes the game.
     */
    public void runGame() {
        Scanner scan = new Scanner(System.in);

        while (!gameWon) {
            currentPlayer = currentPlayer >= players.size() ? 0 : currentPlayer;
            nextPlayer = currentPlayer;

            int dice = rollDice();
            Player p1 = players.get(currentPlayer);
            TokenChar tokenChar = p1.getToken();
            Door door = null;


            // game over state
            if (eliminatedPlayers == players.size()) {
                System.out.println("No one solved the mystery... GAME OVER!");
                displayAccusation(new CardChar(""), new CardRoom(""), new CardWeapon(""));
                break;
            }

            // skips players not in game
            if (p1.getGameOver()) {
                currentPlayer++;
                continue;
            }

            System.out.println("\nIt's " + p1.getName() + "'s turn... you rolled " + dice);
            Room currentRoom = p1.getCurrentRoom();
            System.out.println(currentRoom + " " + p1.getToken().y + " " + p1.getToken().x);

            // asks the user which door to exit
            if (currentRoom != null) {
                while (true) {
                    try {
                        System.out.print("\nYou are in: " + currentRoom.getName());
                        System.out.print("\nWhat door do you wish to exit? [1 - " + currentRoom.getDoors().size() + "] : " + currentRoom.getDoors().toString());
                        door = currentRoom.getDoors().get(scan.nextInt());  //TODO: check this doesnt result in error for invalid inputs
                        p1.leaveRoom(door);
//                    scan.close();
                        break;
                    } catch (Exception e) {

                        System.out.println("Invalid input please enter a valid index.\n");
                    }
                }
            }

            // co-ordinates of this player's character token and the door
            int xPos = tokenChar.x;
            int yPos = tokenChar.y;

            if (door != null) {
                xPos = door.getX();
                yPos = door.getY();
            }

            // moves the player towards/into a room
            HashSet<Tile> tiles = tokenChar.getVisitableTiles(yPos, xPos, dice, new HashSet<Tile>());
            Tile moveToTile = getReachableRoom(tiles, scan, p1);
            board.drawBoard(p1.getToken().x, p1.getToken().y);
            boolean onlyAccuse = false;
            if (moveToTile instanceof Room) {

            } else {
                onlyAccuse = true;
            }

            p1.displayCards();


            // executes suggest or accuse
            while (true) {
                //TODO: if player is not in a room (ie. in hall), skip over ... can they still accuse?
                if (onlyAccuse) {
                    currentPlayer++;
                    break;
                }
                System.out.println("\n" + p1.getName() + " do you want to 'suggest' or 'accuse' ? [suggest / accuse]: ");
                String ans = scan.next();
                if (ans.equalsIgnoreCase("suggest")) {
                    runSuggest(scan, p1, p1.getCurrentRoom());
                    break;
                } else if (ans.equalsIgnoreCase("accuse")) {
                    runAccuse(scan, p1);
                    break;
                } else {
                    System.out.println("Invalid input. Please type in either 'suggest' or 'accuse'.");
                }
            }

            //the out put of this turn
            board.printRoomOccupants();
        }


//        checkNewGame(scan);


    }

    /**
     * Checks if the user wants to start a new game.
     *
     * @param scan -- the current scanner in use.
     */
    public void checkNewGame(Scanner scan) {
        System.out.println("Do you want to play again? [Y / N]: ");
        String input = scan.next();

        if (input.equalsIgnoreCase("Y")) {
            resetGame();
            runGame();
        } else if (input.equalsIgnoreCase("N")) {
        } else
            checkNewGame(scan);
    }

    /**
     * A helper method which returns the user's input.
     *
     * @param question -- the question to be asked.
     * @param scan     -- the current scanner in use.
     * @param cardType -- the type of this card.
     * @return String -- the user input.
     */
    private String getInput(String question, Scanner scan, String cardType) {
        while (true) {
            try {
                scan.useDelimiter("\n");
                System.out.print(question);
                String input = scan.next().toLowerCase();

                if (!(Arrays.asList(cardNames).contains(input)))
                    throw new Exception("Invalid input. Please enter a valid suggestion (make sure spelling is correct).");
                else if (cardType.equals("char") && !charCards.containsKey(input))
                    throw new Exception("Invalid Character card. Please enter a valid suggestion.");
                else if (cardType.equals("room") && !roomCards.containsKey(input))
                    throw new Exception("Invalid Room card. Please enter a valid suggestion.");
                else if (cardType.equals("weapon") && !weaponCards.containsKey(input))
                    throw new Exception("Invalid Weapon card. Please enter a valid suggestion.");

                return input;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * This method allows the user to make a suggestion by selecting one card from each category (character, room, weapon).
     * The room card will automatically be set to the room this Player is currently in (players not in a room can not suggest).
     * The user first suggests to themselves then continues to suggest to other players until a card is refuted.
     * If no matching cards are found, the player can then make an accusation -- TO DO
     *
     * @param scan        -- the current scanner in use.
     * @param p1          -- the current player making the suggestion.
     * @param currentRoom -- the room this player is in.
     */
    public void runSuggest(Scanner scan, Player p1, Room currentRoom) {
        int cardNotFound = 0;           // num of players who don't have any matching cards

        // gets cards user wants to suggest
        String charInput = getInput("\nSuggest a Character : ", scan, "char");
        CardChar character = charCards.get(charInput);
        CardRoom room = roomCards.get(currentRoom.getName().toLowerCase());
        System.out.println("You are in the Room: " + room.getNameOfCard());
        String weaponInput = getInput("Suggest a Weapon : ", scan, "weapon");
        CardWeapon weapon = weaponCards.get(weaponInput);

        //add suggested tokens to the room it's been suggest in
        TokenChar tokenChar = charTokenMap.get(charInput);
        TokenWeapon tokenWeapon = weaponTokenMap.get(weaponInput);

        //remove tokens from the rooms they used to be in
        if (tokenChar.room != null) {
            tokenChar.room.removeOccupant(tokenChar);
        }
        if (tokenWeapon.room != null) {
            tokenWeapon.room.removeOccupant(tokenWeapon);
        }


        tokenChar.room = currentRoom;
        tokenWeapon.room = currentRoom;
        currentRoom.addOccupant(tokenChar);
        currentRoom.addOccupant(tokenWeapon);

        System.out.println(p1.getName() + " suggests to " + p1.getName() + ": '" + character.toString() + "' inside the '" + room.toString() + "' using a '" + weapon.toString() + "' as a weapon.");

        // suggest to current player first
        if (p1.suggest(character, room, weapon, p1)) {
            currentPlayer++;
        } else {
            for (int i = 0; i <= players.size() - 1; i++) {

                // case when user suggests NOT accuses all three winning cards
                if (cardNotFound == players.size() - 1) {
                    System.out.println("Interesting... No other players have these cards...");
                    // TODO: 'an accusation can follow an unrefuted suggestion' (from marking schedule) -- if no card is refuted the player can then accuse?
                    currentPlayer++;
                    break;
                }

                nextPlayer = nextPlayer + 1 >= players.size() ? 0 : nextPlayer + 1;    // sets next player
                Player p2 = players.get(nextPlayer);
                System.out.println(p1.getName() + " suggests to " + p2.getName() + ": '" + character.toString() + "' inside the '" + room.toString() + "' using a '" + weapon.toString() + "' as a weapon.");

                // if matching card is found, move to next player
                if (p1.suggest(character, room, weapon, p2)) {
                    currentPlayer++;
                    break;
                }
                cardNotFound++;
            }
        }
    }

    /**
     * This method allows the user to make an accusation (guess the winning deck).
     * If the user correctly guesses the winning deck, they are the winner and the game ends.
     * If the user incorrectly guesses, they are shown the winning deck and removed from the game.
     * Players removed from the game can still refute cards but can NOT make suggestions/accusations.
     *
     * @param scan -- the current scanner in use.
     * @param p1   -- the current player's turn.
     */
    public void runAccuse(Scanner scan, Player p1) {
        CardChar character = charCards.get(getInput("Accuse a Character : ", scan, "char"));
        CardRoom room = roomCards.get(getInput("Accuse a Room : ", scan, "room"));
        CardWeapon weapon = weaponCards.get(getInput("Accuse a Weapon : ", scan, "weapon"));
        System.out.println(p1.getName() + " accuses: '" + character.toString() + "' inside the '" + room.toString() + "' using a '" + weapon.toString() + "' as a weapon.");

        if (p1.accuse(character, room, weapon)) {       // correct accusation (game won)
            setGameWon();
            displayAccusation(character, room, weapon);
            System.out.println("Your accusation is correct! " + p1.getName() + " is the winner of the game ^_^!\n");
        } else {                                        // incorrect accusation (player eliminated)
            p1.setGameOver();
            displayAccusation(character, room, weapon);
            eliminatedPlayers++;
            System.out.println("Incorrect guess... " + p1.getName() + " is out of the game!\nYou can still refute cards to other players.\n");
        }
    }


    /**
     * Displays the user accusation on the screen.
     * Correct guesses are marked with a "✓". Incorrect guesses are marked with a "x".
     *
     * @param character -- the Character card the user has accused.
     * @param room      -- the Room card the user has accused.
     * @param weapon    -- the Weapon card the user has accused.
     */
    public void displayAccusation(CardChar character, CardRoom room, CardWeapon weapon) {
        System.out.println("\nThe winning cards are: ");

        for (Card card : winningDeck) {
            if (card.equals(character) || card.equals(room) || card.equals(weapon))
                System.out.println(String.format("%-20s%-7s", card.getNameOfCard(), "✓"));
            else
                System.out.println(String.format("%-20s%-7s", card.getNameOfCard(), "x"));
        }
    }

    /**
     * Returns the winning deck in this game.
     *
     * @return ArrayList<Card> -- the winning deck.
     */
    public ArrayList<Card> getWinningDeck() {
        return winningDeck;
    }


    /**
     * Asks the user for the number of players in the game.
     * This is asked at the start of every game... might need if we want to restart game
     */
//    public int getNumOfPlayers() {
//        int num = 0;
//        while (true) {
//            try {
//                Scanner scan = new Scanner(System.in);
//                System.out.println("Select number of players in the game (2-6) : ");
//                num = scan.nextInt();
//                // do we need to close scanners ??
//                break;
//            } catch (Exception e) {
//                System.out.println("Invalid input. Please enter a number between 2-6.\n");
//            }
//        }
//        return num;
//    }
    public static void main(String... args) {
        int num;

        // get valid number of players
        while (true) {
            try {
                Scanner scan = new Scanner(System.in);
                System.out.println("Select number of players in the game (3-6) : ");
                num = scan.nextInt();

                if (num < 3 || num > 6) throw new Exception();
                // do we need to close scanners ??
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number between 3-6.\n");
            }
        }

        new Game(num).runGame();

        System.out.println("Game ended successfully... see you next time!");
    }


}