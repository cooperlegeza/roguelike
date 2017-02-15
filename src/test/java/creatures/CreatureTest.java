package creatures;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import asciiPanel.AsciiPanel;
import creatureAIs.CreatureAI;
import creatureAIs.PlayerAI;
import tiles.FloorTile;
import tiles.Tile;
import tiles.WallTile;
import weapons.Fists;
import world.Layer;
import world.World;
import world.WorldImpl;

@RunWith(MockitoJUnitRunner.class)
public class CreatureTest {
	
	CreatureAI creatureAI;
	CreatureAI creatureAISpy;
	Layer layer;
	
	Creature creature;
	FloorTile floor;
	World world;
	
	@Before
	public void initialize(){
		List<Layer> layers = new LinkedList<Layer>();
		world = new WorldImpl(layers);
		Tile[][] tiles = {
				{new FloorTile(), new FloorTile(), new FloorTile(), new FloorTile()},
				{new FloorTile(), new FloorTile(), new WallTile(), new WallTile()},
				{new WallTile(), new WallTile(), new FloorTile(), new FloorTile()},
				{new WallTile(), new WallTile(), new FloorTile(), new FloorTile()},
		};
		layer = new Layer(tiles, world);
		Layer newLayer = new Layer(tiles, world);
		layers.add(layer);
		layers.add(newLayer);
		creature = Mockito.spy(new Creature(world, '@', AsciiPanel.green, 100));
		Fists fists = Mockito.mock(Fists.class);
		creature.setBaseWeapon(fists);
		creatureAI = new CreatureAI(creature);
		creatureAISpy = Mockito.spy(creatureAI);
		floor = new FloorTile();
		creature.setCreatureAI(creatureAISpy);
		creature.setX(2);
		creature.setY(2);
		creature.setZ(0);
	}
	
	@Test
	public void testGetAI(){
		assertEquals(creatureAISpy, creature.ai());
	}
	
	@Test
	public void testMoveBy(){
		creature.setX(1);
		creature.setY(1);
		creature.moveBy(1, 1, 0);
		verify(creature, times(1)).checkForObstaclesAndReactAccordingly(2, 2, 0);
	}
	
	@Test
	public void testMoveByNextToEdgeOfMapSouth(){
		creature.setX(2);
		creature.setY(3);
		creature.moveBy(0, 1, 0);
		verify(creature, times(0)).checkForObstaclesAndReactAccordingly(2, 4, creature.z());
	}
	
	@Test
	public void testMoveByNextToEdgeOfMapNorth(){
		creature.setX(2);
		creature.setY(0);
		creature.moveBy(0, -1, 0);
		verify(creature, times(0)).checkForObstaclesAndReactAccordingly(2, -1, creature.z());
	}
	
	@Test
	public void testMoveByNextToEdgeOfMapEast(){
		creature.setX(3);
		creature.setY(2);
		creature.moveBy(1, 0, 0);
		verify(creature, times(0)).checkForObstaclesAndReactAccordingly(4, 2, creature.z());
	}
	
	@Test
	public void testMoveByNextToEdgeOfMapWest(){
		creature.setX(0);
		creature.setY(2);
		creature.moveBy(-1, 0, 0);
		verify(creature, times(0)).checkForObstaclesAndReactAccordingly(-1, 2, creature.z());
	}
	
	@Test
	public void testCheckForObstaclesAndReactAccordinglyNoCreature(){
		creature.checkForObstaclesAndReactAccordingly(2, 2, creature.z());
		verify(creatureAISpy, times(1)).onEnter(2, 2, creature.z(), layer.getTile(2, 2));
	}
	
	@Test
	public void testCheckForObstaclesAndReactAccordinglyCreature(){
		Creature newCreature = new Creature(world, 'f', AsciiPanel.green, 100);
		new PlayerAI(newCreature, new ArrayList<String>());
		world.setCreatureAt(3, 2, 0, newCreature);
		creature.setX(2);
		creature.setY(2);
		creature.moveBy(1, 0, 0);
		verify(creature, times(1)).attack(newCreature);
	}
	
	@Test
	public void testAttack(){
		World worldSpy = Mockito.spy(world);
		Creature newCreature = new Creature(worldSpy, '@', AsciiPanel.brightBlack, 100);
		Fists fists = Mockito.mock(Fists.class);
		newCreature.setBaseWeapon(fists);
		newCreature.attack(creature);
		verify(fists, times(1)).applyEffects(creature);
	}
	
	@Test
	public void testUpdate(){
		creature.update();
		verify(creatureAISpy, times(1)).onUpdate();
	}
	
