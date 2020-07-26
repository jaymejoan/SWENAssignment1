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

//  public Player(String aName, boolean aGameOver, TokenChar aTokenChar, Card aCard)
//  {
//    name = aName;
//    gameOver = aGameOver;
//    if (aTokenChar == null || aTokenChar.getPlayer() != null)
//    {
//      throw new RuntimeException("Unable to create Player due to aTokenChar. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
//    }
//    tokenChar = aTokenChar;
//    boolean didAddCard = setCard(aCard);
//    if (!didAddCard)
//    {
//      throw new RuntimeException("Unable to create player due to card. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
//    }
//  }
//
//  public Player(String aName, boolean aGameOver, String aNameForTokenChar, int aXForTokenChar, int aYForTokenChar, Room aRoomForTokenChar, Card aCard)
//  {
//    name = aName;
//    gameOver = aGameOver;
//    tokenChar = new TokenChar(aNameForTokenChar, aXForTokenChar, aYForTokenChar, aRoomForTokenChar, this);
//    boolean didAddCard = setCard(aCard);
//    if (!didAddCard)
//    {
//      throw new RuntimeException("Unable to create player due to card. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
//    }
//  }

  public Player(String playerName, Room startRoom, HashSet<Card> playerCards){
    name = playerName;
    gameOver = false;
    cards = playerCards;
    //@todo link this player obj back to card
    tokenChar = new TokenChar(name,0,0,startRoom,this);
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
   public void suggest(CardChar character , CardRoom room , CardWeapon weapon){
    
  }

  // line 17 "model.ump"
   public boolean refute(Card card){
    return cards.contains(card);
    
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
//  public boolean setName(String aName)
//  {
//    boolean wasSet = false;
//    name = aName;
//    wasSet = true;
//    return wasSet;
//  }
//  public List<Game> getGames()
//  {
//    List<Game> newGames = Collections.unmodifiableList(games);
//    return newGames;
//  }

//  public int numberOfGames()
//  {
//    int number = games.size();
//    return number;
//  }

//  public boolean hasGames()
//  {
//    boolean has = games.size() > 0;
//    return has;
//  }

//  public int indexOfGame(Game aGame)
//  {
//    int index = games.indexOf(aGame);
//    return index;
//  }

  /* Code from template association_IsNumberOfValidMethod */
//  public boolean isNumberOfGamesValid()
//  {
//    boolean isValid = numberOfGames() >= minimumNumberOfGames() && numberOfGames() <= maximumNumberOfGames();
//    return isValid;
//  }
/* Code from template association_MinimumNumberOfMethod */
//  public static int minimumNumberOfGames()
//  {
//    return 2;
//  }
//  /* Code from template association_MaximumNumberOfMethod */
//  public static int maximumNumberOfGames()
//  {
//    return 6;
//  }
//  /* Code from template association_AddMNToOnlyOne */
//  public Game addGame(Card aCard, Tile aTile)
//  {
//    if (numberOfGames() >= maximumNumberOfGames())
//    {
//      return null;
//    }
//    else
//    {
//      return new Game(aCard, aTile, this);
//    }
//  }

//  public boolean addGame(Game aGame)
//  {
//    boolean wasAdded = false;
//    if (games.contains(aGame)) { return false; }
//    if (numberOfGames() >= maximumNumberOfGames())
//    {
//      return wasAdded;
//    }
//
//    Player existingPlayer = aGame.getPlayer();
//    boolean isNewPlayer = existingPlayer != null && !this.equals(existingPlayer);
//
//    if (isNewPlayer && existingPlayer.numberOfGames() <= minimumNumberOfGames())
//    {
//      return wasAdded;
//    }
//
//    if (isNewPlayer)
//    {
//      aGame.setPlayer(this);
//    }
//    else
//    {
//      games.add(aGame);
//    }
//    wasAdded = true;
//    return wasAdded;
//  }

//  public boolean removeGame(Game aGame)
//  {
//    boolean wasRemoved = false;
//    //Unable to remove aGame, as it must always have a player
//    if (this.equals(aGame.getPlayer()))
//    {
//      return wasRemoved;
//    }
//
//    //player already at minimum (2)
//    if (numberOfGames() <= minimumNumberOfGames())
//    {
//      return wasRemoved;
//    }
//    games.remove(aGame);
//    wasRemoved = true;
//    return wasRemoved;
//  }
/* Code from template association_AddIndexControlFunctions */
//  public boolean addGameAt(Game aGame, int index)
//  {
//    boolean wasAdded = false;
//    if(addGame(aGame))
//    {
//      if(index < 0 ) { index = 0; }
//      if(index > numberOfGames()) { index = numberOfGames() - 1; }
//      games.remove(aGame);
//      games.add(index, aGame);
//      wasAdded = true;
//    }
//    return wasAdded;
//  }

//  public boolean addOrMoveGameAt(Game aGame, int index)
//  {
//    boolean wasAdded = false;
//    if(games.contains(aGame))
//    {
//      if(index < 0 ) { index = 0; }
//      if(index > numberOfGames()) { index = numberOfGames() - 1; }
//      games.remove(aGame);
//      games.add(index, aGame);
//      wasAdded = true;
//    }
//    else
//    {
//      wasAdded = addGameAt(aGame, index);
//    }
//    return wasAdded;
//  }
/* Code from template association_SetOneToAtMostN */
//  public boolean setCard(Card aCard)
//  {
//    boolean wasSet = false;
//    //Must provide card to player
//    if (aCard == null)
//    {
//      return wasSet;
//    }
//
//    //card already at maximum (9)
//    if (aCard.numberOfPlayers() >= Card.maximumNumberOfPlayers())
//    {
//      return wasSet;
//    }
//
//    Card existingCard = card;
//    card = aCard;
//    if (existingCard != null && !existingCard.equals(aCard))
//    {
//      boolean didRemove = existingCard.removePlayer(this);
//      if (!didRemove)
//      {
//        card = existingCard;
//        return wasSet;
//      }
//    }
//    card.addPlayer(this);
//    wasSet = true;
//    return wasSet;
//  }
//
//  public void delete()
//  {
//    TokenChar existingTokenChar = tokenChar;
//    tokenChar = null;
//    if (existingTokenChar != null)
//    {
//      existingTokenChar.delete();
//    }
//    for(int i=games.size(); i > 0; i--)
//    {
//      Game aGame = games.get(i - 1);
//      aGame.delete();
//    }
//    Card placeholderCard = card;
//    this.card = null;
//    if(placeholderCard != null)
//    {
//      placeholderCard.removePlayer(this);
//    }
//  }
