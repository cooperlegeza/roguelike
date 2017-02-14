package creatureAIs;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import asciiPanel.AsciiPanel;
import creatures.Creature;
import tiles.FloorTile;
import tiles.Tile;
import tiles.WallTile;
import world.World;

@RunWith(MockitoJUnitRunner.class)
public class PlayerAITest {
	
	@Mock World world;
	
	PlayerAI playerAI;
	Creature player;
	Tile tile;
	
	@Before
	public void initialize() {
		player = new Creature(world, '@', AsciiPanel.green, 100);
		playerAI = new PlayerAI(player);
		player.setCreatureAI(playerAI);
		player.setX(5);
		player.setY(5);
	}

	@Test
	public void testOnEnterFloor() {
		tile = new FloorTile();
		int x = 5;
		int y = 6;
		playerAI.onEnter(x, y, tile);
		assertEquals(x, player.x());
		assertEquals(y, player.y());
	}
	
	@Test
	public void testOnEnterWall() {
		tile = new WallTile();
		int x = 5;
		int y = 6;
		int expectedX = 5;
		int expectedY = 5;
		playerAI.onEnter(x, y, tile);
		assertEquals(expectedX, player.x());
		assertEquals(expectedY, player.y());
	}

}
