import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The Game Class initialises and runs the game sequence.
 */
public class Game {

    private final String[] cardNames = new String[]{"Miss Scarlet", "Rev Green", "Colonel Mustard", "Professor Plum", "Mrs Peacock", "Dr Orchid",
            "Candlestick", "Dagger", "Lead Pipe", "Revolver", "Rope", "Wrench",
            "Kitchen", "Ballroom", "Conservatory", "Dining Room", "Billiard Room", "Library", "Lounge", "Hall", "Study"};

    private final String[] playerNames = new String[]{"Player1", "Player2", "Player3", "Player4", "Player5", "Player6"};


    ;

    //    ArrayList<Card> cards = new ArrayList<>();

//    ArrayList<CardChar> charCards = new ArrayList<>();
//    ArrayList<CardWeapon> weaponCards = new ArrayList<>();
//    ArrayList<CardRoom> roomCards = new ArrayList<>();

    HashMap<String, CardChar> charCards = new HashMap<>();              // TODO: can rename to simply characters, weapons, rooms ?
    HashMap<String, CardWeapon> weaponCards = new HashMap<>();
    HashMap<String, CardRoom> roomCards = new HashMap<>();

    ArrayList<Card> winningDeck = new ArrayList<>();
    ArrayList<Card> fullDeck = new ArrayList<>();
    ArrayList<Player> players = new ArrayList<>();


    //    ArrayList<Tile> tiles = new ArrayList<>(); --  what collection type to use ?


    /**
     * Players, Cards and the Tiles should be constructed here
     * (as they only need to be initialised once).
     *
     * @param numOfPlayers -- number of players in this game.
     */
    public Game(int numOfPlayers) {

        // initialise Character cards
        for (int i = 0; i <= 5; i++) {
            charCards.put(cardNames[i], new CardChar(cardNames[i]));
        }

        // initialise Weapon cards
        for (int i = 6; i <= 11; i++)
            weaponCards.put(cardNames[i], new CardWeapon(cardNames[i]));

        // initialise Room cards
        for (int i = 12; i < cardNames.length; i++)
            roomCards.put(cardNames[i], new CardRoom(cardNames[i]));


//        for (int i = 0; i < cards.size(); i++)
//            System.out.println(i + " name: " + cards.get(i).getNameOfCard() + " type: " + cards.get(i).toString());

        createWinningDeck();

        // add remaining cards to deck
        fullDeck.addAll(charCards.values());
        fullDeck.addAll(weaponCards.values());
        fullDeck.addAll(roomCards.values());

        ArrayList<HashSet<Card>> deck = dealCards(numOfPlayers);

        // initialise players
        for (int i = 0; i < numOfPlayers; i++) {
            players.add(new Player(playerNames[i], 0, 0, deck.get(i)));
            System.out.println("player: " + i + " deck: " + deck.get(i).toString());
        }


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
        winningDeck.add(charCards.get(cardNames[randomChar]));
        winningDeck.add(weaponCards.get(cardNames[randomWeapon]));
        winningDeck.add(roomCards.get(cardNames[randomRoom]));

        // remove winning cards from pack
        charCards.remove(cardNames[randomChar]);
        weaponCards.remove(cardNames[randomWeapon]);
        roomCards.remove(cardNames[randomRoom]);

        // testing

//        for (Card card : winningDeck)
//            System.out.println("type: " + card.toString() + " name: " + card.getNameOfCard());

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
     * @return
     */
    public ArrayList<HashSet<Card>> dealCards(int numOfPlayers) {

        Collections.shuffle(fullDeck);

        ArrayList<HashSet<Card>> decks = new ArrayList<>();
        int count = 0;

        // create HashSet for each Player
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
     * @return
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

        // ask user input for suggest

        try {
            Scanner scan = new Scanner(System.in);
            System.out.println("Suggest a Character : ");
            String character = scan.next();
            System.out.println("Suggest a Room : ");
            String room = scan.next();
            System.out.println("Suggest a Weapon : ");
            String weapon = scan.next();

            scan.close();
//            break;
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a valid suggestion (make sure spelling is correct).\n");
        }


        Player p1 = players.get(0);
        HashSet<Card> demo = new HashSet<>();
        demo.add(charCards.get("Miss Scarlet"));
        demo.add(roomCards.get("Kitchen"));
        demo.add(weaponCards.get("Rope"));

        Player p2 = new Player("b", 0, 0, demo);

        Scanner scan = new Scanner(System.in);

        Card card = p1.suggest(charCards.get("Miss Scarlet"), roomCards.get("Kitchen"), weaponCards.get("Rope"), p2);

        if (card == null)
            System.out.println("p2 does not have card");

        System.out.println("card: " + card);


        // player 1 move charToken

        // player 1 suggest()

        // player 2 refute()
        // if no card -> move to player 3


    }

    public static void main(String... args) {

        while (true) {
            try {
                Scanner scan = new Scanner(System.in);
                System.out.println("Select number of players in the game (2-6) : ");
                int num = scan.nextInt();
                new Game(num).runGame();
                scan.close();
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number between 2-6.\n");
            }
        }
    }


}





