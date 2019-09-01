package Poker;

import java.util.ArrayList;

public class Detect {
	
	Detect(){};
	
	/*----------------- countHand ------------------------------------------------
	 * function countHand(Hand)  
	 *
	 * Purpose: Counts number of occurrences of each card. This is then used to 
	 * 	detect what kind of hand it is. Function is passed an array of cards sorted 
	 * 	low to high. Each new value is stored in an ArrayList. If the card is a repeat,
	 * 	the ArrayList element is incremented. If not, a new element is added.
	 * 
	 *
	 * @param - Hand sHand - the sorted Hand 
	 *
	 * @return - returns the Hand, with "type" value set
	 *
	-----------------------------------------------------------------------------*/
	ArrayList<Integer> countHand(Hand sHand) {
		ArrayList<Integer> countList = new ArrayList<Integer>(Card.CARD_INIT);
		int most, secondMost, thirdMost, fourthMost, lastMost, counter, valueCount;
		most = secondMost = thirdMost = fourthMost = lastMost = Poker.ZERO;
		counter = valueCount = Card.CARD_INIT;
		countList.set(0, Card.CARD_INIT);
		for (int i = Poker.ZERO; i < (Deck.CPH - Card.CARD_INIT); i++) {
			if(sHand.hand[i].rank == sHand.hand[i + 1].rank) {
				counter++;
			}
			else {
				valueCount++;
				counter = Card.CARD_INIT;
				countList.add(valueCount, counter);
			}
			countList.set(valueCount, counter);
		}
		
		return countList;
	}
	
    
    // DETECT PRESENCE OF STRAIGHT
    
/*----------------- hasStraight ------------------------------------------------
 * function hasStraight(int[])  
 *
 * Purpose: Tests the hand for presence of a straight.
 * 	Function is passed an array of cards sorted low to high. Their
 * 	values are compared one by one against the counter of a for loop
 * 	that increments by one. If the value of the counter = the value of 
 * 	the card on every pass, it is a straight.
 *
 * @param - int[] sorted - the sorted cards in an integer array. 
 *
 * @return - true or false 
 *
-----------------------------------------------------------------------------*/
 /**
 *
 */
    static boolean hasStraight(int sorted[]){
        // if 2 low cards are 1 & 10, it is a royal straight, set ace high
	if ((sorted[1] == 1) && (sorted[2] == 10)){
            sorted[1] = sorted[2];
            sorted[2] = sorted[3];
            sorted[3] = sorted[4];
            sorted[4] = sorted[5];
            sorted[5] = Poker.HIGH_ACE;
	}
        // returns false if any card is not one more than the previous
	for(int count = Card.CARD_INIT + 1; count <= Deck.CPH; count++){
            int strtCount = (sorted[count] - sorted[count - 1]);
            if (strtCount != 1){
                    return false;
            } 
	}
	return true;
    }

}
