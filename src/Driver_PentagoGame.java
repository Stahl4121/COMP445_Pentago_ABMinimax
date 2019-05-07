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
		for (int i = 0; i < 5; i++) {
			b.addMarble(i, 0, Status.BLACK);
		}
		b.addMarble(3, 0, Status.EMPTY);
		System.out.println(b);
		System.out.println(b.getBoardFavorability(Status.BLACK));
		//play (b);
	}

	/*
	 * 
	 * nextMove input format: "Row# Col# Rot#"
	 */
	private static void play(Board b) throws IOException {
		//records the number of moves
		int avgMoves = 0;
		int rounds = 0;
		
		//write the results here
		File f = new File("results.txt");
		
		//using this
		PrintWriter pw = new PrintWriter(f);
		
		//figure out what kinda rodeo this is
		System.out.println("Input 1 for a 2 person game, 2 to play our AI, or 3 to watch our AIs go at it");
		Scanner s = new Scanner(System.in);
		int gameType = s.nextInt();
		
		//our pentagoers
		Player miriam;
		Player sarah;
		
		//sarah and miriam fight. both are sentient.
		if (gameType == 1) {
			
			sarah = new Person(Status.WHITE);
			miriam = new Person(Status.BLACK);
		}
		
		//sarah battles miriam AI
		if (gameType == 2) {
			
			//get the level we're playing at
			System.out.println("Choose your difficulty by inputting a number 1 or greater");
			DEPTH = s.nextInt();
			pw.println("Depth: " + DEPTH);
			
			//initialize the players
			miriam = new AI(DEPTH, Status.BLACK);
			sarah = new Person(Status.WHITE);
		} 
		
		//miriam and sarah, neither sentient, fight
		else {
			
			//get the level we're playing at
			System.out.println("Choose your difficulty by inputting a number 1 or greater");
			DEPTH = s.nextInt();
			pw.println("Depth: " + DEPTH);
			
			//initialize the players
			miriam = new AI(DEPTH, Status.BLACK);
			sarah = new AI(DEPTH, Status.WHITE);
		}
				
        while(b.winner()==Status.EMPTY) {  
        	
        	//sarah moves
	        Move r = sarah.getMove(b);
	        
	        //print sarah's move
	        System.out.println(r);
	        
	        //make miriam's move
        	b.makeMove(r);
        	
	        // show sarah's move
	        System.out.println(b.toString());
	        
	        //count the AI's moves (specialize for person vs AI)
	        AI.numMoves = 0;
	        
	        //if Sarah won end now
	        if (b.winner() != Status.EMPTY) { 
	        	break;
	        }
	        
	        //miriam moves
	        Move m = miriam.getMove(b);
	        
	        //print miriams move
	        System.out.println(m);
	        
	        //make miriam's move
        	b.makeMove(m);
        	
        	// show AI's move
	        System.out.println(b.toString());
        	
        	//record moves traversed
			avgMoves += AI.numMoves;
			
			//record another round of the game
			rounds++;
			
			//print to the file
			pw.println("Move "+rounds+": " + AI.numMoves);
			
		}
        
        //declare a winner
        System.out.println(b.winner() + " was the winner");
		
        //print the conclusion and close the file
        pw.println("Average moves: " + (double)avgMoves/(rounds - 1));
        pw.close();
	}
}
