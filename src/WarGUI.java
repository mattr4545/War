/**
 * Deacom Coding Challenge
 * @author Matt Robinson
 * January, 2019
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * class to display the game 
 */
public class WarGUI extends JFrame
{
	
	// location constants
	private static final Color DARK_GREEN = new Color(0, 175, 0);
	private static final int WIN_SIZE = 900;
	private static final int WAR_OFFSET = 50;
	private static final int P1DECK_X = 100;
	private static final int P2DECK_X = 650;
	private static final int P1CARD_X = 300;
	private static final int P2CARD_X = 450;
	private static final int P1CARD_Y = 425;
	private static final int P2CARD_Y = 375;
	private static final int INFO_Y = 800;
	private static final int INFO_X = 250;
	
	// the game for our logic and statemachine
	private static Game game = new Game(new CardComparator());
	
	// for autorunning
	protected static boolean autoRun = false;
	protected static boolean running = false;

	public WarGUI()
	{
		super("War");
		setSize(WIN_SIZE, WIN_SIZE);
		setVisible(true);
		getContentPane().add(new WarPanel());
		setResizable(false);
		setDefaultCloseOperation(3);

		pack();
	}

	public static void main(String[] args)
	{
		new WarGUI();
	}

	private class WarPanel extends JPanel implements KeyListener
	{
		// for key input
		private char k = 'e';
		
		// keeps track of the state we are in and the one we will go to
		private int nextState;
		private int currState = Game.START;

		// consturctor that sets up the keylistener and kicks off the game
		public WarPanel()
		{
			setPreferredSize(new Dimension(WIN_SIZE, WIN_SIZE));
			addKeyListener(this);
			this.nextState = game.start();
		}

		// method keeps focus on the window for keyboard input
		public void addNotify()
		{
			super.addNotify();
			requestFocus();
		}


		// helper for paint method
		// draws the players cards in the center
		private void drawPlayerCards(Graphics2D g2d)
		{
			if ((game.getP1().getCurrentCard() != null) || (game.getP2().getCurrentCard() != null))
			{
				BufferedImage p1Card = null;
				String p1ImgName = WarGUI.game.getP1().getCurrentCard().toString() + ".png";

				BufferedImage p2Card = null;
				String p2ImgName = WarGUI.game.getP2().getCurrentCard().toString() + ".png";
				try
				{
					p1Card = ImageIO.read(new File("images/" + p1ImgName));
					p2Card = ImageIO.read(new File("images/" + p2ImgName));
				}
				catch (IOException e)
				{
					System.err.println("Error loading image");
				}
				Image p1CardIMG = p1Card.getScaledInstance(100, 150, 1);
				Image p2CardIMG = p2Card.getScaledInstance(100, 150, 1);

				g2d.drawImage(p1CardIMG, P1CARD_X, P1CARD_Y, null);
				g2d.drawImage(p2CardIMG, P2CARD_X, P2CARD_Y, null);
			}
		}

