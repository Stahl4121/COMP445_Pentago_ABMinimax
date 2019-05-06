import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @author Tan, Stahl, Calvis
 * Represent and manipulate the Pentago Board
 * [insert lawyer joke here]
 */
public class Board {
	public Status[][] board;						//the Pentago board itself (it's a square)
	public final int BOARD_SIZE = 6;				//the number of rows/columns in the board, which is a square
	public final int QUAD_SIZE = BOARD_SIZE / 2;	//the size of a quadrant [and "most unnecessary variable in this project" goes to...]
	
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
			boolean isBoardFull = true;
			
			//Check wins for black and white players
			for (Status s: Status.values()) {
				
				//Only check wins for black/white, not for empty
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
							
							//Check if the board is actually full
							if (isBoardFull && board[r][c] == Status.EMPTY) {
								isBoardFull = false;
							}
							
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
							if (horizontals[r][c] || verticals[r][c]) {
								
								//If winner isn't still status.empty, and the other player has won
								//It is a tie and we return null
								if (winner != Status.EMPTY) {
									return null;
								}
								else {
									winner = s;
									break;
								}
							}
						}
					}
					
					for (int c = 0; c < 8; c++) {
						if (diagonals[c]) {
							//If winner isn't still status.empty, and the other player has won
							//It is a tie and we return null
							if (winner != Status.EMPTY) {
								return null;					
							}
							else {
								winner = s;
								break;
							}							
						}
					}	
				}
			}
		
			//If the board is full and neither has won, return null for a tie
			if(isBoardFull && winner == Status.EMPTY) {
				winner = null;
			}
			
			return winner;
		}
	
	/**
	 * @return the favorability value of the board based on the number of runs
	 * > 0 favors white
	 * < 0 favors black
	 */
	public int getBoardFavorability(Status color) {
		int[] maxRun = new int[18];
		for (int i = 0; i < 18; i++) {
			maxRun[i] = 0;
		}
		for (int r = 0; r < BOARD_SIZE; r++) {
			for (int c = 0; c < BOARD_SIZE; c++) {
				//if the index is not the state we are checking, then we eliminate some wins
				if (board[r][c] != Status.EMPTY) {
					if (board[r][c] == color) {
						//horizontals
						maxRun[r]++;
						//vertical
						maxRun[r + 6]++;
						//diagonal
						if (r == c) {
							maxRun[12]++;
						}
						if (r + 1 == c) {
							maxRun[13]++;
						}
						if (r - 1 == c) {
							maxRun[14]++;
						}
						if (r + c == 5) {
							maxRun[15]++;
							maxRun[15] = 0;
						}
						if (r + c == 4) {
							maxRun[16]++;
						}
						if (r + c == 6) {
							maxRun[17]++;
						}
					}
					else {
						//horizontals
						maxRun[r] = 0;
						//vertical
						maxRun[r + 6] = 0;
						//diagonal
						if (r == c) {
							maxRun[12] = 0;
						}
						if (r + 1 == c) {
							maxRun[13] = 0;
						}
						if (r - 1 == c) {
							maxRun[14] = 0;
						}
						if (r + c == 5) {
							maxRun[15] = 0;
						}
						if (r + c == 4) {
							maxRun[16] = 0;
						}
						if (r + c == 6) {
							maxRun[17] = 0;
						}
					}
				}
			}
		}
		int fav = 0;
		for (int i = 0; i < 18; i++) {
			if (maxRun[i] > fav) {
				fav = maxRun[i];
			}
		}
		return fav;
	}
	
	
	/**
	 * @param m a move
	 * @return what the board would be were m executed
	 */
	public Board move(Move m) {
		Board b = new Board(this);
		this.addMarble(m.getRow(), m.getCol(), m.getColor());
		if (m.getRotation() != 0) {
			this.rotate(Math.abs(m.getRotation()), m.getRotation() > 0);
		}
		return this;
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
						//You must make a rotation
						if (q != 0) {
							moves.add(new Move(r, c, player, q));
						}
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
			if (r == 3) {
				ret += "-------------\n";
			}
			for (int c = 0; c < BOARD_SIZE; c++) {
				if (c == 3) {
					ret += "| ";
				}
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
