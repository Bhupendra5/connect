package com.fe;

import static org.junit.Assert.*;

import org.junit.Test;

public class GameTest {

	@Test
	public void testIsPlayersTurn() {
		Game game= new Game(1, 255,false); // input is game id , disc color and does player wants to play first
		assertTrue(game.isPlayersTurn());
	}

	@Test
	public void testSpoilPlayersChance() {
		Game game= new Game(1, 255,true); // input is game id , disc color and does player wants to play first
		//play 3 user step in 3 row, computer should block it
		game.play(4,255);
		game.play(3,255);
		game.play(2,255);
		int c = game.spoilPlayersChance();
		assertTrue(c == 0 || c == 4);  // internal representation is 0 starting so 4th col will be represented as 3
	}
	@Test
	public void testSpoilPlayersChance2() {
		Game game= new Game(1, 255,true);
		game.play(1,255);
		game.play(1,255);
		int c = game.spoilPlayersChance();
		assertTrue(c == 0);  // Should return 0 as column one user has 3 discs
	}
	@Test 
	public void testStrategicPlay(){
		Game game= new Game(1, 255,true); // input is game id , disc color and does player wants to play first
		game.play(4,-1);  // Computer col 4, row 6
		game.play(4,-1);  // Computer col 4, row 5
		game.play(3,-1);  // Computer col 3, row 6
		game.play(4,255); // Player   col 4, row 4
		int c = game.strategicPlay(); //it should select col = 3 or 5
		assertTrue(c == 1);  // internal representation is 0 starting so 4th col will be represented as 3		
	}
	
	@Test
	public void testGameTied() {
		Game game= new Game(1, 255,true); // input is game id , disc color and does player wants to play first
		for (int r=1;r<=6;r++) {
			int col=1;
			while (col <= 7) {
				game.play(col,(int)Math.abs(200 * Math.random())+ 1 ); //selecting different colors so there will be no winner
				col++;
			}
		}
		//System.out.println(game);
		assertTrue(game.getGameTied());
	}

	@Test
	public void testDoWeHaveWinner() {
		Game game= new Game(1, 255,true); // input is game id , disc color and does player wants to play first
		//Play 4 disc in 4 different column and check if we won
		game.play(1,255);
		game.play(2,255);
		game.play(3,255);
		game.play(4,255);
		assertTrue(game.getWinner()!=0);
		
		//Play 4 disc diagonally LeftBotton to RightTop
	    game= new Game(2, 255,true); // input is game id , disc color and does player wants to play first
		game.play(1,255);//player col 1
		game.play(2,-1); //computer col 2
		game.play(2,255);//player   col2
		game.play(3,-1); //computer col 3
		game.play(3,-1); //computer col 3
		game.play(3,255);//player   col 3
		game.play(4,-1); //computer col 4
		game.play(4,-1); //computer col 4
		game.play(4,-1); //computer col 4
		game.play(4,255);//player   col 3
		
		assertTrue(game.getWinner()!=0); //player should have won

		//Play 4 disc diagonally LeftTop to RightBotton
	    game= new Game(2, 255,true); // input is game id , disc color and does player wants to play first
		game.play(4,255);//player   col 4
		game.play(3,-1); //computer col 3
		game.play(3,255);//player   col 3
		game.play(2,-1); //computer col 2
		game.play(2,-1); //computer col 2
		game.play(2,255);//player   col 2
		game.play(1,-1); //computer col 1
		game.play(1,-1); //computer col 1
		game.play(1,-1); //computer col 1
		game.play(1,255);//player   col 1
		
	
		assertTrue(game.getWinner()!=0); //player should have won
		
	}

}
