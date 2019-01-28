/**
 *	Deacom Coding Challenge
 *	author: Matt Robinson
 *	January, 2019
 */


/**
 *	a class to represent a player in the game of war
 */
public class Player {

	private Deck deck;
	
	public Player(Deck deck) {
		this.deck = deck;
	}
	
	public Deck getDeck() {
		return deck;
	}
	
	public int getDeckSize() {
		return deck.getSize();
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}


	
}
