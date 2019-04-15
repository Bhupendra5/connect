package com.fe;

/**
 * This is main class which maintains state of the game and provides methods for computer play also
 * @author Bhupendra Kumar
 */
public class Game {

	private int id;
    private static int maxRows =6;
    private static int maxCols =7;
	private int [] [] gameState = new int[maxRows][maxCols];
	// Winner will be populated with color which has 4 continuous disc
	private int winner = 0;
    private static int computerColor =-1;
    private int playerColor;
    private boolean playersTurn;
    private boolean gameTied;
    
	/**
	 * Constructor for the game provide an ID, players chosen color and if he wants to go next
	 * @param id  is required
	 * @param playerColor
	 * @param playersTurn
	 */
	public Game(int id,int playerColor,boolean playersTurn) {
		  this.id = id;
	      this.playerColor= playerColor;
	      this.playersTurn= playersTurn;
	      this.gameTied = false;
	      if (!playersTurn) // if player has not taken first go, let computer play a move
	    	  computerPlayes();
	}

    /** 
     * This method checks if someone has won
     * The method take auxiliary time of Big(n*n)
     * Checks for 4 continuous colors on each row, column then diagonal (see the diagram below)
     * @return returns winning players color or -1 
     * 
     * look diagonally leftTop to RightBottom
	 *	diag 0	 1	2  3
	 *    0	| \ | @| #| x|  |  |  |
     *	 -1	| - |\ | @| #| x|  |  |
	 *	 -2	| x |- |\ | @| #| x|  |
	 *		|   | x| -|\ | @| #| x|
	 *		|   |  |x | -|\ | @| #|
	 *		|   |  |  | x| -|\ | @|
	 *
     *	/* look diagonally leftBottom to RightTop
	 *		|   |  |  | x| @| *| #|
	 *	 	|   |  | x| @| *| #| @|
	 *		|   | x| @| *| #| @| x|
	 *	3	| x | @| *| #| @| x|  |
	 *	4	| @ | *| #| @| x|  |  |
	 *	5	| * | #| @| x|  |  |  |
	 *	diag  5   6	 7	8
	 *    
	 **/
 	
    public int doWeHaveWinner() {
  
    	// look for row winner
    	boolean allSlotsFilled = true;
    	for (int r=maxRows - 1;r >=0 ;r--) { // for each row check from bottom
    		int contCount = 0;
    		int prevCol=0;  // initially all slots are 0
        	for (int c=0;c < maxCols;c++) {
        		if (gameState[r][c]==0)  // keep flag if all slots filled
        			allSlotsFilled= false;
        		if (gameState[r][c]== prevCol && prevCol !=0 ){
        			contCount++;
        			if (contCount >= 4 ) {
        				return prevCol;
        			}
        		}else {
        			prevCol = gameState[r][c];
        			contCount = 1;
        		}
        	}
    	}
    	// look for column winner
    	for (int c = 0;c < maxCols;c++) { // for each col
    		int contCount = 0;
    		int prevRow=0;
        	for (int r=maxRows-1;r >=0 ;r--) {
        		if (gameState[r][c]== prevRow && prevRow != 0){
        			contCount++;
        			if (contCount >= 4 ) {
        				return prevRow;
        			}
        		}else {
        			prevRow = gameState[r][c];
        			contCount = 1;
        		}
        	}
    	}
    	/* look diagonally leftTop to RightBottom
	   	 *	diag 0	 1	2  3
	   	 *    0	| \ | @| #| x|  |  |  |
	     *	 -1	| - |\ | @| #| x|  |  |
	   	 *	 -2	| x |- |\ | @| #| x|  |
	   	 *		|   | x| -|\ | @| #| x|
	   	 *		|   |  |x | -|\ | @| #|
	   	 *		|   |  |  | x| -|\ | @|
	   	 */
    	int r,c=0;
    	for (int diag=3;diag >= -2; diag--) { // do diagonal 4 thru -2, numeric name for loop calc
    		if (diag < 0) {
    			c = 0;
    			r = Math.abs(diag) ; // when diag is negative take positive
    		} else {
    			c = diag;
    			r = 0 ;
    		}
       		int contCount = 0;			    	
    		int prevVal=0;//other than 1,2
    		while (r < maxRows && c < maxCols) {
        		if (gameState[r][c]== prevVal && prevVal !=0 ){
        			contCount++;
        			if (contCount >= 4 ) {
        				return prevVal;
        			}
        		}else {
        			prevVal = gameState[r][c];
        			contCount = 1;
        		}
        		r++;
        		c++;
        	}
    	}
    	
    	/* look diagonally leftBottom to RightTop
			|   |  |  | x| @| *| #|
		 	|   |  | x| @| *| #| @|
			|   | x| @| *| #| @| x|
		3	| x | @| *| #| @| x|  |
		4	| @ | *| #| @| x|  |  |
		5	| * | #| @| x|  |  |  |
		diag  5   6	 7	8
	    
		*/
    	r=0;c=0;
    	for (int diag=3;diag <= 8; diag++) { // do diagonal 3,8
    		if (diag >= maxRows) {
    			r = maxRows-1;
    			c = diag-maxRows;
    		} else {
    			r = diag;
    			c = 0;
    		}
       		int contCount = 0;			    	
    		int prevVal=0;
    		while (r >=0 && c < maxCols) {
        		if (gameState[r][c]== prevVal && prevVal !=0 ){
        			contCount++;
        			if (contCount >= 4 ) {
        				return prevVal;
        			}
        		}else {
        			prevVal = gameState[r][c];
        			contCount = 1;
        		}
        		r--;
        		c++;
        	}
    	}
    	if (allSlotsFilled)
    		this.setGameTied(true);
    	return 0; //no winner yet
    }

