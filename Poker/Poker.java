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

import java.util.Arrays;

/**
*
* @author Sean Quinn
* sean@dementia13.net
* 
* 
|   Description: Simulates a deck of cards. Generates a 52-card deck 
|	and a shuffled version of that deck. Deals five cards to each of a
|	user-specified number of players, from 1 to 7. Prints the resulting
|	dealt hands and evaluates them, displaying the highest-ranked hand
|	as the winner. A test function tests pre-made hands to ensure that
|	all combinations are properly evaluated.
|
|	Input:  User selects a number of players, 1-7
|
|	Output: Prints the original deck, the shuffled deck, and each 
|		hand dealt. Displays the winning hand. Displays rankings of 
|		pre-set hands.
|
|	Process: A card is represented by a structure that contains an ID
|		number for each card along with integer and string values 
|		that represent the card's rank and suit. A deck is an
|		array of 52 cards that is generated by a counter loop.
|		The counter value becomes the ID of each card, and the ID
|		determines the rank value, counting upward from 1 (ace) to
|		13 (king). Each division of 13 values becomes a suit, 
|		assigned alphabetically.
* 
*               This was written in C, where the number of players was 
*               specified at the command line and all output was textual.
*               Java port takes advantage of graphical display, as well as 
*               numerous language conveniences. Code has been updated to 
*               conform to standard Java nomenclature and to take advantage of
*               object-oriented features.
|
|   *===========================================================================
*/



public class Poker {
	
	static final int ZERO = 0;	
	static final int MIN_PLYRS = 1;
	static final int MAX_PLYRS = 7;
	static final int TRUE = 0;
	static final int FALSE = 1;
	static final int HIGH_ACE = 14;

    static boolean isTie = false;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Window.main(args);

	Hand allCards = new Hand();
	Hand[] allHands = new Hand[Deck.NUM_HANDS + Card.CARD_INIT];
        
        System.out.println("Dealing hands: ");
        
        System.out.println("Sorted hands: ");
        if(!isTie){
           // System.out.println("The winning hand is number " 
            //        + getWinner(allHands));
        }
        else
            System.out.println("No winner: tie");


    }
}
