/**
 *	Deacom Coding Challenge
 *	author: Matt Robinson
 *	January, 2019
 */

/**
 *	a class to compare cards
 */

import java.util.Comparator;

public class CardComparator implements Comparator<Card>{

	
	/**
	 * @return  a positive value if card, c1, is greater than card, c2
	 * @return  a negative value if card, c1, is less than card, c2
	 * @return  0 if there is a war, (cards are equal in rank)
	 */
	@Override
	public int compare(Card c1, Card c2) {
		return c1.getRank() - c2.getRank();
		
	}

}
