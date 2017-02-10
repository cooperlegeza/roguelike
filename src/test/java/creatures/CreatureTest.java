package creatures;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import asciiPanel.AsciiPanel;
import creatureAIs.CreatureAI;
import tiles.FloorTile;
import tiles.Tile;
import tiles.WallTile;
import world.World;

@RunWith(MockitoJUnitRunner.class)
public class CreatureTest {
	
	CreatureAI creatureAI;
	CreatureAI creatureAISpy;
	World world;
	
	Creature creature;
	FloorTile floor;
	
	@Before
	public void initialize(){
		Tile[][] tiles = {
				{new FloorTile(), new FloorTile(), new FloorTile(), new FloorTile()},
				{new FloorTile(), new FloorTile(), new WallTile(), new WallTile()},
				{new WallTile(), new WallTile(), new FloorTile(), new FloorTile()},
				{new WallTile(), new WallTile(), new FloorTile(), new FloorTile()},
		};
		world = new World(tiles);
		creature = Mockito.spy(new Creature(world, '@', AsciiPanel.green));
		creatureAI = new CreatureAI(creature);
		creatureAISpy = Mockito.spy(creatureAI);
		floor = new FloorTile();
		creature.setCreatureAI(creatureAISpy);
		creature.setX(2);
		creature.setY(2);
	}
	
	@Test
	public void testGetAI(){
		assertEquals(creatureAISpy, creature.ai());
	}
	
	@Test
	public void testMoveBy(){
		creature.setX(1);
		creature.setY(1);
		creature.moveBy(1, 1);
		verify(creature, times(1)).checkForObstaclesAndReactAccordingly(2, 2);
	}
	
	@Test
	public void testMoveByNextToEdgeOfMapSouth(){
		creature.setX(2);
		creature.setY(3);
		creature.moveBy(0, 1);
		verify(creature, times(0)).checkForObstaclesAndReactAccordingly(2, 4);
	}
	
	@Test
	public void testMoveByNextToEdgeOfMapNorth(){
		creature.setX(2);
		creature.setY(0);
		creature.moveBy(0, -1);
		verify(creature, times(0)).checkForObstaclesAndReactAccordingly(2, -1);
	}
	
	@Test
	public void testMoveByNextToEdgeOfMapEast(){
		creature.setX(3);
		creature.setY(2);
		creature.moveBy(1, 0);
		verify(creature, times(0)).checkForObstaclesAndReactAccordingly(4, 2);
	}
	
	@Test
	public void testMoveByNextToEdgeOfMapWest(){
		creature.setX(0);
		creature.setY(2);
		creature.moveBy(-1, 0);
		verify(creature, times(0)).checkForObstaclesAndReactAccordingly(-1, 2);
	}
	
	@Test
	public void testCheckForObstaclesAndReactAccordinglyNoCreature(){
		creature.checkForObstaclesAndReactAccordingly(2, 2);
		verify(creatureAISpy, times(1)).onEnter(2, 2, world.getTile(2, 2));
	}
	
	@Test
	public void testCheckForObstaclesAndReactAccordinglyCreature(){
		Creature newCreature = new Creature(world, 'f', AsciiPanel.green);
		world.setCreatureAt(3, 2, newCreature);
		creature.setX(2);
		creature.setY(2);
		creature.moveBy(1, 0);
		verify(creature, times(1)).attack(newCreature);
	}
	
	@Test
	public void testAttack(){
		World worldSpy = Mockito.spy(world);
		Creature newCreature = new Creature(worldSpy, '@', AsciiPanel.brightBlack);
		newCreature.attack(creature);
		verify(worldSpy, times(1)).remove(creature);
	}
	
	@Test
	public void testUpdate(){
		creature.update();
		verify(creatureAISpy, times(1)).onUpdate();
	}
}
