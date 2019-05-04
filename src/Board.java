import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Tan, Stahl, Calvis
 * Represent and manipulate the Pentago Board
 * [insert lawyer joke here]
 */
public class Board {
	private Status[][] board;	//the Pentago board itself (it's a square)
	private final int BOARD_SIZE = 6;		//the number of rows/columns in the board, which is a square
	private final int QUAD_SIZE = BOARD_SIZE / 2;	//the size of a quadrant [and "most unnecessary variable in this project" goes to...]
	
	public Board() {
		board = new Status[BOARD_SIZE][BOARD_SIZE];
		for(int r = 0; r < BOARD_SIZE; r++) {
			for(int c = 0; c < BOARD_SIZE; c++) {
				board[r][c] = Status.EMPTY;
			}
		}
	}
	
	//you already know what it is
	public void addWhiteMarble(int r, int c) {
		board[r][c] = Status.WHITE;
	}
	
	public void addBlackMarble(int r, int c) {
		board[r][c] = Status.BLACK;
	}
	
	/**
	 * @param quadrant
	 * here is how quadrants work
	 * 2 1
	 * 3 4
	 * @param clockwise
	 * true = rotate the board clockwise
	 * false = rotate the board counterclockwise {what a long word}
	 */
	public void rotate(int quadrant, boolean clockwise) {
		Status[][] quad = new Status[QUAD_SIZE][QUAD_SIZE]; //the new quadrant
		int x = 0; //starting row of our quadrant
		int y = 0; //starting column of our quadrant
		
		//find the starting row or column of the board
		if (quadrant == 1 || quadrant == 4) {
			x = QUAD_SIZE;
		}
		if (quadrant == 3 || quadrant == 4) {
			y = QUAD_SIZE;
		}
		
		//move values from the board into quad, rotating them as you go
		for (int r = 0; r < QUAD_SIZE; r++) {
			for (int c = 0; c < QUAD_SIZE; c++) {
				if (clockwise) {
					quad[c][2 - r] = board[r + x][c + y];
				}
				else {
					quad[2 - c][r] = board[r + x][c + y];
				}
			}
		}
		
		//replace old quadrant of board with quad
		for (int r = x; r < x + 3; r++) {
			for (int c = y; c < y + 3; c++) {
				board[r + x][c + y] = quad[r][c];
			}
		}
	}
	
