import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStreamReader; 

public class Driver_PentagoGame {
	private static int DEPTH = 1;
	public static void main(String[] args) throws IOException {
		//PROJECT TODO (Not necessarily in this class):
		Board b = new Board();
		//System.out.println("Favorablility: " + b.getBoardFavorability());
		b.addMarble(0, 4, Status.BLACK);
		b.addMarble(3, 3, Status.WHITE);
		b.addMarble(2, 2, Status.WHITE);
		b.addMarble(5, 0, Status.BLACK);
		b.addMarble(1, 1, Status.WHITE);
		b.addMarble(5, 4, Status.WHITE);
		System.out.println(b);
		
		for (int i = 1; i < 5; i++) {
			System.out.println("Q"+i+" rotated counterclockwise");
			b.rotate(i, false);
			System.out.println(b);
			System.out.println("Q"+i+" rotated clockwise");
			b.rotate(i, true);
			System.out.println(b);
		}
		//Create Pentago Game Structure (Board, moves, inputs, check win/lose conditions) {<- done i think}
		//Create UI for game? (Maybe not too much harder than doing a console printed UI)
		
		//play(b);
		//Enable user to make inputs (get 2-player working)
		//Write minimax
		//Write alpha-beta 
		//Profit
	}

	/*
	 * 
	 * nextMove input format: "Row# Col# Rot#"
	 */
	private static void play(Board b) throws IOException {
		
		Person sarah = new Person(Status.WHITE);
		Person miriam = new Person(Status.BLACK);
		//AI ai = new AI(DEPTH, Status.BLACK);
		        
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
