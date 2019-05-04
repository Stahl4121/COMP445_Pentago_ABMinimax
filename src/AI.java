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
	public double minimax(Board b, int depth, Status s) {
		double maxEval;
		double minEval;
		double eval;
		
		//figure out whether we are the maximizing player
		boolean maximize = true;
		if (s == Status.BLACK) {
			maximize = false;
		}
		
		if (maximize) {
			maxEval = Double.NEGATIVE_INFINITY;
			for (/*move in possible moves*/;;) {
				eval = minimax(b, depth, s);
				maxEval = Math.max(maxEval, eval);
			}
			return maxEval;
		}
		else {
			minEval = Double.POSITIVE_INFINITY;
			for (/*move in possible moves*/;;) {
				eval = minimax(b, depth, s);
				minEval = Math.min(minEval, eval);
			}
			return minEval;
		}
		
	}
}
