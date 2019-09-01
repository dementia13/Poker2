package Poker;

import java.util.ArrayList;

/**                     Hand
 * A Hand is the set of Card objects dealt to each player. Each Hand is 
 * represented by an integer from 1 to the number of players (default is 5).
 * The "score" and "type" values are assigned separately.
 * 
 * @see dealHands
 * @see Score.getScore
 * @see getType
 * @see setType
 *
 */

public class Hand implements Comparable<Hand>{
    int handID = 0;
    long score = 0;
    String type = "";			// type of hand, e.g. "full house", "two pairs", etc.
    int typeNum = 0;			// ID number for the type of hand, in order of value	
    Card[] hand = new Card[Deck.CPH + Card.CARD_INIT];
    
    Hand(){}
    
    Hand(int handID, long score, Card[] hand){
        type = "";
        typeNum = Poker.ZERO;
    }
    

    
    /**                     assignHand
     *  Assigns a new Hand given an ID number and an array of Card objects
     * 
     * @param   iD      ID number of the Hand
     * @param   theHand an array of Card objects 
     * @return          a fully implemented Hand object
     */
    static Hand assignHand(int iD, Card[] theHand){
        Hand newHand = new Hand();
        newHand.handID = iD;
        newHand.hand = theHand;
        newHand.score = 0;
        newHand.type = "unassigned";
        return newHand;
    }
    
/**                     compareTo
 * Distributes cards from an already-generated deck to the individual players.
 * Cards are not dealt in sequence but are instead dealt in round-robin fashion
 * 
**/
    @Override
    public int compareTo(Hand otherHand) {
    	return Long.compare(this.score, otherHand.score);
    }
    
 /**                     dealHands
  * Distributes cards from an already-generated deck to the individual players.
  * Cards are not dealt in sequence but are instead dealt in round-robin fashion
  * as would be done in a physical poker game.
  * 
  * Cards are selected from the deck and sorted in order of ascending value. 
  * The resulting array is evaluated for a score value. The array of Card objects,
  * the score and the type of hand are then assembled into a Hand object. An
  * array of these Hand objects constitutes the game.
  *
  * @param   sDeck   a shuffled deck of cards
  * @param   aHands  array of all Hand objects in the game
  * @return          the array of all Hand objects
  * @see     Score.sortHand
  * @see     Score.getScore
  * @see     getType
  */
  static Hand[] dealHands(Deck sDeck, 
		Hand[] aHands){
	int handCount = Card.CARD_INIT;
	int deckCount = Card.CARD_INIT;
	ArrayList<Integer> rankCount = new ArrayList<Integer>(1);
     
     // loop that calls the dealing function
	for (int gameCount = Card.CARD_INIT; gameCount <= Deck.NUM_HANDS; gameCount++){
         int nextCard = gameCount;               // First card to be dealt 
         Hand newHand = new Hand();
         Card[] newCard = new Card[Deck.CPH + 1];
         for(int cardCount = Card.CARD_INIT; cardCount <= Deck.CPH; cardCount++){
			newCard[cardCount] = sDeck.deck[nextCard];
			nextCard += Deck.NUM_HANDS;
         }
         newHand = assignHand(gameCount, newCard);
         newHand = Sort.sortHand(newHand);
         rankCount = Sort.countHand(newHand);
         newHand = Sort.scoreSort(newHand, rankCount);
         newHand.score = Score.getScore(newHand, rankCount);
         newHand.type = newHand.getType();
         aHands[gameCount] = newHand;
         // System.out.println(newHand.toString());		# for text-only versions
         /* reset deal counters */
         deckCount += Deck.CPH;	/* Tracks how many cards
                 have been dealt from deck. It was already 
                 validated that there will not be more hands
                 dealt than cards in the deck, but this is
                 an extra safeguard. */
	}
	if(deckCount >= Deck.DECK_SIZE){
		throw new IllegalArgumentException("Programming error: "
                     + "all cards dealt.");
	}
	return aHands;
 }
  
  /**                     getScore
   * Retrieves value of the hand's score
   * 
   * @return a long value representing the Hand's score
   *
   */
  long getScore(Hand aHand) {
	  return this.score;
  }
  
 
 /**                     getType
  * Retrieves value of the variable handType, to be assigned to the "type" 
  * field of a Hand object
  * 
  * @return a String value representing the Hand ranking
  *
  */
     String getType(){
         return this.type;
     }
     
 /**                     setType
  * Assigns the "type" field of a Hand object
  * 
  * @return void
  *
  */
     void setType(String newType){
         this.type = newType;
     }
 /**
 *
 */  
     
@Override
    public String toString(){
        String handString = "";
        handString += "Hand " + this.handID + "\n";
        handString += this.score + "\n";
        for(int i = 1; i <= Deck.CPH; i++){
            handString += this.hand[i].toString() + "\n";
        }
        handString += this.type;
        return handString;    
        }

}
