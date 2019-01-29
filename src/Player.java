public class Player
{
  private Deck deck;
  private Card currentCard;
  
  public Player(Deck deck)
  {
    this.deck = deck;
  }
  
  public int getDeckSize()
  {
    return this.deck.getSize();
  }
  
  public Deck getDeck()
  {
    return this.deck;
  }
  
  public void setDeck(Deck deck)
  {
    this.deck = deck;
  }
  
  public Card getCurrentCard()
  {
    return this.currentCard;
  }
  
  public void setCurrentCard(Card currentCard)
  {
    this.currentCard = currentCard;
  }
}
