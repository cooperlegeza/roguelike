package tiles;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DoorTileTest {
	
	DoorTile door;
	
	@Before
	public void initialize(){
		door = new DoorTile();
	}
	
	@Test
	public void testChangeDoorState() {
		door.changeDoorState();
		boolean open = true;
		char openDoor = '/';
		assertEquals(open, door.getOpenState());
		assertEquals(openDoor, door.getSymbol());
	}
	
	@Test
	public void testChangeDoorStateTwice(){
		door.changeDoorState();
		door.changeDoorState();
		boolean open = false;
		char closedDoor = '+';
		assertEquals(open, door.getOpenState());
		assertEquals(closedDoor, door.getSymbol());
	}
	
	@Test
	public void testGetOpenState(){
		boolean open = false;
		assertEquals(open, door.getOpenState());
	}
	
	@Test
	public void testGetOpenStateClosed(){
		boolean open = true;
		door.changeDoorState();
		assertEquals(open, door.getOpenState());
	}
	
	@Test
	public void testIsGroundClosed(){
		boolean ground = false;
		assertEquals(ground, door.isGround());
	}
	
	@Test
	public void testIsGroundOpen(){
		boolean ground = true;
		door.changeDoorState();
		assertEquals(ground, door.isGround());
	}

}
