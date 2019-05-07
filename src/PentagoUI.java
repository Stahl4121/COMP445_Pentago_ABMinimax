
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
	final int AI_DEPTH = 4;
	Board b;
	GridPane myGrid;
	Label gameMessage;
	int choice = 0;
	boolean isP1Turn = true;
	boolean hasPlaced = false;
	boolean hasRotated = false;
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
			gameMessage.setFont(new Font(22));

			//Pad around the grid
			myGrid.setPadding(new Insets(25,25,25,25));

			loadStartChoices();

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

	public void loadGameStart() {
		myGrid.getChildren().removeAll(myGrid.getChildren());

		if (choice != 3) {
			displayAIButton();	
		}

		updateBoard();
	}

	public void loadStartChoices() {
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
				p1 = new AI(AI_DEPTH, Status.WHITE);
				p2 = new AI(AI_DEPTH, Status.BLACK);
				loadGameStart();
				gameMessage.setText("White, it's your turn!");
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
				p2 = new AI(AI_DEPTH, Status.BLACK);
				loadGameStart();

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
				loadGameStart();
				gameMessage.setText("Click the button to let the AI move!");
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

	public void displayAIButton() {
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
				if(choice==1) {
					if(isP1Turn) {
						b.makeMove(((AI)p1).getMove(b));
						updateBoard();
						isP1Turn = false;
						gameMessage.setText("AI White moved! Click the button to initiate AI Black's turn!");
					}
					else {
						b.makeMove(((AI)p2).getMove(b));
						updateBoard();
						isP1Turn = true;
						gameMessage.setText("AI Black moved! Click the button to initiate AI White's turn!");
					}
				}
				else if(choice==2) {
					if(!isP1Turn) {
						b.makeMove(((AI)p2).getMove(b));
						updateBoard();
						isP1Turn = true;
						gameMessage.setText("White, make your move!");
					}
				}
			}
		});

		btnAI.setFont(new Font(14));
		myGrid.add(btnAI, 0, 6, 6, 1);
	}

	public void updateBoard() {
		//Clear board
		myGrid.getChildren().removeAll(myGrid.getChildren());

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
										b.addMarble(row, col, Status.WHITE);
										hasPlaced = true;
									}
									if(hasRotated) {
										isP1Turn = true;
										hasPlaced = false;
										gameMessage.setText("White, it's your turn!");
									}	
								}
							}
							updateBoard();
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

		myGrid.add(gameMessage, 0, 7, 6, 2);

		if(b.winner() != Status.EMPTY) {
			endGame();
		}
	}

	public void endGame() {

	}

	public static void main(String[] args) { launch(args); }

}
