
public class Driver_PentagoGame {
	public static void main(String[] args) {
		//PROJECT TODO (Not necessarily in this class):
		Board b = new Board();
		b.addBlackMarble(1, 1);
		b.addWhiteMarble(0,1);
		b.addBlackMarble(2, 2);
		b.addBlackMarble(2, 1);
		System.out.println(b);
		b.rotate(2, true);
		System.out.println(b);
		b.rotate(2, false);
		System.out.println(b);
		//Create Pentago Game Structure (Board, moves, inputs, check win/lose conditions)
		//Create UI for game? (Maybe not too much harder than doing a console printed UI)
		//Enable user to make inputs (get 2-player working)
		//Write minimax
		//Write alpha-beta 
		//Profit
	}
}
