package Poker;

/**                     Card
 * Defines a Card object. The card is identified by an ID, an integer 1-52. 
 * Each ID# specifies a rank (face value of card) and suit:
 * <p>
 * Suit:    ID:
 * Clubs    1-13
 * Diamonds 14-26
 * Hearts   27-39
 * Spades   40-52
 * <p>
 * Rank:    ID:
 * Ace      1
 * Two      2
 * Three    3
 * Four     4
 * Five     5
 * Six      6
 * Seven    7   
 * Eight    8
 * Nine     9
 * Ten      10
 * Jack     11
 * Queen    12
 * King     13
 * These are the values for the suit "clubs". Other suits are represented
 * at the next group of 13. For instance, 14 is the Ace of Diamonds.
 * <p>
 * Each Card object includes String values for the card name and the suit
 * name.
 *
 * @see Deck
 * @see assignCard
 * @see getRank
 * @see getRankName
 * @see getSuit
 * @see getSuitName
 */
public class Card implements Comparable<Card>{
	

	static final int CARD_INIT = 1;	/* initialize counting loops */
	static final int MAX_SUIT = 4;         /* suits numbered 1-4 */
	static final int MAX_RANK = 13;	/* value of highest rank (king) */
	static final int NUM_RANKS = 13;	/* number of ranks in a suit */
	static final int MAX_SUITNAME = 9;	/* longest suit name, "diamonds" + EOS */
	static final int MAX_RANKNAME = 6;	/* longest rank name + EOS */
	int iDNum = 0;
	int suit = 0;
	int rank = 0;
	String suitName = "";
	String rankName = "";
    
    Card(){}
    
    Card(int cardID){
        int idNum = cardID;
        suit = 0;
        rank = 0;
        suitName = "Undefined";
        rankName = "Undefined";
    }

    
  /**                     assignCard  
   * Returns a Card object generated from an int that represents the Card's
   * ID number parameter. Sets the values for a previously generated Card.
   *
   * @param   iD  the Card's ID field, from which rank & suit information is
   * extracted
   * @return      A fully implemented Card object
   */
  static Card assignCard(int iD){
	Card newCard = new Card();
	String suitName = "";
	String rankName = "";
	newCard.iDNum = iD;
      newCard.suit = getSuit(iD);
      newCard.rank = getRank(iD);
      newCard.suitName = getSuitName(newCard.suit);
      newCard.rankName = getRankName(newCard.rank);	
	return newCard;
  } 

  public int compareTo(Card otherCard) {
		return Integer.compare(this.rank, otherCard.rank);
	}
  
  /*----------------- getRank -------------------------------------------------
   * function getRank(int iD)
   *
   * Purpose: Returns an int used to represent the rank value of a Card.
   * 	Simply returns the modulo of the id# of the card in the deck 
   * 	divided by the number of ranks (13). The highest card then has
   * 	modulo 0, so 13 is added to maintain its position.	
   *
   * @param int(iD) - 1-52 id number of a card in a deck. That's 4 groups
   * 	of 13 ranks, so each number 1-13 represents a rank. 
   *
   * @return int- 1-10 represent the numbered ranks, 11 is "jack",
   * 	12 is "queen" and 13 is "king".
   *
  -----------------------------------------------------------------------------*/    
  /**
   *
   */
      static int getRank(int iD){
  	int rank;
  	rank = (iD % NUM_RANKS);
  	if (rank == Poker.ZERO){
  		return 13;      // value of KING
  	}
  	return rank;  
    }

/*----------------- getRankName -------------------------------------------
 * function getRankName(int rankID) 
 *
 * Purpose: Receives an int, 1-13, that represents the rank of the card.
 * 	A switch statement selects the rank name, which is returned. An
 *      IllegalArgumentException is thrown should an invalid value pass.
 *
 * @param int rankID - The integer value of the card's rank. 
 *
 * @return String rankName - The string name of the card's rank. 
-----------------------------------------------------------------------------*/    
    /**
     *
     */
    static String getRankName(int rankID){
        String rankName = "";
        switch(rankID){
        case 1:
                rankName = "Ace";
                break;
        case 2:
                rankName = "Two";
                break;
        case 3:
                rankName = "Three";
                break;
        case 4:
                rankName = "Four";
                break;
        case 5: 
                rankName = "Five"; 
                break;
        case 6:
                rankName = "Six";
                break;
        case 7:
                rankName = "Seven";
                break;
        case 8:
                rankName = "Eight";
                break;
        case 9:
                rankName = "Nine";
                break;
        case 10:
                rankName = "Ten";
                break;
        case 11:
                rankName = "Jack";
                break;
        case 12:
                rankName = "Queen";
                break;
        case 13:
                rankName = "King";
                break;
        default:
                throw new IllegalArgumentException("Invalid rank assignment.");
        }
        return rankName;
    }
    
    /*----------------- getSuit -------------------------------------------------
     * function getSuit(int iD)
     *
     * Purpose: Returns an int used to represent the suit value of a struct card.
     * 	Uses a homemade ceiling function to return a number 1-4.  
     *
     * @param int(i_d) - 1-52 id number of a card in a deck. Each set of 13
     * 	numbers represents a single suit. 
     *
     * @return int- 1 = "clubs", 2 = "diamonds", 3 = "hearts", 4 = "clovers".
     * 	No, wait, 4 = "spades" 
     *
    -----------------------------------------------------------------------------*/    
        /**
         *
         */
        static int getSuit(int iD){
    	int suit;
    	suit = iD / NUM_RANKS;
    	if ((iD % NUM_RANKS) == 0){	 
    		return suit;	 
    	}
    	else
    		return suit + 1; 
        }
        
        /*----------------- getSuitName -------------------------------------------
         * function getSuitName
         *
         * Purpose: retrieves the string value associated with the card suit 
         *
         * @param - int suit - integer value 1-4 
         *
         * @return - none. Value is returned by pointer manipulation. 
         *
        -----------------------------------------------------------------------------*/    
            /**
             *
             */
        static String getSuitName(int suit){
            String suitName = "";
            switch (suit){
            case 1: 
                    suitName = "clubs";
                    break;
            case 2:
                    suitName = "diamonds";
                    break;
            case 3:
                    suitName = "hearts";
                    break;
            case 4:
                    suitName = "spades";
                    break;
            default:
                    throw new IllegalArgumentException("Invalid suit assignment");
            }
            return suitName;
        }
        
        @Override
        public String toString(){
            String cardString = "Card " + this.iDNum + "\n";
            cardString += "\t" + this.rankName + " of " + this.suitName;
            return cardString;
        }
}
