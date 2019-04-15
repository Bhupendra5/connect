package com.fe;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConnectApplicationTests {
	
	@Autowired
    private GameRepositoryImpl repository = new GameRepositoryImpl();

    @Test
	public void whenFindByName_thenReturnEmployee() {
	    // given
		Game gameLocal= new Game(1, 255,false); // input is game id , disc color and does player wants to play first
		repository.save(gameLocal);
	 
	    // when
	    Game found = repository.getGameById(1);
	 
	    // then
	    assertTrue(found.getId()== gameLocal.getId());
	}
}
