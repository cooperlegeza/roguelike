package worldBuilding;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import tiles.FloorTile;
import tiles.Tile;
import utils.Coordinate;

public class WandererTest {
	
	Wanderer wandererSpy;
	CavernBuilder builder;
	
	int testWidth;
	int testHeight;
	int testStartLocX;
	int testStartLocY;
	Directions directions;
	
	@Before
	public void initialize(){
		testWidth = 10;
		testHeight = 10;
		testStartLocX = testWidth/2;
		testStartLocY = testHeight/2;
		
		wandererSpy = Mockito.spy(new Wanderer(testWidth, testHeight));
		builder = new CavernBuilder(testWidth, testHeight);
		builder.wallUp();
		wandererSpy.setTiles(builder.getTiles());
	}
	
	@Test
	public void testInitWithDifferentWidth(){
		int newTestWidth = 20;
		Wanderer newWanderer = new Wanderer(newTestWidth, testHeight);
		assertEquals(newWanderer.getX(), newTestWidth/2);
	}
	
	@Test
	public void testInitWithDifferentHeight(){
		int newTestHeight = 20;
		Wanderer newWanderer = new Wanderer(testWidth, newTestHeight);
		assertEquals(newTestHeight/2, newWanderer.getY());
	}
	
	@Test
	public void testGetX(){
		int x = wandererSpy.getX();
		assertEquals(x, testStartLocX);
	}
	
	@Test
	public void testGetXAfterMovement(){
		int expectedX = 8;
		wandererSpy.sprint(3, Directions.EAST);
		assertEquals(wandererSpy.getX(), expectedX);
	}
	
	@Test
	public void testGetY(){
		int y = wandererSpy.getY();
		assertEquals(y, testStartLocY);
	}

	@Test
	public void testSprint3() {
		int expectedX = 8;
		wandererSpy.sprint(3, Directions.EAST);
		assertEquals(wandererSpy.getX(), expectedX);
	}
	
	@Test
	public void testSprint2() {
		int expectedX = 7;
		wandererSpy.sprint(2, Directions.EAST);
		assertEquals(wandererSpy.getX(), expectedX);
	}
	
	@Test
	public void testSprintSouth(){
		int expectedY = 8;
		wandererSpy.sprint(3, Directions.SOUTH);
		assertEquals(wandererSpy.getY(), expectedY);
	}
	
	@Test
	public void testSprintNorth(){
		int expectedY = 2;
		wandererSpy.sprint(3, Directions.NORTH);
		assertEquals(wandererSpy.getY(), expectedY);
	}
	
	@Test
	public void testSprintWest(){
		int expectedX = 2;
		wandererSpy.sprint(3, Directions.WEST);
		assertEquals(wandererSpy.getX(), expectedX);
	}
	
	@Test
	public void testSprintCannotGoBeyondMapWest(){
		int expectedX = 0;
		wandererSpy.sprint(1000, Directions.WEST);
		assertEquals(wandererSpy.getX(), expectedX);
	}
	
	@Test
	public void testSprintCannotGoBeyondMapEast(){
		int expectedX = testWidth - 1;
		wandererSpy.sprint(testWidth+1, Directions.EAST);
		assertEquals(wandererSpy.getX(), expectedX);
	}
	
	@Test
	public void testSprintCannotGoBeyondMapSouth(){
		int expectedY = testHeight - 1;
		wandererSpy.sprint(testHeight+1000, Directions.SOUTH);
		assertEquals(wandererSpy.getY(), expectedY);
	}
	
	@Test
	public void testSprintCannotGoBeyondMapNorth(){
		int expectedY = 0;
		wandererSpy.sprint(testHeight+1000, Directions.NORTH);
		assertEquals(expectedY, wandererSpy.getY());
	}
	
	@Test
	public void testSprintChangeTilesToFloorsEast(){
		int expectedX = 8;
		int distance = 3;
		wandererSpy.sprint(distance, Directions.EAST);
		verify(wandererSpy, times(1)).sprintedTiles(testStartLocX, distance, Directions.EAST);
		assertEquals(expectedX, wandererSpy.getX());
	}
	
