/**
 * Position on board
 *
 */
public class Move {
	private int row;
	private int col;
	private Status color;
	private int rot;

	/**
	 * Constructor for Move
	 * @param r row of move
	 * @param c column of move
	 * @param s Color of piece being placed
	 */
	public Move(int r,int c, Status s, int rotation){
		row=r;
		col=c;
		color=s;
		rot = rotation;
	}

	/**
	 * Creates a Move equal to the representation  
	 * returned by minimax (an integer representing a move)
	 * 
	 * @param moveEncoding an integer representation of a move
	 * @param s	the color of the piece being placed
	 */
	public Move(int moveEncoding, Status s) {
		row = Math.abs(moveEncoding / 100);
		col = (Math.abs(moveEncoding) - (row*100)) / 10;
		rot = (Math.abs(moveEncoding) - (row*100 + col*10))*(moveEncoding/moveEncoding);
		color = s;
	}

	/**
	 * Copy constructor for Move
	 * @param m Takes a move object and makes a deep copy
	 */
	public Move(Move m){
		this.row=m.row;
		this.col=m.col;
		this.color=m.color;
	}
	
	
	/**
	 * Default constructor for Move
	 */
	public Move(){
		row=-1;
		col=-1;
		color=Status.EMPTY;
	}

	/**
	 * 
	 * @return Returns row of the move object
	 */
	public int getRow(){
		return row;
	}
	
	/**
	 * 
	 * @return Returns column of the move object
	 */
	public int getCol(){
		return col;
	}
	
	/**
	 * 
	 * @return Returns color of the SquareStatus for the move object
	 */
	public Status getColor(){
		return color;
	}
	
	/**
	 * 
	 * @return Returns rotation direction for a move
	 * <0 => counter-clockwise
	 * >0 => clockwise
	 * =0 => no rotation
	 */
	public int getRotation(){
		return rot;
	}
	
	/**
	 * Converts a move into an integer representation.
	 * Used by minimax.
	 * E.g. Place piece on (4,5) and rotate 2nd quad ctrclockwise:
	 * 			(-2/-2) * (400 + 50 + 2) = -452
	 * 
	 * @return integer representation of a move
	 */
	public int toInt() {
		return (rot/rot)*(row*100 + col*10 + Math.abs(rot));
	}
	
	public String toString() {
		String ro;
		if (rot == 0) {
			ro = " no rotation";
		}
		else {
			ro = ", quadrant: " + Integer.toString(Math.abs(rot));
			if (rot > 0) {
				ro += " clockwise";
			}
			else {
				ro += " counterclockwise";
			}
		}
		return color + ": row "+row+", column "+col+ro;
	}
}
