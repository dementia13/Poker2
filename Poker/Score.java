/*
/*
 * Copyright 2017 Sean R Quinn

Redistribution and use in source and binary forms, with or without 
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, 
this list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice, 
this list of conditions and the following disclaimer in the documentation 
and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE 
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL 
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR S
ERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER 
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, 
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE 
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package Poker;

import java.util.ArrayList;
import Poker.Card;
import Poker.Deck;
import Poker.Hand;
import Poker.Poker;

/**
 *
 * @author Sean Quinn
 * sean@dementia13.net
 * 
 * This class contains the various functions used by Poker for scoring and 
 *  ranking the hands. Scoring is based on five-card draw rules as
 * 	referenced from website https://www.pokerstars.com/poker/games/rules/hand-rankings/ 
 * 
 * 	Order of rankings:
 * 	Straight flush (a sequence of five cards in order, all of the same suit)
 * 	Four of a kind
 * 	Full house (three of one value and two of another)
 * 	Flush (all five cards the same suit)
 * 	Straight (a sequence of five cards in order)
 * 	Three of a kind
 * 	Two pair
 * 	One pair
 * 	High card (no value is present more than once)
 * 
 * 	The low cards are counted in the score. Hands are sorted in order, and
 * 	a tie at one position will result in the next lower card being used as a tiebreaker.
 *  For instance, two hands may each contain a pair of fours and will require the
 *  values of the low cards to determine a winner.
 *  
 * 	Each position is weighted so that no combination of lower card values can be 
 * 	valued higher than the cards that combine to give the rank. If card values alone 
 * 	are counted, then a "high card" hand could easily be scored higher than a hand
 * 	with four "two"s, for example. Each rank is given a weight which is calculated
 * 	by adding the card combination and shifting left by an appropriate amount
 * 	before adding in the values of the low cards. The shift left amount is determined
 * 	by how many bits are necessary to represent the highest possible score of the low
 * 	card combination. For instance, the highest possible "high card" combination is
 *  K-Q-J-10-8, or 13 + 12 + 11 + 10 + 8, or 54. This requires six bits to represent.
 *  The next highest combination is "one pair". The pair value will be shifted left by
 *  six bits and added to the score of the three remaining cards.
 * 
 * 	High card - 	combination of face values
 * 	One pair - 		pair shifted six bits
 * 	Two pairs -		first pair shifted five bits, second pair shifted six
 * 	Three/kind -	five bits
 * 	Straight -		six bits
 * 	Flush - 		four bits
 * 	Full house -	six bits
 * 	Four/kind -		six bits
 * 	Straight flush -	six bits
 * 
 * 	Bit shifts are cumulative, so the straight flush score is shifted left a total of
 * 	44 bits
 */
public class Score {
    
    static final int HIGH_ACE = 14; // value of high ace in a straight
    
