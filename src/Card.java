/**
 * Deacom Coding Challenge
 * @author Matt Robinson
 * January, 2019
 */

/**
 * a simple class to represent a card
 */
public class Card
{
  private static final int CLUBS = 1;
  private static final int HEARTS = 2;
  private static final int SPADES = 3;
  private static final int DIAMONDS = 4;
  private int suit;
  private int rank;
  
  public Card(int suit, int rank)
  {
    this.suit = suit;
    this.rank = rank;
  }
  
  public int getSuit()
  {
    return this.suit;
  }
  
  public int getRank()
  {
    return this.rank;
  }
  
  public String toString()
  {
    String st = "S";
    String rnk = this.rank + "";
    if (this.suit == CLUBS) {
      st = "C";
    } else if (this.suit == DIAMONDS) {
      st = "D";
    } else if (this.suit == HEARTS) {
      st = "H";
    }
    if (this.rank == 11) {
      rnk = "J";
    } else if (this.rank == 12) {
      rnk = "Q";
    } else if (this.rank == 13) {
      rnk = "K";
    } else if (this.rank == 14) {
      rnk = "A";
    }
    return rnk + st;
  }
}
