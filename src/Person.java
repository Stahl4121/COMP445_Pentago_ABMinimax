/**
 * This is a class for the human players. It inherits from player.
 *
 */
public class Person extends Player{

	public Person(Status color){
		this.color = color;
	}
	
	@Override
	public void makeMove(Board b) {
		System.out.println("vorerride");
	}
	
}
