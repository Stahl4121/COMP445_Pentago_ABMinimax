/*
 * 
 * Used Silvain Saurel's Tic Tac Toe JavaFX tutorial
 * https://www.youtube.com/watch?v=YJjqZIyUIrM
 */


import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;

public class PentagoUI extends Application {

	//Eliminates some nasty bugs from having multiple System.in scanners
	public static Scanner scnr = new Scanner(System.in);

	@Override
	public void start(Stage primaryStage) throws Exception {

		try {
			//Create New Grid
			Board b = new Board();
			GridPane myGrid = new GridPane();
			final int AI_DEPTH = 4;
			
			int choice = 1;
			boolean isP1Turn = true;
			
			//Player references are made, but not initialized to types
			Player player1;
			Player player2;

			
			
			if (choice==0) {
				p1 = new Person(Status.BLACK);
				p2 = new Person(Status.WHITE);
			}
			else if (choice==1) {
				p1 = new Person(Status.BLACK);
				p2 = new AI(AI_DEPTH, Status.WHITE);
			}
			else {
				p1 = new Person(Status.BLACK);
				p2 = new AI(AI_DEPTH,Status.WHITE);
			}
			
			displayAIButton(myGrid);
			updateBoard(b, myGrid);
			
			BorderPane borderPane = new BorderPane();
			borderPane.setCenter(myGrid);
			borderPane.setBottom(statusMsg);

			Scene scene = new Scene(borderPane, 450, 350);
			primaryStage.setTitle("Pentago");
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void displayAIButton(GridPane myGrid) {
		Button btnAI = new Button("Let AI Move");
		DropShadow shadow = new DropShadow();
		
		btnAI.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			btnAI.setEffect(shadow);
					});
		btnAI.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			btnAI.setEffect(null);
					});
		
		btnAI.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
			}
		});
		myGrid.add(btnAI, 6, 0);
	}
	
	public void updateBoard(Board b, GridPane myGrid) {
		//GridPane myGrid = new GridPane();

		
		//Pad around the grid
		myGrid.setPadding(new Insets(25,25,25,25));


		// load the images
		Image blackImg = new Image("/black.png");
		Image whiteImg = new Image("/white.png");
		Image emptyImg = new Image("/empty.png");


		for (int r = 0; r < b.BOARD_SIZE; r++) {
			for (int c = 0; c < b.BOARD_SIZE; c++) {
				ImageView iv = new ImageView();
				
				if(b.board[r][c] == Status.BLACK) {
					iv.setImage(blackImg);
				}
				else if(b.board[r][c] == Status.WHITE) {
					iv.setImage(whiteImg);
				}
				else {
					iv.setImage(emptyImg);
					iv.setPickOnBounds(true); // allows click on transparent areas
//					iv.setOnMouseClicked((MouseEvent e) -> {
//						if(isP1Turn) {
//							int row = ((int) (e.getScreenY() - 25))/60;
//							int col = ((int) (e.getScreenY() - 25))/60;
//							
//							
//						}
//						System.out.println("Clicked!"); // change functionality
//					});
				}

				iv.setFitWidth(50);
				iv.setPreserveRatio(true);
				iv.setSmooth(true);
				iv.setCache(true);

				myGrid.add(iv, c, r);
			}
		}
	}

//	public boolean hasWon(char player) {
//		// call board method. 
//		return false;
//	}
//
//	public class Cell extends Pane {
//		private char player = ' ';
//
//		public Cell() {
//			setStyle("-fx-border-color : black");
//			this.setPrefSize(300,300);
//			this.setOnMouseClicked(e -> handleClick());
//		}
//
//		private void handleClick() {
//			setMarble(Color.BLACK); // set human player's marble
//			// insert option to rotate the board
//
//			if (hasWon(currentPlayer)) {
//				statusMsg.setText(currentPlayer + "has won");
//				currentPlayer = ' ' ;
//			}
//			else if (isBoardFull()) {
//				statusMsg.setText("It's a draw");
//			}
//			else {
//				currentPlayer = (currentPlayer == 'B') ? 'R' : 'B';
//				statusMsg.setText(currentPlayer + " must play");
//			}
//
//			setMarble(Color.RED); // set ai player's marble
//			// insert option to rotate the board
//
//			if (hasWon(currentPlayer)) {
//				statusMsg.setText(currentPlayer + "has won");
//				currentPlayer = ' ' ;
//			}
//			else if (isBoardFull()) {
//				statusMsg.setText("It's a draw");
//			}
//			else {
//				currentPlayer = (currentPlayer == 'B') ? 'R' : 'B';
//				statusMsg.setText(currentPlayer + " must play");
//			}
//		}
//
//		public char getPlayer() {
//			return player;
//		}
//
//		public void setMarble(Color c) {
//			Ellipse ellipse = new Ellipse(this.getWidth()/2, this.getHeight()/2, this.getWidth()/2 -10, this.getHeight()/2 -10);
//			ellipse.centerXProperty().bind(this.widthProperty().divide(2));
//			ellipse.centerYProperty().bind(this.heightProperty().divide(2));
//			ellipse.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));
//			ellipse.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));
//			//ellipse.setStroke(c);
//			//ellipse.setFill(c);
//			getChildren().add(ellipse);	
//		}
//	}

	public static void main(String[] args) { launch(args); }

}
