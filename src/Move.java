/**
 * Position on board
 *
 */
public class Move {
	private int row;
	private int col;
	private Status stat;
	private int rot;
	private int favorability;

	/**
	 * Constructor for Move
	 * @param r row of move
	 * @param c column of move
	 * @param piece Color of piece being placed <--lol isn't colour british? or is this for purebreds?
	 */
	public Move(int r,int c, Status piece, int rotation){
		row=r;
		col=c;
		stat=piece;
		rot = rotation;
	}
	
	

	/**
	 * Copy constructor for Move
	 * @param m Takes a move object and makes a deep copy
	 */
	public Move(Move m){
		this.row=m.row;
		this.col=m.col;
		this.stat=m.stat;
	}
	
	
	
	/**
	 * Copy constructor that allows us to add favorability for move
	 * @param m a move we copy
	 * @param fav
	 */
	public Move(Move m, int fav){
		this.row=m.row;
		this.col=m.col;
		this.stat=m.stat;
		favorability = fav;
	}
	
	/**
	 * Default constructor for Move. Creates a move with an impossible move that cannot
	 * be played solely for an ai's inability to not move.
	 */
	public Move(){		//is is purely for if there are no possible moves
		row=-1;
		col=-1;
		stat=Status.EMPTY;
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
		return stat;
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
	 * @return the favorability of this move
	 */
	public int getFavorability() {
		return favorability;
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
		return stat + ": row "+row+", column "+col+ro;
	}
}
