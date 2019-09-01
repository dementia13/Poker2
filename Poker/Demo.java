/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Poker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Scanner;
import Poker.Card;
import Poker.Hand;

/**
 *
 * @author Sean Quinn
 */
public class Demo {
    
    static String filePath = "src/poker/resources/";
        
    
    Demo(){
        
    }
    
    static Hand[] getDemo(String demoFile, Hand[] allHands){
        filePath += demoFile;
        File dmFile = new File(filePath);
        int newHandID = 0;
        try{
            Scanner scanner = new Scanner(dmFile);
            for(int handCount = 1; scanner.hasNext(); handCount++){
                Hand newHand = new Hand();          
                /*
                    System.out.println(handCount);
                    if (handCount > 1){
                    System.out.println(allHands[(handCount - 1)].toString());
                    }
                */                
                newHand.handID = scanner.nextInt();
                for(int cardCount = 1; cardCount <= Deck.CPH; cardCount++){
                    if(scanner.hasNextInt()){
                        Card newCard = new Card();
                        newCard = Card.assignCard(scanner.nextInt());
                        newHand.hand[cardCount] = newCard;
                    }
                }
                newHand = Hand.assignHand(newHand.handID, newHand.hand);
            //System.out.println(newHand.toString());
                //newHand = Score.sortHand(newHand);
            //System.out.println(newHand.toString());
                //newHand.score = Score.getScore(newHand);
            //System.out.println(newHand.toString());
               // newHand.type = Hand.getType();
                allHands[handCount] = newHand;
            //System.out.println(newHand.toString());
            }
            scanner.close();
        }catch(FileNotFoundException fnfe ){
            System.err.println("File not found: " + fnfe.getMessage());
        }
        return allHands;
    }
    
}