    /*
     * Plays a specific column for the player (not computer);
     * This method makes no guarantees as to how <em>well</em> the song is played.
     * @param songTitle must have content, and must have trimmed length greater than 2.
     *
     * @param c the column player (not computer) wants to play
     * @return
     */
    public String play(int c) {
    							
    	 String ret = play(c,playerColor);
     	 setPlayersTurn(false);
		 winner = doWeHaveWinner();
		 return ret;
    }
    /** 
     * Computer plays; is baby AI which uses strategy of spoiling the players game first then 
     * plays open positions
     * @return returns OK or Invalid if no slot available
     */
    public String computerPlayes() {
    	
    	int bestColToPlay = -1;  
        
       	bestColToPlay = spoilPlayersChance();
    	if (bestColToPlay == -1)
    		bestColToPlay = strategicPlay();

    	setPlayersTurn(true);
    	if (bestColToPlay != -1) {
    		 bestColToPlay++;
			 play(bestColToPlay,computerColor); //visually columns are 1 to 7 but stored 0 to 6
			 setWinnersColor(doWeHaveWinner());
			 return "OK";
    	} else {
    		// Column 4 to 1
    		for (int c = 3; c >= 0; c--) {
    			for (int r = maxRows-1; r >=0 ; r--) {
	    			if (gameState [r][c] == 0) {
	    				c++;
	    				String ret= play(c ,computerColor); //visually columns are 1 to 7 but stored 0 to 6
	    				r = -1;
	    				c = -1; // make it exit
	    				return ret;
	    			}
    			}
    		}
    		// col 5 to 7
    		for (int c = 4; c < maxCols ; c++) {
    			for (int r = maxRows-1; r >=0 ; r--) {
	    			if (gameState [r][c] == 0) {
	    				c++;
	    				String ret= play(c ,computerColor); //visually columns are 1 to 7 but stored 0 to 6
	    				r = -1;
	    				c = -1; // make it exit
	    				return ret;
	    			}
    			}
    		}
    	}
    	return "Invalid play";
    }
    	   
    /** 
	 * This method finds best open spot for computer to play
	 * @return
	 */
	public int strategicPlay() {
    	// look each column
    	int i= 0;
		int contCount = 0;
		int prevCol=0;  // initially all slots are 0
		int maxContCount = 1; // Look for at least 1 computer slot
		int spotOpen = -1;
		
    	for (int c=0;c < maxCols;c++) {
        	int r = maxRows-1 ; //check last row
        	// if it is not computer disc and same as prev
    		if (gameState[r][c]== prevCol && prevCol !=0 && prevCol == -1 ){
    			contCount++;
    			if (contCount > maxContCount ) {
    				spotOpen = spotAvailOnRow(r,i,c);
    				if (spotOpen != -1)
    					maxContCount= contCount;
    			}
    		}else {
    			prevCol = gameState[r][c];
    			contCount = 1;
    			i = c; // reset beginning pointer
    		}
    	}
    	// check column chances
		prevCol=0;  // initially all slots are 0
    	for (int c=0;c < maxCols;c++) {
        	for (int r=maxRows-1;r >= 0; r--) {
	    		if (gameState[r][c]== prevCol && prevCol !=0 && prevCol == -1){
	    			contCount++;
	    			if (contCount > maxContCount ) {
	    				spotOpen = spotAvailInCol(c);
	    				if (spotOpen != -1)
	    					maxContCount= contCount;
	    			}
	    		}else {
	    			prevCol = gameState[r][c];
	    			contCount = 1;
	    		}
        	}
    	}
    	return spotOpen;
	
	}

