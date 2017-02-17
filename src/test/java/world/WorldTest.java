package world;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import asciiPanel.AsciiPanel;
import creatures.Creature;
import creatures.CreatureFactory;
import tiles.FloorTile;
import tiles.Tile;
import tiles.WallTile;
import utils.RogueMath;

@RunWith(MockitoJUnitRunner.class)
public class WorldTest {
	
	public List<Layer> layers;
	public World world;
	public Layer spyLayer;
	int x, y, z;
	public WallTile spyWall;
	public RogueMath math;
	CreatureFactory factory;
	
	@Before
	public void initialize(){
		layers = new LinkedList<Layer>();
		spyWall = new WallTile();
		math = Mockito.mock(RogueMath.class);
		
		for(int layerCount = 0; layerCount < 3; layerCount++){
			if(layerCount == 1){
				spyLayer = Mockito.mock(Layer.class);
				layers.add(spyLayer);
			} else {
				Layer layer = Mockito.mock(Layer.class);
				layers.add(layer);
			}
		}
		x = 1;
		y = 1;
		z = 1;
		world = new WorldImpl(layers, math);
		factory = new CreatureFactory(world);
	}
	
	@Test
	public void testGetTile(){
		when(spyLayer.getTile(x, y)).thenReturn(spyWall);
		Tile test = world.getTile(x, y, z);
		verify(spyLayer, times(1)).getTile(x, y);
		assertEquals(spyWall, test);
	}
	
	@Test
	public void testGetGlyph(){
		when(spyLayer.getGlyph(x, y)).thenReturn(spyWall.getSymbol());
		char test = world.getGlyph(x, y, z);
		verify(spyLayer, times(1)).getGlyph(x, y);
		assertEquals(spyWall.getSymbol(), test);
	}
	
	@Test
	public void testGetColor(){
		when(spyLayer.getColor(x, y)).thenReturn(spyWall.getColor());
		Color test = world.getColor(x, y, z);
		verify(spyLayer, times(1)).getColor(x, y);
		assertEquals(spyWall.getColor(), test);
	}
	
	@Test
	public void testAddAtEmptyLocation(){
		when(math.random()).thenReturn(0.5);
		Creature creature = Mockito.mock(Creature.class);
		world.addAtEmptyLocation(creature);
		verify(spyLayer, times(1)).addAtEmptyLocation(creature, 1);
	}
	
	@Test
	public void testGetCreatureAt(){
		world.getCreatureAt(x, y, z);
		verify(spyLayer, times(1)).getCreatureAt(x, y);
	}
	
	@Test
	public void testAddCreature(){
		Creature creature = Mockito.mock(Creature.class);
		int expected = world.getCreatures().size() + 1;
		world.addCreature(creature);
		assertEquals(expected, world.getCreatures().size());
		assertEquals(creature, world.getCreatures().get(0));
	}
	
	@Test
	public void testRemove(){
		Creature creature = factory.newPlayer();
		int expectedCreaturesSize = world.getCreatures().size() - 1;
		boolean expectedCreatureInList = false;
		world.remove(creature);
		assertEquals(expectedCreaturesSize, world.getCreatures().size());
		assertEquals(expectedCreatureInList, world.getCreatures().remove(creature));
	}
	
	@Test
	public void testSetCreatureAt(){
		int expectedCreaturesSize = world.getCreatures().size() + 1;
		Creature creature = new Creature(world, '@', AsciiPanel.green, 100);
		int expectedX = 2;
		int expectedY = 2;
		int expectedZ = 1;
		world.setCreatureAt(expectedX, expectedY, expectedZ, creature);
		assertEquals(expectedZ, creature.z());
		verify(spyLayer, times(1)).setCreatureAt(expectedX, expectedY, expectedZ, creature);
		assertEquals(world.getCreatures().size(), expectedCreaturesSize);
	}

	
	@Test
	public void testUpdate(){
		world.setCreatureAt(2, 2, 0, Mockito.mock(Creature.class));
		world.setCreatureAt(1, 1, 0, Mockito.mock(Creature.class));
		world.setCreatureAt(1, 2, 0, Mockito.mock(Creature.class));
		world.setCreatureAt(2, 1, 0, Mockito.mock(Creature.class));

		world.update();
		for(Creature creature : world.getCreatures()){
			verify(creature, times(1)).update();
		}
	}
	
	@Test
	public void testGetDepth(){
		int expectedDepth = 3;
		assertEquals(expectedDepth, world.getDepth());
	}
	
	@Test
	public void testSetTileAt(){
		int x = 1, y = 1, z = 1;
		Tile newTile = new FloorTile();
		world.setTileAt(x, y, z, newTile);
		verify(spyLayer, times(1)).setTileAt(x, y, newTile);
	}
	
	@Test
	public void testAddAtEmptyLocationWithSpecifiedLayer(){
		Creature creature = Mockito.mock(Creature.class);
		int expectedLayer = 1;
		world.addAtEmptyLocation(creature, expectedLayer);
		verify(spyLayer, times(1)).addAtEmptyLocation(creature, expectedLayer);
	}

}