	@Test
	public void testSprintChangeTilesToFloorsEastDistanceMoreThanWidth(){
		int expectedX = 9;
		int distance = 10000;
		int actualDistance = wandererSpy.getWidth() - wandererSpy.getX() - 1;
		wandererSpy.sprint(distance, Directions.EAST);
		verify(wandererSpy, times(1)).sprintedTiles(testStartLocX, actualDistance,  Directions.EAST);
		assertEquals(expectedX, wandererSpy.getX());
	}
	
	@Test
	public void testSprintChangeTilesToFloorsNorth(){
		int expectedY = 2;
		int distance = 3;
		wandererSpy.sprint(distance, Directions.NORTH);
		verify(wandererSpy, times(1)).sprintedTiles(testStartLocY, 3,  Directions.NORTH);
		assertEquals(expectedY, wandererSpy.getY());
	}
	
	@Test
	public void testSprintChangeTilesToFloorsNorthDistanceMoreThanHeight(){
		int expectedY = 0;
		int distance = wandererSpy.getHeight() + 1;
		int actualDistance = wandererSpy.getY();
		wandererSpy.sprint(distance, Directions.NORTH);
		verify(wandererSpy, times(1)).sprintedTiles(testStartLocY, actualDistance,  Directions.NORTH);
		assertEquals(expectedY, wandererSpy.getY());
	}
	
	@Test
	public void testSprintChangeTilesToFloorsSouth(){
		int expectedY = 8;
		int distance = 3;
		wandererSpy.sprint(distance, Directions.SOUTH);
		verify(wandererSpy, times(1)).sprintedTiles(testStartLocY, distance, Directions.SOUTH);
		assertEquals(expectedY, wandererSpy.getY());
	}
	
	@Test
	public void testSprintChangeTilesToFloorsSouthMoreThanHeight(){
		int expectedY = wandererSpy.getHeight() - 1;
		int distance = wandererSpy.getHeight() + 1;
		int actualDistance = wandererSpy.getHeight() - wandererSpy.getY() - 1;
		wandererSpy.sprint(distance, Directions.SOUTH);
		verify(wandererSpy, times(1)).sprintedTiles(testStartLocY, actualDistance, Directions.SOUTH);
		assertEquals(expectedY, wandererSpy.getY());
	}
	@Test
	public void testSprintChangeTilesToFloorsWest(){
		int expectedX = 2;
		int distance = 3;
		wandererSpy.sprint(distance, Directions.WEST);
		verify(wandererSpy, times(1)).sprintedTiles(testStartLocY, distance, Directions.WEST);
		assertEquals(expectedX, wandererSpy.getX());
	}
	
	@Test
	public void testSprintChangeTilesToFloorsWestTooFar(){
		int expectedX = 0;
		int distance = wandererSpy.getWidth() + 1;
		int actualDistance = wandererSpy.getX();
		wandererSpy.sprint(distance, Directions.WEST);
		verify(wandererSpy, times(1)).sprintedTiles(testStartLocX, actualDistance, Directions.WEST);
		assertEquals(expectedX, wandererSpy.getX());
	}
	
	@Test
	public void testSprintedTilesLimiter(){
		int distance = 3;
		int actual = wandererSpy.limitSprintedTiles(testStartLocX, distance, Directions.EAST);
		assertEquals(distance, actual);
	}
	
