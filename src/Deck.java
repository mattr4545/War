/**
 * Deacom Coding Challenge
 * @author Matt Robinson
 * January, 2019
 */

import java.util.LinkedList;
import java.util.Queue;

/**
 * class that represents a deck
 * essentially a modified queue
 */
public class Deck
{
  private LinkedList<Card> deck;
  
  // constructors
  public Deck()
  {
    this.deck = new LinkedList<>();
  }
  
  public Deck(Card[] cards)
  {
    this.deck = new LinkedList<>();
    
    for (int i = 0; i < cards.length; i++)
    {
      Card c = cards[i];
      addCard(c);
    }
  }
  
  public Queue<Card> getCards()
  {
    return deck;
  }
  
  public boolean isEmpty()
  {
    return deck.isEmpty();
  }
  
  public void clear()
  {
    deck.clear();
  }
  
  public int getSize()
  {
    return deck.size();
  }
  
  /**
   * gets and returns the top card of the deck
   */
  public Card flip()
  {
    return (Card)deck.poll();
  }
  
  /**
   * adds the deck to the bottom of this deck
   * @return true if the deck was added successfully, false if the deck is null
   */
  public boolean addDeck(Deck d)
  {
    return deck.addAll(d.getCards());
  }
  
  /**
   * adds the card to the bottom of this deck
   * @return true if the card was added successfully, false if the card is null
   */
  public boolean addCard(Card c)
  {
    if (c != null) {
      return deck.add(c);
    }
    return false;
  }
  
  /**
   * shuffles the deck
   */
  public void shuffle()
  {
    for (int i = 0; i < deck.size(); i++)
    {
      int randomSpot = (int)(Math.random() * deck.size());
      swap(Math.min(i, randomSpot), Math.max(i, randomSpot)); // ensures c1 smaller than c2
    }
  }
  
  /**
   * swaps the cards at the given indexes
   * c1 must be smaller than c2
   */
  private void swap(int c1, int c2)
  {
	  // get the cards
    Card card1 = deck.get(c1);
    Card card2 = deck.get(c2);
    
    //add card2 where c1 is and remove it
    deck.add(c1, card2);
    deck.remove(c2 + 1);
    
    //add card1 where c2 is and remove then remove card1 from its original spot
    deck.add(c2 + 1, card1);
    deck.remove(c1 + 1);
  }
  
  /**
   * splits the deck in half
   * used for setup in Game.java
   * @return returns the second half of the deck and removes it from this deck
   */
  public Deck split()
  {
    Deck newDeck = new Deck();
    for (int i = deck.size() / 2; i < deck.size(); i++) {
      newDeck.addCard(deck.get(i));
    }
    for (int i = newDeck.getSize() * 2 - 1; i >= newDeck.getSize(); i--) {
      deck.removeLast();
    }
    return newDeck;
  }
  
  public String toString()
  {
    String str = "Deck of " + deck.size() + " cards: [ ";
    for (Card c : deck) {
      str = str + c.toString() + " ";
    }
    return str + "]";
  }
}
