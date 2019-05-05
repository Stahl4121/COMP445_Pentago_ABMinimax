import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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
	
	/**
	 * copy constructor for board
	 * @param b
	 */
	public Board(Board b) {
		board = new Status[BOARD_SIZE][BOARD_SIZE];
		for(int r = 0; r < BOARD_SIZE; r++) {
			for(int c = 0; c < BOARD_SIZE; c++) {
				board[r][c] = b.getStatus(r, c);
			}
		}
	}
	
	public Status getStatus(int r, int c) {
		return board[r][c];
	}
	
	//you already know what it is
	/**
	 * @param r row
	 * @param c column
	 * @param s color
	 * adds a marble to the board
	 */
	public void addMarble(int r, int c, Status s) {
		board[r][c] = s;
	}
	
	/**
	 * clears the board
	 */
	public void clear() {
		for (int r = 0; r < BOARD_SIZE; r++) {
			for (int c = 0; c < BOARD_SIZE; c++) {
				board[r][c] = Status.EMPTY;
			}
		}
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
		if (quadrant == 0) {return;}
		Status[][] quad = new Status[QUAD_SIZE][QUAD_SIZE]; //the new quadrant
		int x = 0; //starting row of our quadrant
		int y = 0; //starting column of our quadrant
		
		//find the starting row or column of the board
		if (quadrant == 1 || quadrant == 4) {
			y = QUAD_SIZE;
		}
		if (quadrant == 3 || quadrant == 4) {
			x = QUAD_SIZE;
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
		for (int r = x; r < x + QUAD_SIZE; r++) {
			for (int c = y; c < y + QUAD_SIZE; c++) {
				board[r][c] = quad[r - x][c - y];
			}
		}
	}
	
	//sorry it's ugly
	/**
	 * @return the winner of the game
	 * returns empty if there is not winner
	 */
//	public Status winner() {
//		//the return value
//		Status winner = Status.EMPTY;
//		
//		//iterate through the states to check for a win for each state
//		for (Status s: Status.values()) {
//			//but empty cannot win so skip that one
//			if (s != Status.EMPTY) {
//				
//				//There are 32 win conditions, if winCondFailed is less than 32, then it is a win
//				int winCondFailed = 0;	
//				
//				//loop through every index in the board
//				for (int r = 0; r < BOARD_SIZE; r++) {
//					for (int c = 0; c < BOARD_SIZE; c++) {
//						//if the index is not the state we are checking, then we eliminate some wins
//						if (board[r][c] != s) { 
//							/*
//							 * These wins are sorta complicated so I drew pictures
//							 * 0 0 0 0 0 0 (the row of ones could be any of the rows)
//							 * 1 1 1 1 1 0
//							 * 0 0 0 0 0 0
//							 * 0 0 0 0 0 0
//							 * 0 0 0 0 0 0
//							 * 0 0 0 0 0 0
//							 */
//							if (c < BOARD_SIZE - 1) {
//								winCondFailed++;
//							}
//							/*
//							 * 0 0 0 0 0 0 (the row of ones could be any of the rows)
//							 * 0 1 1 1 1 1
//							 * 0 0 0 0 0 0
//							 * 0 0 0 0 0 0
//							 * 0 0 0 0 0 0
//							 * 0 0 0 0 0 0
//							 */
//							if (c > 0) {
//								winCondFailed++;
//							}
//							/*
//							 * 0 1 0 0 0 0 (the column of ones could be any of the columns)
//							 * 0 1 0 0 0 0
//							 * 0 1 0 0 0 0
//							 * 0 1 0 0 0 0
//							 * 0 1 0 0 0 0
//							 * 0 0 0 0 0 0
//							 */
//							if (r < BOARD_SIZE - 1) {
//								winCondFailed++;
//							}
//							/*
//							 * 0 0 0 0 0 0 (the column of ones could be any of the columns)
//							 * 0 1 0 0 0 0
//							 * 0 1 0 0 0 0
//							 * 0 1 0 0 0 0
//							 * 0 1 0 0 0 0
//							 * 0 1 0 0 0 0
//							 */
//							if (r > 0) {
//								winCondFailed++;
//							}
//							if (r == c) {
//								/*
//								 * 0 0 0 0 0 0
//								 * 0 1 0 0 0 0
//								 * 0 0 1 0 0 0
//								 * 0 0 0 1 0 0
//								 * 0 0 0 0 1 0
//								 * 0 0 0 0 0 1
//								 */
//								if (r > 0) {
//									winCondFailed++;
//								}
//								/*
//								 * 1 0 0 0 0 0
//								 * 0 1 0 0 0 0
//								 * 0 0 1 0 0 0
//								 * 0 0 0 1 0 0
//								 * 0 0 0 0 1 0
//								 * 0 0 0 0 0 0
//								 */
//								if (r < BOARD_SIZE - 1) {
//									winCondFailed++;
//								}
//							}
//							/*
//							 * 0 1 0 0 0 0
//							 * 0 0 1 0 0 0
//							 * 0 0 0 1 0 0
//							 * 0 0 0 0 1 0
//							 * 0 0 0 0 0 1
//							 * 0 0 0 0 0 0
//							 */
//							if (r + 1 == c) {
//								winCondFailed++;
//							}
//							/*
//							 * 0 0 0 0 0 0
//							 * 1 0 0 0 0 0
//							 * 0 1 0 0 0 0
//							 * 0 0 1 0 0 0
//							 * 0 0 0 1 0 0
//							 * 0 0 0 0 1 0
//							 */
//							if (r - 1 == c) {
//								winCondFailed++;
//							}
//							/*
//							 * 0 0 0 0 0 1
//							 * 0 0 0 0 1 0
//							 * 0 0 0 1 0 0
//							 * 0 0 1 0 0 0
//							 * 0 1 0 0 0 0
//							 * 0 0 0 0 0 0
//							 */
//							if (r + c == 5 && r < 5) {
//								winCondFailed++;
//							}
//							/*
//							 * 0 0 0 0 0 0
//							 * 0 0 0 0 1 0
//							 * 0 0 0 1 0 0
//							 * 0 0 1 0 0 0
//							 * 0 1 0 0 0 0
//							 * 1 0 0 0 0 0
//							 */
//							if (r + c == 5 && r > 0) {
//								winCondFailed++;
//							}
//							/*
//							 * 0 0 0 0 1 0
//							 * 0 0 0 1 0 0
//							 * 0 0 1 0 0 0
//							 * 0 1 0 0 0 0
//							 * 1 0 0 0 0 0
//							 * 0 0 0 0 0 0
//							 */
//							if (r + c == 4) {
//								winCondFailed++;
//							}
//							/*
//							 * 0 0 0 0 0 0 
//							 * 0 0 0 0 0 1
//							 * 0 0 0 0 1 0
//							 * 0 0 0 1 0 0
//							 * 0 0 1 0 0 0
//							 * 0 1 0 0 0 0
//							 */
//							if (r + c == 6) {
//								winCondFailed++;
//							}
//						}
//					}
//				}	
//				if(winCondFailed < 32) {
//					//If winner isn't still status.empty, and the other player has won
//					//It is a tie and we return null
//					if (winner != Status.EMPTY) {
//						winner = null;					
//					}
//					else {
//						winner = s;
//					}
//				}
//			}
//		}
//		return winner;
//	}

	//sorry it's ugly
		public Status winner() {
			Status winner = Status.EMPTY;
			for (Status s: Status.values()) {
				if (s != Status.EMPTY) {
					boolean[][] horizontals = new boolean[BOARD_SIZE][2];
					boolean[][] verticals = new boolean[BOARD_SIZE][2];
					boolean[] diagonals = new boolean[8];
					for (int r = 0; r < BOARD_SIZE; r++) {
						for (int c = 0; c < 2; c++) {
							horizontals[r][c] = true;
							verticals[r][c] = true;
						}
					}
					for (int c = 0; c < 8; c++) {
						diagonals[c] = true;
					}		

					for (int r = 0; r < BOARD_SIZE; r++) {
						for (int c = 0; c < BOARD_SIZE; c++) {
							if (board[r][c] != s) { 
								if (c < BOARD_SIZE - 1) {
									horizontals[r][0] = false;
								}
								if (c > 0) {
									horizontals[r][1] = false;
								}
								if (r < BOARD_SIZE - 1) {
									verticals[c][0] = false;
								}
								if (r > 0) {
									verticals[c][1] = false;
								}
								if (r == c) {
									if (r > 0) {
										diagonals[0] = false;
									}
									if (r < BOARD_SIZE - 1) {
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
					for (int r = 0; r < BOARD_SIZE; r++) {
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
	
	/**
	 * @return the favorability value of the board based on the number of runs
	 * > 0 favors white
	 * < 0 favors black
	 */
	public int getBoardFavorability() {
		HashMap<Status, int[]> maxRun = new HashMap<Status, int[]>();
		int[] b = new int[18];
		int[] w = new int[18];
		for (int i = 0; i < 18; i++) {
			b[i] = 0;
			w[i] = 0;
		}
		maxRun.put(Status.BLACK, b);
		maxRun.put(Status.WHITE, w);
		for (int r = 0; r < BOARD_SIZE; r++) {
			for (int c = 0; c < BOARD_SIZE; c++) {
				//if the index is not the state we are checking, then we eliminate some wins
				if (board[r][c] != Status.EMPTY) {
					Status s1 = board[r][c];
					Status s2 = Status.BLACK;
					if (s1 == Status.BLACK) {
						s2 = Status.WHITE;
					}
					//horizontals
					maxRun.get(s1)[r]++;
					maxRun.get(s2)[r] = 0;
					//vertical
					maxRun.get(s1)[r + 6]++;
					maxRun.get(s2)[r + 6] = 0;
					//diagonal
					if (r == c) {
						maxRun.get(s1)[13]++;
						maxRun.get(s2)[13] = 0;
					}
					if (r + 1 == c) {
						maxRun.get(s1)[14]++;
						maxRun.get(s2)[14] = 0;
					}
					if (r - 1 == c) {
						maxRun.get(s1)[15]++;
						maxRun.get(s2)[15] = 0;
					}
					if (r + c == 5) {
						maxRun.get(s1)[16]++;
						maxRun.get(s2)[16] = 0;
					}
					if (r + c == 4) {
						maxRun.get(s1)[17]++;
						maxRun.get(s2)[17]++;
					}
					if (r + c == 6) {
						maxRun.get(s1)[18]++;
						maxRun.get(s2)[18] = 0;
					}
				}
			}
		}
		int favorability = 0;
		for (int i = 0; i < 18; i++) {
			favorability += maxRun.get(Status.BLACK)[i];
			favorability -= maxRun.get(Status.WHITE)[i];
		}
		return favorability;
	}
	
	
	/**
	 * @param m a move
	 * @return what the board would be were m executed
	 */
	public Board move(Move m) {
		Board b = new Board(this);
		b.addMarble(m.getRow(), m.getCol(), m.getColor());
		if (m.getRotation() != 0) {
			b.rotate(Math.abs(m.getRotation()), m.getRotation() > 0);
		}
		return b;
	}
	
	
	/**
	 * @param player
	 * @return all the moves that player can do
	 */
	public ArrayList<Move> getPossibleMoves(Status player) {
		ArrayList<Move> moves = new ArrayList<Move>();
		for (int r = 0; r < BOARD_SIZE; r++) {
			for (int c = 0; c < BOARD_SIZE; c++) {
				if (board[r][c] == Status.EMPTY) {
					for (int q = -4; q < 5; q++) {
						moves.add(new Move(r, c, player, q));
					}
				}
			}
		}
		return moves;
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

	/**
	 * @param rot- value in range [-4,4]
	 */
	public void parseAndRotate(int rot) {
		int quad = Math.abs(rot);
		boolean clockwise = false;
		if(rot > 0) {
			clockwise = true;
		}
		this.rotate(quad, clockwise);
	}
}
