import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The Game Class initialises and runs the game sequence.
 */
public class Game {

    private String[] cardNames = new String[]{"Miss Scarlet", "Rev Green", "Colonel Mustard", "Professor Plum", "Mrs Peacock", "Dr Orchid",
            "Candlestick", "Dagger", "Lead Pipe", "Revolver", "Rope", "Wrench",
            "Kitchen", "Ballroom", "Conservatory", "Dining Room", "Billiard Room", "Library", "Lounge", "Hall", "Study"};


    private final String[] playerNames = new String[]{"Player1", "Player2", "Player3", "Player4", "Player5", "Player6"};

    HashMap<String, CardChar> charCards = new HashMap<>();              // TODO: can rename to simply characters, weapons, rooms ?
    HashMap<String, CardWeapon> weaponCards = new HashMap<>();
    HashMap<String, CardRoom> roomCards = new HashMap<>();

    ArrayList<Card> winningDeck = new ArrayList<>();                    // deck containing the three winning cards
    ArrayList<Card> fullDeck = new ArrayList<>();                       // deck containing the remaining 18 cards
    ArrayList<Player> players = new ArrayList<>();


    //--- NEW FIELDS
    // Setting board up as integer for tile type, (integer so it's shorthanded)
    // and then will go through this list and construct board with tiles based
    // on integer.

    // 1 = Blocked
    // 2 = Hallway
    // 3 = Room
    // 4 = Door
    // boardSetUp
    private static ArrayList<Integer> boardSetUp = new ArrayList<Integer>(
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
    );

    HashSet<Room> rooms = new HashSet<>();
    Tile[][] board = new Tile[25][24]; //0-24[25] by 0-23[24]
    //--- NEW FIELDS END


    /**
     * Players, Cards and the Tiles should be constructed here
     * (as they only need to be initialised once).
     *
     * @param numOfPlayers -- number of players in this game.
     */
    public Game(int numOfPlayers) {

        createTiles();

        // initialise Character cards
        for (int i = 0; i <= 5; i++)
            charCards.put(cardNames[i].toLowerCase(), new CardChar(cardNames[i]));

        // initialise Weapon cards
        for (int i = 6; i <= 11; i++)
            weaponCards.put(cardNames[i].toLowerCase(), new CardWeapon(cardNames[i]));

        // initialise Room cards
        for (int i = 12; i < cardNames.length; i++)
            roomCards.put(cardNames[i].toLowerCase(), new CardRoom(cardNames[i]));


//        for (int i = 0; i < cards.size(); i++)
//            System.out.println(i + " name: " + cards.get(i).getNameOfCard() + " type: " + cards.get(i).toString());

        createWinningDeck();

        // add remaining cards (ie. not winning cards) to deck
        fullDeck.addAll(charCards.values());
        fullDeck.addAll(weaponCards.values());
        fullDeck.addAll(roomCards.values());

        ArrayList<HashSet<Card>> hand = dealCards(numOfPlayers);        // List of each player's hand of cards

        // initialise players
        for (int i = 0; i < numOfPlayers; i++) {
            players.add(new Player(playerNames[i], 0, 0, hand.get(i), this));
            System.out.println("player: " + i + " deck: " + hand.get(i).toString());
        }

        // add winning cards back into original deck (needed for rest of gameplay)
        for (Card card : winningDeck) {
            if (card instanceof CardChar)
                charCards.put(card.getNameOfCard(), (CardChar) card);
            else if (card instanceof CardWeapon)
                weaponCards.put(card.getNameOfCard(), (CardWeapon) card);
            else if (card instanceof CardRoom)
                roomCards.put(card.getNameOfCard(), (CardRoom) card);
        }

        //TODO: make array lowercase
        for (int i = 0; i < cardNames.length; i++) {
            cardNames[i] = cardNames[i].toLowerCase();
//            System.out.println(i + ": " + cardNames[i]);
        }


    }

    /**
     * Creates the Tiles for the Board.
     */
    public void createTiles() {

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
        int randomRoom = ThreadLocalRandom.current().nextInt(11, 21);

        // add cards to winning deck
        winningDeck.add(charCards.get(cardNames[randomChar].toLowerCase()));
        winningDeck.add(weaponCards.get(cardNames[randomWeapon].toLowerCase()));
        winningDeck.add(roomCards.get(cardNames[randomRoom].toLowerCase()));

        // remove winning cards from pack
        charCards.remove(cardNames[randomChar]);
        weaponCards.remove(cardNames[randomWeapon]);
        roomCards.remove(cardNames[randomRoom]);

        // testing

        for (Card card : winningDeck)
            System.out.println("type: " + card.toString() + " name: " + card.getNameOfCard());

//        for (Map.Entry<String, CardChar> c : charCards.entrySet()
//          System.out.println("name: " + c.getKey());
//
//        for (Map.Entry<String, CardWeapon> c : weaponCards.entrySet())
//            System.out.println("name: " + c.getKey());
//
//        for (Map.Entry<String, CardRoom> c : roomCards.entrySet())
//            System.out.println("name: " + c.getKey());
//

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

        System.out.println("Cards have been handed out.");

        return decks;
    }

