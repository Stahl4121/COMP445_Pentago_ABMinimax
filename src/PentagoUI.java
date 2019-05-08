
import java.util.ArrayList;

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
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PentagoUI extends Application {
	final int AI_DEPTH = 3;
	Board b;
	GridPane myGrid;
	Label gameMessage;
	Button btnAI;
	ArrayList<Button> rotateBtns;
	int choice = 0;
	boolean isP1Turn = true;
	boolean hasPlaced = false;
	boolean hasRotated = false;
	boolean demoGreedy = true;
	int row = 0;
	int col = 0;
	int rot = 0;
	Player p1;
	Player p2;


	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			//Create New Grid
			b = new Board();
			myGrid = new GridPane();
			gameMessage = new Label("TESTING");
			gameMessage.setFont(new Font(18));

			//Pad around the grid
			myGrid.setPadding(new Insets(25,25,25,25));

			loadStartChoices();
			setupRotateButtons();
			setupAIButton();

			BorderPane borderPane = new BorderPane();
			borderPane.setCenter(myGrid);

			Scene scene = new Scene(borderPane, 450, 450);
			primaryStage.setTitle("Pentago");
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void loadGameScreen() {
		try {
			myGrid.getChildren().removeAll(myGrid.getChildren());

			if (choice != 3) {
				myGrid.add(btnAI, 0, 6, 6, 1);
			}
			if (choice != 1) {
				for(int i=0; i<4; i++) {
					myGrid.add(rotateBtns.get(i), 7, i+1);
				}
				for(int i=4; i<8; i++) {
					myGrid.add(rotateBtns.get(i), 8, i-3);
				}
			}

			myGrid.add(gameMessage, 0, 7, 7, 1);
			updateBoard();

			Status s = b.winner();
			if(s != Status.EMPTY) {
				endGame(s);
			}
		}
		catch(Exception e) {}
	}

	public void setupRotateButtons() {
		try {
			rotateBtns = new ArrayList<Button>();

			for(int i=1; i<=8; i++) {
				int rot = i;
				if(((i-1) / 4) > 0) {
					rot = -1* (i-4);
				}
				final int r = rot;

				Button btn = new Button("R " + r);
				btn.setFont(new Font(12));

				btn.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
					btn.setEffect(new DropShadow());
				});
				btn.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
					btn.setEffect(null);
				});

				btn.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {

						if(isP1Turn) {
							if(!hasRotated) {
								b.rotate(r);
								hasRotated = true;
							}
							if(hasPlaced) {
								isP1Turn = false;
								hasPlaced = false;
								hasRotated = false;

								if (choice == 2) {
									gameMessage.setText("Click the button to let AI Black move!");
								}
								else {
									gameMessage.setText("Black, it's your turn!");
								}
							}
						}
						else {
							if (choice != 2) {
								if(!hasRotated) {
									b.rotate(r);
									hasRotated = true;
								}
								if(hasPlaced) {
									isP1Turn = true;
									hasPlaced = false;
									hasRotated = false;

									gameMessage.setText("White, it's your turn!");
								}	
							}
						}
						loadGameScreen();
					}
				});

				rotateBtns.add(btn);
			}
		}
		catch(Exception e){}

	}

	public void loadStartChoices() {
		try {
			DropShadow shadow = new DropShadow();
			Button btnChoice1 = new Button("AI vs. AI");
			Button btnChoice2 = new Button("Human vs. AI");
			Button btnChoice3 = new Button("Human vs. Human");


			btnChoice1.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
				btnChoice1.setEffect(shadow);
			});
			btnChoice1.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
				btnChoice1.setEffect(null);
			});

			btnChoice1.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					choice = 1;
					if (demoGreedy) {
						p1 = new GreedyAI(Status.WHITE);

					}
					else {
						p1 = new AI(AI_DEPTH, Status.WHITE);
					}
					
					p2 = new AI(AI_DEPTH, Status.BLACK);
					loadGameScreen();
					gameMessage.setText("Click to run AI White!");
				}
			});


			btnChoice2.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
				btnChoice2.setEffect(shadow);
			});
			btnChoice2.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
				btnChoice2.setEffect(null);
			});

			btnChoice2.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					choice = 2;
					p1 = new Person(Status.WHITE);

					if (demoGreedy) {
						p2 = new GreedyAI(Status.BLACK);

					}
					else {
						p2 = new AI(AI_DEPTH, Status.BLACK);
					}
					loadGameScreen();
					gameMessage.setText("White, it's your turn!");
				}
			});

			btnChoice3.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
				btnChoice3.setEffect(shadow);
			});
			btnChoice3.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
				btnChoice3.setEffect(null);
			});

			btnChoice3.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					choice = 3;
					p1 = new Person(Status.WHITE);
					p2 = new Person(Status.BLACK);
					loadGameScreen();
					gameMessage.setText("White it's your turn!");
				}
			});

			btnChoice1.setFont(new Font(20));
			btnChoice2.setFont(new Font(20));
			btnChoice3.setFont(new Font(20));


			myGrid.add(btnChoice1, 0, 0);
			myGrid.add(btnChoice2, 0, 1);
			myGrid.add(btnChoice3, 0, 2);

			gameMessage.setText("Choose your play style!");
			myGrid.add(gameMessage, 0, 3);

		}
		catch(Exception e) {}
	}

	public void setupAIButton() {
		try {
			btnAI = new Button("Let AI Move");
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
					if(choice==1) {
						if(isP1Turn) {

							if (demoGreedy) {
								b.makeMove(((GreedyAI)p1).getMove(b));

							}
							else {
								b.makeMove(((AI)p1).getMove(b));
							}
							isP1Turn = false;
							gameMessage.setText("AI White moved! Click to run AI Black!");
						}
						else {
							b.makeMove(((AI)p2).getMove(b));
							isP1Turn = true;
							gameMessage.setText("AI Black moved! Click to run AI White!");
						}
					}
					else if(choice==2) {
						if(!isP1Turn) {
							if (demoGreedy) {
								b.makeMove(((GreedyAI)p2).getMove(b));

							}
							else {
								b.makeMove(((AI)p1).getMove(b));
							}
							isP1Turn = true;
							gameMessage.setText("White, make your move!");
						}
					}
					loadGameScreen();
				}
			});

			btnAI.setFont(new Font(14));
		}
		catch(Exception e) {}
	}

	public void updateBoard() {
		try {

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
						iv.setOnMouseClicked((MouseEvent e) -> {
							int xpos = (int) e.getSceneX();
							int ypos = (int) e.getSceneY();
							row = (ypos - 25)/50;
							col = (xpos - 25)/50;

							if(choice!=1) {
								if(isP1Turn) {
									if(!hasPlaced) {
										b.addMarble(row, col, Status.WHITE);
										hasPlaced = true;
									}
									if(hasRotated) {
										isP1Turn = false;
										hasRotated = false;
										hasPlaced = false;

										if (choice == 2) {
											gameMessage.setText("Click the button to let AI Black move!");
										}
										else {
											gameMessage.setText("Black, it's your turn!");
										}
									}
								}
								else {
									if (choice != 2) {
										if(!hasPlaced) {
											b.addMarble(row, col, Status.BLACK);
											hasPlaced = true;
										}
										if(hasRotated) {
											isP1Turn = true;
											hasRotated = false;
											hasPlaced = false;
											gameMessage.setText("White, it's your turn!");
										}	
									}
								}
								loadGameScreen();
							}
						});
					}

					iv.setFitWidth(50);
					iv.setFitHeight(50);
					iv.setPreserveRatio(true);
					iv.setSmooth(true);
					iv.setCache(true);

					myGrid.add(iv, c, r);
				}
			}

		}
		catch(Exception e) {}
	}

	public void endGame(Status s) {
		try {
			myGrid.getChildren().removeAll(myGrid.getChildren());
			updateBoard();

			myGrid.add(gameMessage, 0, 7, 7, 1);
			if(s == null) {
				gameMessage.setText("The two players tied!");
			}
			else if(s == Status.BLACK) {
				gameMessage.setText("Black wins!");
			}
			else {
				gameMessage.setText("White wins!");
			}

			for(int i=0 ; i<myGrid.getChildren().size() ; i++){
				myGrid.getChildren().get(i).setDisable(true);
			}
		}
		catch(Exception e) {}
	}

	public static void main(String[] args) { launch(args); }

}
