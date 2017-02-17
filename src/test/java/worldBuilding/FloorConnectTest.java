package worldBuilding;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import tiles.DownStairsTile;
import tiles.FloorTile;
import tiles.UpStairsTile;
import utils.Coordinate;
import utils.RogueMath;
import world.Layer;
import world.World;

@RunWith(MockitoJUnitRunner.class)
public class FloorConnectTest {
	
	FloorConnect connector;
	int topLayer = 0;
	int middleLayer = 1;
	int bottomLayer = 2;
	
	@Mock World world;
	@Mock Layer layerMock;
	@Mock FloorTile floor;
	@Mock RogueMath math;
	@Mock UpStairsTile up;
	@Mock DownStairsTile down;
	
	@Before
	public void initialize(){
		connector = Mockito.spy(new FloorConnect(world, math));
		topLayer = 0;
		middleLayer = 1;
		when(world.getLayer(anyInt())).thenReturn(layerMock);
		when(layerMock.getWidth()).thenReturn(3);
		when(layerMock.getHeight()).thenReturn(3);
		when(world.getTile(anyInt(), anyInt(), anyInt())).thenReturn(floor);
		when(world.getDepth()).thenReturn(5);
		when(floor.isGround()).thenReturn(true);
		when(floor.isStairs()).thenReturn(false);
	}
	
	@Test
	public void testFindEmptySpotSecondSpotButChecking() {
		int topLayer = 0;
		when(math.random()).thenReturn(0.0);
		connector.findEmptySpot(topLayer);
		verify(floor, times(1)).isGround();
		verify(floor, times(1)).isStairs();
	}
	
	@Test
	public void testFindEmptySpotCallsCallsWorldLayerToFindHeightAndWidth(){
		when(math.random()).thenReturn(0.0);
		connector.findEmptySpot(topLayer);
		verify(world, times(2)).getLayer(topLayer);
		verify(layerMock, times(1)).getWidth();
		verify(layerMock, times(1)).getHeight();
	}
	
	@Test
	public void testFindEmptySpot() {
		int middleLayer = 1;
		when(math.random()).thenReturn(.5);
		Coordinate test = connector.findEmptySpot(middleLayer);
		assertEquals(1, test.x);
		assertEquals(1, test.y);
		assertEquals(1, test.z);
	}

	@Test
	public void testConnectToLayerBelow(){
		when(math.random()).thenReturn(0.0);
		connector.connectToLayerBelow(topLayer);
		verify(connector, times(1)).findEmptySpot(topLayer);
		verify(connector, times(1)).findEmptySpot(middleLayer);
		verify(connector, times(1)).setUpAndDownStairsTogether(
				any(UpStairsTile.class), any(DownStairsTile.class), any(Coordinate.class), any(Coordinate.class));
		verify(world, times(1)).setTileAt(eq(0), eq(0), eq(0), any(DownStairsTile.class));
		verify(world, times(1)).setTileAt(eq(0), eq(0), eq(1), any(UpStairsTile.class));
	}
	
	@Test
	public void testSetUpAndDownStairsTogether(){
		Coordinate topFloor = new Coordinate(0, 0, 0);
		Coordinate nextFloor = new Coordinate(0, 0, 1);
		connector.setUpAndDownStairsTogether(up, down, topFloor, nextFloor);
		verify(up, times(1)).setPartner(down);
		verify(down, times(1)).setPartner(up);
		verify(up, times(1)).setPartnerLoc(topFloor.x, topFloor.y, topFloor.z);
		verify(down, times(1)).setPartnerLoc(nextFloor.x, nextFloor.y, nextFloor.z);
	}
	
	@Test
	public void testConnectAllLayers(){
		connector.connectAllLayers();
		verify(world, times(1)).getDepth();
		verify(connector, times(4)).connectToLayerBelow(anyInt());
	}

}