	/** 
     *  This method finds out if player is about to win, if so any way to block it 
     * @return is column computer can play next
     */
    public int spoilPlayersChance() {
    	  
    	// look each column
    	int i= 0;
		int contCount = 0;
		int prevCol=0;  // initially all slots are 0
		int maxContCount = 1; // Do not bother blocking if only 2 continuous slot filled
		int spotOpen = -1;
		
    	for (int c=0;c < maxCols;c++) {
        	int r = maxRows-1 ; //check last row
    		if (gameState[r][c]== prevCol && prevCol !=0 ){
    			contCount++;
    			if (contCount > maxContCount ) {
    				spotOpen = spotAvailOnRow(r,i,c);
    				if (spotOpen != -1)
    					maxContCount= contCount;
    			}
    		}else {
    			prevCol = gameState[r][c];
    			contCount = 1;
    			i = c; // reset beginning pointer
    		}
    	}
    	// check column chances
		prevCol=0;  // initially all slots are 0
    	for (int c=0;c < maxCols;c++) {
        	for (int r=maxRows-1;r >= 0; r--) {
	    		if (gameState[r][c]== prevCol && prevCol !=0 ){
	    			contCount++;
	    			if (contCount > maxContCount ) {
	    				spotOpen = spotAvailInCol(c);
	    				if (spotOpen != -1)
	    					maxContCount= contCount;
	    			}
	    		}else {
	    			prevCol = gameState[r][c];
	    			contCount = 1;
	    		}
        	}
    	}
    	return spotOpen;
    }
    	
    /** 
     * This method finds out if there is spot around players plays on same row to block him/her
     * @param r
     * @param bc
     * @param ec
     * @return
     */
    private int spotAvailOnRow(int r, int bc,int ec) {
    	bc--;
    	ec++;
    	if (bc > 0 && gameState[r][bc] == 0 )
    		return bc;
    	else
    		if (ec < maxRows && gameState[r][ec] == 0 )
        		return ec;
    	
    	return -1;
    }
    		
    /** 
     * This method checks for computer AI if any slot is available in given column
     * @param c
     * @return int value of -1 if nothing available else c sent
     */
    private int spotAvailInCol(int c) {

    	for (int r=0;r < maxRows;r++) {
	    	if (gameState[r][c] == 0 )
	    		return c;
    	}
    	return -1;
    }

    
    /** 
     * Play a specific column for given player identified by color, computer is given -1 as color
     * this in turn also sets winner's color and game tied if no slots available after the play 
     * @param c for column desired to be played
     * @param colorPlayed
     * @return
     */
    public String play(int c, int colorPlayed) {
    	
    	if (c < 1 || c > 7 )
    		return "Column is not valid, please select colums 1 through 7 where a spot is open";
    	else if (winner != 0)
    			return (winner == -1)? "Game is already won by computer ":
    					"Game is already won by the player";
	    	else if (getGameTied())
				return "Game is finished in tie";
    	
    	c--; //visually columns are 1 to 7 but stored 0 to 6
    	for (int r=maxRows-1;r >= 0; r--) {
			if (gameState [r][c]  == 0 ) { 
				 gameState [r][c]  = colorPlayed ;
				 if (doWeHaveWinner() == colorPlayed) {
					 setWinnersColor(colorPlayed);
				 }
				 return "OK";
			}
    	}
		return "Column is not available to play";
    }
    /** 
     * This method checks if all slots are filled and no winner
     * @return 
     */
    public boolean checkIfGameTied() {
    	// if winner is already set it is not tie situation so return false
    	if (winner != 0)
    		return false;
    	// for each cell make sure if any column is empty game can still be played
    	// for performance reason the check is done from top row down as discs fall in bottom row
    	for (int r= maxRows -1 ; r >= 0; r--) {
        	for (int c=0;c < maxCols;c++) {
        		if (gameState[r][c] == 0) {
        				return false;
        		}
        	}
    	}
    	setGameTied(true);
    	return true;
    }

    /* This customized toString prints the game board for terminal
     */
    public String toString() {
    	StringBuilder sb= new StringBuilder();
    	for (int r=0;r < maxRows;r++) {
    		sb.append( "| ");
        	for (int c=0;c < maxCols;c++) {
        		String p;
        		if (gameState[r][c] == -1) 
    				p = "C";
    		else if (gameState[r][c] == playerColor)
    				p = "P";
    			else 
    				 if (gameState[r][c] == 0)
    	    				p = " ";
    	    			else 
    	    				p = ""+ gameState[r][c];
    	        		
        	sb.append(p + " |");
        	}
        	sb.append("\n");
    	}
    	if (winner !=0)
    		sb.append("\nWinner is:" + winner);
    	if (gameTied)
    		sb.append("\nGame is tied" );
        	
    	return sb.toString();
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public int getWinner() {
		return winner;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}

	public int getPlayerColor() {
		return playerColor;
	}

	public void setPlayerColor(int playerColor) {
		this.playerColor = playerColor;
	}

	public boolean isPlayersTurn() {
		return playersTurn;
	}

	public void setPlayersTurn(boolean playersTurn) {
		this.playersTurn = playersTurn;
	}

	public void setGameTied(boolean gameTied) {
		this.gameTied = gameTied;
	}

	public boolean getGameTied() {
		return gameTied;
	}

	public int [] [] getGameState() {
		return gameState;
	}

	public void setGameState(int [] [] gameState) {
		this.gameState = gameState;
	}

	public int getWinnersColor() {
		return winner;
	}

	public void setWinnersColor(int winner) {
		this.winner = winner;
	}

	public static int getComputerColor() {
		return computerColor;
	}

	public static void setComputerColor(int computerColor) {
		Game.computerColor = computerColor;
	}
}
