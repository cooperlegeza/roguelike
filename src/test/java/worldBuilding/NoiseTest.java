package worldBuilding;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import tiles.FloorTile;
import tiles.Tile;

public class NoiseTest {
	
	CavernBuilder builder;
	
	Noise noise;
	Noise noiseSpy;
	int testTileX;
	int testTileY;
	int testHeight;
	int testWidth;
	
	@Before
	public void initialize(){
		testTileX = 1;
		testTileY = 1;
		testHeight = 5;
		testWidth = 5;
		builder = new CavernBuilder(testHeight, testWidth);
		noise = new Noise(builder);
		noiseSpy = Mockito.spy(noise);
		builder.wallUp();
	}


	@Test
	public void testRandomTile(){
		Tile tile = noise.randomizeTile(testTileX, testTileY);
		assertTrue("Unexpected value for random tile: " + tile.getSymbol(), 
				tile.getSymbol() == '.' || tile.getSymbol() == '#');
	}
	
	@Test
	public void testRandomizeAllTiles(){
		Noise returned = noiseSpy.randomizeAllTiles();
		
		verify(noiseSpy, times(testHeight*testWidth)).randomizeTile(anyInt(), anyInt());
		for(Tile[] column : builder.getTiles()){
			for(Tile tile : column){
				assertTrue("Unexpected value for tile: " + tile.getSymbol(),
						tile.getSymbol() == '.' || tile.getSymbol() == '#');
			}
		}
		assertEquals(returned, noiseSpy);
	}
	
	@Test
	public void testSmooth(){
		int times = 5;
		Noise returned = noiseSpy.smooth(times);
		
		verify(noiseSpy, times(times*testWidth*testHeight)).checkSurroundingTilesForWalls(anyInt(), anyInt());
		assertEquals(returned, noiseSpy);
	}
	
	@Test
	public void testCheckSurroundingTiles(){
		int tilesAroundTileAndTile = 9;
		int numWalls = 9;
		int timesCalled = 1;
		
		for(int modX = -1; modX < 2; modX++){
			for(int modY = -1; modY < 2; modY++){
				assertEquals(numWalls, noiseSpy.checkSurroundingTilesForWalls(testTileX, testTileY));
				verify(noiseSpy, times(tilesAroundTileAndTile*timesCalled)).isTileWall(anyInt(), anyInt());
				builder.changeTile(testTileX + modX, testTileY + modY, new FloorTile());
				numWalls--;
				timesCalled++;
			}
		}
	}
	
	@Test
	public void testIsTileWallTrue(){
		assertTrue(noise.isTileWall(testTileX, testTileY));
	}
	
	@Test
	public void testIsTileWallFalse(){
		builder.changeTile(testTileX, testTileY, new FloorTile());
		assertFalse(noise.isTileWall(testTileX, testTileY));
	}
	
	@Test
	public void testMakeCaves(){
		noiseSpy.makeCaves();
		verify(noiseSpy, times(1)).randomizeAllTiles();
		verify(noiseSpy, times(1)).smooth(anyInt());
	}
}
