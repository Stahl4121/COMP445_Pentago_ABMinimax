import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStreamReader;
import java.util.Scanner;

public class Driver_PentagoGame {
	private static int DEPTH = 1;
	public static void main(String[] args) throws IOException {
		//PROJECT TODO (Not necessarily in this class):
		Board b = new Board();
		play (b);
	}

	/*
	 * 
	 * nextMove input format: "Row# Col# Rot#"
	 */
	private static void play(Board b) throws IOException {
		System.out.println("Input 1 to play our AI, 2 for a 2 player game");
		Scanner s = new Scanner(System.in);
		Player miriam;
		if (s.nextInt() == 1) {
			miriam = new AI(DEPTH, Status.BLACK);
		} else {
			miriam = new Person(Status.BLACK);
		}
		Person sarah = new Person(Status.WHITE);		
		        
        while(b.winner()==Status.EMPTY) {        	
        	sarah.makeMove(b);
	        // show player's move
	        System.out.println(b.toString());
	        //AI makes a move
	        //ai.makeMove(b);
			miriam.makeMove(b);
			// show AI's move
	        System.out.println(b.toString());
		}
        System.out.println(b.winner() + " was the winner");
		
	}
	
}
