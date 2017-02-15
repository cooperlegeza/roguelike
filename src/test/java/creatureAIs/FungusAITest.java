package creatureAIs;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

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
import utils.RogueMath;
import world.Layer;
import world.World;
import world.WorldImpl;

@RunWith(MockitoJUnitRunner.class)
public class FungusAITest {
	
	CreatureFactory factory;
	Creature creature;
	Layer layer;
	@Mock RogueMath math;
	FungusAI ai;
	FungusAI aiSpy;
	World world;
	
	@Before
	public void initialize(){
		List<Layer> layers = new LinkedList<Layer>();
		world = new WorldImpl(layers);
		Tile[][] tiles = {
				{new FloorTile(), new FloorTile()},
				{new FloorTile(), new FloorTile()}
		};
		layer = new Layer(tiles, world);
		layers.add(layer);
		factory = Mockito.spy(new CreatureFactory(world));
		creature = Mockito.spy(new Creature(world, 'f', AsciiPanel.green, 100));
		world.setCreatureAt(0, 0, 0, creature);
		ai = new FungusAI(creature, factory, math);
		aiSpy = Mockito.spy(ai);
	}
	
	@Test
	public void testOnUpdateLowRandomChance() {
		when(math.random()).thenReturn(.01);
		aiSpy.onUpdate();
		verify(aiSpy, times(1)).spread();
	}
	
	@Test
	public void testOnUpdateHighRandomChance(){
		when(math.random()).thenReturn(.8);
		aiSpy.onUpdate();
		verify(aiSpy, times(0)).spread();
	}
	
	@Test
	public void testGetSpreadCount(){
		int spreadCountOnInit = 0;
		assertEquals(spreadCountOnInit, ai.getSpreadCount());
	}
	
	@Test
	public void testSetSpreadCount(){
		int newSpreadCount = 3;
		ai.setSpreadCount(newSpreadCount);
		assertEquals(newSpreadCount, ai.getSpreadCount());
	}
	
	@Test
	public void testOnUpdateNormalSpreadCount(){
		when(math.random()).thenReturn(.00000000001);
		aiSpy.setSpreadCount(1);
		aiSpy.onUpdate();
		verify(aiSpy, times(1)).spread();
	}
	
	@Test
	public void testOnUpdateTooHighSpreadCount(){
		when(math.random()).thenReturn(.1);
		aiSpy.setSpreadCount(5);
		aiSpy.onUpdate();
		verify(aiSpy, times(0)).spread();
	}
	
	@Test
	public void testSpreadIncreasesSpreadCount(){
		int oldSpreadCount = 0;
		int newSpreadCount = 1;
		when(math.random()).thenReturn(.6);
		assertEquals(oldSpreadCount, aiSpy.getSpreadCount());
		aiSpy.spread();
		assertEquals(newSpreadCount, aiSpy.getSpreadCount());
	}
	
	@Test
	public void testGetGoodXLocationHighest(){
		int expected = 5;
		when(math.random()).thenReturn(.999999);
		int highest = ai.getGoodXLocation();
		verify(math, times(1)).random();
		assertEquals(expected, highest);
	}
	
	@Test
	public void testGetGoodXLocationLowest(){
		int expected = -5;
		when(math.random()).thenReturn(.0);
		int lowest = ai.getGoodXLocation();
		verify(math, times(1)).random();
		assertEquals(expected, lowest);
	}
	
	@Test
	public void testGetGoodXLocationCreatureDifferentX(){
		creature.setX(1);
		int expected = 6;
		when(math.random()).thenReturn(.9999999);
		int actual = ai.getGoodXLocation();
		verify(math, times(1)).random();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetGoodYLocationHighest(){
		int expected = 5;
		when(math.random()).thenReturn(.9999999);
		int highest = ai.getGoodYLocation();
		verify(math, times(1)).random();
		assertEquals(expected, highest);
	}
	
	@Test
	public void testGetGoodYLocationLowest(){
		int expected = -5;
		when(math.random()).thenReturn(0.0);
		int lowest = ai.getGoodYLocation();
		verify(math, times(1)).random();
		assertEquals(expected, lowest);
	}
	
	@Test
	public void testGetGoodYLocationDifferentY(){
		int expected = 6;
		creature.setY(1);
		when(math.random()).thenReturn(.999999999);
		int actual = ai.getGoodYLocation();
		verify(math, times(1)).random();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testSpreadCreatesNewFungus(){
		when(math.random()).thenReturn(.6);
		aiSpy.spread();
		when(creature.canEnter(1, 1)).thenReturn(true);
		verify(factory, times(1)).newFungus(1, 1, 0);
	}
	
	@Test
	public void testSpreadCallsGetGoodLocations(){
		aiSpy.spread();
		verify(aiSpy, times(1)).getGoodXLocation();
		verify(aiSpy, times(1)).getGoodYLocation();
	}
	
	@Test
	public void testSpreadOnlyCanPutOnEmptyTiles(){
		when(math.random()).thenReturn(0.0);
		aiSpy.spread();
		verify(creature, times(1)).canEnter(-5, -5);
		verify(factory, times(0)).newFungus();
	}
	
	@Test
	public void testSpreadOnlyCanPutOnTilesWithoutCreatures(){
		when(math.random()).thenReturn(.5);
		aiSpy.spread();
		verify(creature, times(1)).canEnter(0, 0);
		verify(factory, times(0)).newFungus();
	}
	
	@Test
	public void testSpreadSetsNewCreaturesLocation(){
		when(math.random()).thenReturn(.6);
		aiSpy.spread();
		verify(factory, times(1)).newFungus(1, 1, 0);
	}

}
