/**
 * This Class creates an AI player for Pentago. 
 * It inherits from the Player class, implementing the getMove method.
 * @author STAHLLR1
 *
 */
import java.util.ArrayList;

public class AI extends Player{
	public int maxDepth;
	public static int numMoves;
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
	

	public int minimax(Board b, int depth, int alpha, int beta, Status s, boolean maximize) {
		ArrayList<Move> possibleMoves;
		int maxEval;
		int minEval;
		int eval;
		
		
		if(maximize) {
			possibleMoves = b.getPossibleMoves(s);
		}
		else {
			//Want to be looking at the other player's moves made
			Status opposingPlayer = s == Status.BLACK ? Status.WHITE : Status.BLACK;
			possibleMoves = b.getPossibleMoves(opposingPlayer);
		}
		

		if (depth == 0 || possibleMoves.isEmpty() || b.winner() != Status.EMPTY) {
			return b.getBoardFavorability(s);
		}
				
		if (maximize) {

			maxEval = Integer.MIN_VALUE;
			Move bestMove = null;
			
			for (int i = 0; i < possibleMoves.size(); i++) {
				
				Board copy = new Board(b);
				Move m = possibleMoves.get(i);
				numMoves++;
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
		else {
			
			minEval = Integer.MAX_VALUE;
			
			for (int i = 0; i < possibleMoves.size(); i++) {
				
				Board copy = new Board(b);
				Move m = possibleMoves.get(i);
				numMoves++;
				eval = minimax(copy.movedBoard(m), depth, alpha, beta, s, true);
				
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
