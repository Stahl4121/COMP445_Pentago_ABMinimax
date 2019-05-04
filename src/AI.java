
public class AI extends Player{
	public int depth;
	public Status color;
	public AI(int difficulty, Status color) {
		depth = difficulty;
		this.color = color;
	}
	@Override
	public void makeMove() {
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
