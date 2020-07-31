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
    private Board board;
    private ArrayList<Integer> boardSetUp;

    HashMap<String, CardChar> charCards = new HashMap<>();
    HashMap<String, CardWeapon> weaponCards = new HashMap<>();
    HashMap<String, CardRoom> roomCards = new HashMap<>();

    ArrayList<Card> fullDeck = new ArrayList<>();                       // deck containing the remaining 18 cards
    ArrayList<Player> players = new ArrayList<>();


//    HashSet<Room> rooms = new HashSet<>();
//    Tile[][] board = new Tile[25][24]; //0-24[25] by 0-23[24]


    //--- NEW FIELDS
    // Setting board up as integer for tile type, (integer so it's shorthanded)
    // and then will go through this list and construct board with tiles based
    // on integer.

    // 1 = Blocked
    // 2 = Hallway
    // 3 = Room
    // 4 = Door
    // boardSetUp
  /*  private static ArrayList<Integer> boardSetUp = new ArrayList<Integer>(
            Arrays.asList(1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                    3, 3, 3, 3, 3, 3, 1, 2, 2, 2, 3, 3, 3, 3, 2, 2, 2, 1, 3, 3, 3, 3, 3, 3,
                    3, 3, 3, 3, 3, 3, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 2, 2, 3, 3, 3, 3, 3, 3,
                    3, 3, 3, 3, 3, 3, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 2, 2, 3, 3, 3, 3, 3, 3,
                    3, 3, 3, 3, 3, 3, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 2, 2, 3, 3, 3, 3, 3, 3,
                    3, 3, 3, 3, 3, 3, 2, 4, 3, 3, 3, 3, 3, 3, 3, 3, 4, 2, 4, 3, 3, 3, 3, 1,
                    1, 3, 3, 3, 3, 3, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 2, 2, 2, 2, 2, 2, 2, 2,
                    2, 2, 2, 2, 4, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 2, 2, 2, 2, 2, 2, 2, 1,
                    1, 2, 2, 2, 2, 2, 2, 2, 2, 4, 2, 2, 2, 2, 4, 2, 2, 2, 3, 3, 3, 3, 3, 3,
                    3, 3, 3, 3, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 3, 3, 3, 3, 3, 3,
                    3, 3, 3, 3, 3, 3, 3, 3, 2, 2, 1, 1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 3, 3, 3,
                    3, 3, 3, 3, 3, 3, 3, 3, 2, 2, 1, 1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 3, 3, 3,
                    3, 3, 3, 3, 3, 3, 3, 3, 4, 2, 1, 1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 3, 3, 3,
                    3, 3, 3, 3, 3, 3, 3, 3, 2, 2, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 4, 2, 4, 1,
                    3, 3, 3, 3, 3, 3, 3, 3, 2, 2, 1, 1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 3, 3, 1,
                    3, 3, 3, 3, 3, 3, 3, 3, 2, 2, 1, 1, 1, 1, 1, 2, 2, 3, 3, 3, 3, 3, 3, 3,
                    1, 2, 2, 2, 2, 2, 4, 2, 2, 2, 1, 1, 1, 1, 1, 2, 4, 3, 3, 3, 3, 3, 3, 3,
                    2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3,
                    1, 2, 2, 2, 2, 2, 4, 2, 2, 3, 3, 3, 3, 3, 3, 2, 2, 2, 3, 3, 3, 3, 3, 1,
                    3, 3, 3, 3, 3, 3, 3, 2, 2, 3, 3, 3, 3, 3, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2,
                    3, 3, 3, 3, 3, 3, 3, 2, 2, 3, 3, 3, 3, 3, 3, 4, 2, 4, 2, 2, 2, 2, 2, 1,
                    3, 3, 3, 3, 3, 3, 3, 2, 2, 3, 3, 3, 3, 3, 3, 2, 2, 3, 3, 3, 3, 3, 3, 3,
                    3, 3, 3, 3, 3, 3, 3, 2, 2, 3, 3, 3, 3, 3, 3, 2, 2, 3, 3, 3, 3, 3, 3, 3,
                    3, 3, 3, 3, 3, 3, 3, 2, 2, 3, 3, 3, 3, 3, 3, 2, 2, 3, 3, 3, 3, 3, 3, 3,
                    3, 3, 3, 3, 3, 3, 1, 2, 1, 3, 3, 3, 3, 3, 3, 1, 2, 1, 3, 3, 3, 3, 3, 3)
    ); */

    //--- NEW FIELDS END


    /**
     * Players, Cards and Tiles are constructed here
     * (as they only need to be initialised once).
     *
     * @param numOfPlayers -- number of players in this game.
     */
    public Game(int numOfPlayers) {
        board = new Board();
        boardSetUp = board.boardSetUp;
        totalPlayers = numOfPlayers;

//        createTiles(); TODO: do we still need this or is it already executed in board?

        // initialise Character cards
        for (int i = 0; i <= 5; i++)
            charCards.put(cardNames[i].toLowerCase(), new CardChar(cardNames[i]));

        // initialise Weapon cards
        for (int i = 6; i <= 11; i++)
            weaponCards.put(cardNames[i].toLowerCase(), new CardWeapon(cardNames[i]));

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
            players.add(new Player(playerNames[i], 0, 0, hand.get(i), this, new TokenChar(cardNames[i], 7, 1, board)));     //TODO: double check fields for Board... what should x and y be ? do you want to create a list of starting positions and iterate through?
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
     * Creates the Tiles for the Board.
     */
 /*   public void createTiles() {

        //----- ONLY CHANGES
        // Set up the board of Tile objects based on the integer shorthand
        int x = 0;
        int y = 0;
        Tile newTile = null;
        for (int i = 0; i < 600; i++) {
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

            if (x == 23) {
                x = 0;
                y++;
            } else {
                x++;
            }
        }

        // Designating Room Tile that represents whole room:

        // Kitchen [4][2]
        // Door 1: [7][4]
        board[4][2].setName("Kitchen");
        board[4][2].addDoor(1, (Door) board[7][4]);

        // Ballroom [5][11]
        // Door 1: [5][7]
        // Door 2: [8][9]
        // Door 3: [8][14]
        // Door 4: [5][16]
        board[5][11].setName("Ballroom");
        board[5][11].addDoor(1, (Door) board[5][7]);
        board[5][11].addDoor(2, (Door) board[8][9]);
        board[5][11].addDoor(3, (Door) board[8][14]);
        board[5][11].addDoor(4, (Door) board[5][16]);
        rooms.add((Room) board[5][11]);

        // Conservatory [3][20]
        // Door 1: [5][18]
        board[3][20].setName("Conservatory");
        board[3][20].addDoor(1, (Door) board[5][18]);
        rooms.add((Room) board[3][20]);

        // Dining Room [12][3]
        // Door 1: [12][8]
        // Door 2: [16][6]
        board[12][3].setName("Dining Room");
        board[12][3].addDoor(1, (Door) board[12][8]);
        board[12][3].addDoor(2, (Door) board[16][6]);
        rooms.add((Room) board[12][3]);

        // Billiard Room [10][20]
        // Door 1: [9][17]
        // Door 2: [13][22]
        board[10][20].setName("Billiard Room");
        board[10][20].addDoor(1, (Door) board[9][17]);
        board[10][20].addDoor(2, (Door) board[12][8]);
        rooms.add((Room) board[10][20]);

        // Library [16][20]
        // Door 1: [13][20]
        // Door 2: [16][16]
        board[16][20].setName("Library");
        board[16][20].addDoor(1, (Door) board[13][20]);
        board[16][20].addDoor(2, (Door) board[16][16]);
        rooms.add((Room) board[16][20]);

        // Lounge [22][3]
        // Door 1: [18][6]
        board[22][3].setName("Lounge");
        board[22][3].addDoor(1, (Door) board[18][6]);
        rooms.add((Room) board[22][3]);

        // Hall [21][11]
        // Door 1: [17][11]
        // Door 2: [17][12]
        // Door 3: [20][15]
        board[21][11].setName("Hall");
        board[21][11].addDoor(1, (Door) board[13][20]);
        board[21][11].addDoor(1, (Door) board[13][20]);
        board[21][11].addDoor(1, (Door) board[13][20]);
        rooms.add((Room) board[21][11]);


        // Study [23][20]
        // Door 1: [20][17]
        board[23][20].setName("Study");
        board[23][20].addDoor(1, (Door) board[20][17]);
        rooms.add((Room) board[23][20]);
        //----- NEW CONTENT END

    } */

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
     * @param range -- the range of Tiles this Player can reach (?? is that right Phoebe)
     * @param scan  -- the current Scanner in use.
     * @return Room -- the Room this Player can reach.
     */
    public Room getReachableRoom(HashSet<Tile> range, Scanner scan) {
        ArrayList<Room> reachableRooms = new ArrayList<>();
        System.out.print("These are the rooms you can reach: [");
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
                System.out.print("\nWhat room would you like to move to? [1 - " + reachableRooms.size() + "] : ");
                Room room = reachableRooms.get(scan.nextInt() - 1);
//                    scan.close();
                return room;
            } catch (Exception e) {
                System.out.println("Invalid input please enter a valid index.\n");
            }
        }
    }


    /**
     * Executes the game.
     */
    public void runGame() {
        Scanner scan = new Scanner(System.in);
        board.displayBoard();

        while (!gameWon) {
            currentPlayer = currentPlayer >= players.size() ? 0 : currentPlayer;
            nextPlayer = currentPlayer;

            int dice = rollDice();
            Player p1 = players.get(currentPlayer);
            TokenChar tokenChar = p1.getToken();
            Door door = null;
            Room currentRoom = p1.getCurrentRoom();

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

            // asks the user which door to exit
            if (currentRoom != null) {
                while (true) {
                    try {
                        System.out.print("\nWhat door do you wish to exit? [1 - " + currentRoom.getDoors().size() + "] : " + currentRoom.getDoors().toString());
                        door = currentRoom.getDoors().get(scan.nextInt());  //TODO: check this doesnt result in error for invalid inputs
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
            HashSet<Tile> tiles = tokenChar.getVisitableTiles(xPos, yPos, dice, new HashSet<Tile>());
            Room choseRoom = getReachableRoom(tiles, scan);
            System.out.println(p1 + " is moving to : " + choseRoom.getName());
            p1.move(choseRoom);
            System.out.println(p1 + " is now in : " + p1.getCurrentRoom());
            p1.displayCards();

            // executes suggest or accuse
            while (true) {
                //TODO: if player is not in a room (ie. in hall), skip over ... can they still accuse?

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
     * @param scan         -- the current scanner in use.
     * @param p1           -- the current player making the suggestion.
     * @param currentRoom  -- the room this player is in.
     */
    public void runSuggest(Scanner scan, Player p1, Room currentRoom) {
        int cardNotFound = 0;           // num of players who don't have any matching cards

        // gets cards user wants to suggest
        CardChar character = charCards.get(getInput("\nSuggest a Character : ", scan, "char"));
        CardRoom room = roomCards.get(currentRoom.getName().toLowerCase());
        System.out.println("You are in the Room: " + room.getNameOfCard());
        CardWeapon weapon = weaponCards.get(getInput("Suggest a Weapon : ", scan, "weapon"));

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





