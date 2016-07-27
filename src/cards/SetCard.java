package cards;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;


public class SetCard {
	
	
	private CardNumber myCardNumber;
	private CardShape myCardShape;
	private CardColor myCardColor;
	private CardFill myCardFill;
	private CardTilt myTilt;
	
	private boolean isSelected = false;
	private EventHandler<MouseEvent> myOnClick;
	
	private HBox myCardDrawing;
	
	public SetCard(CardNumber num, CardShape shape, CardColor color, CardFill fill){
		this(num, shape, color, fill, CardTilt.straight);
	}
	
	public SetCard(CardNumber num, CardShape shape, CardColor color, CardFill fill, CardTilt tilt){
		myCardNumber = num;
		myCardShape = shape;
		myCardColor = color;
		myCardFill = fill;
		myTilt = tilt;		
		myCardDrawing = this.drawMyShape();
	}
	
	private HBox drawMyShape(){
		HBox card = new CardDrawer().drawCard(myCardColor, myCardFill, myCardNumber, myCardShape, myTilt);
		//card.setOnMouseClicked(myOnClick);
		return card;
	
	}
	
	public CardNumber getNum(){
		return myCardNumber;
	}
	
	public CardShape getShape(){
		return myCardShape;
	}
	
	public CardColor getColor(){
		return myCardColor;
	}
	
	public CardFill getFill(){
		return myCardFill;
	}
	
	public CardTilt getTilt(){
		return myTilt;
	}
	
	public boolean isSelected(){
		return isSelected;
	}
	
	public void flipSelected(){
		isSelected = !isSelected;
	}
	
	public void setOnClick(EventHandler<MouseEvent> onClick){
		myCardDrawing.setOnMouseClicked(onClick);
	}
	
	public HBox getMyDrawing(){
		return myCardDrawing;
	}
	
	public void selectVisually(){
		myCardDrawing.setStyle("-fx-background-color: #ffffb3; -fx-background-radius: 15; -fx-border-radius: 15; -fx-border-color: black;");
	}
	
	public void unselectVisually(){
		myCardDrawing.setStyle("-fx-background-color: white; -fx-border-radius: 15; -fx-border-color: black;");
	}
	
	public void highlightBlue(){
		myCardDrawing.setStyle("-fx-background-color: #e5f5ff; -fx-background-radius: 15; -fx-border-radius: 15; -fx-border-color: black;");
	}
	
}
