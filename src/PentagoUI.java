/*
 * 
 * Used Silvain Saurel's Tic Tac Toe JavaFX tutorial
 * https://www.youtube.com/watch?v=YJjqZIyUIrM
 */


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;

public class PentagoUI extends Application {
	private char currentPlayer = 'B';
	private int boardSize = 6;
	private Cell[][] cell = new Cell[boardSize][boardSize];
	private Label statusMsg = new Label("Person must play");
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		GridPane pane = new GridPane();
		
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				cell[i][j] = new Cell();
				pane.add(cell[i][j], j, i);
			}
		}
		
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(pane);
		borderPane.setBottom(statusMsg);
		
		Scene scene = new Scene(borderPane, 450, 300);
		primaryStage.setTitle("Pentago");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public boolean isBoardFull() {
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (cell[i][j] == null) { // need to link this to the board to check if it's empty
					return false;
				}				
			}
		}
		return true;
	}
	
	public boolean hasWon(char player) {
		// call board method. 
		return false;
	}
	
	public class Cell extends Pane {
		private char player = ' ';
		
		public Cell() {
			setStyle("-fx-border-color : black");
			this.setPrefSize(300,300);
			this.setOnMouseClicked(e -> handleClick());
		}
		
		private void handleClick() {
			setMarble(Color.BLACK); // set human player's marble
			// insert option to rotate the board
			
			if (hasWon(currentPlayer)) {
				statusMsg.setText(currentPlayer + "has won");
				currentPlayer = ' ' ;
			}
			else if (isBoardFull()) {
				statusMsg.setText("It's a draw");
			}
			else {
				currentPlayer = (currentPlayer == 'B') ? 'R' : 'B';
				statusMsg.setText(currentPlayer + " must play");
			}
			
			setMarble(Color.RED); // set ai player's marble
			// insert option to rotate the board
			
			if (hasWon(currentPlayer)) {
				statusMsg.setText(currentPlayer + "has won");
				currentPlayer = ' ' ;
			}
			else if (isBoardFull()) {
				statusMsg.setText("It's a draw");
			}
			else {
				currentPlayer = (currentPlayer == 'B') ? 'R' : 'B';
				statusMsg.setText(currentPlayer + " must play");
			}
		}
		
		public char getPlayer() {
			return player;
		}
		
		public void setMarble(Color c) {
			Ellipse ellipse = new Ellipse(this.getWidth()/2, this.getHeight()/2, this.getWidth()/2 -10, this.getHeight()/2 -10);
			ellipse.centerXProperty().bind(this.widthProperty().divide(2));
			ellipse.centerYProperty().bind(this.heightProperty().divide(2));
			ellipse.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));
			ellipse.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));
			//ellipse.setStroke(c);
			//ellipse.setFill(c);
			getChildren().add(ellipse);	
		}
	}
}
