/**
 *	Deacom Coding Challenge
 *	author: Matt Robinson
 *	January, 2019
 */

/**
 *	a class to represent cards
 */

public class Card {

	/**
	 * constant integers which help us assign and sort through the cards's suits
	 */
	private static final int CLUBS = 1, HEARTS = 2, SPADES = 3, DIAMONDS = 4;

	/**
	 * an integer representing the cards suit: CLUBS, DIAMONDS, HEARTS, SPADES
	 */
	private int suit; 

	/**
	 * an integer 2-14, representing the cards value, 11=jack, 12=queen, 13=king, 14=ace
	 */
	private int rank;    


	public Card(int suit, int rank) {
		this.suit = suit;
		this.rank = rank;
	}

	/**
	 * @return the card's suit
	 */
	public int getSuit() {
		return suit;
	}

	/**
	 * @return the card's rank
	 */
	public int getRank() {
		return rank;
	}
	

	/**
	 * @return the card in a String form
	 */
	public String toString() {
		String st = "S"; // spades
		String rnk = "" + this.rank;
		if (suit == CLUBS ) { 	//clubs
			st = "C";
		}else if (suit == DIAMONDS) { // diamonds
			st = "D";
		}else if (suit == HEARTS) { // hearts
			st = "H";
		}
		if (this.rank == 11) { // jack
			rnk = "J";
		}else if (this.rank == 12) { // queen
			rnk = "Q";
		}else if (this.rank == 13) { // king
			rnk = "K";
		}else if (this.rank == 14) { // ace
			rnk = "A";
		}
		return rnk + st;
	}

}
