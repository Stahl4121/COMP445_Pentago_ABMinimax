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
		b.move(minimax(b, depth, color));
	}
	
	//white is the maximizing player
	//black is the minimizing player
	public Move minimax(Board b, int depth, Status s) {
		Move maxEval;
		Move minEval;
		Move eval;
		
		//figure out whether we are the maximizing player
		boolean maximize = true;
		if (s == Status.BLACK) {
			maximize = false;
		}
		
		if (depth == 0 || b.getPossibleMoves(s).isEmpty() || b.winner() != Status.EMPTY) {
			return new Move(new Move(), b.getBoardFavorability());
		}
		
		if (maximize) {
			maxEval = new Move(new Move(), Integer.MAX_VALUE);
			ArrayList<Move> possibleMoves = b.getPossibleMoves(s);
			for (int i = 0; i < possibleMoves.size(); i++) {
				Move m = possibleMoves.get(i);
				eval = minimax(b.move(m), depth - 1, s);
				if (eval.getFavorability() > maxEval.getFavorability()) {
					maxEval = new Move(eval, eval.getFavorability());
				}
			}
			return maxEval;
		}
		
		else {
			minEval = new Move(new Move(), Integer.MAX_VALUE);
			ArrayList<Move> possibleMoves = b.getPossibleMoves(s);
			for (int i = 0; i < possibleMoves.size(); i++) {
				Move m = possibleMoves.get(i);
				eval = minimax(b.move(m), depth, s);
				if (eval.getFavorability() < minEval.getFavorability()) {
					minEval = new Move(eval, eval.getFavorability());
				}
			}
			return minEval;
		}
		
	}
}
