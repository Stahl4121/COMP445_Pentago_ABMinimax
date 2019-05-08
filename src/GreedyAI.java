/**
 * This Class creates an AI player for Pentago. 
 * It inherits from the Player class, implementing the getMove method.
 * @author Stahl, Tan, Calvis
 *
 */
import java.util.ArrayList;

public class GreedyAI extends Player{
	
	/**
	 * Constructor for Greedy AI player
	 * 
	 * @param maxDepth
	 * @param color
	 */
	public GreedyAI(Status color) {
		this.color = color;
	}
	
	/**
	 * Starts the minimax process, and then makes the chosen move
	 * @param b Takes in the current board 
	 */
	@Override
	public Move getMove(Board b) {
		ArrayList<Move> possibleMoves = b.getPossibleMoves(color);
		
		int maxScore = Integer.MIN_VALUE;
		int score = Integer.MIN_VALUE;
		Move bestMove = null;
		
		for(int i = 0; i < possibleMoves.size(); i++) {
			
			score = b.movedBoard(possibleMoves.get(i)).getBoardFavorability(color);
			
			if(score > maxScore) {
				maxScore = score;
				bestMove = possibleMoves.get(i);
			}
		}
		
		return bestMove;
	}
		
}
