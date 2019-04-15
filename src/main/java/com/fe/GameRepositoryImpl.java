package com.fe;

import java.util.Map;

import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class GameRepositoryImpl implements GameRepository {
	
    private final Map<Integer, Game> games; 
	
	public GameRepositoryImpl() {
	    games = new HashMap<Integer, Game>();
	}
    
    public void save(Game game) {
    	games.put(game.getId(),game);
    }
    
    public Game getGameById (int id) {
    	Game game = games.get(id);
    	if (game == null)
    		throw new GameNotFoundException(id); 
        return game;

    }

}
