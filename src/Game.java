/**
 * Deacom Coding Challenge
 * @author Matt Robinson
 * January, 2019
 */

/**
 * class that handles the state switching and logic of War
 */
public class Game
{

	// our state constants, 
	// each int represents a state the we can go to in the stateMachine method and in the render method of WarGUI
	public static final int START = 0;
	public static final int GAME_OVER = 1;
	public static final int CLEAR = 2;
	public static final int MID_CARDS = 3;
	public static final int WAR = 4;
	public static final int WAR_END = 5;

	// for creating the initial deck
	private static int NUM_SUITS = 4;
	private static int NUM_RANKS = 13;

	// message consts because they are same for ui and console
	public static final String INTRO1 = "Welcome to War!";
	public static final String INTRO2 = "Each player has 26 cards in their deck to start.";
	public static final String INTRO3 = "Each round both players present their top card.";
	public static final String INTRO4 = "Whoever has the highest card wins the round, and gains the other player's card.";
	public static final String INTRO5 = "Aces are considered high.";
	public static final String INTRO6 = "When there is a tie, its WAR!!!";
	public static final String INTRO7 = "Press the backspace key enable and disable autorun!";
	public static final String INTRO8 = "In autorun, space acts as a play and pause button.";
	public static final String INTRO9 = "Press escape at any time to quit.";

	public static final String SPACE1 = "Press the space bar to start!";
	public static final String SPACE2 = "Press the space bar to determine the round winner.";
	public static final String SPACE3 = "Press the space bar to play your next card.";
	public static final String SPACE4 = "Press the space bar to wage war!";
	public static final String SPACE5 = "Press the space bar to play again!";
	public static final String WAR_TEXT = "Its WAR!";
	// end constants

	// tells the ui who won
	public static String roundWin;

	private Player p1;
	private Player p2;

	// games current round
	private int round = 0;

	// the comaprator for cards
	private CardComparator comparator;

	// the middle deck that the winner of the round gets to add to their deck
	private Deck middleDeck = new Deck();

	public Game(CardComparator cc)
	{
		this.comparator = cc;
	}

	// starts the statemachine in the start state
	public int start()
	{
		return stateMachine(START);
	}


