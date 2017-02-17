package tiles;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import asciiPanel.AsciiPanel;

public class DownStairsTileTest {
	
	DownStairsTile down;

	@Before
	public void initialize() {
		down = new DownStairsTile();
	}
	
	@Test
	public void testCharGetters() {
		char expectedChar = '>';
		assertEquals(expectedChar, down.getSymbol());
	}
	
	@Test
	public void testColorGetters() {
		Color expectedColor = AsciiPanel.brightYellow;
		assertEquals(expectedColor, down.getColor());
	}
	
	@Test
	public void testIsGround() {
		boolean isGround = true;
		assertEquals(isGround, down.isGround());
	}
	
	@Test
	public void testPartnerGettersAndSetters() {
		DownStairsTile partner = new DownStairsTile();
		down.setPartner(partner);
		assertEquals(partner, down.getPartner());
	}
	
	@Test
	public void testPartnerLocGettersAndSetters(){
		int expectedX = 2;
		int expectedY = 3;
		int expectedZ = 8;
		down.setPartnerLoc(2, 3, 8);
		int[] location = down.getPartnerLoc();
		assertEquals(expectedX, location[0]);
		assertEquals(expectedY, location[1]);
		assertEquals(expectedZ, location[2]);
	}
	
	@Test
	public void testIsStairs(){
		boolean stairs = true;
		assertEquals(stairs, down.isStairs());
	}
	
	@Test
	public void testBlocksVision(){
		boolean blocksVision = false;
		assertEquals(blocksVision, down.blocksVision());
	}
	
	@Test
	public void testIsLitOnInit(){
		boolean isLit = false;
		assertEquals(isLit, down.isLit());
	}
	
	@Test
	public void testChangeLitState(){
		boolean isLit = true;
		down.changeLitState();
		assertEquals(isLit, down.isLit());
	}

}
