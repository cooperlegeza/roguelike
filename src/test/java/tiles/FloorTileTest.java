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

}
