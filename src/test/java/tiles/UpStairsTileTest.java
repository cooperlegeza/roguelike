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

}
