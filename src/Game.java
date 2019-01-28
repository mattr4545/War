/**
 *	Deacom Coding Challenge
 *	author: Matt Robinson
 *	January, 2019
 */


/**
 *	a class to represent the game of war
 * 	includes the main gameplay loop and logic
 */
public class Game {
	
	private static int NUM_SUITS = 4;
	private static int NUM_RANKS = 13;
	
	private Player p1;
	private Player p2;
	
	private int round = 0;
	
	private CardComparator comparator;
	
	public Game(CardComparator cc) {
		comparator = cc;
	}
	
	
//    f.addKeyListener(new KeyListener() {
//        @Override
//        public void keyTyped(KeyEvent e) {
//        }
//
//        @Override
//        public void keyPressed(KeyEvent e) {
//            System.out.println("Key pressed code=" + e.getKeyCode() + ", char=" + e.getKeyChar());
//        }
//
//        @Override
//        public void keyReleased(KeyEvent e) {
//        }
//    });
//	
	public void play() {
		
		System.out.println("Welcome to War!");
		System.out.println("Each player has 26 cards in their deck to start.");
		System.out.println("Each round both players present their top card.");
		System.out.println("Whoever has the highest card wins the round and gains the other player's card.");
		System.out.println("When there is a tie, its WAR!");
		
		setUp();
		gameLoop:
		while(!gameOver()) { //game not over
			round++;
			// get deck sizes and print them to the console
			int p1Size = p1.getDeckSize();
			String card_s = "cards"; // var used for 'card' or 'cards' depending if players deck has 1 or more cards
			if (p1Size == 1)
				card_s = "card";
			System.out.println("Player 1's deck has " + p1.getDeckSize() + " " + card_s + " left.");
			
			int p2Size = p2.getDeckSize();
			card_s = "cards";
			if (p2Size == 1)
				card_s = "card";	
			System.out.println("Player 2's deck has " + p2.getDeckSize() + " " + card_s + " left.");
			
			// get the top card of each deck
			Card p1Card = p1.getDeck().flip();
			Card p2Card = p2.getDeck().flip();
			// the small deck of 2 cards that will be added to the winning players deck
			if (gameOver()) { // one of the flips returned null, so one of the decks is empty, end this loop iteration
				continue;
			}
			System.out.println("Player 1 drew the " + p1Card + ".");
			System.out.println("Player 2 drew the " + p2Card + ".");
			Deck smallDeck = new Deck(new Card[]{ p1Card, p2Card }); 
			while(comparator.compare(p1Card, p2Card) == 0) { // only enters this loop if a war
				System.out.println("WAR! Both players put 3 extra cards in the middle and the 4th card determines the victor.");
				// add the next 3 cards for each player into the smallDeck
				// do this as if statements, because deck.addCard returns false if the card is null 
				// this means that if one of the players runs out of cards while flipping the next 3, end the gameloop
				if (!(smallDeck.addCard(p1.getDeck().flip()) && 
					smallDeck.addCard(p1.getDeck().flip())   &&
					smallDeck.addCard(p1.getDeck().flip()))) {
						System.out.println("Player 1 ran out of cards!");
						break gameLoop;
				}
					
				if (!(smallDeck.addCard(p2.getDeck().flip()) && 
					smallDeck.addCard(p2.getDeck().flip())   &&
					smallDeck.addCard(p2.getDeck().flip()))) {
						System.out.println("Player 2 ran out of cards!");
						break gameLoop;
				}
				
				// get the 4th card of each deck for the comparison after the war cards are down and put them on the smallDeck
				p1Card = p1.getDeck().flip();
				p2Card = p2.getDeck().flip();
				// a player ran out of cards, end this loop iteration and end the game
				if (p1Card == null ) { 
					System.out.println("Player 1 ran out of cards!");
					break gameLoop;
				}
				if (p2Card == null ) { 
					System.out.println("Player 2 ran out of cards!");
					break gameLoop;
				}
				smallDeck.addCard(p1Card);
				smallDeck.addCard(p2Card);
				
				System.out.println("Player 1's 4th card was the " + p1Card + ".");
				System.out.println("Player 2's 4th card was the " + p2Card + ".");
			} 
			// someone won, there is no tie
			if (comparator.compare(p1Card, p2Card) > 0) { // p1 wins
				p1.getDeck().addDeck(smallDeck);
				System.out.println("Player 1 wins round " + round + ".");
			}else if(comparator.compare(p1Card, p2Card) < 0) { // p2 wins
				p2.getDeck().addDeck(smallDeck);
				System.out.println("Player 2 wins round " + round + ".");
			}
			System.out.println("-------------------------");
		} // end gameloop
		
		System.out.println("The game is over. It lasted " + this.round + " rounds.");
		if (p1.getDeck().isEmpty()) {
			System.out.println("Player 2 wins!");
		}else {
			System.out.println("Player 1 wins!");
		}
		
		
	}
	
	/**
	 * sets up the initial state of a new game of War
	 */
	private void setUp() {
		// create the initial deck of 52 cards, 13 for each suit
		Deck initDeck = new Deck();
		for(int i = 1; i <= NUM_SUITS; i++) {
			for(int j = 2; j <= NUM_RANKS + 1; j++) { // + one because we start at 2 - this is because Ace is considered high
				initDeck.addCard(new Card(i,j));
			}
		}
		// shuffle the initial deck
		initDeck.shuffle();
		
		// split the deck
		Deck newDeck = initDeck.split();
		
		p1 = new Player(initDeck);
		p2 = new Player(newDeck);
		
	}
	
	
	/**
	 * @return true if the game is done
	 */
	private boolean gameOver() {
		return p1.getDeck().isEmpty() || p2.getDeck().isEmpty();
	}
}
