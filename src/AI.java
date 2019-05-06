/**
 * This Class creates an AI player for Pentago. 
 * It inherits from the Player class, implementing the getMove method.
 * @author STAHLLR1
 *
 */
import java.util.ArrayList;
public class AI extends Player{
	public int maxDepth;
	
	public AI(int maxDepth, Status color) {
		this.maxDepth = maxDepth;
		this.color = color;
	}
	
	/**
	 * Starts the minimax process, and then makes the chosen move
	 * @param b Takes in the current board 
	 */
	@Override
	public void makeMove(Board b) {
		
		int moveEncoding = minimax(b, maxDepth, color);
		
		b.move(new Move(moveEncoding, color));
	}
	

	public int minimax(Board b, int depth, Status s) {
		int maxEval;
		int minEval;
		int eval;
		
		//figure out whether we are the maximizing player
		boolean maximize = true;
		if (s != this.color) {
			maximize = false;
		}
		
		ArrayList<Move> possibleMoves = b.getPossibleMoves(s);

		if (depth == 0 || possibleMoves.isEmpty() || b.winner() != Status.EMPTY) {
			return b.getBoardFavorability();
		}
				
		if (maximize) {
			maxEval = Integer.MIN_VALUE;
			Move bestMove = null;
			
			//Flip the color for the next depth
			s = s == Status.BLACK ? Status.WHITE : Status.BLACK;
			
			for (int i = 0; i < possibleMoves.size(); i++) {
				
				Board copy = new Board(b);
				Move m = possibleMoves.get(i);
				eval = minimax(copy.move(m), depth - 1, s);
				
				if (eval > maxEval) {
					maxEval = eval;
					
					//Only store the best move for the current board
					if(depth == maxDepth) {
						bestMove = m;
					}
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
			
			//Flip the color for the next depth
			s = s == Status.BLACK ? Status.WHITE : Status.BLACK;
			
			minEval = Integer.MAX_VALUE;
			
			for (int i = 0; i < possibleMoves.size(); i++) {
				
				Board copy = new Board(b);
				Move m = possibleMoves.get(i);
				eval = minimax(copy.move(m), depth, s);
				
				if (eval < minEval) {
					minEval = eval;
				}
			}
			
			return minEval;
		}
		
	}
}
