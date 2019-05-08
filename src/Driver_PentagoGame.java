import java.io.BufferedReader;
import java.io.File;
import java.io.IOException; 
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
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
		
		//figure out what kinda rodeo this is
		System.out.println("Input 1 for a 2 person game, 2 to play our AI, or 3 to watch our AIs go at it");
		Scanner s = new Scanner(System.in);
		int gameType = s.nextInt();
		
		//our pentagoers
		Player miriam;
		Player sarah;
		
		if (gameType != 3) {
			System.out.println("\nTo move, use this format: row column rotation");
			System.out.println("rotation is an integer between 1 and 4 denoting quadrants");
			System.out.println("2 1\n3 4\nWhere a negative number means rotating counterclockwise\n");
		}
		
		//sarah and miriam fight. both are sentient.
		if (gameType == 1) {
			
			sarah = new Person(Status.BLACK);
			miriam = new Person(Status.WHITE);
		}
		
		//sarah battles miriam AI
		if (gameType == 2) {
			
			//get the level we're playing at
			System.out.println("Choose your difficulty by inputting a number 1 or greater");
			DEPTH = s.nextInt();
			
			//initialize the players
			miriam = new AI(DEPTH, Status.WHITE);
			sarah = new Person(Status.BLACK);
		} 
		
		//miriam and sarah, neither sentient, fight
		else {
			
			//get the level we're playing at
			System.out.println("Choose your difficulty by inputting a number 1 or greater");
			DEPTH = s.nextInt();
			
			//initialize the players
			miriam = new AI(DEPTH, Status.WHITE);
			sarah = new AI(DEPTH, Status.BLACK);
		}
				
        while(b.winner()==Status.EMPTY) {  
        	 //get start time
	        double t = System.nanoTime();
	        
	        //miriam moves
	        Move m = sarah.getMove(b);
	        
	        //print miriams move
	        System.out.println(m);
	        
	        //make miriam's move
        	b.makeMove(m);
        	
        	// show AI's move
	        System.out.println(b.toString());
        	
	        
	        //if Sarah won end now
	        if (b.winner() != Status.EMPTY) { 
	        	break;
	        }
	        
	      	//sarah moves
	        Move r = miriam.getMove(b);
	        
	        //print sarah's move
	        System.out.println(r);
	        
	        //make miriam's move
        	b.makeMove(r);
        	
	        // show sarah's move
	        System.out.println(b.toString());
			
		}
        
        //declare a winner
        System.out.println(b.winner() + " was the winner");
	}
}
