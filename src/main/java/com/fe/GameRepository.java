/**
 * 
 */
package com.fe;

/**
 * @author Bhupendra
 *
 */
public interface GameRepository {

	
	public void save(Game game);

	public Game getGameById(int id);
	
	
}


