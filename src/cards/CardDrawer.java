package cards;

import java.util.HashMap;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.VLineTo;

public class CardDrawer {
	
	
	private Map<CardNumber, Integer> myNumberMap;
	private Map<CardColor, Color> myColorMap;
	
	
	public CardDrawer(){
		myNumberMap = new HashMap<CardNumber, Integer>();
		myNumberMap.put(CardNumber.one, 1);
		myNumberMap.put(CardNumber.two, 2);
		myNumberMap.put(CardNumber.three, 3);
		
		
		myColorMap = new HashMap<CardColor, Color>();
		myColorMap.put(CardColor.green, Color.rgb(42, 212, 56));
		myColorMap.put(CardColor.orange, Color.rgb(242, 102, 56));
		myColorMap.put(CardColor.purple, Color.rgb(135, 70, 179));
	}
	
	public HBox drawCard(CardColor color, CardFill fill, CardNumber num, CardShape shape){
		HBox card = new HBox();
		card.setPrefSize(180, 90);
		card.setSpacing(15);
		card.setPadding(new Insets(12,0,0,0));
		card.setStyle("-fx-background-color: white; -fx-border-radius: 15; -fx-border-color: black;");
		card.setAlignment(Pos.CENTER);
		
		for(int i = 0; i < myNumberMap.get(num).intValue(); i++){
			card.getChildren().add(drawShape(shape, color, fill));
		}	
		
		return card;
	}
	
	private HBox drawShape(CardShape shape, CardColor color, CardFill fill){
		HBox box = new HBox();
		//box.setStyle("-fx-border-color: black;");
		Path path = null;
		if(shape.equals(CardShape.circle)){
			
			path = new Path();

	       
	        MoveTo moveTo = new MoveTo(50,50);
	        
	        ArcTo arcTop = new ArcTo();
	        arcTop.setX(80);
	        arcTop.setY(50);
	        arcTop.setRadiusX(15);
	        arcTop.setRadiusY(15);
	        arcTop.setSweepFlag(true);
	        
	        //HLineTo h = new HLineTo(80);
	        VLineTo a = new VLineTo(80);
	        
	        ArcTo arcBot = new ArcTo();
	        arcBot.setX(50);
	        arcBot.setY(80);
	        arcBot.setRadiusX(15);
	        arcBot.setRadiusY(15);
	        arcBot.setSweepFlag(true);
	        
	        VLineTo c = new VLineTo(50);

			path.getElements().addAll(moveTo, arcTop, a, arcBot, c);
			

		}
		else if (shape.equals(CardShape.diamond)){
			path = new Path();

			
			MoveTo one = new MoveTo(0,0);
			LineTo two = new LineTo(-16, 30);
			LineTo three = new LineTo(0, 60);
			LineTo four = new LineTo(16, 30);
			LineTo five = new LineTo(0,0);
			
			
	
			
			
			path.getElements().addAll(one, two, three, four, five);
			
		}
		
		else if(shape.equals(CardShape.squiggly)){
			path = new Path();
			

			MoveTo one = new MoveTo(-7,0);
			
			ArcTo two = new ArcTo();
			two.setX(17);
			two.setY(35);
			two.setRadiusX(25);
			two.setRadiusY(25);
			two.setSweepFlag(true);
			
			ArcTo three = new ArcTo();
			three.setX(17);
			three.setY(60);
			three.setRadiusX(40);
			three.setRadiusY(25);
			three.setSweepFlag(false);
			
			ArcTo four = new ArcTo();
			four.setX(15);
			four.setY(68);
			four.setRadiusX(17);
			four.setRadiusY(7);
			four.setSweepFlag(true);
			
			ArcTo five = new ArcTo();
			five.setX(-7);
			five.setY(33);
			five.setRadiusX(25);
			five.setRadiusY(25);
			five.setSweepFlag(true);
			
			
			ArcTo six = new ArcTo();
			six.setX(-7);
			six.setY(8);
			six.setRadiusX(40);
			six.setRadiusY(25);
			six.setSweepFlag(false);
			
			ArcTo seven = new ArcTo();
			seven.setX(-7);
			seven.setY(0);
			seven.setRadiusX(17);
			seven.setRadiusY(7);
			seven.setSweepFlag(true);
			
//			Path path2 = new Path();
//			path2.setStroke(Color.YELLOW);
//			path2.setFill(null);
//			path2.setStrokeWidth(3.0);
//			path2.getElements().addAll(one, two, three, four);
//			path2.setTranslateX(-37);
//			path2.setRotate(180);
			
			

			path.getElements().addAll(one, two, three, four, five, six, seven);
			
//			System.out.println(path.getElements());
//			System.out.println(path2.getElements());
			
			
			
			
		}
		path.setStrokeWidth(2);
		path.setStroke(myColorMap.get(color));
		
		if(fill.equals(CardFill.empty)){
			path.setFill(null);
		}
		else if(fill.equals(CardFill.full)){
			path.setFill(myColorMap.get(color));
		}
		else if(fill.equals(CardFill.lined)){
			
			if(color.equals(CardColor.green)){
				path.setFill(new ImagePattern(new Image("green_lines.png")));
			}
			else if(color.equals(CardColor.purple)){
				path.setFill(new ImagePattern(new Image("purple_lines.png")));
			}
			else if(color.equals(CardColor.orange)){
				path.setFill(new ImagePattern(new Image("orange_lines.png")));
			}
			
			
			
		}
		
        //path.setScaleY(.85);
		//path.setRotate(45);
		
		box.getChildren().addAll(path);
		
		return box;
	}
	
}
