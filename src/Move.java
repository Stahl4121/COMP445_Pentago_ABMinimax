/**
 * Position on board
 *
 */
public class Move {
	private int row;
	private int col;
	private Status stat;


	/**
	 * Constructor for Move
	 * @param r row of move
	 * @param c column of move
	 * @param piece Colour of piece being placed
	 */
	public Move(int r,int c, Status piece){
		row=r;
		col=c;
		stat=piece;

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

}
