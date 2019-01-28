import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class WarUI extends JFrame
{

	private static Color BORDER_GREEN = new Color(0,100,0);
	private static Color DARK_GREEN = new Color(0,175,0);
	private static int WIN_SIZE = 900;
	
	private Game game = new Game( new CardComparator());

	public WarUI() {
		super("War");
		setSize(WIN_SIZE, WIN_SIZE);
		setVisible(true);
        getContentPane().add(new MyPanel());
        this.setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        
//        game.start();
//		game.play();
	
	}

	public static void main(String [] args)
	{   
		new WarUI();
	}

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.setBackground(DARK_GREEN);
		g2d.setColor(DARK_GREEN);
		g2d.fillRect(0, 0, WIN_SIZE+50, WIN_SIZE+50);

		BufferedImage deckBack = null;
		BufferedImage player2Deck = null;
		try {
			deckBack = ImageIO.read(new File("images/blue_back.png"));
		} catch (IOException e) {
			System.err.println("Error loading image");
		}
		g2d.setBackground(DARK_GREEN);
		Image deckBackIMG = deckBack.getScaledInstance(100, 150, Image.SCALE_DEFAULT);
		g2d.drawImage(deckBackIMG, 100, 650, null);
		g2d.drawImage(deckBackIMG, 650, 100, null);
		//		repaint();
	}
	
	
	private class MyPanel extends JPanel implements KeyListener {
	    private char k = 'e';

	    public MyPanel() {
	        this.setPreferredSize(new Dimension(WIN_SIZE, WIN_SIZE));
	        addKeyListener(this);
	    }

	    public void addNotify() {
	        super.addNotify();
	        requestFocus();
	    }

	    public void paintComponent(Graphics g) {
	        g.clearRect(0, 0, getWidth(), getHeight());
	        g.drawString("the key that pressed is " + k, 250, 250);
	    }

	    public void keyPressed(KeyEvent e) { }
	    public void keyReleased(KeyEvent e) { }
	    public void keyTyped(KeyEvent e) {
	        k = e.getKeyChar();
	        if (k == (' ')) {
	        	
	        }
	        repaint();
	    }
	}
}


























