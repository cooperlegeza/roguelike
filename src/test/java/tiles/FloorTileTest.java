package tiles;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asciiPanel.AsciiPanel;

public class FloorTileTest {
	
	FloorTile floor;
	
	@Before
	public void initialize(){
		floor = new FloorTile();
	}

	@Test
	public void testGetSymbol() {
		assertEquals(floor.getSymbol(), '.');
	}
	
	@Test
	public void testGetColor(){
		assertEquals(floor.getColor(), AsciiPanel.brightWhite);
	}
	
	@Test
	public void testIsGround(){
		boolean ground = true;
		assertEquals(ground, floor.isGround());
	}
	
	@Test
	public void testIsStairs(){
		boolean stairs = false;
		assertEquals(stairs, floor.isStairs());
	}
	
	@Test
	public void testPartnerMethods(){
		assertNull(floor.getPartner());
		assertNull(floor.getPartnerLoc());
	}
	
	@Test
	public void testBlocksVision(){
		boolean blocksVision = false;
		assertEquals(blocksVision, floor.blocksVision());
	}
	
	@Test
	public void testIsLitOnInit(){
		boolean isLit = false;
		assertEquals(isLit, floor.isLit());
	}
	
	@Test
	public void testChangeLitState(){
		boolean isLit = true;
		floor.changeLitState();
		assertEquals(isLit, floor.isLit());
	}

}
