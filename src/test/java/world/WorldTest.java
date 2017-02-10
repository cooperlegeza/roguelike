package world;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

import java.awt.List;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import creatures.Creature;
import creatures.CreatureFactory;
import tiles.FloorTile;
import tiles.OutOfBoundsTile;
import tiles.Tile;
import tiles.WallTile;

public class WorldTest {
	
	int worldWidth = 4;
	int worldHeight = 4;
	Tile[][] tiles;
	World world;
	WallTile testTile;
	CreatureFactory factory;
	int testTileX;
	int testTileY;
	int outOfBoundsXLow;
	int outOfBoundsXHigh;
	int outOfBoundsYLow;
	int outOfBoundsYHigh;
	
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
		world = new World(tiles);
		factory = new CreatureFactory(world);
	}

	@Test
	public void testGetHeight() {
		assertEquals(world.getHeight(), worldHeight);
	}
	
	@Test
	public void testGetWidth(){
		assertEquals(world.getWidth(), worldWidth);
	}
	
	@Test
	public void testGetTiles(){
		assertArrayEquals(world.getTiles(), tiles);
	}
	
	@Test
	public void testGetTileGetsTileThatExists(){
		assertEquals(world.getTile(testTileX, testTileY), testTile);
	}
	
	@Test
	public void testGetTileGetsOutOfBoundsIfTileXIsLessThan0(){
		assertThat(world.getTile(outOfBoundsXLow, testTileY), instanceOf(OutOfBoundsTile.class));
	}
	
	@Test
	public void testGetTileGetsOutOfBoundsIfTileXIsGreaterThanWidth(){
		assertThat(world.getTile(outOfBoundsXHigh, testTileY), instanceOf(OutOfBoundsTile.class));
	}
	
	@Test
	public void testGetTileGetsOutOfBoundsIfTileYIsGreaterThanHeight(){
		assertThat(world.getTile(testTileX, outOfBoundsYHigh), instanceOf(OutOfBoundsTile.class));
	}
	
	@Test
	public void testGetTileGetsOutOfBoundsIfTileYIsLessThan0(){
		assertThat(world.getTile(testTileX, outOfBoundsYLow), instanceOf(OutOfBoundsTile.class));
	}
	
	@Test
	public void testGetTileGetsOutOfBoundsIfBothParamsAreInappropriate(){
		int[] paramsX = {outOfBoundsXHigh, outOfBoundsXLow};
		int[] paramsY = {outOfBoundsYHigh, outOfBoundsYLow};
		for(int x : paramsX){
			for(int y : paramsY){
				assertThat(world.getTile(x, y), instanceOf(OutOfBoundsTile.class));
			}
		}
	}
	
	@Test
	public void testGetGlyph(){
		assertEquals(world.getGlyph(testTileX, testTileY), testTile.getSymbol());
	}
	
	@Test
	public void testGetColor(){
		assertEquals(world.getColor(testTileX, testTileY), testTile.getColor());
	}
	
	@Test
	public void testAddAtEmptyLocation(){
		boolean isGround = true;
		Creature player = factory.newPlayer();
		world.addAtEmptyLocation(player);
		assertEquals(isGround, world.getTile(player.x(), player.y()).isGround());
	}
	
	@Test
	public void testAddAtEmptyLocationAddsToCreatures(){
		int expectedSizeOfCreatures = world.getCreatures().size() + 1;
		Creature player = factory.newPlayer();
		world.addAtEmptyLocation(player);
		assertEquals(expectedSizeOfCreatures, 1);
	}
	
	@Test
	public void testGetCreatures(){
		assertThat(world.getCreatures(), instanceOf(LinkedList.class));
	}
	
	@Test
	public void testGetCreatureAt(){
		Creature creature = factory.newPlayer();
		world.addAtEmptyLocation(creature);
		Creature test = world.getCreatureAt(creature.x(), creature.y());
		assertEquals(creature, test);
	}

}