	@Test
	public void testCanEnterCreatureOnTile(){
		World worldSpy = Mockito.spy(world);
		Creature newCreature = new Creature(worldSpy, '@', AsciiPanel.brightCyan, 100);
		Creature fakeCreature = new Creature(worldSpy, '@', AsciiPanel.brightRed, 100);
		when(worldSpy.getCreatureAt(2, 2, newCreature.z())).thenReturn(fakeCreature);
		boolean actual = newCreature.canEnter(2, 2);
		boolean expected = false;
		assertEquals(expected, actual);
	}
	
	@Test
	public void testCanEnterXLessThanZero(){
		boolean actual = creature.canEnter(-1, 2);
		boolean expected = false;
		assertEquals(expected, actual);
	}
	
	@Test
	public void testCanEnterYLessThanZero(){
		boolean actual = creature.canEnter(2, -1);
		boolean expected = false;
		assertEquals(expected, actual);
	}
	
	@Test
	public void testCanEnterXGreaterThanWorldWidth(){
		boolean actual = creature.canEnter(layer.getWidth() + 1, 2);
		boolean expected = false;
		assertEquals(expected, actual);
	}
	
	@Test
	public void testCanEnterYGreaterThanWorldHeight(){
		boolean actual = creature.canEnter(2, layer.getHeight() + 1);
		boolean expected = false;
		assertEquals(expected, actual);
	}
	
	@Test
	public void testCanEnterActuallyCanEnter(){
		boolean actual = creature.canEnter(2, 2);
		boolean expected = true;
		assertEquals(expected, actual);
	}
	
	@Test
	public void testMaxHPGettersAndSetters(){
		int newMaxHP = 20;
		creature.setMaxHP(newMaxHP);
		assertEquals(newMaxHP, creature.getMaxHP());
	}
	
	@Test
	public void testHPGettersAndSetters(){
		int newHP = 10;
		creature.setHP(newHP);
		assertEquals(newHP, creature.getHP());
	}
	
	@Test
	public void testModifyHP(){
		int modifiedAmount = 10;
		int expected = creature.getHP() + modifiedAmount;
		creature.modifyHP(modifiedAmount);
		assertEquals(expected, creature.getHP());
	}
	
	@Test
	public void testModifyHPMore(){
		int modifiedAmount = 15;
		int expected = creature.getHP() + modifiedAmount;
		creature.modifyHP(modifiedAmount);
		assertEquals(expected, creature.getHP());
	}
	
	@Test
	public void testModifyHPNegativeAmount(){
		int modifiedAmount = -10;
		int expected = creature.getHP() + modifiedAmount;
		creature.modifyHP(modifiedAmount);
		assertEquals(expected, creature.getHP());
	}
	
	@Test
	public void testCreatureDiesAtLessThan1HP(){
		World worldSpy = Mockito.spy(world);
		Creature creature = Mockito.spy(new Creature(worldSpy, '@', AsciiPanel.brightBlue, 100));
		creature.modifyHP(-(creature.getMaxHP()+1));
		verify(worldSpy, times(1)).remove(creature);
	}
	
	@Test
	public void testDamageReductionGettersAndSetters(){
		int newDamageReduction = 5;
		creature.setDamageReduction(newDamageReduction);
		assertEquals(newDamageReduction, creature.getDamageReduction());
	}
	
	@Test
	public void testNotifyOnlyMessage(){
		String message = "This is a message!";
		creature.notify(message);
		verify(creatureAISpy, times(1)).onNotify(message);
	}
	
	@Test
	public void testNotifyWithParams(){
		String message = "This is a %s";
		Object[] params = {"message"};
		creature.notify(message, params);
		verify(creatureAISpy, times(1)).onNotify(String.format(message, params));
	}
	
	@Test
	public void testZGettersAndSetters1(){
		int expected = 1;
		creature.setZ(expected);
		assertEquals(expected, creature.z());
	}
	
	@Test
	public void testZGettersAndSetters2(){
		int expected = 2;
		creature.setZ(expected);
		assertEquals(expected, creature.z());
	}
	
	@Test
	public void testMoveByZ(){
		creature.setX(0);
		creature.setY(0);
		creature.setZ(0);
		creature.moveBy(0, 0, 1);
		verify(creature, times(1)).checkForObstaclesAndReactAccordingly(0, 0, 1);
	}
	
	@Test
	public void testMoveByZAtTop(){
		creature.setX(0);
		creature.setY(0);
		creature.setZ(0);
		creature.moveBy(0, 0, -1);
		verify(creature, times(0)).checkForObstaclesAndReactAccordingly(0, 0, -1);
	}
	
	@Test
	public void testMoveByZAtBottom(){
		creature.setX(0);
		creature.setY(0);
		creature.setZ(1);
		creature.moveBy(0, 0, 1);
		verify(creature, times(0)).checkForObstaclesAndReactAccordingly(0, 0, 2);
	}
}