    /**
     * Resets the game to its starting state.
     */
    public void reset() {
        charCards.clear();
        weaponCards.clear();
        roomCards.clear();
        winningDeck.clear();
        players.clear();
    }

    /**
     * Returns true if a Player has won the game.
     *
     * @return boolean -- true if Player has won game. Otherwise, false.
     */
    public boolean gameWon() {

        // if player's guess = winning deck

        return false;
    }

    // should we include a gameLost in the event NO players correctly guess the winning deck ?


    /**
     * Executes the game.
     */
    public void runGame() {
        int currentPlayer = 0;
        int nextPlayer;         // the player currentPlayer is suggesting to


        while (true) {
            Scanner scan = new Scanner(System.in);

            currentPlayer = currentPlayer >= players.size() ? 0 : currentPlayer;
            nextPlayer = currentPlayer;

            Player p1 = players.get(currentPlayer);
            System.out.println("\nIt's " + p1.getName() + "'s turn.");

            // TODO: add move tokens FIRST before suggestion

            // get user input for suggestion
            //TODO: handle equalsIgnoreCase
            CardChar character = charCards.get(getInput("Suggest a Character : ", scan, "char"));
            CardRoom room = roomCards.get(getInput("Suggest a Room : ", scan, "room"));                 // TODO: remove this later. Room should be same room as the player (user does not choose)
            CardWeapon weapon = weaponCards.get(getInput("Suggest a Weapon : ", scan, "weapon"));

            int cardNotFound = 0;

            // ask other players until a card is successfully refuted
            for (int i = 0; i <= players.size() - 1; i++) {

                // cases when no matching cards are found
//                if (cardNotFound == players.size() - 1 && (p1.getHand().contains(character) || p1.getHand().contains(room) || p1.getHand().contains(weapon))) {
                if (cardNotFound == players.size() - 1 &&
                        (p1.checkHand(character, room, weapon))) {
                    System.out.println("You have one (or more) of these cards!");     // TODO: should we allow the user to ask a card from their own hand ?
                    currentPlayer++;
                    // TODO: check if user wants to accuse
                    break;
                } else if (cardNotFound == players.size() - 1) {                          // cards are in winning deck (case: user suggests NOT accuses all three winning cards)
                    System.out.println("Interesting... No other players have these cards...");
                    currentPlayer++;

                    //TODO: check if user wants to accuse
                    break;
                }


                nextPlayer = nextPlayer + 1 >= players.size() ? 0 : nextPlayer + 1;    // sets next player
                Player p2 = players.get(nextPlayer);

                System.out.println(p1.getName() + " suggests to " + p2.getName() + ": '" + character.toString() + "' inside the '" + room.toString() + "' using a '" + weapon.toString() + "' as a weapon.");

                // if matching card is found, move to next player
                if (p1.suggest(character, room, weapon, p2)) {
                    System.out.println("breaking out of loop.");
                    currentPlayer++;

                    //TODO: check if user wants to accuse
                    break;
                }

                cardNotFound++;


//                System.out.println(character.getNameOfCard());
//                System.out.println(weapon.getNameOfCard());
//                System.out.println(room.getNameOfCard());


//            Player p1 = players.get(0);
//            HashSet<Card> demo = new HashSet<>();
//            demo.add(charCards.get("Miss Scarlet"));
//            demo.add(roomCards.get("Kitchen"));
//            demo.add(weaponCards.get("Rope"));
//
//            Player p2 = new Player("b", 0, 0, demo, this);
//
//            Card card = p1.suggest(charCards.get("Miss Scarlet"), roomCards.get("Kitchen"), weaponCards.get("Rope"), p2);
//
//            if (card == null)
//                System.out.println("p2 does not have card");

//                System.out.println("card: " + card);


                // player 1 move charToken

                // player 1 suggest()

                // player 2 refute()
                // if no card -> move to player 3
            }
        }


    }

    /**
     * A helper method which returns the user's input.
     *
     * @return String -- the user input.
     */
    private String getInput(String question, Scanner scan, String cardType) {

        while (true) {
            try {
                scan.useDelimiter("\n");
                System.out.println(question);
//                String input = scan.next();
                String input = scan.next().toLowerCase();
                System.out.println(input);

                if (!(Arrays.asList(cardNames).contains(input)))
                    throw new Exception("Invalid input. Please enter a valid suggestion (make sure spelling is correct).");
                else if (cardType.equals("char") && !charCards.containsKey(input))
                    throw new Exception("Invalid Character card. Please enter a valid suggestion.");
                else if (cardType.equals("room") && !roomCards.containsKey(input))
                    throw new Exception("Invalid Room card. Please enter a valid suggestion.");
                else if (cardType.equals("weapon") && !weaponCards.containsKey(input))
                    throw new Exception("Invalid Weapon card. Please enter a valid suggestion.");


                System.out.println("input: " + input);
//                scan.close();
                return input;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public static void main(String... args) {
        int num;

        // get valid number of players
        while (true) {
            try {
                Scanner scan = new Scanner(System.in);
                System.out.println("Select number of players in the game (2-6) : ");
                num = scan.nextInt();
                // do we need to close scanners ??
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number between 2-6.\n");
            }
        }

        new Game(num).runGame();
    }


}