	//sorry it's ugly
	/**
	 * @return the winner of the game
	 * returns empty if there is not winner
	 */
	public Status winner() {
		//the return value
		Status winner = Status.EMPTY;
		
		//iterate through the states to check for a win for each state
		for (Status s: Status.values()) {
			//but empty cannot win so skip that one
			if (s != Status.EMPTY) {
				
				//There are 32 win conditions, if winCondFailed is less than 32, then it is a win
				int winCondFailed = 0;	
				
				//loop through every index in the board
				for (int r = 0; r < BOARD_SIZE; r++) {
					for (int c = 0; c < BOARD_SIZE; c++) {
						//if the index is not the state we are checking, then we eliminate some wins
						if (board[r][c] != s) { 
							/*
							 * These wins are sorta complicated so I drew pictures
							 * 0 0 0 0 0 0 (the row of ones could be any of the rows)
							 * 1 1 1 1 1 0
							 * 0 0 0 0 0 0
							 * 0 0 0 0 0 0
							 * 0 0 0 0 0 0
							 * 0 0 0 0 0 0
							 */
							if (c < BOARD_SIZE - 1) {
								winCondFailed++;
							}
							/*
							 * 0 0 0 0 0 0 (the row of ones could be any of the rows)
							 * 0 1 1 1 1 1
							 * 0 0 0 0 0 0
							 * 0 0 0 0 0 0
							 * 0 0 0 0 0 0
							 * 0 0 0 0 0 0
							 */
							if (c > 0) {
								winCondFailed++;
							}
							/*
							 * 0 1 0 0 0 0 (the column of ones could be any of the columns)
							 * 0 1 0 0 0 0
							 * 0 1 0 0 0 0
							 * 0 1 0 0 0 0
							 * 0 1 0 0 0 0
							 * 0 0 0 0 0 0
							 */
							if (r < BOARD_SIZE - 1) {
								winCondFailed++;
							}
							/*
							 * 0 0 0 0 0 0 (the column of ones could be any of the columns)
							 * 0 1 0 0 0 0
							 * 0 1 0 0 0 0
							 * 0 1 0 0 0 0
							 * 0 1 0 0 0 0
							 * 0 1 0 0 0 0
							 */
							if (r > 0) {
								winCondFailed++;
							}
							if (r == c) {
								/*
								 * 0 0 0 0 0 0
								 * 0 1 0 0 0 0
								 * 0 0 1 0 0 0
								 * 0 0 0 1 0 0
								 * 0 0 0 0 1 0
								 * 0 0 0 0 0 1
								 */
								if (r > 0) {
									winCondFailed++;
								}
								/*
								 * 1 0 0 0 0 0
								 * 0 1 0 0 0 0
								 * 0 0 1 0 0 0
								 * 0 0 0 1 0 0
								 * 0 0 0 0 1 0
								 * 0 0 0 0 0 0
								 */
								if (r < BOARD_SIZE - 1) {
									winCondFailed++;
								}
							}
							/*
							 * 0 1 0 0 0 0
							 * 0 0 1 0 0 0
							 * 0 0 0 1 0 0
							 * 0 0 0 0 1 0
							 * 0 0 0 0 0 1
							 * 0 0 0 0 0 0
							 */
							if (r + 1 == c) {
								winCondFailed++;
							}
							/*
							 * 0 0 0 0 0 0
							 * 1 0 0 0 0 0
							 * 0 1 0 0 0 0
							 * 0 0 1 0 0 0
							 * 0 0 0 1 0 0
							 * 0 0 0 0 1 0
							 */
							if (r - 1 == c) {
								winCondFailed++;
							}
							/*
							 * 0 0 0 0 0 1
							 * 0 0 0 0 1 0
							 * 0 0 0 1 0 0
							 * 0 0 1 0 0 0
							 * 0 1 0 0 0 0
							 * 0 0 0 0 0 0
							 */
							if (r + c == 5 && r < 5) {
								winCondFailed++;
							}
							/*
							 * 0 0 0 0 0 0
							 * 0 0 0 0 1 0
							 * 0 0 0 1 0 0
							 * 0 0 1 0 0 0
							 * 0 1 0 0 0 0
							 * 1 0 0 0 0 0
							 */
							if (r + c == 5 && r > 0) {
								winCondFailed++;
							}
							/*
							 * 0 0 0 0 1 0
							 * 0 0 0 1 0 0
							 * 0 0 1 0 0 0
							 * 0 1 0 0 0 0
							 * 1 0 0 0 0 0
							 * 0 0 0 0 0 0
							 */
							if (r + c == 4) {
								winCondFailed++;
							}
							/*
							 * 0 0 0 0 0 0 
							 * 0 0 0 0 0 1
							 * 0 0 0 0 1 0
							 * 0 0 0 1 0 0
							 * 0 0 1 0 0 0
							 * 0 1 0 0 0 0
							 */
							if (r + c == 6) {
								winCondFailed++;
							}
						}
					}
				}
				
				if(winCondFailed < 32) {
					//If winner isn't still status.empty, and the other player has won
					//It is a tie and we return null
					if (winner != Status.EMPTY) {
						winner = null;					
					}
					else {
						winner = s;
					}
				}
			}
		}
		return winner;
	}
	
	public int getBoardFavorability(Status s) {
		return 0;
	}
	
	public Board move(Move m) {
		//actually do the move on the board
		return this;
	}
	
	//incomplete
	public ArrayList<Move> getPossibleMoves() {
		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		//fill the arraylist
		return possibleMoves;
	}
	
	//returns the board
	public String toString() {
		String ret = "";
		for (int r = 0; r < BOARD_SIZE; r++) {
			for (int c = 0; c < BOARD_SIZE; c++) {
				ret += statusString(board[r][c]) + " ";
			}
			ret += "\n";
		}
		return ret;
	}
	
	//can't figure out toString for enums
	//can fix later sorry
	/**
	 * @param s- the status
	 * @return a string: B for BLACK, W for WHITE, E for EMPTY
	 */
	public String statusString(Status s) {
		if (s == Status.BLACK) {
			return "B";
		}
		if (s == Status.WHITE) {
			return "W";
		}
		return "E";
	}
}
