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

}
