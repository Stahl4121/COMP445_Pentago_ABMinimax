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

	/**
	 * initialize a board object
	 */
	public Board() {
		//create an array to represent the board
		board = new Status[BOARD_SIZE][BOARD_SIZE];
		
		//fill the array- every index is empty
		for(int r = 0; r < BOARD_SIZE; r++) {
			for(int c = 0; c < BOARD_SIZE; c++) {
				board[r][c] = Status.EMPTY;
			}
		}
	}

	/**
	 * copy constructor for board
	 * @param b- the board we are copying
	 */
	public Board(Board b) {
		//create a new board array
		board = new Status[BOARD_SIZE][BOARD_SIZE];
		
		//copy b's array into the new array
		for(int r = 0; r < BOARD_SIZE; r++) {
			for(int c = 0; c < BOARD_SIZE; c++) {
				board[r][c] = b.getStatus(r, c);
			}
		}
	}
	
	/**
	 * @param b- gets a board
	 * @return whether this is equal to b
	 */
	public boolean equals(Board b) {
		//as soon as we find an inequality between the two boards, return false
		for (int r = 0; r < BOARD_SIZE; r++) {
			for (int c = 0; c < BOARD_SIZE; c++) {
				if (b.getStatus(r, c) != this.board[r][c]) {
					return false;
				}
			}
		}
		
		//if we make it between the loops, return true
		return true;
	}
	
	/**
	 * @param r row
	 * @param c column
	 * @return return the board at those indeces
	 */
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
	 * 	executes a move
	 *  @param m	the move to execute
	 */
	public void makeMove(Move m) {
		addMarble(m.getRow(),m.getCol(),m.getColor());
		rotate(m.getRotation());
	}

	/**
	 * @param quadrant
	 * here is how quadrants work
	 * 2 1
	 * 3 4
	 * @param rot	value in range [-4,4]
	 * 				positive = rotate clockwise
	 * 				negative = rotate ctrclockwise
	 */
	public void rotate(int rot) {
		int quadrant = Math.abs(rot);
		boolean clockwise = rot > 0;


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
	
	/**
	 * @return the winner of the game
	 * returns empty if there is not winner
	 */
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
						//check if win conditions are broken
						if (board[r][c] != s) { 
							
							//horizontal wins
							if (c < BOARD_SIZE - 1) {
								horizontals[r][0] = false;
							}
							if (c > 0) {
								horizontals[r][1] = false;
							}
							
							//vertical wins
							if (r < BOARD_SIZE - 1) {
								verticals[c][0] = false;
							}
							if (r > 0) {
								verticals[c][1] = false;
							}
							
							//diagonal wins
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
	public int getBoardFavorability(Status myColor) {
		//figure out the color of the opposing player
		Status hisColor = myColor==Status.WHITE ? Status.BLACK : Status.WHITE;
		
		//holds favorability of the player
		int myFav = 0;
		int hisFav = 0;
		
		//holds the runs for your player and yuor opPONENT
		int[] me = new int[18];
		int[] him = new int[18];
		
		//fill the runs array
		for (int r = 0; r < BOARD_SIZE; r++) {
			for (int c = 0; c < BOARD_SIZE; c++) {
				if (board[r][c] == myColor) {
					//horizontals
					me[c]++;
					him[c] = 0;
					//vertical
					me[r + 6]++;
					him[r + 6] = 0;
					//diagonal
					if (r == c) {
						me[12]++;
						him[12] = 0;
					}
					if (r + 1 == c) {
						me[13]++;
						him[13] = 0;
					}
					if (r - 1 == c) {
						me[14]++;
						him[14] = 0;
					}
					if (r + c == 5) {
						me[15]++;
						him[15] = 0;
					}
					if (r + c == 4) {
						me[16]++;
						him[16] = 0;
					}
					if (r + c == 6) {
						me[17]++;
						him[17] = 0;
					}
				}
				else if (board[r][c] == hisColor) {
					//horizontals
					him[c]++;
					me[c] = 0;
					//vertical
					him[r + 6]++;
					me[r + 6] = 0;
					//diagonal
					if (r == c) {
						him[12]++;
						me[12] = 0;
					}
					if (r + 1 == c) {
						him[13]++;
						me[13] = 0;
					}
					if (r - 1 == c) {
						him[14]++;
						me[14] = 0;
					}
					if (r + c == 5) {
						him[15]++;
						me[15] = 0;
					}
					if (r + c == 4) {
						him[16]++;
						me[16] = 0;
					}
					if (r + c == 6) {
						him[17]++;
						me[17] = 0;
					}
				}
			}
		}
		
		//total up favorability:
		//5 points for having your marble in the center of the quadrant
		if (board[1][1] == myColor) {
			myFav += 5;
		} else if (board[1][1] == hisColor) {
			hisFav += 5;
		}
		if (board[4][4] == myColor) {
			myFav += 5;
		} else if (board[4][4] == hisColor) {
			hisFav += 5;
		}
		if (board[1][4] == myColor) {
			myFav += 5;
		} else if (board[1][4] == hisColor) {
			hisFav += 5;
		}
		if (board[4][1] == myColor) {
			myFav += 5;
		} else if (board[4][1] == hisColor) {
			hisFav += 5;
		}
		
		//100000 points for five in a row
		//1000 points for four in a row
		//100 points for three in a row
		for (int i = 0; i < 18; i++) {
			if (me[i] >= 5) {
				//if there's a win
				if (this.winner() == myColor) {
					myFav += 100000;
				}
			}
			if (me[i] == 4) {
				myFav += 1000;
			}
			if (me[i] == 3) {
				myFav += 100;
			}
			if (him[i] >= 5) {
				//if there's a win
				if (this.winner() == hisColor) {
					myFav += 100000;
				}
			}
			if (him[i] == 4) {
				hisFav += 1000;
			}
			if (him[i] == 3) {
				hisFav += 100;
			}
		}
		
		//return the difference between your favorability and your opposition's favorability
		return myFav-hisFav;
	}


	/**
	 * @param m 	a move
	 * @return 		what the board would be were m executed
	 */
	public Board movedBoard(Move m) {
		//copy the board
		Board b = new Board(this);
		
		//add a marble
		b.addMarble(m.getRow(), m.getCol(), m.getColor());
		b.rotate(m.getRotation());
		
		//return the board
		return b;
	}


	/**
	 * @param player
	 * @return all the moves that player can do
	 */
	public ArrayList<Move> getPossibleMoves(Status player) {
		//initialize a move
		Move m = new Move();
		
		//initialize an arraylist to hold all the moves
		ArrayList<Move> moves = new ArrayList<Move>();
		
		//iterate through the rows and columns of the board
		for (int r = 0; r < BOARD_SIZE; r++) {
			for (int c = 0; c < BOARD_SIZE; c++) {
				
				//if the board is empty
				if (board[r][c] == Status.EMPTY) {
					
					//get ready for those centers of the quadrant
					if (((r == 1) || (r == 4)) && ((c == 1) || (c == 4))) {
						moves.add(new Move(r, c, player, 1));
					}
					
					//add all the rotations of all the moves
					for (int q = -4; q < 5; q++) {
						
						//get a new move
						m = new Move(r, c, player, q);
						
						//if the rotation doesn't change the board, don't bother with it
						if (!this.movedBoard(new Move(r, c, player, 0)).equals(this.movedBoard(m))) {
							moves.add(m);
						}
					}
				}
			}
		}
		
		return moves;
	}

	//returns the board
	public String toString() {
		//we will return this
		String ret = "";
		
		//add the board to a string
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
		
		//return the string
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
}
