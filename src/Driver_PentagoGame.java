import java.io.BufferedReader;
import java.io.File;
import java.io.IOException; 
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
		File f = new File("results.txt");
		PrintWriter pw = new PrintWriter(f);
		System.out.println("Input 1 to play our AI, 2 for a 2 player game");
		Scanner s = new Scanner(System.in);
		Player miriam;
		if (s.nextInt() == 1) {
			System.out.println("Choose your difficulty by inputting a number 1 or greater");
			DEPTH = s.nextInt();
			pw.println("Depth: " + DEPTH);
			miriam = new AI(DEPTH, Status.BLACK);
		} else {
			miriam = new Person(Status.BLACK);
		}
		Person sarah = new Person(Status.WHITE);		
		int avgMoves = 0;
		int count = 0;
        while(b.winner()==Status.EMPTY) {        	
        	sarah.makeMove(b);
	        // show player's move
	        System.out.println(b.toString());
	        //AI makes a move
	        //ai.makeMove(b);
	        AI.numMoves = 0;
			miriam.makeMove(b);
			System.out.println(AI.numMoves);
			avgMoves += AI.numMoves;
			count++;
			pw.println("Move "+count+": " + AI.numMoves);
			// show AI's move
	        System.out.println(b.toString());
		}
        pw.println("Average moves: " + (double)avgMoves/(count - 1));
        System.out.println(b.winner() + " was the winner");
		pw.close();
	}
	
}
