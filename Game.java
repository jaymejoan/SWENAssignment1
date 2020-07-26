import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The Game Class initialises and runs the game sequence.
 */
public class Game {

    String[] cardNames = new String[]{"Miss Scarlet", "Rev Green", "Colonel Mustard", "Professor Plum", "Mrs Peacock", "Dr Orchid",
            "Candlestick", "Dagger", "Lead Pipe", "Revolver", "Rope", "Wrench",
            "Kitchen", "Ballroom", "Conservatory", "Dining Room", "Billiard Room", "Library", "Lounge", "Hall", "Study"};

    //    ArrayList<Card> cards = new ArrayList<>();

//    ArrayList<CardChar> charCards = new ArrayList<>();
//    ArrayList<CardWeapon> weaponCards = new ArrayList<>();
//    ArrayList<CardRoom> roomCards = new ArrayList<>();

    HashMap<String, CardChar> charCards = new HashMap<>();              // TODO: can rename to simply characters, weapons, rooms ?
    HashMap<String, CardWeapon> weaponCards = new HashMap<>();
    HashMap<String, CardRoom> roomCards = new HashMap<>();

    ArrayList<Card> winningDeck = new ArrayList<>();
    ArrayList<Player> players = new ArrayList<>();


    //    ArrayList<Tile> tiles = new ArrayList<>(); --  what collection type to use ?


    /**
     * Players, Cards and the Tiles should be constructed here
     * (as they only need to be initialised once).
     *
     * @param numOfPlayers -- number of players in this game.
     */
    public Game(int numOfPlayers) {

        // initialise players
//        for (int i = 0; i <= numOfPlayers; i++)
//            players.add(new Player());

        // initialise Character cards
        for (int i = 0; i <= 5; i++)
            charCards.put(cardNames[i], new CardChar(cardNames[i]));

        // initialise Weapon cards
        for (int i = 6; i <= 11; i++)
            weaponCards.put(cardNames[i], new CardWeapon(cardNames[i]));

        // initialise Room cards
        for (int i = 12; i < cardNames.length; i++)
            roomCards.put(cardNames[i], new CardRoom(cardNames[i]));

//        for (int i = 0; i < cards.size(); i++)
//            System.out.println(i + " name: " + cards.get(i).getNameOfCard() + " type: " + cards.get(i).toString());

        createWinningDeck();
        dealCards();

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
     */
    public void dealCards() {

        // deal Character cards (draft)
//        for (CardChar c : charCards.values()) {
//            for (Player p : players) {
//                p.addCard(c);
//            }
//        }

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

    }

    public static void main(String... args) {
        new Game(0).runGame();
    }
}

/**
 * Dummy class to get rid of errors
 */
class Player {


}



