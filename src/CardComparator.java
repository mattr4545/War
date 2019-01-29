/**
 * Deacom Coding Challenge
 * @author Matt Robinson
 * January, 2019
 */

import java.util.Comparator;

/**
 * basic class to compare cards
 */
public class CardComparator implements Comparator<Card>
{
  public int compare(Card c1, Card c2)
  {
    return c1.getRank() - c2.getRank();
  }
}
