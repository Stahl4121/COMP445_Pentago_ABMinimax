import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStreamReader; 

public class Driver_PentagoGame {
	private static int DEPTH = 1;
	public static void main(String[] args) throws IOException {
		//PROJECT TODO (Not necessarily in this class):
		//demonstrate rotation
		Board b = new Board();
		b.addMarble(0, 4, Status.BLACK);
		b.addMarble(3, 3, Status.WHITE);
		b.addMarble(2, 2, Status.WHITE);
		b.addMarble(5, 0, Status.BLACK);
		b.addMarble(1, 1, Status.WHITE);
		b.addMarble(5, 4, Status.WHITE);
		
		System.out.println("The original board\n" + b);
		
		for (int i = 1; i < 5; i++) {
			System.out.println("Q"+i+" rotated counterclockwise");
			b.rotate(i, false);
			System.out.println(b);
			System.out.println("Q"+i+" rotated clockwise");
			b.rotate(i, true);
			System.out.println(b);
		}
		
		//demonstrate wins
		b.clear();
		/*
		 * 0 0 0 0 0 0 (the row of ones could be any of the rows)
		 * 1 1 1 1 1 0
		 * 0 0 0 0 0 0
		 * 0 0 0 0 0 0
		 * 0 0 0 0 0 0
		 * 0 0 0 0 0 0
		 */
		for (int r = 0; r < 6; r++) {
			for (int c = 0; c < 6; c++) {
				if (c < 5) {
					b.addMarble(r, c, Status.BLACK);
				}
			}
			System.out.print(b);
			System.out.println(b.winner() + "\n");
			b.clear();
		}
		/*
		 * 0 0 0 0 0 0 (the row of ones could be any of the rows)
		 * 0 1 1 1 1 1
		 * 0 0 0 0 0 0
		 * 0 0 0 0 0 0
		 * 0 0 0 0 0 0
		 * 0 0 0 0 0 0
		 */
		for (int r = 0; r < 6; r++) {
			for (int c = 0; c < 6; c++) {
				if (c > 0) {
					b.addMarble(r, c, Status.BLACK);
				}
			}
			System.out.print(b);
			System.out.println(b.winner() + "\n");
			b.clear();
		}
		/*
		 * 0 1 0 0 0 0 (the column of ones could be any of the columns)
		 * 0 1 0 0 0 0
		 * 0 1 0 0 0 0
		 * 0 1 0 0 0 0
		 * 0 1 0 0 0 0
		 * 0 0 0 0 0 0
		 */
		for (int r = 0; r < 6; r++) {
			for (int c = 0; c < 6; c++) {
				if (c < 5) {
					b.addMarble(c, r, Status.BLACK);
				}
			}
			System.out.print(b);
			System.out.println(b.winner() + "\n");
			b.clear();
		}
		/*
		 * 0 0 0 0 0 0 (the column of ones could be any of the columns)
		 * 0 1 0 0 0 0
		 * 0 1 0 0 0 0
		 * 0 1 0 0 0 0
		 * 0 1 0 0 0 0
		 * 0 1 0 0 0 0
		 */
		for (int r = 0; r < 6; r++) {
			for (int c = 0; c < 6; c++) {
				if (c > 0) {
					b.addMarble(c, r, Status.BLACK);
				}
			}
			System.out.print(b);
			System.out.println(b.winner() + "\n");
			b.clear();
		}
		/*
		 * 0 0 0 0 0 0
		 * 0 1 0 0 0 0
		 * 0 0 1 0 0 0
		 * 0 0 0 1 0 0
		 * 0 0 0 0 1 0
		 * 0 0 0 0 0 1
		 */
		for (int r = 0; r < 6; r++) {
			for (int c = 0; c < 6; c++) {
				if (r == c) {
					if (r > 0) {
						b.addMarble(c, r, Status.BLACK);
					}
				}
			}
		}
		System.out.print(b);
		System.out.println(b.winner() + "\n");
		b.clear();
		/*
		 * 1 0 0 0 0 0
		 * 0 1 0 0 0 0
		 * 0 0 1 0 0 0
		 * 0 0 0 1 0 0
		 * 0 0 0 0 1 0
		 * 0 0 0 0 0 0
		 */
		for (int r = 0; r < 6; r++) {
			for (int c = 0; c < 6; c++) {
				if (r == c) {
					if (r < 5) {
						b.addMarble(c, r, Status.BLACK);
					}
				}
			}
		}
		System.out.print(b);
		System.out.println(b.winner() + "\n");
		b.clear();
		/*
		 * 0 1 0 0 0 0
		 * 0 0 1 0 0 0
		 * 0 0 0 1 0 0
		 * 0 0 0 0 1 0
		 * 0 0 0 0 0 1
		 * 0 0 0 0 0 0
		 */
		for (int r = 0; r < 6; r++) {
			for (int c = 0; c < 6; c++) {
				if (r + 1 == c) {
					b.addMarble(c, r, Status.BLACK);
				}
			}
		}
		System.out.print(b);
		System.out.println(b.winner() + "\n");
		b.clear();
		/*
		 * 0 0 0 0 0 0
		 * 1 0 0 0 0 0
		 * 0 1 0 0 0 0
		 * 0 0 1 0 0 0
		 * 0 0 0 1 0 0
		 * 0 0 0 0 1 0
		 */
		for (int r = 0; r < 6; r++) {
			for (int c = 0; c < 6; c++) {
				if (r - 1 == c) {
					b.addMarble(c, r, Status.BLACK);
				}
			}
		}
		System.out.print(b);
		System.out.println(b.winner() + "\n");
		b.clear();
		/*
		 * 0 0 0 0 0 1
		 * 0 0 0 0 1 0
		 * 0 0 0 1 0 0
		 * 0 0 1 0 0 0
		 * 0 1 0 0 0 0
		 * 0 0 0 0 0 0
		 */
		for (int r = 0; r < 6; r++) {
			for (int c = 0; c < 6; c++) {
				if (r + c == 5 && r < 5) {
					b.addMarble(c, r, Status.BLACK);
				}
			}
		}
		System.out.print(b);
		System.out.println(b.winner() + "\n");
		b.clear();
		/*
		 * 0 0 0 0 0 0
		 * 0 0 0 0 1 0
		 * 0 0 0 1 0 0
		 * 0 0 1 0 0 0
		 * 0 1 0 0 0 0
		 * 1 0 0 0 0 0
		 */
		for (int r = 0; r < 6; r++) {
			for (int c = 0; c < 6; c++) {
				if (r + c == 5 && r > 0) {
					b.addMarble(c, r, Status.BLACK);
				}
			}
		}
		System.out.print(b);
		System.out.println(b.winner() + "\n");
		b.clear();
		/*
		 * 0 0 0 0 1 0
		 * 0 0 0 1 0 0
		 * 0 0 1 0 0 0
		 * 0 1 0 0 0 0
		 * 1 0 0 0 0 0
		 * 0 0 0 0 0 0
		 */
		for (int r = 0; r < 6; r++) {
			for (int c = 0; c < 6; c++) {
				if (r + c == 4) {
					b.addMarble(c, r, Status.BLACK);
				}
			}
		}
		System.out.print(b);
		System.out.println(b.winner() + "\n");
		b.clear();
		/*
		 * 0 0 0 0 0 0 
		 * 0 0 0 0 0 1
		 * 0 0 0 0 1 0
		 * 0 0 0 1 0 0
		 * 0 0 1 0 0 0
		 * 0 1 0 0 0 0
		 */
		for (int r = 0; r < 6; r++) {
			for (int c = 0; c < 6; c++) {
				if (r + c == 6) {
					b.addMarble(c, r, Status.BLACK);
				}
			}
		}
		System.out.print(b);
		System.out.println(b.winner());
		b.clear();
		
		AI a = new AI(5, Status.BLACK);
		a.makeMove(b);
		
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
