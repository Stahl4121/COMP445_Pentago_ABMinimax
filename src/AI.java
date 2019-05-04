/**
 * This Class creates an AI player for Pentago. 
 * It inherits from the Player class, implementing the getMove method.
 * @author STAHLLR1
 *
 */
import java.util.ArrayList;
public class AI extends Player{
	public int depth;
	
	public AI(int depth, Status color) {
		this.depth = depth;
		this.color = color;
	}
	
	/**
	 * Starts the minimax process, and then makes the chosen move
	 * @param b Takes in the current board 
	 */
	@Override
	public void makeMove(Board b) {
		//where does he get the board from
		minimax(b, depth, color);
	}
	
	//white is the maximizing player
	//black is the minimizing player
	public int minimax(Board b, int depth, Status s) {
		int maxEval;
		int minEval;
		int eval;
		
		//figure out whether we are the maximizing player
		boolean maximize = true;
		if (s == Status.BLACK) {
			maximize = false;
		}
		
		if (depth == 0 /*or game over*/) {
			return b.getBoardFavorability();
		}
		
		if (maximize) {
			maxEval = Integer.MAX_VALUE;
			ArrayList<Move> possibleMoves = b.getPossibleMoves();
			for (int i = 0; i < possibleMoves.size(); i++) {
				Move m = possibleMoves.get(i);
				eval = minimax(b.move(m), depth - 1, s);
				maxEval = Math.max(maxEval, eval);
			}
			return maxEval;
		}
		
		else {
			minEval = Integer.MIN_VALUE;
			ArrayList<Move> possibleMoves = b.getPossibleMoves();
			for (int i = 0; i < possibleMoves.size(); i++) {
				Move m = possibleMoves.get(i);
				eval = minimax(b.move(m), depth, s);
				minEval = Math.min(minEval - 1, eval);
			}
			return minEval;
		}
		
	}
}
