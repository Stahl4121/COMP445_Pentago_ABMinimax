import java.io.IOException;

/**
 * A Player class for Person and 
 * AI to inherit from. 
 * 
 * @author STAHLLR1
 *
 */
public abstract class Player {
	protected Status color;
	
	/**
	 * A method overwritten in both child classes,
	 * allowing child classes to make a move on the board.
	 * @throws IOException 
	 * 
	 */
	abstract public Move getMove(Board b) throws IOException;
	
	/**
	 * A simple getter for the 
	 * player's color.
	 * 
	 * @return The player's color
	 */
	public Status getColor(){
		return color;
	}
}
