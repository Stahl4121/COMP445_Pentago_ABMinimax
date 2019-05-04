/**
 * Position on board
 *
 */
public class Move {
	private int row;
	private int col;
	private Status stat;
	private boolean rotate;
	private boolean clockwise;
	private int rotQuad;


	/**
	 * Constructor for Move
	 * @param r row of move
	 * @param c column of move
	 * @param piece Colour of piece being placed
	 */
	public Move(int r,int c, Status piece, boolean rot, boolean clockW, int rotQ){
		row=r;
		col=c;
		stat=piece;
		rotate=rot;
		clockwise=clockW;
		rotQuad=rotQ;
	}

	/**
	 * Copy constructor for Move
	 * @param m Takes a move object and makes a deep copy
	 */
	public Move(Move m){
		this.row=m.row;
		this.col=m.col;
		this.stat=m.stat;
		this.rotate=m.rotate;
		this.clockwise=m.clockwise;
		this.rotQuad=m.rotQuad;

	}
	
	/**
	 * Default constructor for Move. Creates a move with an impossible move that cannot
	 * be played solely for an ai's inability to not move.
	 */
	public Move(){		//is is purely for if there are no possible moves
		row=-1;
		col=-1;
		stat=Status.EMPTY;
		rotate=false;
		clockwise=false;
		rotQuad=-1;
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
	 * @return Returns if there is a rotation for the move object
	 */
	public boolean getRotation(){
		return rotate;
	}
	
	/**
	 * 
	 * @return Returns rotation direction  for the move object
	 */
	public boolean getRotationDir(){
		return clockwise;
	}
	
	/**
	 * 
	 * @return Returns the quadrant to rotate for the move object
	 */
	public int getRotQuad(){
		return rotQuad;
	}
}
