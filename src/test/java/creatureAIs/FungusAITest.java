package creatureAIs;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import asciiPanel.AsciiPanel;
import creatures.Creature;
import creatures.CreatureFactory;
import tiles.FloorTile;
import tiles.Tile;
import world.World;

@RunWith(MockitoJUnitRunner.class)
public class FungusAITest {
	
	CreatureFactory factory;
	Creature creature;
	World world;
	Math math;
	
	@Before
	public void initialize(){
		Tile[][] tiles = {
				{new FloorTile(), new FloorTile()},
				{new FloorTile(), new FloorTile()}
		};
		world = new World(tiles);
		factory = new CreatureFactory(world);
		creature = new Creature(world, 'f', AsciiPanel.green);
		math = mock(Math.class);
	}
	
	@Test
	public void testOnUpdateLowRandomChance() {
		FungusAI aiSpy = Mockito.spy(new FungusAI(creature, factory));
		aiSpy.onUpdate();
		verify(aiSpy, times(1)).spread();
	}

}
