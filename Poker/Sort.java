package Poker;

import java.util.ArrayList;
import Poker.Card;
import Poker.Deck;
import Poker.Hand;
import Poker.Poker;

public class Sort {
	
	Sort(){};

    
/*--------------------- countHand (Hand) -----------------------------------------
 * Takes a sorted hand and counts whether any cards have repeating values. 
 *  The results are used to determine what type of hand it is. If the hand
 *  turns out to be a better value than a "high card" hand, there may be
 *  a card that appears in combination that becomes the hand's highest value
 *  without being its highest face value. This will require that the hand
 *  be sorted again, and this information will be used to execute the sort.
 *  
 *  The results are returned as an ArrayList. The size of the ArrayList will 
 *  be anything from 2 to 5, depending on what kind of hand it is. For instance,
 *  a "full house" hand will return a 2-element list containing 3 and 2.
 * 
 * @param   sorted  Hand sorted in order of face value
 * @return          ArrayList of ints representing the number of times each
 * 					card value appears in the hand.
------------------------------------------------------------------------------*/
	static ArrayList<Integer> countHand(Hand sortedHand){
		ArrayList<Integer> handCount = new ArrayList<Integer>(Card.CARD_INIT);
		int position = 0;
		int count = 1;
		for(int i = 0; i < (Deck.CPH - 1); i++) {
			if (sortedHand.hand[i].rank == sortedHand.hand[i + 1].rank) {
				count++;
				handCount.set(i, count);
			}
			else {
				count = 1;
				position++;
				handCount.add(position, count);
			}
		}
		return handCount;
	}
    
/*------------------------ sortHand -----------------------------------------
 * Arranges a Hand of Card objects in order from lowest to highest value. 
 * 	Insertion sort will work well for such a small number of inputs.
 * 
 * @param uHand    the Hand object to be processed
 * @return         the Hand in sorted order
 *
 -----------------------------------------------------------------------------*/
    static Hand sortHand(Hand uHand){
    	int j;
    	for(int i = Card.CARD_INIT; i < Deck.CPH; i++) {
    		Card tmp = uHand.hand[i];
    		for(j = i; j > Poker.ZERO && tmp.compareTo(uHand.hand[j - 1]) < 0; j--) {
    			uHand.hand[j] = uHand.hand[j - 1];
    			uHand.hand[j - 1] = tmp;
    		}
    	}
    	return uHand;
    }
    
/*------------------------ scoreSort(Hand, ArrayList<Integer> -------------------
 * Arranges a Hand of Card objects in order of highest rankings, the way a card 
 * 	player would. Cards that appear in combination are ordered into the lower
 * 	elements of the array, and cards that appear singly are ordered into the 
 * 	higher elements of the array. This allows the hand to be displayed in an
 * 	order that a human card player might expect.
 * 
 * @param Hand uHand - the Hand object to be processed
 * @param ArrayList<Integer> scoreVals - count of ranks
 * @return Hand - sorted - the Hand re-sorted so that high ranks appear on the left
 *
 -----------------------------------------------------------------------------*/
    static Hand scoreSort(Hand uHand, ArrayList<Integer> scoreVals) {
    	int j;
    	int tmp;
    	Card tmpCard = new Card();
    	for(int i = 1; i < Deck.CPH; i++) {
    		tmp = scoreVals.get(i);
    		tmpCard = uHand.hand[i];
    		for(j = i; (j > 0) && tmp > scoreVals.get(i - 1); j--) {
    			if (scoreVals.get(i) > scoreVals.get(i - 1)){
	    			scoreVals.set(j, scoreVals.get(j - 1));
	    			scoreVals.set(j - 1, tmp);
	    			uHand.hand[i] = uHand.hand[i - 1];
	    			uHand.hand[i - 1] = tmpCard;
    			}
    		}
    	}
    	return uHand;
    }
}
