package creatures;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import creatureAIs.PlayerAI;
import world.World;

@RunWith(MockitoJUnitRunner.class)
public class CreatureFactoryTest {
	
	@Mock World world;
	
	CreatureFactory factory;

	@Before
	public void initialize(){
		factory = new CreatureFactory(world);
	}
	
	@Test
	public void testNewPlayerCreatesNewPlayer() {
		assertThat(factory.newPlayer(), instanceOf(Creature.class));
	}
	
	@Test
	public void testNewPlayerCreatesNewPlayerWithAnAI(){
		Creature player = factory.newPlayer();
		assertThat(player.ai(), instanceOf(PlayerAI.class));
	}
	
	@Test
	public void testNewPlayerCallsAddAtEmptyLocationFromWorld(){
		Creature player = factory.newPlayer();
		verify(world, times(1)).addAtEmptyLocation(player);
	}

}
