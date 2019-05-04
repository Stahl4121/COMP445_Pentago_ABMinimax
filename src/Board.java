import java.util.Arrays;

/**
 * @author Tan, Stahl, Calvis
 * Represent and manipulate the Pentago Board
 * [insert lawyer joke here]
 */
public class Board {
	Status[][] board;	//the Pentago board itself (it's a square)
	int boardSize;		//the number of rows/columns in the board, which is a square
	int quadrantSize;
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
		
		//put the rotated quadrant into quad
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
		
		//replace old quadrant with quad
		for (int r = x; r < x + 3; r++) {
			for (int c = y; c < y + 3; c++) {
				board[r + x][c + y] = quad[r][c];
			}
		}
	}
	
	public String toString() {
		String ret = "";
		for (int r = 0; r < boardSize; r++) {
			for (int c = 0; c < boardSize; c++) {
				ret += board[r][c] + " ";
			}
			ret += "\n";
		}
		return ret;
	}
	
	//sorry it's ugly
	public Status winner() {
		Status winner = Status.EMPTY;
		for (Status s: Status.values()) {
			if (s != Status.EMPTY) {
				boolean[][] horizontals = new boolean[boardSize][2];
				boolean[][] verticals = new boolean[boardSize][2];
				boolean[] diagonals = new boolean[8];
				for (int r = 0; r < boardSize; r++) {
					for (int c = 0; c < 2; c++) {
						horizontals[r][c] = true;
						verticals[r][c] = true;
					}
				}
				for (int c = 0; c < 8; c++) {
					diagonals[c] = true;
				}		
				
				for (int r = 0; r < boardSize; r++) {
					for (int c = 0; c < boardSize; c++) {
						if (board[r][c] != s) { 
							if (c < boardSize - 1) {
								horizontals[r][0] = false;
							}
							if (c > 0) {
								horizontals[r][1] = false;
							}
							if (r < boardSize - 1) {
								verticals[c][0] = false;
							}
							if (r > 0) {
								verticals[c][1] = false;
							}
							if (r == c) {
								if (r > 0) {
									diagonals[0] = false;
								}
								if (r < boardSize - 1) {
									diagonals[1] = false;
								}
							}
							if (r + 1 == c) {
								diagonals[2] = false;
							}
							if (r - 1 == c) {
								diagonals[3] = false;
							}
							if (r + c == 5 && r < 5) {
								diagonals[4] = false;
							}
							if (r + c == 5 && r > 0) {
								diagonals[5] = false;
							}
							if (r + c == 4) {
								diagonals[6] = false;
							}
							if (r + c == 6) {
								diagonals[7] = false;
							}
						}
					}
				}
				//to do: add a check for bools
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
}
