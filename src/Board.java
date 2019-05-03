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
					quad[c][-1 * r + 2] = board[r + x][c + y];
				}
				else {
					quad[-1 * c + 2][r] = board[r + x][c + y];
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
}
