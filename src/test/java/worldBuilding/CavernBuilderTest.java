package worldBuilding;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import tiles.FloorTile;
import tiles.Tile;
import tiles.WallTile;
import world.Layer;
import world.World;

@RunWith(MockitoJUnitRunner.class)
public class CavernBuilderTest {

	@Mock Noise mockedNoise;
	@Mock World world;
	
	CavernBuilder builder;
	CavernBuilder builderSpy;
	
	int testHeight;
	int testWidth;
	
	int testTileX;;
	int testTileY;
	
	@Before
	public void initialize(){
		testHeight = 5;
		testWidth = 5;
		testTileX = 1;
		testTileY = 1;
		builder = new CavernBuilder(testWidth, testHeight);
		builderSpy = Mockito.spy(builder);
		builder.wallUp();
		builderSpy.wallUp();
	}
	
	@Test
	public void testBuild() {
		assertThat(builder.build(world), instanceOf(Layer.class));
	}
	
	@Test
	public void testGetTiles(){
		Tile[][] testTiles = {
				{new WallTile(), new WallTile(), new WallTile(), new WallTile()},
				{new WallTile(), new WallTile(), new WallTile(), new WallTile()},
				{new WallTile(), new WallTile(), new WallTile(), new WallTile()},
				{new WallTile(), new WallTile(), new WallTile(), new WallTile()},
		};
		CavernBuilder newBuilder = new CavernBuilder(testTiles.length, testTiles[0].length, mockedNoise);
		newBuilder.setTiles(testTiles);
		assertArrayEquals(newBuilder.getTiles(), testTiles);
	}
	
	
	@Test
	public void testWallUp(){
		for(Tile[] column : builderSpy.getTiles()){
			for(Tile tile : column){
				assertTrue("Unexpected value for tile: " + tile.getSymbol(), tile.getSymbol() == '#');
			}
		}
	}
	
	@Test
	public void testChangeTileFloor(){
		Tile newTile = new FloorTile();
		builder.changeTile(testTileX, testTileY, newTile);
		Tile testTile = builder.getTiles()[testTileX][testTileY];
		assertThat(testTile, instanceOf(FloorTile.class));
	}
	
	@Test
	public void testChangeTileWall(){
		Tile newTile = new WallTile();
		builder.changeTile(testTileX, testTileY, newTile);
		Tile testTile = builder.getTiles()[testTileX][testTileY];
		assertThat(testTile, instanceOf(WallTile.class));
	}
	
	@Test
	public void testSetTiles(){
		Tile[][] testTiles = {
				{new WallTile(), new WallTile(), new WallTile(), new WallTile()},
				{new WallTile(), new WallTile(), new WallTile(), new WallTile()},
				{new WallTile(), new WallTile(), new WallTile(), new WallTile()},
				{new WallTile(), new WallTile(), new WallTile(), new WallTile()},
		};
		builder.setTiles(testTiles);
		assertArrayEquals(testTiles, builder.getTiles());
	}

}
