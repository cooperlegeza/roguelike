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
	
	@Test
	public void testIsStairs(){
		boolean stairs = false;
		assertEquals(stairs, door.isStairs());
	}
	
	@Test
	public void testPartnerMethods(){
		assertNull(door.getPartner());
		assertNull(door.getPartnerLoc());
	}
	
	@Test
	public void testBlocksVisionClosed(){
		boolean closedBlocksVision = true;
		assertEquals(closedBlocksVision, door.blocksVision());
	}
	
	@Test
	public void testBlocksVisionOpen(){
		boolean openBlocksVision = false;
		door.changeDoorState();
		assertEquals(openBlocksVision, door.blocksVision());
	}
	
	@Test
	public void testIsLit(){
		boolean isLit = false;
		assertEquals(isLit, door.isLit());
	}
	
	@Test
	public void testChangeLitState(){
		boolean isLit = true;
		door.changeLitState();
		assertEquals(isLit, door.isLit());
	}

}
