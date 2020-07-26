/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5059.b674b8115 modeling language!*/


import java.util.*;

// line 8 "model.ump"
// line 140 "model.ump"
public class Player
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Player Attributes
  private String name;
  private boolean gameOver;

  //Player Associations
  private TokenChar tokenChar;
  private Game game;
  private HashSet<Card> cards = new HashSet<>();

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(String playerName, int x , int y, HashSet<Card> playerCards){
    name = playerName;
    gameOver = false;
    cards = playerCards;
    //@todo link this player obj back to card
    tokenChar = new TokenChar(name,0,0); //number of player
    //@todo need to know where the player is going to start might need to add (x,y) in constructor
    //@todo add Game object
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setGameOver(boolean aGameOver)
  {
    boolean wasSet = false;
    gameOver = aGameOver;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public boolean getGameOver()
  {
    return gameOver;
  }
  /* Code from template association_GetOne */
  public TokenChar getTokenChar()
  {
    return tokenChar;
  }
  /* Code from template association_GetMany */
  public Game getGame()
  {
    return game;
  }


  /* Code from template association_GetOne */
  public HashSet<Card> getCard()
  {
    return cards;
  }

  // line 14 "model.ump"
   public Card suggest(CardChar character , CardRoom room , CardWeapon weapon,Player other){
      return other.refute(character,room,weapon);
  }

  // line 17 "model.ump"
   public Card refute(CardChar character , CardRoom room , CardWeapon weapon){
    List<Card> options = new ArrayList<>();
    if(cards.contains(character)){
      options.add(character);
    }
     if(cards.contains(room)){
       options.add(room);
     }
     if(cards.contains(weapon)){
       options.add(weapon);
     }
     int size = options.size();
     if(size == 1){
       return options.get(0);
     } else if(size == 0){ 
       return null;
     } else {
       Scanner scan = new Scanner(System.in);
       System.out.println("These are your options: " + options.toString() + "\n what would you like to refute ? Please select an index [ 1 , 2 , 3] : ");
       
       //keep asking the user until there's a valid index
       while(true) {
         try {
           Card card = options.get(scan.nextInt()-1);
           scan.close();
           return card;
         } catch (Exception e) {
           System.out.println("Invalid input please enter a valid index\n");
         }
       }
     }
    
  }

  // line 20 "model.ump"
   public boolean accuse(CardChar character , CardRoom room , CardWeapon weapon){
    return false;
    
  }

  // line 23 "model.ump"
   public void move(){
    //will this just be changing rooms?
    
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "gameOver" + ":" + getGameOver()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "tokenChar = "+(getTokenChar()!=null?Integer.toHexString(System.identityHashCode(getTokenChar())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "card = "+(getCard()!=null?Integer.toHexString(System.identityHashCode(getCard())):"null");
  }
}
