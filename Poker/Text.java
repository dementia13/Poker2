package Poker;

/* -- 	This class contains functions used in text-only versions of the program 
 * 		They are kept here for future use --*/

public class Text {
	
	Text(){};
    
    
/*----------------- textValidate -------------------------------------------------
 * function textValidate() 
 *
 * Purpose: Verifies that user input is within proper boundaries:
 * 	- the number of players must be between 1-7. Values outside this range 
 * 	  display an error.
 *
 * @param - receives no parameters: validates the constant NUM_HANDS
 *
 * @return - boolean, true if NUM_HANDS is within the proper range
 *
-----------------------------------------------------------------------------*/    
    /**
     *
     */
    boolean textValidate(){
	if ((Deck.NUM_HANDS < Poker.MIN_PLYRS) || (Deck.NUM_HANDS > Poker.MAX_PLYRS)){
            System.out.print("Improper input. Enter a number of players ");
            System.out.print("between " + Poker.MIN_PLYRS);
            System.out.println(" and " + Poker.MAX_PLYRS + ".");
            return false;
	}
        else
            return true;
    }

}