		// helper for paint method
		// draws the decks
		private void drawDecks(Graphics2D g2d, Image img)
		{
			Deck p1Deck = WarGUI.game.getP1().getDeck();
			Deck p2Deck = WarGUI.game.getP2().getDeck();
			if ((p1Deck != null) && (!p1Deck.isEmpty())) {
				g2d.drawImage(img, P1DECK_X, P2DECK_X, null);
			}
			if ((p2Deck != null) && (!p2Deck.isEmpty())) {
				g2d.drawImage(img, P2DECK_X, P1DECK_X, null);
			}
			g2d.setFont(new Font("TimesRoman", 0, 20));
			g2d.drawString(WarGUI.game.getP1().getDeckSize()+ "", P1DECK_X, P2DECK_X);
			g2d.drawString(WarGUI.game.getP2().getDeckSize()+ "", P2DECK_X, P1DECK_X);
		}

		
		// our render method that is recalled several times
		public void paint(Graphics g)
		{
			Graphics2D g2d = (Graphics2D)g;

			// get the deck back image
			BufferedImage deckBack = null;
			try
			{
				deckBack = ImageIO.read(new File("images/blue_back.png"));
			}
			catch (IOException e)
			{
				System.err.println("Error loading image");
			}
			Image deckBackIMG = deckBack.getScaledInstance(100, 150, 1);

			g2d.setColor(WarGUI.DARK_GREEN);
			g2d.fillRect(0, 0, WIN_SIZE+50, WIN_SIZE+50);
			g2d.setColor(Color.BLACK);
			g2d.setFont(new Font("TimesRoman", 0, 20));

			// decks drawn in every state
			drawDecks(g2d, deckBackIMG);
			if (this.currState == Game.START)
			{
				// draw info text to the use
				g2d.drawString(Game.INTRO1, INFO_X, 450);
				g2d.drawString(Game.INTRO2, INFO_X, 475);
				g2d.drawString(Game.INTRO3, INFO_X, 500);
				g2d.drawString(Game.INTRO4, INFO_X, 525);
				g2d.drawString(Game.INTRO5, INFO_X, 550);
				g2d.drawString(Game.INTRO6, INFO_X, 575);
				g2d.drawString(Game.INTRO7, INFO_X, 600);
				g2d.drawString(Game.INTRO8, INFO_X, 625);
				g2d.drawString(Game.INTRO9, INFO_X, 650);
			 
				g2d.drawString(Game.SPACE1, INFO_X, INFO_Y);
			}
			else if (this.currState ==  Game.GAME_OVER)
			{
				// draw text about the game and invite player to play again
				g2d.drawString("The game is over. It lasted " + game.getRound() + " rounds.", 250, 750);
				g2d.drawString("You " + Game.roundWin + " the game!", 250, 775);
				g2d.drawString(Game.SPACE5, 250, 800);
				WarGUI.running = false;
			}
			else if (this.currState == Game.CLEAR)
			{
				// clear the board and display who won
				g2d.drawString("You " + Game.roundWin + " the round!", 250, 775);
				g2d.drawString(Game.SPACE3, 250, 800);
			}
			else if (this.currState == Game.MID_CARDS)
			{
				// draw the players cards in the middle
				drawPlayerCards(g2d);
				g2d.drawString(Game.SPACE2, 250, 800);
			}
			else if (this.currState == Game.WAR)
			{
				// draw the players card in the middle and the war cards on top
				drawPlayerCards(g2d);
				for (int i = 1; i <= 3; i++)
				{
					g2d.drawImage(deckBackIMG, P1CARD_X - WAR_OFFSET * i, P1CARD_Y, null);
					g2d.drawImage(deckBackIMG, P2CARD_X + WAR_OFFSET * i, P2CARD_Y, null);
				}
				g2d.drawString(Game.WAR_TEXT, 250, 750);
				g2d.drawString(Game.SPACE4, 250, 800);
			}
			else if (this.currState == Game.WAR_END)
			{
				// draw the war cards then each players 4th card on top
				for (int i = 1; i <= 3; i++)
				{
					g2d.drawImage(deckBackIMG, P1CARD_X - WAR_OFFSET * i, P1CARD_Y, null);
					g2d.drawImage(deckBackIMG, P2CARD_X + WAR_OFFSET * i, P2CARD_Y, null);
				}
				drawPlayerCards(g2d);
				g2d.drawString(Game.SPACE2, 250, 800);
			}
			
			// if autorun immediately advance to next state and repaint
			if ((autoRun) && (running))
			{
				this.currState = this.nextState;
				this.nextState = game.stateMachine(this.nextState);
				repaint();
			}
		}

		public void keyPressed(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {}

		
		// handles our keyboard input
		public void keyTyped(KeyEvent e)
		{
			this.k = e.getKeyChar();
			// space key - advance to the next state / pause or play autorun
			if (this.k == ' ')
			{
				this.currState = this.nextState;
				this.nextState = WarGUI.game.stateMachine(this.nextState);
				repaint();
				
				if ((autoRun) && (!running)) {
					running = true;
				} else if ((autoRun) && (running)) {
					running = false;
				}
			}
			// escape key - exit
			else if (this.k == '\033')
			{
				System.out.println("<Exiting>");
				System.exit(0);
			}
			// backspace - toggle autorun
			else if (this.k == '\b')
			{
				if (autoRun)
				{
					autoRun = false;
					running = false;
					System.out.println("Autorun disabled");
				}
				else
				{
					autoRun = true;
					System.out.println("Autorun enabled");
				}
			}
		}
	}
}
