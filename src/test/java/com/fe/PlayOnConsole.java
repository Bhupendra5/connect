package com.fe;

import java.util.Scanner;
public class PlayOnConsole {

    public static void main(String args[]) {
    	
    	Scanner scan = new Scanner(System.in);
    	System.out.print("What color you want your discs to be (inter a RGB number 1 to " + 0xFFFFFF + ":");
    	int playerColor = scan.nextInt();
    	scan.nextLine();
    	System.out.println();
    	System.out.print("Do you want to go first? (Y/N):");
    	String turnIn = scan.nextLine();
    	boolean playersTurn = (turnIn.equalsIgnoreCase("y")) ? true : false ;
    	
    	Game game= new Game(1,playerColor,playersTurn);
    	int haveWinner = 0;
    	boolean quit = false;
    	
    	do {
    		System.out.println(game);
    		int nextMove = -1;
    		String ret= "";
    		while (!ret.equalsIgnoreCase("ok")) {
    			System.out.println("Which column you want to play (enter 0 to quit?");
    			nextMove = scan.nextInt(); 
        		ret = game.play(nextMove,playerColor);
        		if( !ret.equalsIgnoreCase("ok"))
        			System.out.print(ret);
    				
    		}
    		if (nextMove != 0) {
            	haveWinner = game.doWeHaveWinner();
            	//System.out.println(haveWinner);
            	if (haveWinner == 0)  
            		game.computerPlayes();
            	haveWinner = game.doWeHaveWinner();
    		} else {
    			quit = true;
    		}
    	}
    	while (!((haveWinner !=0) || game.getGameTied() || quit));
    	scan.close();
		System.out.println(game);
    	if (!(game.getGameTied() || quit)) {  // have a Winner
	    	if (haveWinner == playerColor )
	    		System.out.println("You are the winner!");
	    	else 
	    		System.out.println("Computer won!");
    	}
	        	    	
    }
}