    Score(){};
    
/*-------------------- getScore -------------------------------------------------
 * Receives a sorted hand and assigns it a score
 * 
 * @param   aHand   Each hand is passed to this function at its creation
 * @return          a long integer score value
 ---------------------------------------------------------------------------------*/
    static long getScore(Hand theHand, ArrayList<Integer> rankCount) {
    	switch (rankCount.size()) {
    	case 2:
    		switch (rankCount.get(0)) {
    		case 4:
    			return isFourKind(theHand);
    		case 3:
    			return isFullHouse(theHand);
    		default:
				break;
    		}
    		
    	case 3:
    		switch (rankCount.get(0)) {
    		case 3:
    			return isThreeKind(theHand);
    		case 2:
    			return isTwoPair(theHand);
    		}
    	
    	case 4:
    		return isPair(theHand);
    		
    	case 5:
    		if(getStraight(theHand)) {
	    		if(getFlush(theHand)) {
	    			return isStraightFlush(theHand);
	    		}
	    		else
	    			return isStraight(theHand);
    		}
    		else
    			if(getFlush(theHand)) {
    				return isFlush(theHand);
    			}
    		else
    			return isHighCard(theHand);
    		
		default:
			throw new IllegalArgumentException("Invalid array size in Score.getScore");
    	}
    	
    	return aHand.score;
    }
        
/*-------------------- getTie ----------------------------------------------------
 * Detects whether a game has two hands with the same winning score. Doesn't
 * care if there is a tie for a lower place.
 * 
 * @param   allHands    array of all Hand objects
 * @param   winner      ID# of hand with high score
 * 
 * return               true if tied, false otherwise
 *
 --------------------------------------------------------------------------------*/
    static boolean getTie(Hand[] allHands, int winner){
        //boolean tie = false;
        long target = allHands[winner].score;
        for(int handex = 1; handex <= Deck.NUM_HANDS; handex++){
            if((handex != winner) && (allHands[handex].score == target)){
                Poker.isTie = true;
                return true;
            }
        }
        return false;
    }

/*----------------- getWinner ----------------------------------------------------
 * compares the scores of several hands to determine a winner
 * 
 * @param   allHands    array of all Hand objects
 * @param   winner      ID# of hand with high score
 * 
 * return               true if tied, false otherwise
 *
 -------------------------------------------------------------------------------*/
    static int getWinner(Hand[] aHands) {
    	int winner = 0;
    	long highScore = 0;
    	for (int i = 0; i < Deck.CPH; i++) {
    		if(aHands[i].score > highScore) {
    			winner = i;
    			highScore = aHands[i].score;
    		}
    	}
    	return winner;
    }
    