	@Test
	public void testLimitSprintedTilesEastTooFar(){
		int distance = wandererSpy.getWidth()+1;
		int expected = wandererSpy.getWidth() - wandererSpy.getX() - 1;
		int actual = wandererSpy.limitSprintedTiles(testStartLocX, distance, Directions.EAST);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testLimitSprintedTilesSouth(){
		int distance = 3;
		int actual = wandererSpy.limitSprintedTiles(testStartLocY, distance, Directions.SOUTH);
		assertEquals(distance, actual);
	}
	
	@Test
	public void testLimitSprintedTilesSouthTooFar(){
		int distance = wandererSpy.getHeight() + 1;
		int expected = wandererSpy.getHeight() - wandererSpy.getY() - 1;
		int actual = wandererSpy.limitSprintedTiles(testStartLocY, distance, Directions.SOUTH);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testLimitSprintedTilesNorth(){
		int distance = 3;
		int actual = wandererSpy.limitSprintedTiles(testStartLocY, distance, Directions.NORTH);
		assertEquals(distance, actual);
	}
	
	@Test
	public void testLimitSprintedTilesNorthTooFar(){
		int distance = wandererSpy.getHeight() + 1;
		int expected = wandererSpy.getY();
		int actual = wandererSpy.limitSprintedTiles(testStartLocY, distance, Directions.NORTH);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testLimitSprintedTilesWest(){
		int distance = 3;
		int actual = wandererSpy.limitSprintedTiles(testStartLocX, distance, Directions.WEST);
		assertEquals(distance, actual);
	}
	
	@Test
	public void testLimitSprintedTilesWestTooFar(){
		int distance = wandererSpy.getWidth() + 1;
		int expected = wandererSpy.getX();
		int actual = wandererSpy.limitSprintedTiles(testStartLocX, distance, Directions.WEST);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetTiles(){
		CavernBuilder builder2 = new CavernBuilder(20, 20);
		wandererSpy.setTiles(builder2.getTiles());
		Tile[][] tiles = wandererSpy.getTiles();
		Tile[][] newTiles = builder2.getTiles();
		assertArrayEquals(newTiles, tiles);
		assertEquals(wandererSpy.getWidth(), tiles.length);
		assertEquals(wandererSpy.getHeight(), tiles[0].length);
	}
	
	@Test
	public void testSetTiles(){
		wandererSpy.setTiles(builder.getTiles());
		assertArrayEquals(builder.getTiles(), wandererSpy.getTiles());
	}
	
	@Test
	public void testSprintedTiles(){
		wandererSpy.setTiles(builder.getTiles());
		int distance = 3;
		wandererSpy.sprintedTiles(testStartLocX, distance, Directions.EAST);
		int expectedX = 8;
		for(int x = 0; x <= distance; x++){
			assertThat(wandererSpy.getTile(testStartLocX + x, testStartLocY), instanceOf(FloorTile.class));
		}
		assertEquals(expectedX, wandererSpy.getX());
	}
	
	@Test
	public void testSprintedTilesNorth(){
		wandererSpy.setTiles(builder.getTiles());
		int distance = 3;
		wandererSpy.sprintedTiles(testStartLocX, distance, Directions.NORTH);
		int expectedY = 2;
		for(int y = 0; y <= distance; y++){
			assertThat(wandererSpy.getTile(testStartLocX, testStartLocY - y), instanceOf(FloorTile.class));
		}
		assertEquals(expectedY, wandererSpy.getY());
	}
	
	@Test
	public void testSprintedTilesSouth(){
		wandererSpy.setTiles(builder.getTiles());
		int distance = 3;
		wandererSpy.sprintedTiles(testStartLocX, distance, Directions.SOUTH);
		int expectedY = 8;
		for(int y = 0; y <= distance; y++){
			assertThat(wandererSpy.getTile(testStartLocX, testStartLocY + y), instanceOf(FloorTile.class));
		}
		assertEquals(expectedY, wandererSpy.getY());
	}
	
	@Test
	public void testSprintedTilesWest(){
		wandererSpy.setTiles(builder.getTiles());
		int distance = 3;
		wandererSpy.sprintedTiles(testStartLocX, distance, Directions.WEST);
		int expectedX = 2;
		for(int x = 0; x <= distance; x++){
			assertThat(wandererSpy.getTile(testStartLocX - x, testStartLocY), instanceOf(FloorTile.class));
		}
		assertEquals(expectedX, wandererSpy.getX());
	}
	
	@Test
	public void testGetTile(){
		wandererSpy.setTiles(builder.tiles);
		Tile tile = wandererSpy.getTile(testStartLocX, testStartLocY);
		assertThat(tile, instanceOf(Tile.class));
	}
	
	@Test
	public void testGetWidth(){
		assertEquals(testWidth, wandererSpy.getWidth());
	}
	
	@Test
	public void testGetHeight(){
		assertEquals(testHeight, wandererSpy.getHeight());
	}
	
	@Test
	public void testChangeTile(){
		wandererSpy.setTiles(builder.getTiles());
		wandererSpy.changeTile(testStartLocX, testStartLocY, new FloorTile());
		assertThat(wandererSpy.getTile(testStartLocX, testStartLocY), instanceOf(FloorTile.class));
	}
	
	@Test
	public void testWander(){
		int lifetime = 90;
		wandererSpy.wander(lifetime);
		verify(wandererSpy, times(lifetime)).step(any());
	}
	
	@Test
	public void testSprintChangesDirectionSouth(){
		assertEquals(Directions.EAST, wandererSpy.getFacing());
		wandererSpy.sprint(1, Directions.SOUTH);
		assertEquals(Directions.SOUTH, wandererSpy.getFacing());
	}
	
	@Test
	public void testSprintChangesDirectionAnyDirection(){
		Directions[] directions = {Directions.NORTH, Directions.EAST, Directions.SOUTH, Directions.WEST};
		for(Directions direction : directions){
			wandererSpy.sprint(1, direction);
			assertEquals(direction, wandererSpy.getFacing());
		}
	}
	
	@Test
	public void testWanderIfAtEdgeEastAndFacingEast(){
		wandererSpy.sprint(wandererSpy.getWidth(), Directions.EAST);
		Directions facing = wandererSpy.getFacing();
		assertEquals(facing, Directions.EAST);
		assertEquals(wandererSpy.getWidth() - 1, wandererSpy.getX());
		wandererSpy.changeFacingIfAtEdge();
		assertTrue(wandererSpy.getFacing() == Directions.NORTH || wandererSpy.getFacing() == Directions.SOUTH);
	}
	
	@Test
	public void testWanderIfAtEdgeEastAndFacingNorth(){
		wandererSpy.sprint(wandererSpy.getWidth(), Directions.EAST);
		wandererSpy.sprint(1, Directions.NORTH);
		Directions facing = wandererSpy.getFacing();
		wandererSpy.changeFacingIfAtEdge();
		assertEquals(facing, wandererSpy.getFacing());
	}
	
	@Test
	public void testWanderIfAtEdgeNorthAndFacingNorth(){
		wandererSpy.sprint(wandererSpy.getHeight(), Directions.NORTH);
		Directions facing = wandererSpy.getFacing();
		assertEquals(facing, Directions.NORTH);
		wandererSpy.changeFacingIfAtEdge();
		assertTrue(wandererSpy.getFacing() == Directions.WEST || wandererSpy.getFacing() == Directions.EAST);
	}
	
	@Test
	public void testWanderIfAtEdgeNorthAndNotFacingNorth(){
		wandererSpy.sprint(wandererSpy.getHeight(), Directions.NORTH);
		wandererSpy.sprint(1, Directions.SOUTH);
		Directions facing = wandererSpy.getFacing();
		wandererSpy.changeFacingIfAtEdge();
		assertEquals(facing, wandererSpy.getFacing());
	}
	
	@Test
	public void testWanderIfAtEdgeSouthAndFacingSouth(){
		wandererSpy.sprint(wandererSpy.getHeight(), Directions.SOUTH);
		wandererSpy.changeFacingIfAtEdge();
		Directions facing = wandererSpy.getFacing();
		assertTrue(facing == Directions.WEST || facing == Directions.EAST);
	}
	
	@Test
	public void testWanderIfAtEdgeSouthAndNotFacingSouth(){
		wandererSpy.sprint(wandererSpy.getHeight(), Directions.SOUTH);
		wandererSpy.sprint(1, Directions.WEST);
		Directions facing = wandererSpy.getFacing();
		wandererSpy.changeFacingIfAtEdge();
		assertEquals(facing, wandererSpy.getFacing());
	}
	
	@Test
	public void testWanderIfAtEdgeWestAndFacingWest(){
		wandererSpy.sprint(wandererSpy.getWidth(), Directions.WEST);
		wandererSpy.changeFacingIfAtEdge();
		Directions facing = wandererSpy.getFacing();
		assertTrue(facing == Directions.NORTH || facing == Directions.SOUTH);
	}
	
	@Test
	public void testWanderIfAtEdgeWestAndNotFacingWest(){
		wandererSpy.sprint(wandererSpy.getWidth(), Directions.WEST);
		wandererSpy.sprint(1, Directions.NORTH);
		Directions facing = wandererSpy.getFacing();
		wandererSpy.changeFacingIfAtEdge();
		assertEquals(facing, wandererSpy.getFacing());
	}
	
	@Test
	public void testStep(){
		wandererSpy.step(Directions.NORTH);
		verify(wandererSpy, times(1)).sprint(1, Directions.NORTH);
	}
	
	@Test
	public void testGetFacing(){
		assertEquals(Directions.EAST, wandererSpy.getFacing());
	}
	
	@Test
	public void testWanderCallsChangeFacingIfAtEdge(){
		int lifetime = 10;
		wandererSpy.wander(lifetime);
		verify(wandererSpy, times(lifetime)).changeFacingIfAtEdge();
	}
	
	@Test
	public void testMakeRoomDiameter2(){
		int diameter = 2;
		wandererSpy.makeOpenSpace(diameter);
		for(int x = -1; x < 1; x++){
			for(int y = -1; y < 1; y++){
				assertThat(wandererSpy.getTile(wandererSpy.getX() + x, wandererSpy.getY() + y),
					instanceOf(FloorTile.class));
			}
		}
	}
	
	@Test
	public void testMoveTo(){
		int newX = 3;
		int newY = 3;
		wandererSpy.moveTo(newX, newY);
		assertEquals(newX, wandererSpy.getX());
		assertEquals(newY, wandererSpy.getY());
	}
	
	@Test
	public void testCannotMoveToOffMapX(){
		int newX = 1000;
		int newY = 3;
		int expectedX = wandererSpy.getWidth() - 1;
		wandererSpy.moveTo(newX, newY);
		assertEquals(expectedX, wandererSpy.getX());
		assertEquals(newY, wandererSpy.getY());
	}
	
	@Test
	public void testCannotMoveToOffMapY(){
		int newX = 3;
		int newY = 1000;
		int expectedY = wandererSpy.getHeight() - 1;
		wandererSpy.moveTo(newX, newY);
		assertEquals(expectedY, wandererSpy.getY());
		assertEquals(newX, wandererSpy.getX());
	}
	
	@Test
	public void testCannotMoveToOffMapNegativeX(){
		int newX = -999;
		int newY = 3;
		int expectedX = 0;
		wandererSpy.moveTo(newX, newY);
		assertEquals(expectedX, wandererSpy.getX());
		assertEquals(newY, wandererSpy.getY());
	}
	
	@Test
	public void testCannotMoveToOffMapNegativeY(){
		int newY = -999;
		int newX = 3;
		int expectedY = 0;
		wandererSpy.moveTo(newX, newY);
		assertEquals(expectedY, wandererSpy.getY());
	}
	
	@Test
	public void testMakeOpenSpaceDiameter2AtWestWall(){
		wandererSpy.moveTo(0, 1);
		wandererSpy.makeOpenSpace(2);
	}
	
	@Test
	public void testMakeOpenSpaceDiameter1AtEastWall(){
		wandererSpy.moveTo(wandererSpy.getWidth()-1, 5);
		wandererSpy.makeOpenSpace(2);
	}
	
	@Test
	public void testMakeOpenSpaceDiameter2AtNorthWall(){
		wandererSpy.moveTo(5, 0);
		wandererSpy.makeOpenSpace(2);
	}
	
	
	@Test
	public void testMakeOpenSpaceDiameter3(){
		wandererSpy.makeOpenSpace(3);
		Coordinate[] coordinates = {
				new Coordinate(4, 5),
				new Coordinate(5, 5),
				new Coordinate(6, 5),
				new Coordinate(5, 6),
				new Coordinate(5, 4)
		};
		
		for(Coordinate coordinate: coordinates){
			assertThat(wandererSpy.getTile(coordinate.x, coordinate.y), instanceOf(FloorTile.class));
		}
	}
	
	@Test
	public void testMakeOpenSpaceDiameter5(){
		wandererSpy.makeOpenSpace(5);
		Coordinate[] coordinates = {
				new Coordinate(3, 5),
				new Coordinate(4, 5),
				new Coordinate(5, 5),
				new Coordinate(6, 5),
				new Coordinate(7, 5),
				new Coordinate(4, 6),
				new Coordinate(5, 6),
				new Coordinate(6, 6),
				new Coordinate(4, 4),
				new Coordinate(5, 4),
				new Coordinate(6, 4),
				new Coordinate(5, 3),
				new Coordinate(5, 7)
		};
		
		for(Coordinate coordinate : coordinates){
			assertThat(wandererSpy.getTile(coordinate.x, coordinate.y), instanceOf(FloorTile.class));
		}
	}
	
	@Test
	public void testMakeOpenSpaceDiameter7(){
		wandererSpy.makeOpenSpace(7);
		Coordinate[] coordinates = {
				new Coordinate(2, 5),
				new Coordinate(3, 5),
				new Coordinate(4, 5),
				new Coordinate(5, 5),
				new Coordinate(6, 5),
				new Coordinate(7, 5),
				new Coordinate(8, 5),
				new Coordinate(3, 6),
				new Coordinate(4, 6),
				new Coordinate(5, 6),
				new Coordinate(6, 6),
				new Coordinate(7, 6),
				new Coordinate(3, 4),
				new Coordinate(4, 4),
				new Coordinate(5, 4),
				new Coordinate(6, 4),
				new Coordinate(7, 4),
				new Coordinate(4, 3),
				new Coordinate(5, 3),
				new Coordinate(6, 3),
				new Coordinate(4, 7),
				new Coordinate(5, 7),
				new Coordinate(6, 7),
				new Coordinate(5, 3),
				new Coordinate(5, 8)
		};
		for(Coordinate coordinate : coordinates){
			assertThat(wandererSpy.getTile(coordinate.x, coordinate.y), instanceOf(FloorTile.class));
		}
	}
	
	@Test
	public void testMakeOpenSpaceDiameter5AtWestWall(){
		wandererSpy.moveTo(0, 5);
		Coordinate[] coordinates = {
				new Coordinate(0, 5),
				new Coordinate(1, 5),
				new Coordinate(2, 5),
				new Coordinate(0, 6),
				new Coordinate(1, 6),
				new Coordinate(0, 7),
				new Coordinate(0, 4),
				new Coordinate(1, 4),
				new Coordinate(0, 3)
		};
		wandererSpy.makeOpenSpace(5);
		for(Coordinate coordinate : coordinates){
			assertThat(wandererSpy.getTile(coordinate.x, coordinate.y), instanceOf(FloorTile.class));
		}
	}
	
	@Test
	public void testMakeOpenSpaceDiameter5AtEastWall(){
		wandererSpy.moveTo(wandererSpy.getWidth()-1, 5);
		Coordinate[] coordinates = {
				new Coordinate(9, 5),
				new Coordinate(8, 5),
				new Coordinate(7, 5),
				new Coordinate(9, 6),
				new Coordinate(8, 6),
				new Coordinate(9, 7),
				new Coordinate(9, 4),
				new Coordinate(8, 4),
				new Coordinate(9, 3)
		};
		wandererSpy.makeOpenSpace(5);
		for(Coordinate coordinate : coordinates){
			assertThat(wandererSpy.getTile(coordinate.x, coordinate.y), instanceOf(FloorTile.class));
		}
	}
	
	@Test
	public void testMakeOpenSpaceDiameter5AtNorthWall(){
		wandererSpy.moveTo(5, 0);
		Coordinate[] coordinates = {
				new Coordinate(3, 0),
				new Coordinate(4, 0),
				new Coordinate(5, 0),
				new Coordinate(6, 0),
				new Coordinate(7, 0),
				new Coordinate(4, 1),
				new Coordinate(5, 1),
				new Coordinate(6, 1),
				new Coordinate(5, 2)
		};
		wandererSpy.makeOpenSpace(5);
		for(Coordinate coordinate : coordinates){
			assertThat(wandererSpy.getTile(coordinate.x, coordinate.y), instanceOf(FloorTile.class));
		}
	}
	
	@Test
	public void testMakeOpenSpaceDiameter5AtSouthWall(){
		wandererSpy.moveTo(5, wandererSpy.getHeight()-1);
		Coordinate[] coordinates = {
				new Coordinate(3, 9),
				new Coordinate(4, 9),
				new Coordinate(5, 9),
				new Coordinate(6, 9),
				new Coordinate(7, 9),
				new Coordinate(4, 8),
				new Coordinate(5, 8),
				new Coordinate(6, 8),
				new Coordinate(5, 7)
		};
		wandererSpy.makeOpenSpace(5);
		for(Coordinate coordinate : coordinates){
			assertThat(wandererSpy.getTile(coordinate.x, coordinate.y), instanceOf(FloorTile.class));
		}
	}
	
	@Test
	public void testMakeOpenSpaceDiameter4(){
		Coordinate[] coordinates = {
				new Coordinate(4, 5),
				new Coordinate(5, 5),
				new Coordinate(6, 5),
				new Coordinate(7, 5),
				new Coordinate(4, 6),
				new Coordinate(5, 6),
				new Coordinate(6, 6),
				new Coordinate(7, 6),
				new Coordinate(5, 4),
				new Coordinate(6, 4),
				new Coordinate(5, 7),
				new Coordinate(6, 7)
		};
		
		wandererSpy.makeOpenSpace(4);
		for(Coordinate coordinate : coordinates){
			assertThat(wandererSpy.getTile(coordinate.x, coordinate.y), instanceOf(FloorTile.class));
		}
	}
	
	@Test
	public void testMakeOpenSpaceDiameter6(){
		Coordinate[] coordinates = {
				new Coordinate(3, 5),
				new Coordinate(4, 5),
				new Coordinate(5, 5),
				new Coordinate(6, 5),
				new Coordinate(7, 5),
				new Coordinate(8, 5),
				new Coordinate(3, 6),
				new Coordinate(4, 6),
				new Coordinate(5, 6),
				new Coordinate(6, 6),
				new Coordinate(7, 6),
				new Coordinate(8, 6),
				new Coordinate(4, 4),
				new Coordinate(5, 4),
				new Coordinate(6, 4),
				new Coordinate(7, 4),
				new Coordinate(4, 7),
				new Coordinate(5, 7),
				new Coordinate(6, 7),
				new Coordinate(7, 7),
				new Coordinate(5, 3),
				new Coordinate(6, 3),
				new Coordinate(5, 8),
				new Coordinate(6, 8)
		};
		
		wandererSpy.makeOpenSpace(6);
		for(Coordinate coordinate : coordinates){
			assertThat(wandererSpy.getTile(coordinate.x, coordinate.y), instanceOf(FloorTile.class));
		}	
	}
	
	@Test
	public void testMakeOpenSpaceDiameter4AtWestWall(){
		wandererSpy.moveTo(0, 5);
		Coordinate[] coordinates = {
				new Coordinate(0, 5),
				new Coordinate(1, 5),
				new Coordinate(2, 5),
				new Coordinate(0, 6),
				new Coordinate(1, 6),
				new Coordinate(2, 6),
				new Coordinate(0, 4),
				new Coordinate(1, 4),
				new Coordinate(0, 7),
				new Coordinate(1, 7)
		};
		
		wandererSpy.makeOpenSpace(4);
		for(Coordinate coordinate : coordinates){
			assertThat(wandererSpy.getTile(coordinate.x, coordinate.y), instanceOf(FloorTile.class));
		}
	}
	
	@Test
	public void testMakeOpenSpaceDiameter4AtEastWall(){
		wandererSpy.moveTo(wandererSpy.getWidth()-1, 5);
		Coordinate[] coordinates = {
				new Coordinate(9, 5),
				new Coordinate(8, 5),
				new Coordinate(9, 6),
				new Coordinate(8, 6),
				new Coordinate(9, 4),
				new Coordinate(9, 7)
		};
		
		wandererSpy.makeOpenSpace(4);
		for(Coordinate coordinate : coordinates){
			assertThat(wandererSpy.getTile(coordinate.x, coordinate.y), instanceOf(FloorTile.class));
		}
	}
	
	@Test
	public void testMakeOpenSpaceDiameter4AtSouthWall(){
		wandererSpy.moveTo(5, wandererSpy.getHeight()-1);
		Coordinate[] coordinates = {
				new Coordinate(4, 9),
				new Coordinate(5, 9),
				new Coordinate(6, 9),
				new Coordinate(7, 9),
				new Coordinate(5, 8),
				new Coordinate(6, 8),
		};
		
		wandererSpy.makeOpenSpace(4);
		for(Coordinate coordinate : coordinates){
			assertThat(wandererSpy.getTile(coordinate.x, coordinate.y), instanceOf(FloorTile.class));
		}
	}
	
	@Test
	public void testMakeOpenSpaceDiameter4AtNorthWall(){
		wandererSpy.moveTo(5, 0);
		Coordinate[] coordinates = {
				new Coordinate(4, 0),
				new Coordinate(5, 0),
				new Coordinate(6, 0),
				new Coordinate(7, 0),
				new Coordinate(4, 1),
				new Coordinate(5, 1),
				new Coordinate(6, 1),
				new Coordinate(7, 1),
				new Coordinate(5, 2),
				new Coordinate(6, 2),
		};
		
		wandererSpy.makeOpenSpace(4);
		for(Coordinate coordinate : coordinates){
			assertThat(wandererSpy.getTile(coordinate.x, coordinate.y), instanceOf(FloorTile.class));
		}
	}
	
	@Test
	public void testMakeChild(){
		
	}
}
