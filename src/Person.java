import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This is a class for the human players. It inherits from player.
 *
 */
public class Person extends Player{
	public BufferedReader input = new BufferedReader(new InputStreamReader(System.in)); 
	public Person(Status color){
		this.color = color;
	}
	
	@Override
	public void makeMove(Board b) throws IOException {               
    	System.out.println("Input your next move");
        while(true) {
	        String s = input.readLine(); 
	        String[] tokens = s.split("");
	        System.out.println(tokens);
	        /*
	        int row = Integer.parseInt(tokens[0]);
	        int col = Integer.parseInt(tokens[1]);
	        int rot = Integer.parseInt(tokens[2]);
	        */
	        int row = 0;
	        int col = 0;
	        int rot = 0;
	        if (b.getStatus(row, col) == Status.EMPTY) {
	        	b.addMarble(row, col, this.color);
	        	b.parseAndRotate(rot);
	        	break;
	        }
	        System.out.println("Input a valid move");
        }
	}
	
}
