import java.util.Arrays;

/**
 * @author Tan, Stahl, Calvis
 * Represent and manipulate the Pentago Board
 * [insert lawyer joke here]
 */
public class Board {
	Status[][] board;	//the Pentago board itself (it's a square)
	int boardSize;		//the number of rows/columns in the board, which is a square
	int quadrantSize;	//the size of a quadrant [and "most unnecessary variable in this project" goes to...]
	public Board() {
		boardSize = 6;
		quadrantSize = boardSize / 2;
		board = new Status[boardSize][boardSize];
		for(int r = 0; r < boardSize; r++) {
			for(int c = 0; c < boardSize; c++) {
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
		Status[][] quad = new Status[quadrantSize][quadrantSize]; //the new quadrant
		int x = 0; //starting row of our quadrant
		int y = 0; //starting column of our quadrant
		
		//find the starting row or column of the board
		if (quadrant == 1 || quadrant == 4) {
			x = quadrantSize;
		}
		if (quadrant == 3 || quadrant == 4) {
			y = quadrantSize;
		}
		
		//move values from the board into quad, rotating them as you go
		for (int r = 0; r < quadrantSize; r++) {
			for (int c = 0; c < quadrantSize; c++) {
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
				
				//these arrays hold all the possible wins in the vertical, horizontal, and diagonal directions
				boolean[][] horizontals = new boolean[boardSize][2];
				boolean[][] verticals = new boolean[boardSize][2];
				boolean[] diagonals = new boolean[8];
				
				//we initialize them all to true, but they will have to make it through the trial of fire to stay that way
				for (int r = 0; r < boardSize; r++) {
					for (int c = 0; c < 2; c++) {
						horizontals[r][c] = true;
						verticals[r][c] = true;
					}
				}
				for (int c = 0; c < 8; c++) {
					diagonals[c] = true;
				}		
				
				//loop through every index in the board
				for (int r = 0; r < boardSize; r++) {
					for (int c = 0; c < boardSize; c++) {
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
							if (c < boardSize - 1) {
								horizontals[r][0] = false;
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
								horizontals[r][1] = false;
							}
							/*
							 * 0 1 0 0 0 0 (the column of ones could be any of the columns)
							 * 0 1 0 0 0 0
							 * 0 1 0 0 0 0
							 * 0 1 0 0 0 0
							 * 0 1 0 0 0 0
							 * 0 0 0 0 0 0
							 */
							if (r < boardSize - 1) {
								verticals[c][0] = false;
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
								verticals[c][1] = false;
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
									diagonals[0] = false;
								}
								/*
								 * 1 0 0 0 0 0
								 * 0 1 0 0 0 0
								 * 0 0 1 0 0 0
								 * 0 0 0 1 0 0
								 * 0 0 0 0 1 0
								 * 0 0 0 0 0 0
								 */
								if (r < boardSize - 1) {
									diagonals[1] = false;
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
								diagonals[2] = false;
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
								diagonals[3] = false;
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
								diagonals[4] = false;
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
								diagonals[5] = false;
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
								diagonals[6] = false;
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
								diagonals[7] = false;
							}
						}
					}
				}
				//if there's any true in any of the arrays, then we know this state has a win
				for (int r = 0; r < boardSize; r++) {
					for (int c = 0; c < 2; c++) {
						if (horizontals[r][c] == true || verticals[r][c] == true) {
							winner = s;
						}
					}
				}
				for (int c = 0; c < 8; c++) {
					if (diagonals[c] == true) {
						winner = s;
					}
				}	
			}
		}
		return winner;
	}
	
	//returns the board
	public String toString() {
		String ret = "";
		for (int r = 0; r < boardSize; r++) {
			for (int c = 0; c < boardSize; c++) {
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