	// this method has 6 blocks, one for each state
	public int stateMachine(int state)
	{
		if (state == START)
		{
			setUp();
			return MID_CARDS;
		}
		else if (state == GAME_OVER)
		{
			// determine winner, add final cards to their pile, and set roundWin to the winner
			System.out.println("The game is over. It lasted " + getRound() + " rounds.");
			if (getWinner() == 2) {
				this.p2.getDeck().addDeck(this.middleDeck);
				System.out.println("Player 2 wins!");
				roundWin = "lost";
			} else {
				this.p1.getDeck().addDeck(this.middleDeck);
				System.out.println("Player 1 wins!");
				roundWin = "won";
			}
			
			// ask player to play again
			System.out.println(SPACE5);
			return START;
		}
		else if (state == CLEAR)
		{
			// whoever won the last round gets the middleDeck
			if (roundWin.equals("won")) {
				this.p1.getDeck().addDeck(this.middleDeck);
			} else {
				this.p2.getDeck().addDeck(this.middleDeck);
			}
			System.out.println(SPACE3);
			return MID_CARDS;
		}
		else if (state == MID_CARDS)
		{
			System.out.println("-------------------------");
			this.round += 1;

			// print deck sizes
			int p1Size = this.p1.getDeckSize();
			String card_s = "cards";
			if (p1Size == 1) {
				card_s = "card";
			}
			System.out.println("Player 1's deck has " + this.p1.getDeckSize() + " " + card_s + " left.");

			int p2Size = this.p2.getDeckSize();
			card_s = "cards";
			if (p2Size == 1) {
				card_s = "card";
			}
			System.out.println("Player 2's deck has " + this.p2.getDeckSize() + " " + card_s + " left.");

			// flip the next card for each player, assign it to the player and make the middle deck
			Card p1Card = this.p1.getDeck().flip();
			Card p2Card = this.p2.getDeck().flip();
			// if either player has no cards, send to gameover state
			if (gameOver()) {
				return GAME_OVER;
			}
			this.p1.setCurrentCard(p1Card);
			this.p2.setCurrentCard(p2Card);

			System.out.println("Player 1 drew the " + p1Card + ".");
			System.out.println("Player 2 drew the " + p2Card + ".");

			this.middleDeck = new Deck(new Card[] { p1Card, p2Card });
			
			// compare the cards
			if (this.comparator.compare(p1Card, p2Card) == 0) 
			{
				System.out.println(WAR_TEXT);
				return WAR;
			}
			if (this.comparator.compare(p1Card, p2Card) > 0)
			{
				roundWin = "won";
				System.out.println("Player 1 wins round " + this.round + ".");
				System.out.println("Hit space to take your cards!");
			}
			else if (this.comparator.compare(p1Card, p2Card) < 0)
			{
				roundWin = "lost";
				System.out.println("Player 2 wins round " + this.round + ".");
				System.out.println("Hit space to continue.");
			}
			// go to clear state after a normal comparison
			return CLEAR;
		}
		else if (state == WAR)
		{
			// flip the next 3 cards for each player
			// as if statements so that if a player runs our of cards we can send to gameOver
			if ((!middleDeck.addCard(p1.getDeck().flip())) || 
					(!middleDeck.addCard(p1.getDeck().flip())) || 
					(!middleDeck.addCard(p1.getDeck().flip())))
			{
				System.out.println("Player 1 ran out of cards!");
				return GAME_OVER;
			}
			if ((!middleDeck.addCard(p2.getDeck().flip())) || 
					(!middleDeck.addCard(p2.getDeck().flip())) || 
					(!middleDeck.addCard(p2.getDeck().flip())))
			{
				System.out.println("Player 2 ran out of cards!");
				return GAME_OVER;
			}
			return WAR_END;
		}
		else { // state = WAR_END
			
			// get each players 4th card and compare them
			Card p1Card = this.p1.getDeck().flip();
			Card p2Card = this.p2.getDeck().flip();
			if (gameOver()) {
				return GAME_OVER;
			}
			
			// set as players current cards and add those cards to the middle deck
			this.p1.setCurrentCard(p1Card);
			this.p2.setCurrentCard(p2Card);
			this.middleDeck.addCard(p1Card);
			this.middleDeck.addCard(p2Card);

			System.out.println("Player 1's 4th card was the " + p1Card + ".");
			System.out.println("Player 2's 4th card was the " + p2Card + ".");
			
			// compare cards
			if (this.comparator.compare(p1Card, p2Card) == 0) {
				return WAR;
			}
			else if (this.comparator.compare(p1Card, p2Card) > 0)
			{
				roundWin = "won";
				System.out.println("Player 1 wins round " + this.round + ".");
			}
			else if (this.comparator.compare(p1Card, p2Card) < 0)
			{
				roundWin = "lost";
				System.out.println("Player 2 wins round " + this.round + ".");
			}
			
			// go to clear if normal comparison
			return CLEAR;
		}
	}

	
	// setup - displays messages to users and creates, shuffles and splits a new deck
	public void setUp()
	{
		System.out.println(INTRO1);
		System.out.println(INTRO2);
		System.out.println(INTRO3);
		System.out.println(INTRO4);
		System.out.println(INTRO5);
		System.out.println(INTRO6);
		System.out.println(INTRO7);
		System.out.println(INTRO8);
		System.out.println(INTRO9);

		// create deck
		Deck initDeck = new Deck();
		for (int i = 1; i <= NUM_SUITS; i++) {
			for (int j = 2; j <= NUM_RANKS + 1; j++) {
				initDeck.addCard(new Card(i, j));
			}
		}
		
		// shuffle it
		initDeck.shuffle();

		// split it and give each player half
		Deck newDeck = initDeck.split();

		this.p1 = new Player(initDeck);
		this.p2 = new Player(newDeck);
	}

	// check if either player has no cards
	private boolean gameOver()
	{
		return (this.p1.getDeck().isEmpty()) || (this.p2.getDeck().isEmpty());
	}

	// determine who won
	public int getWinner()
	{
		if (this.p1.getDeck().isEmpty()) {
			return 2;
		}
		return 1;
	}

	public Player getP1()
	{
		return this.p1;
	}

	public Player getP2()
	{
		return this.p2;
	}

	public int getRound()
	{
		return this.round;
	}
}
