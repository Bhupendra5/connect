/**
 * 
 */
package com.fe;
import java.util.HashMap;
/**
 * @author Bhupendra
 *
 */
public class GameService {

    private HashMap<Integer, Game> games = new HashMap<Integer, Game>();

    public void save(int id, Game game) {
    	games.put(id, game);
    }
    
    public Game getGameById (int id) {
        return games.get(id);

    }
}
