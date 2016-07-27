package mvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import javafx.scene.layout.HBox;
import cards.CardColor;
import cards.CardFill;
import cards.CardNumber;
import cards.CardShape;
import cards.SetCard;

public class SetModel {
	
	private CardColor[] myPossibleColors = {CardColor.green, CardColor.orange, CardColor.purple};
	private CardShape[] myPossibleShapes = {CardShape.circle, CardShape.diamond, CardShape.squiggly};
	private CardFill[] myPossibleFills = {CardFill.empty, CardFill.lined, CardFill.full};
	private CardNumber[] myPossibleNumbers = {CardNumber.one, CardNumber.two, CardNumber.three};

	
	private Stack<SetCard> myDeck;
	private List<SetCard> myCardsOnTable;
	
	
	private Random r = new Random(12); //no set on first 12
	

	public SetModel(){
		myCardsOnTable = new ArrayList<SetCard>();
		myDeck = this.createDeck();
	}
	
	
	/**
	 * Creates a deck of all possible set cards
	 * @param shouldShuffle whether or not to shuffle the deck
	 * @return The deck as a List
	 */
	private Stack<SetCard> createDeck(){
		Stack<SetCard> deck = new Stack<SetCard>();
		for(CardNumber num : myPossibleNumbers){
			for(CardShape shape : myPossibleShapes){
				for(CardColor color : myPossibleColors){
					for(CardFill fill : myPossibleFills){
						SetCard card = new SetCard(num, shape, color, fill);
						deck.push(card);
					}
				}
			}
		}

		Collections.shuffle(deck);
		return deck;
	}
		
	public SetCard drawCard(){
		if(myDeck.empty()){
			return null;
		}
		SetCard toRet = myDeck.pop();
		myCardsOnTable.add(toRet);
		return toRet; 
	}
	
	
	public Set<Set<SetCard>> findSets(){
		
		int numCardsOnTable = myCardsOnTable.size();
		Set<Set<SetCard>> allSets = new HashSet<Set<SetCard>>();
		
		for(int i = 0; i < numCardsOnTable; i++){
			for(int j = i+1; j < numCardsOnTable; j++){
				for(int k = j+1; k < numCardsOnTable; k++){
					
					SetCard card1 = myCardsOnTable.get(i);
					SetCard card2 = myCardsOnTable.get(j);
					SetCard card3 = myCardsOnTable.get(k);
					
					if(isSet(card1, card2, card3)){
						
						Set<SetCard> thisSet = new HashSet<SetCard>(3);
						thisSet.add(card1);
						thisSet.add(card2);
						thisSet.add(card3);
						allSets.add(thisSet);
						
					}
						
					
				}
			}
		}
		return allSets;
		
	}
	
	
	public int howManySets(){
		return findSets().size();	
	}
	
	public boolean isSet(SetCard card1, SetCard card2, SetCard card3){
		Set<CardColor> checkSetColor = new HashSet<CardColor>();
		checkSetColor.add(card1.getColor());
		checkSetColor.add(card2.getColor());
		checkSetColor.add(card3.getColor());
		
		Set<CardNumber> checkSetNumber = new HashSet<CardNumber>();
		checkSetNumber.add(card1.getNum());
		checkSetNumber.add(card2.getNum());
		checkSetNumber.add(card3.getNum());
		
		Set<CardShape> checkSetShape = new HashSet<CardShape>();
		checkSetShape.add(card1.getShape());
		checkSetShape.add(card2.getShape());
		checkSetShape.add(card3.getShape());
		
		Set<CardFill> checkSetFill = new HashSet<CardFill>();
		checkSetFill.add(card1.getFill());
		checkSetFill.add(card2.getFill());
		checkSetFill.add(card3.getFill());
		
		return  checkSetColor.size() != 2 &&
				checkSetNumber.size() != 2 &&
		        checkSetShape.size() != 2 &&
		        checkSetFill.size() != 2;

	}
	
	public void removeCardFromTable(SetCard c){
		myCardsOnTable.remove(c);
		
		//System.out.println(myCardsOnTable.size());
	}
	
	public int getNumCardsRemaining(){
		return myDeck.size();
	}
		
}
