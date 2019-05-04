
public class AI extends Player{
	public int depth;
	public AI(int difficulty) {
		depth = difficulty;
	}
	@Override
	public void makeMove() {
		System.out.println("vorerride");
	}
	
	//white is the maximizing player
	//black is the minimizing player
	public void minimax(Board b, int depth, Status s) {
		//figure out whether we are the maximizing player
		boolean maximize = true;
		if (s == Status.BLACK) {
			maximize = false;
		}
		
		
	}
}
