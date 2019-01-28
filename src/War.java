import java.util.Scanner;

/**
 *	Deacom Coding Challenge
 *	author: Matt Robinson
 *	January, 2019
 */


/**
 *	this class is the testing class, has a main, that runs the game
 */
public class War {


	public static void main(String[] args) {
		
		Game game = new Game(new CardComparator());
		Scanner s = new Scanner(System.in);
		while (true) {
			game.play();
			if (s.nextLine().equals( "q") ){
				break;
			}
			
		}
			
		
	} 

}
