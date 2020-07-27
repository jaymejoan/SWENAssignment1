/**
 * A Card object belongs to one of the three categories of cards in the Game:
 * Characters, Weapons, Rooms.
 */

public class Card {

    private String nameOfCard;

    public Card(String name) {
        nameOfCard = name;
    }

    public String getNameOfCard() {
        return nameOfCard;
    }

    public String toString() {
        return nameOfCard;
    }
}

/**
 * CardChar represents the six Character cards.
 */
class CardChar extends Card{

    public CardChar(String name) {
        super(name);
    }

    public String toString() {
        return " " + super.getNameOfCard() + " ";
    }
}

/**
 * CardWeapon represents the six Weapon cards.
 */
class CardWeapon extends Card{

    public CardWeapon(String name) {
        super(name);
    }

    public String toString() {
        return " " + super.getNameOfCard() + " ";
    }

}

/**
 * CardRoom represents the nine Room cards.
 */
class CardRoom extends Card{

    public CardRoom(String name) {
        super(name);
    }

    public String toString() {
        return " " + super.getNameOfCard() + " ";
    }
}
