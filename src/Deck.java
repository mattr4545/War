/**
 *	Deacom Coding Challenge
 *	author: Matt Robinson
 *	January, 2019
 */


import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 *	a class to represent a deck of cards
 */
public class Deck {

	/**
	 * a queue that will represent all the cards in the deck, 
	 * a queue because cards are loaded into the deck from the bottom, and then taken off the deck from the top
	 */
	private LinkedList<Card> deck;
	
	public Deck() {
		deck = new LinkedList<>();
	}
	
	public Deck(Card[] cards) {
		deck = new LinkedList<>();
		for (Card c: cards) {
			this.addCard(c);
		}
	}
	
	/**
	 * @return  the queue of cards
	 */
	public Queue<Card> getCards() {
		return deck;
	}
	
	/**
	 * @return  if the deck is empty or not
	 */
	public boolean isEmpty() {
		return deck.isEmpty();
	}
	
	/**
	 * @return  the number of cards currently in the deck
	 */
	public int getSize() {
		return deck.size();
	}
	
	/**
	 * @return  the top card of the deck, poll returns null if empty
	 */
	public Card flip() {
		return deck.poll();
	}
	
	/**
	 * @param d - the deck to be added into the bottom of this deck
	 */
	public boolean addDeck(Deck d) {
		return deck.addAll(d.getCards());
	}
	
	/**
	 * @param c - the card to be added into the bottom of this deck
	 */
	public boolean addCard(Card c) {
		if (c != null)
			return deck.add(c);
		return false;
	}
	
	/**
	 * shuffles the deck into a random order
	 */
	public void shuffle() {
		for (int i = 0; i < deck.size(); i++) {
			int randomSpot = (int) (Math.random()*deck.size()); // gets a random spot for the card, 0->the decks size
			swap(Math.min(i, randomSpot), Math.max(i, randomSpot)); // swap the card with the random spot
		}
	}
	
	/**
	 * swaps the cards at the two given locations
	 * c1 must be smaller than c2
	 */
	public void swap(int c1, int c2) {
		Card card1 = deck.get(c1);
		Card card2 = deck.get(c2);
		
		// add card2 where card1 is
		deck.add(c1, card2);
		// remove card2 from the list, add one because indexes are shifted down 1
		deck.remove(c2 + 1); 
		
		// add card1 where card2 was
		deck.add(c2+1, card1);
		// remove card2 from the list, add one because indexes are shifted down 1
		deck.remove(c1 + 1);
		
	}
	
	/**
	 * splits the deck into two equal halves - just used for set up
	 * @return the second half of the deck, removing it from this deck
	 */
	public Deck split() {
		Deck newDeck = new Deck();
		// add the back half of the deck to newDeck
		for (int i = deck.size()/2; i < deck.size(); i++) {
			newDeck.addCard(deck.get(i));
		}
		
		// remove from the back of the deck, starting at the length - 1, down to half the length
		for (int i = (newDeck.getSize()*2) - 1; i >= newDeck.getSize(); i--) {
			deck.removeLast();
		}
		return newDeck;
	}
	
	/**
	 * @return the Deck in a String form
	*/
	public String toString() {
		String str = "Deck of " + deck.size() + " cards: [ ";
		for(Card c: this.deck) {
			str += c.toString() + " ";
		}
		return str + "]";
	}
}
