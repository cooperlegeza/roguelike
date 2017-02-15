package tiles;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asciiPanel.AsciiPanel;

public class WallTileTest {

	WallTile wall;
	
	@Before
	public void initialize(){
		wall = new WallTile();
	}
	
	@Test
	public void testGetSymbol() {
		assertEquals(wall.getSymbol(), '#');
	}
	
	@Test
	public void testGetColor(){
		assertEquals(wall.getColor(), AsciiPanel.brightWhite);
	}
	
	@Test
	public void testIsGround(){
		boolean ground = false;
		assertEquals(ground, wall.isGround());
	}
	
	@Test
	public void testIsStairs(){
		boolean stairs = false;
		assertEquals(stairs, wall.isStairs());
	}

}
