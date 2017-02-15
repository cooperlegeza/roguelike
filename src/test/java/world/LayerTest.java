package world;

import static org.hamcrest.CoreMatchers.instanceOf;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import asciiPanel.AsciiPanel;
import creatures.Creature;
import creatures.CreatureFactory;
import tiles.FloorTile;
import tiles.OutOfBoundsTile;
import tiles.Tile;
import tiles.WallTile;

@RunWith(MockitoJUnitRunner.class)
public class LayerTest {
	
	int worldWidth = 4;
	int worldHeight = 4;
	Tile[][] tiles;
	Layer layer;
	WallTile testTile;
	CreatureFactory factory;
	int testTileX;
	int testTileY;
	int outOfBoundsXLow;
	int outOfBoundsXHigh;
	int outOfBoundsYLow;
	int outOfBoundsYHigh;
	@Mock World world;
	
	@Before
	public void initialize(){
		testTileX = 1;
		testTileY = 2;
		outOfBoundsXLow = -1;
		outOfBoundsXHigh = 4;
		outOfBoundsYLow = -1;
		outOfBoundsYHigh = 4;
		
		testTile = new WallTile();
		Tile[][] newTiles = {
				{new WallTile(), new FloorTile(), new WallTile(), new FloorTile()},
				{new FloorTile(), new FloorTile(), new WallTile(), new WallTile()},
				{new WallTile(), new WallTile(), new WallTile(), new FloorTile()},
				{new WallTile(), new FloorTile(), new WallTile(), new FloorTile()},
		};
		tiles = newTiles;
		tiles[testTileX][testTileY] = testTile;
		layer = new Layer(tiles, world);
		factory = new CreatureFactory(world);
	}

	@Test
	public void testGetHeight() {
		assertEquals(layer.getHeight(), worldHeight);
	}
	
	@Test
	public void testGetWidth(){
		assertEquals(layer.getWidth(), worldWidth);
	}
	
	@Test
	public void testGetTiles(){
		assertArrayEquals(layer.getTiles(), tiles);
	}
	
	@Test
	public void testGetTileGetsTileThatExists(){
		assertEquals(layer.getTile(testTileX, testTileY), testTile);
	}
	
	@Test
	public void testGetTileGetsOutOfBoundsIfTileXIsLessThan0(){
		assertThat(layer.getTile(outOfBoundsXLow, testTileY), instanceOf(OutOfBoundsTile.class));
	}
	
	@Test
	public void testGetTileGetsOutOfBoundsIfTileXIsGreaterThanWidth(){
		assertThat(layer.getTile(outOfBoundsXHigh, testTileY), instanceOf(OutOfBoundsTile.class));
	}
	
	@Test
	public void testGetTileGetsOutOfBoundsIfTileYIsGreaterThanHeight(){
		assertThat(layer.getTile(testTileX, outOfBoundsYHigh), instanceOf(OutOfBoundsTile.class));
	}
	
	@Test
	public void testGetTileGetsOutOfBoundsIfTileYIsLessThan0(){
		assertThat(layer.getTile(testTileX, outOfBoundsYLow), instanceOf(OutOfBoundsTile.class));
	}
	
	@Test
	public void testGetTileGetsOutOfBoundsIfBothParamsAreInappropriate(){
		int[] paramsX = {outOfBoundsXHigh, outOfBoundsXLow};
		int[] paramsY = {outOfBoundsYHigh, outOfBoundsYLow};
		for(int x : paramsX){
			for(int y : paramsY){
				assertThat(layer.getTile(x, y), instanceOf(OutOfBoundsTile.class));
			}
		}
	}
	
	@Test
	public void testGetGlyph(){
		assertEquals(layer.getGlyph(testTileX, testTileY), testTile.getSymbol());
	}
	
	@Test
	public void testGetColor(){
		assertEquals(layer.getColor(testTileX, testTileY), testTile.getColor());
	}
	
	@Test
	public void testAddAtEmptyLocation(){
		boolean isGround = true;
		Creature player = factory.newPlayer();
		layer.addAtEmptyLocation(player);
		assertEquals(isGround, layer.getTile(player.x(), player.y()).isGround());
	}
	
	@Test
	public void testGetCreatureAt(){
		List<Creature> worldList = new LinkedList<Creature>();
		Creature creature = new Creature(world, '@', AsciiPanel.black, 100);
		worldList.add(creature);
		when(world.getCreatures()).thenReturn(worldList);
		creature.setX(1);
		creature.setY(1);
		Creature test = layer.getCreatureAt(creature.x(), creature.y());
		assertEquals(creature, test);
	}
	
	@Test
	public void testSetCreatureAt(){
		List<Creature> worldList = new LinkedList<Creature>();
		Creature creature = factory.newPlayer();
		worldList.add(creature);
		when(world.getCreatures()).thenReturn(worldList);
		int expectedX = 2;
		int expectedY = 2;
		layer.setCreatureAt(expectedX, expectedY, creature);
		assertEquals(expectedX, creature.x());
		assertEquals(expectedY, creature.y());
	}
	
	@Test
	public void testGetTileOverloadedForLayers(){
		
	}

}
