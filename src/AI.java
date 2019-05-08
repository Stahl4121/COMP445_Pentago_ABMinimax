/**
 * This Class creates an AI player for Pentago. 
 * It inherits from the Player class, implementing the getMove method.
 * @author Stahl, Tan, Calvis
 *
 */
import java.util.ArrayList;

public class AI extends Player{
	
	public int maxDepth;
	public int numMoves = 0;
	
	/**
	 * Constructor for AI player
	 * 
	 * @param maxDepth
	 * @param color
	 */
	public AI(int maxDepth, Status color) {
		this.maxDepth = maxDepth;
		this.color = color;
	}
	
	/**
	 * Starts the minimax process, and then makes the chosen move
	 * @param b Takes in the current board 
	 */
	@Override
	public Move getMove(Board b) {
		
		int moveEncoding = minimax(b, maxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, color, true);
		Move m = new Move(moveEncoding, color);
		
		return m;
	}
	

	/**
	 * Implements the minimax algorithm with alpha beta pruning.
	 * 
	 * @param b			The board to run minimax on
	 * @param depth		The depth to run minimax to
	 * @param alpha		Alpha parameter for alphabeta pruning
	 * @param beta		Beta parameter for alphabeta pruning
	 * @param s			The color of the player using minimax
	 * @param maximize	Whether on the maximize or minimize level
	 * @return			The final return will be a move encoded as an int
	 */
	public int minimax(Board b, int depth, int alpha, int beta, Status s, boolean maximize) {
		ArrayList<Move> possibleMoves;	//Stores all the possible moves
		int maxEval;
		int minEval;
		int eval;
		
		//If at the bottom of the tree, or there was a winner
		if (depth == 0  || b.winner() != Status.EMPTY ) {
			return b.getBoardFavorability(s);
		}
		
		//If on the maximize level
		if(maximize) {
			possibleMoves = b.getPossibleMoves(s);
			//System.out.println(possibleMoves.size());
		}
		else {//If on the minimize level
			//Want to be looking at the other player's moves made
			Status opposingPlayer = s == Status.BLACK ? Status.WHITE : Status.BLACK;
			possibleMoves = b.getPossibleMoves(opposingPlayer);
			//System.out.println(possibleMoves.size());
		}
		
		//If there are no possible moves, also return favorability
		if (possibleMoves.isEmpty()) {
			return b.getBoardFavorability(s);
		}
		
		//Start the maximizing section
		if (maximize) {

			maxEval = Integer.MIN_VALUE;
			Move bestMove = null;
			
			for (int i = 0; i < possibleMoves.size(); i++) {
				
				//Make a copy of the board
				Board copy = new Board(b);
				
				//Get the current move
				Move m = possibleMoves.get(i);
				numMoves++;
				
				//Evaluate the next level of minimax on the board with the current move
				eval = minimax(copy.movedBoard(m), depth - 1, alpha, beta, s, false);
				
				if (eval > maxEval) {
					maxEval = eval;
					
					//Only store the best move for the current board
					if(depth == maxDepth) {
						bestMove = m;
					}
				}
				
				alpha = Math.max(alpha, eval);
				if (beta <= alpha) {
					break;
				}
			}
			
			//If this is the original minimax call
			if(depth == maxDepth) {
				return bestMove.toInt();
			}
			//otherwise
			return maxEval;
		}
		else { //Start the minimizing code
			
			minEval = Integer.MAX_VALUE;
			
			for (int i = 0; i < possibleMoves.size(); i++) {
				
				//Make a copy of the board
				Board copy = new Board(b);
				
				//Get the next move
				Move m = possibleMoves.get(i);
				numMoves++;
				
				//Evaluate the next level of minimax on the board with the current move
				eval = minimax(copy.movedBoard(m), depth - 1, alpha, beta, s, true);
				
				if (eval < minEval) {
					minEval = eval;
				}
				
				beta = Math.max(beta, eval);
				if (beta <= alpha) {
					break;
				}
			}
			
			return minEval;
		}
		
	}
}
