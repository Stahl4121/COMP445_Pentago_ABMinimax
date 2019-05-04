/**
 * This Class creates an AI player for Pentago. 
 * It inherits from the Player class, implementing the getMove method.
 * @author STAHLLR1
 *
 */
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
		
		if (maximize) {
			maxEval = Integer.MAX_VALUE;
			for (;;/*move in possible moves*/) {
				eval = minimax(b, depth - 1, s);
				maxEval = Math.max(maxEval, eval);
			}
			return maxEval;
		}
		else {
			minEval = Integer.MIN_VALUE;
			for (/*move in possible moves*/) {
				eval = minimax(b, depth, s);
				minEval = Math.min(minEval - , eval);
			}
			return minEval;
		}
		
	}
}