    // -- "IS" FUNCTIONS - RETURN SCORE FOR A GIVEN HAND
    
/*----------------- isFlush ----------------------------------------------------
 * function isFlush(Hand)  
 *
 * Purpose: Calculates the score of a hand that is a flush. 
 *
 * @param - Hand in sorted order 
 *
 * @return - long - hand score shifted left by 26 bits.
 *
------------------------------------------------------------------------------*/
    static long isFlush(Hand sHand){
	
    	return (isHighCard(sHand) << 26);
    }
    
    
/*----------------- isFourKind ------------------------------------------------
 * function isFourKind(Hand)  
 *
 * Purpose: Calculates the score of a four-of-a-kind hand. Follows scoring
 * 	pattern of lower-scoring hands. High card value is shifted left by 38 bits,
 * 	and the low card value is added
 *
 * @param - Hand - sorted - a sorted Hand
 *
 * @return - long - score + scores of all lower-ranked combinations
 *
-----------------------------------------------------------------------------*/
 /**
 *
 */
    static long isFourKind(Hand sorted){
    	final int HIGHVAL = 4;	// multiplier because this is four of a kind
    	final int MULTIPLIER = 38;
		long score = 0;
		score = sorted.hand[Poker.ZERO].rank * HIGHVAL;
		score = score << MULTIPLIER;
		score += sorted.hand[Deck.CPH - 1].rank;
		return score;
    }
    
/*----------------- isFullHouse ----------------------------------------------
 * function isFullHouse(Hand)  
 *
 * Purpose: Calculates score of a hand containing a full house. High card is 
 * shifted left by 32 bits, low card is shifted left by 26
 *
 * @param - Hand - sorted - a sorted hand
 *
 * @return - long score - 
 *
-----------------------------------------------------------------------------*/
 static long isFullHouse(Hand sorted){
    	final int HIGHVAL = 3;
    	final int LOWVAL = 2;
    	final int HIGHMULTIPLIER = 32;
    	final int LOWMULTIPLIER = 26;
    	return ((sorted.hand[Poker.ZERO].rank * HIGHVAL) << HIGHMULTIPLIER) + 
    			((sorted.hand[HIGHVAL].rank * LOWVAL) << LOWMULTIPLIER);
    }
    
/*----------------- isHighCard ----------------------------------------------
 * function isHighCard(Hand)  
 *
 * Purpose:  
 *	Scoring: Returns the combined score of five discrete cards in a poker hand.
 *
 * @param - Hand - the Hand to be tested 
 *
 * @return - long score - value of all cards in hand
 *
-----------------------------------------------------------------------------*/
    static long isHighCard(Hand sorted){
	long score = 0;
	for(int i = Poker.ZERO; i < Deck.CPH; i++) {
		score += sorted.hand[i].rank;
	}
	return score; 
    }
    
/*----------------- isPair ----------------------------------------------
 * function isPair(Hand)  
 *
 * Purpose: Calculates the score of a hand identified to contain one pair.
 * 	Score of high card is shifted left six bits and added to score of low cards.
 *
 * @param - Hand - the Hand to be tested 
 *
 * @return - long - score of pair shifted left six bits, plus other cards
 *
-----------------------------------------------------------------------------*/
 static long isPair(Hand sHand){
	 final int HIGHVAL = 2;
	 final int MULTIPLIER = 6;
	 long score = (sHand.hand[Poker.ZERO].rank * HIGHVAL) << 6;
	 for(int i = HIGHVAL; i < Deck.CPH; i++) {
		 score += sHand.hand[i].rank;
	 }
	 return score; 
 }
    
/*----------------- isStraight -------------------------------------------------
 * function isStraight(Hand sorted)  
 *
 * Purpose: Calculates the score of a hand that contains a straight. Sum
 * 	is shifted left by 22 bits
 *
 * @return - long - score of cards shifted left 26 bits
 *
-----------------------------------------------------------------------------*/
    static long isStraight(Hand sHand){
    	final int MULTIPLIER = 26;
    	return isHighCard(sHand) << MULTIPLIER; 
    }
    
/*----------------- isStraightFlush ------------------------------------------
 * function isStraightFlush(Hand sHand)  
 *
 * Purpose: Calculates the score of a straight flush. Score of cards is shifted
 * 	left by 44 bits
 *	  
 * @param - Hand - sHand - sorted Hand
 * 
 * @return - long - the score of the cards shifted left by 44 bits
 *
-----------------------------------------------------------------------------*/
    static long isStraightFlush(Hand sHand){
		final int MULTIPLIER = 44;
		return isHighCard(sHand) << MULTIPLIER; 
    }
    
/*------------------ isThreeKind -----------------------------------------------
 * Calculates the score of a three-of-a-kind hand. Value of high card is shifted
 * 	left by 16 bits and added to the low cards
 * 
 * @return - long - score 
*
----------------------------------------------------------------------------*/
    static long isThreeKind(Hand sorted){
    	final int HIGHVAL = 3;
    	final int MULTIPLIER = 16;
    	long score = (sorted.hand[Poker.ZERO].rank * HIGHVAL) << 16;
    	for(int i = HIGHVAL; i < Deck.CPH; i++) {
    		score += sorted.hand[i].rank;
    	}
    	return score; 
    }
    
/*------------------ isTwoPair -------------------------------------------------
 * Calculates the score of a hand with two pairs. Value of high cards is shifted
 * 	left 11 bits. Value of the low cards is shifted left 6 bits. These are added 
 * 	to the value of the low card
 * 
 * @param Hand - sorted - a sorted Hand
*
*  @return - long - score, as described above
-------------------------------------------------------------------------- */
    static long isTwoPair(Hand sorted){
    	final int HIGHVAL = 2;
    	final int HIGHMULTIPLIER = 11;
    	final int LOWMULTIPLIER = 6;
    	long score = (sorted.hand[Poker.ZERO].rank* HIGHVAL) << HIGHMULTIPLIER;
    	score += (sorted.hand[HIGHVAL].rank * HIGHVAL) << LOWMULTIPLIER;
    	score += sorted.hand[Deck.CPH - 1].rank;
    	return score; 
    }

}
