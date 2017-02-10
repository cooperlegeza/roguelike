package worldBuilding;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class FloodTest {
	
	CavernBuilder builder;
	Flood flood;
	Flood floodSpy;
	
	int testHeight;
	int testWidth;
	int testTileX;
	int testTileY;
	
	@Before
	public void initialize(){
		testHeight = 10;
		testWidth = 10;
		testTileX = 1;
		testTileY = 1;
		builder = new CavernBuilder(testHeight, testWidth);
		flood = new Flood();
		flood.setTiles(builder.getTiles());
		floodSpy = Mockito.spy(flood);
	}
	
	@Test
	public void testErode(){
		floodSpy.erode();
		verify(floodSpy, times(1)).erode();
	}
	
	@Test
	public void testChangeWallAround(){
		floodSpy.erodeWall(testTileX, testTileY);
		verify(floodSpy, times(4)).changeWall(anyInt(), anyInt());
	}
	
	@Test
	public void makeCaves(){
		floodSpy.makeCaves();
		verify(floodSpy, times(1)).flood();
	}
}
