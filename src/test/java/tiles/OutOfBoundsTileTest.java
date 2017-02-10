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

}
