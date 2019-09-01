package Poker;

import java.util.Random;

/**                     Deck
 * Standard deck is an array of 52 Card objects. It is instantiated as an
 * array of Cards in sequential order. It is shuffled (randomized) before
 * hands are dealt to individual players.
 * 
 * @see createDeck
 * @see shuffleDeck
 *
 */
public class Deck{
	
	static final int DECK_SIZE = 53;	/* number of cards in deck */
	static final int CPH = 5;              /* cards per hand fixed at 5 */
	static int NUM_HANDS = 5;
	
    Card[] deck = new Card[DECK_SIZE];
    
    /**                     createDeck
     * Generates a deck of cards. Each pass through the loop (52 iterations)
     * produces a new Card ID# and calls functions to assign rank & suit.
     * 
     * @param   null
     * @return  A new, standard deck of cards
     *
     */
    static Deck createDeck(){
        Deck newDeck = new Deck();
        for (int iD = Card.CARD_INIT; iD < DECK_SIZE; iD++){
	            Card newCard = new Card();
			newCard.iDNum = iD;
			newCard.suit = Card.getSuit(iD);
			newCard.rank = Card.getRank(iD);
			newCard.suitName = Card.getSuitName(newCard.suit);
			newCard.rankName = Card.getRankName(newCard.rank);
	                newDeck.deck[iD] = newCard; 
		} 
		return newDeck;
    }
    
    /**                     shuffleDeck
     * Randomizes a deck of cards according to the Fisher-Yates algorithm. See
     * https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle#The_modern_algorithm
     * for more details. Starting with card ID# 52, it swaps places with a 
     * random card from the deck, and repeats while counting downward.
     * 
     * @param origDeck  Standard (unshuffled) deck
     * @return          Shuffled deck
     *
     */
    static Deck shuffleDeck(Deck origDeck){
        Deck sDeck = origDeck;
        Random randm = new Random(System.nanoTime());
        int randInt = 0;
	Card temp = new Card();
	for (int sIndex = DECK_SIZE; sIndex > Card.CARD_INIT; ){
		--sIndex;
		randInt = randm.nextInt(sIndex);
		if(randInt == Poker.ZERO){
			randInt = Card.CARD_INIT;    // there is no "zero" card, set
		}                               // it to 1
		temp = sDeck.deck[randInt];	
		sDeck.deck[randInt] = sDeck.deck[sIndex];
		sDeck.deck[sIndex]  = temp;
	}
	return sDeck;
    }
}
