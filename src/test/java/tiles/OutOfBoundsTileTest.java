package tiles;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asciiPanel.AsciiPanel;

public class OutOfBoundsTileTest {

	public OutOfBoundsTile outOfBounds;
	
	@Before
	public void initialize(){
		outOfBounds = new OutOfBoundsTile();
	}
	
	@Test
	public void testGetSymbol() {
		assertEquals(outOfBounds.getSymbol(), '#');
	}
	
	@Test
	public void testGetColor(){
		assertEquals(outOfBounds.getColor(), AsciiPanel.brightWhite);
	}
	
	@Test
	public void testIsGround(){
		boolean ground = false;
		assertEquals(ground, outOfBounds.isGround());
	}
	
	@Test
	public void testIsStairs(){
		boolean stairs = false;
		assertEquals(stairs, outOfBounds.isStairs());
	}
	
	@Test
	public void testPartnerMethods(){
		assertNull(outOfBounds.getPartner());
		assertNull(outOfBounds.getPartnerLoc());
	}
	
	@Test
	public void testBlocksVision(){
		boolean blocksVision = true;
		assertEquals(blocksVision, outOfBounds.blocksVision());
	}
	
	@Test
	public void testIsLitOnInit(){
		boolean isLit = false;
		assertEquals(isLit, outOfBounds.isLit());
	}
	
	@Test
	public void testChangeLitStateDoesNotChangeLitState(){
		boolean isLit = false;
		outOfBounds.changeLitState();
		assertEquals(isLit, outOfBounds.isLit());
	}

}
