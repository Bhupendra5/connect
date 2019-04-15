package com.fe;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@Api(value ="Connect 4 game", tags= {"API to play connect 4 game with computer"})
 
public class GameController {

    private final AtomicInteger counter = new AtomicInteger();
    
    private GameRepositoryImpl repository = new GameRepositoryImpl();
    
    @PostMapping("/connect/start")
    @ApiOperation( "Start the connect4 game with computer.")
    
    public Game game(@RequestParam(value="playerColor", defaultValue="255") int playerColor,
    			@RequestParam(value="playersTurn", defaultValue="0") boolean playersTurn) {
    		Integer id = counter.incrementAndGet();
    		System.out.println(id + " " + playerColor + playersTurn);
    		Game game = new Game(id, playerColor,playersTurn);
    		repository.save(game);
    		return game;
    }
    @PutMapping("/connect/nextMove/{id}")
    @ApiOperation("Play the game by providing which column you want to put the disc.")
    
    Game one(@PathVariable (value="id", required= true) int id,
    			@RequestParam(value="column", defaultValue="1") int c) {
    		try {
    			Game game = repository.getGameById(id);
    			String msg = game.play(c);
    			if (!msg.equalsIgnoreCase("ok"))
     		       throw new ResponseStatusException(
 		    	          HttpStatus.BAD_REQUEST, msg);	
    			if (game.getWinner() == 0 || !game.getGameTied() ) {
    				msg = game.computerPlayes();
        			if (!msg.equalsIgnoreCase("ok"))
         		       throw new ResponseStatusException(
     		    	          HttpStatus.BAD_REQUEST, msg);
    			}
        		return game;
    		} catch (GameNotFoundException gnf ) {
    		       throw new ResponseStatusException(
    		    	          HttpStatus.NOT_FOUND, 
    		    	          "Game not found, provide correct id or start new game", gnf);

			} 
	}

    @GetMapping("/connect/{id}")
    @ApiOperation("Get the game status")
    Game one(@PathVariable (value="id", required= true) int id) {
		try {
			return repository.getGameById(id);
		} catch (GameNotFoundException gnf ) {
		       throw new ResponseStatusException(
		    	          HttpStatus.NOT_FOUND, 
		    	          "Game not found, provide correct id or start new game", gnf);

		} 
	}
    
    
    @RequestMapping("/")
    public class RootController {
        @RequestMapping(method = RequestMethod.GET)
        public String swaggerUi() {
            return "redirect:/swagger-ui.html";
        }
    }
}
