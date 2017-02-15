package creatureAIs;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

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
import world.Layer;
import world.World;

@RunWith(MockitoJUnitRunner.class)
public class PlayerAITest {
	
	@Mock Layer layer;
	
	PlayerAI playerAI;
	Creature player;
	Tile tile;
	@Mock World world;
	
	@Before
	public void initialize() {
		player = new Creature(world, '@', AsciiPanel.green, 100);
		playerAI = new PlayerAI(player);
		player.setCreatureAI(playerAI);
		player.setX(5);
		player.setY(5);
		player.setZ(0);
	}

	@Test
	public void testOnEnterFloor() {
		tile = new FloorTile();
		int x = 5;
		int y = 6;
		int z = 0;
		playerAI.onEnter(x, y, z, tile);
		assertEquals(x, player.x());
		assertEquals(y, player.y());
		assertEquals(z, player.z());
	}
	
	@Test
	public void testOnEnterWall() {
		tile = new WallTile();
		int x = 5;
		int y = 6;
		int z = 0;
		int expectedX = 5;
		int expectedY = 5;
		int expectedZ = 0;
		playerAI.onEnter(x, y, z, tile);
		assertEquals(expectedX, player.x());
		assertEquals(expectedY, player.y());
		assertEquals(expectedZ, player.z());
	}
	
	@Test
	public void testGetMessages(){
		List<String> list = new ArrayList<String>();
		PlayerAI ai = new PlayerAI(new Creature(world, '@', AsciiPanel.green, 100), list);
		assertEquals(list, ai.getMessages());
	}
	
	@Test
	public void testOnNotify(){
		List<String> list = new ArrayList<String>();
		PlayerAI ai = new PlayerAI(new Creature(world, '@', AsciiPanel.green, 100), list);
		String message = "This is a message!"; 
		ai.onNotify(message);
		int expectedSize = 1;
		assertEquals(expectedSize, ai.getMessages().size());
		assertEquals(message, ai.getMessages().get(0));
	}
}
