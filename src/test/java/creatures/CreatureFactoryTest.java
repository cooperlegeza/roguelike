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

import creatureAIs.FungusAI;
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
		int firstLayer = 0;
		verify(world, times(1)).addAtEmptyLocation(player, firstLayer);
	}
	
	@Test
	public void testNewFungusCreatesNewFungus(){
		assertThat(factory.newFungus(), instanceOf(Creature.class));
	}
	
	@Test
	public void testNewFungusCreatesNewFungusWithAnAI(){
		Creature fungus = factory.newFungus();
		assertThat(fungus.ai(), instanceOf(FungusAI.class));
	}
	
	@Test
	public void testNewFungusCallsAddAtEmptyLocationFromWorld(){
		Creature fungus = factory.newFungus();
		verify(world, times(1)).addAtEmptyLocation(fungus);
	}
	
	@Test
	public void testNewFungusOverloadedSetsNewFungusAtSpotInWorld(){
		int expectedX = 0;
		int expectedY = 0;
		int expectedZ = 0;
		Creature fungus = factory.newFungus(expectedX, expectedY, expectedZ);
		assertEquals(expectedX, fungus.x());
		assertEquals(expectedY, fungus.y());
		assertEquals(expectedZ, fungus.z());
	}

}
