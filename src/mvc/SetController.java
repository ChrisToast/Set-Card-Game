package mvc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import cards.SetCard;

public class SetController {

	private Group myRoot;
	private SetModel myModel;
	private List<SetCard> mySelected;
	private List<SetCard> myHighlightedSet;
	private GridPane myGrid;
	private Text cardsLeft;
	private Text myTime;
	
	private Button howMany;
	private Button showSet;
	private Timeline timeline;
	
	private static final int NUM_ROWS = 4;
	private static final int NUM_COLS = 3;
	private static final int NUM_CARDS_ON_TABLE = NUM_ROWS * NUM_COLS;

	public SetController(Group root) {
		myRoot = root;
		mySelected = new ArrayList<SetCard>();
		myHighlightedSet = new ArrayList<SetCard>();
		cardsLeft = new Text();
		myTime = new Text();
		
		BorderPane myBorderPane = new BorderPane();

		myGrid = new GridPane();
		myGrid.setHgap(10);
		myGrid.setVgap(10);

		
		
		Button newGame = new Button("New Time Trial");
		newGame.setOnMouseClicked(e -> startTimeTrial());
		
		ToolBar toolBar = new ToolBar(
			     newGame,
			     new Button("Open"),
			     new Button("Save"),
			     new Separator(),
			     new Button("Clean"),
			     new Button("Compile"),
			     new Button("Run"),
			     new Separator(),
			     new Button("Debug"),
			     new Button("Profile"),
			     new Button("Profile"),
			     new Button("Profile")
			 );
		
		
		
		
		
		howMany = new Button("Show Set Number");
		howMany.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				System.out.println(myModel.howManySets());
			}
		});
		
		Button noSet = new Button("There's no set");
		noSet.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				if(myModel.howManySets() == 0){
					addThreeMoreCards();
				}
				else{
					System.out.println("No, there is a set.");
				}
			}
		});
		
		showSet = new Button("Show Set");
		showSet.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				showSet();
			}
		});
		
		VBox right = new VBox();
		right.setSpacing(10.0);
		right.setPadding(new Insets(10,10,10,10));
		right.getChildren().addAll(noSet, showSet, howMany);
		
		VBox bottom = new VBox();
		bottom.setPadding(new Insets(10,10,10,10));
		bottom.setSpacing(10.0);
		bottom.getChildren().add(cardsLeft);
		bottom.getChildren().add(myTime);
		
		
		myBorderPane.setBottom(bottom);
		myBorderPane.setTop(toolBar);
		myBorderPane.setRight(right);
		myBorderPane.setCenter(myGrid);
		myRoot.getChildren().add(myBorderPane);
		


	}

	public void startGame() {
		myModel = new SetModel();
		myGrid.getChildren().clear();
		for (int i = 0; i < NUM_ROWS; i++) {
			for (int j = 0; j < NUM_COLS; j++) {
				addNewCardToGrid(j, i);
			}
		}
		
		
	}
	
	private void startTimeTrial(){
		
		//showSet.setDisable(true);
		//howMany.setDisable(true);
		addTimer();
		startGame();
		
	}

	public void addSelected(SetCard c) {
		mySelected.add(c);

		if (mySelected.size() == 3) {

			if (myModel.isSet(mySelected.get(0), 
							  mySelected.get(1),
							  mySelected.get(2))) {

				handleSet();

			} else {
				System.out.println("NO SET");
			}
			for (SetCard s : mySelected) {
				// TODO put these in one method in SetCard
				s.flipSelected();
				s.unselectVisually();
			}
			mySelected.clear();

		}

	}

	public void removeSelected(SetCard c) {
		//creating a new wrapper every time, have to check against the actual card	
		mySelected.remove(c);
	}


	public Node getNodeByColRowIndex(final int col, final int row,
			GridPane gridPane) {
		Node result = null;
		ObservableList<Node> children = gridPane.getChildren();
		for (Node node : children) {
			if (gridPane.getRowIndex(node) == row
					&& gridPane.getColumnIndex(node) == col) {
				result = node;
				break;
			}
		}
		return result;
	}

	private void handleSet() {
		System.out.println("SET!");
		
		boolean shouldNotAddMore = myGrid.getChildrenUnmodifiable().size() > 12;
		//we just added three extra due to no set, so now we shouldn't replace them

		for (SetCard card : mySelected) {
			int[] pos = null;
			
			for(Node node : myGrid.getChildren()){ //remove selected cards from grid, store their position to later fill
				if(card.getMyDrawing().equals(node)){
					myGrid.getChildren().remove(node);
					pos = new int[]{myGrid.getColumnIndex(node), myGrid.getRowIndex(node)};
					break;
				}			
			}

			myModel.removeCardFromTable(card);

			if(!shouldNotAddMore){ //only add if we should
				addNewCardToGrid(pos[0], pos[1]);
			}
		}
		
		if(shouldNotAddMore){
			rearrangeBottomRow();
		}
		
		//un-highlight a shown set
		for(SetCard card : myHighlightedSet){
			card.unselectVisually();
		}
		
		checkDone();
	}
	
	private void rearrangeBottomRow(){
		
		//TODO move bottom row from 18 cards to vacancies
		
		//some janky ass logic here

		if(myGrid.getChildrenUnmodifiable().size() == 12){
			
			//first find where the 1 or 2 empty positions in the normal 12 cards are
			List<int[]> emptyPositions = new ArrayList<int[]>();
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 3; j++) {
					Node node = getNodeByColRowIndex(j, i, myGrid);
					if(node == null){
						emptyPositions.add(new int[]{i, j});
					}		
				}
			}
			
			//get the 1 or 2 cards from the bottom row that need to be shifted up
			//delete them from the grid for now
			List<Node> cardsToMove = new ArrayList<Node>();		
			for(int i = 0; i <= 2; i++){
				Node node = getNodeByColRowIndex(i, 4, myGrid);
				if(node != null){
					cardsToMove.add(node);
					myGrid.getChildren().remove(node);
				}
				
			}
			
			//fill the empty positions with the bottom row card(s)
			for(int i = 0; i < emptyPositions.size(); i++){
				int iPos = emptyPositions.get(i)[0];
				int jPos = emptyPositions.get(i)[1];
				myGrid.add(  cardsToMove.get(i), jPos, iPos  );	
			}
		}
	}
	
	private void addNewCardToGrid(int j, int i){
		SetCard nextCard = myModel.drawCard();
		if(nextCard == null) return; //deck is empty
	
		nextCard.setOnClick(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				if (!nextCard.isSelected()) {
					nextCard.selectVisually();
					addSelected(nextCard);
				} else {
					nextCard.unselectVisually();
					removeSelected(nextCard);
				}
				nextCard.flipSelected();
			}
		});

		myGrid.add(nextCard.getMyDrawing(), j, i);
		cardsLeft.setText("Cards Remaining in Deck: " + myModel.getNumCardsRemaining());
		
		
	}
	
	private void addThreeMoreCards(){
		if(myGrid.getChildrenUnmodifiable().size() == 12){
			for(int i = 0; i <= 2; i++){				
				addNewCardToGrid(i, 4);
			}
		}
		else if(myGrid.getChildrenUnmodifiable().size() == 15){
			for(int i = 0; i <= 2; i++){
				addNewCardToGrid(i, 5);
			}
		}
	}
	
	private void showSet(){
		Set<Set<SetCard>> allSets = myModel.findSets();
		if(allSets.size() == 0) return;
		Set<SetCard> toShow = allSets.iterator().next();
		for(SetCard c : toShow){
			myHighlightedSet.add(c);
			c.highlightBlue();
		}
	}
	
	
	private void addTimer(){
		
		Date startDate = new Date();
		
		if(timeline != null) timeline.stop();
		
		timeline = new Timeline(
                new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>(){

                	
					@Override
					public void handle(ActionEvent event) {
						
						
						Date nowDate = new Date();
						
						Date diff = new Date(nowDate.getTime() - startDate.getTime());
						
						SimpleDateFormat sdf = new SimpleDateFormat("mm:ss.SSS");
						String formatted = sdf.format(diff);
						formatted = formatted.substring(0, formatted.length() - 2);
						myTime.setText(formatted);
					
					}
                	
                	
                	
                	
                }, (KeyValue) null)
            );
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
            
		
	}
	
	
	private void checkDone(){
		
		if(myModel.getNumCardsRemaining() == 0 && myModel.howManySets() == 0){
			
			timeline.stop();
			System.out.println(myTime.toString());
			
		}
		
	}

}
