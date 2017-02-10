package creatures;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import asciiPanel.AsciiPanel;
import creatureAIs.CreatureAI;
import tiles.FloorTile;
import world.World;

@RunWith(MockitoJUnitRunner.class)
public class CreatureTest {
	
	CreatureAI creatureAI;
	CreatureAI creatureAISpy;
	@Mock World world;
	
	Creature creature;
	FloorTile floor;
	
	@Before
	public void initialize(){
		creature = new Creature(world, '@', AsciiPanel.green);
		creatureAI = new CreatureAI(creature);
		creatureAISpy = Mockito.spy(creatureAI);
		floor = new FloorTile();
		when(world.getTile(6, 6)).thenReturn(floor);
		creature.setCreatureAI(creatureAISpy);
		creature.setX(5);
		creature.setY(5);
	}
	
	@Test
	public void testGetAI(){
		assertEquals(creatureAISpy, creature.ai());
	}

}
