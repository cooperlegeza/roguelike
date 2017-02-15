package tiles;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import asciiPanel.AsciiPanel;

public class UpStairsTileTest {
	
	UpStairsTile up;

	@Before
	public void initialize() {
		up = new UpStairsTile();
	}
	
	@Test
	public void testCharGetters() {
		char expectedChar = '<';
		assertEquals(expectedChar, up.getSymbol());
	}
	
	@Test
	public void testColorGetters() {
		Color expectedColor = AsciiPanel.brightYellow;
		assertEquals(expectedColor, up.getColor());
	}
	
	@Test
	public void testIsGround() {
		boolean isGround = true;
		assertEquals(isGround, up.isGround());
	}
	
	@Test
	public void testPartnerGettersAndSetters() {
		DownStairsTile partner = new DownStairsTile();
		up.setPartner(partner);
		assertEquals(partner, up.getPartner());
	}
	
	@Test
	public void testPartnerLocGettersAndSetters(){
		int expectedX = 2;
		int expectedY = 3;
		int expectedZ = 8;
		up.setPartnerLoc(2, 3, 8);
		int[] location = up.getPartnerLoc();
		assertEquals(expectedX, location[0]);
		assertEquals(expectedY, location[1]);
		assertEquals(expectedZ, location[2]);
	}
	
	@Test
	public void testIsStairs(){
		boolean stairs = true;
		assertEquals(stairs, up.isStairs());
	}

}
