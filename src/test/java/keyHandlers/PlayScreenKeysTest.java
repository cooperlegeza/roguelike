package keyHandlers;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import creatures.Creature;
import screens.LoseScreen;
import screens.PlayScreen;
import screens.WinScreen;
import tiles.DownStairsTile;
import tiles.UpStairsTile;
import utils.Coordinate;
import world.World;

@RunWith(MockitoJUnitRunner.class)
public class PlayScreenKeysTest {
	
	@Mock private static KeyEvent mockedKey;
	@Mock private static PlayScreen playScreen;
	@Mock World world;
	@Mock Creature player;
	
	private static PlayScreenKeys playScreenKeys;
	
	@Before
	public void initialize(){
		playScreenKeys = Mockito.spy(new PlayScreenKeys());
		when(playScreen.getWorld()).thenReturn(world);
		when(playScreen.getPlayer()).thenReturn(player);
		when(player.x()).thenReturn(1);
		when(player.y()).thenReturn(1);
		when(player.z()).thenReturn(1);
	}
	

	@Test
	public void testRespondToUserInput(){
		@SuppressWarnings("rawtypes") // Don't actually need to add the raw types here, it doesn't add anything.
		Map<Integer, Class> keyNums = new HashMap<Integer, Class>();
		keyNums.put(KeyEvent.VK_ENTER, WinScreen.class);
		keyNums.put(KeyEvent.VK_ESCAPE, LoseScreen.class);
		keyNums.put(KeyEvent.VK_CONTROL, PlayScreen.class);

		
		for(int key : keyNums.keySet()){
			when(mockedKey.getKeyCode()).thenReturn(key);
			assertThat(playScreenKeys.respondToUserInput(mockedKey, playScreen), instanceOf(keyNums.get(key)));
		}
	}
	
	@Test
	public void testRespondToUserInputMovement(){
		Map<Integer, Coordinate> keyNums = new HashMap<Integer, Coordinate>();
		keyNums.put(KeyEvent.VK_LEFT, new Coordinate(-1, 0, 0));
		keyNums.put(KeyEvent.VK_UP, new Coordinate(0, -1, 0));
		keyNums.put(KeyEvent.VK_DOWN, new Coordinate(0, 1, 0));
		keyNums.put(KeyEvent.VK_RIGHT, new Coordinate(1, 0, 0));
		
		for(int key : keyNums.keySet()){
			Coordinate coordinate = keyNums.get(key);
			when(mockedKey.getKeyCode()).thenReturn(key);
			assertThat(playScreenKeys.respondToUserInput(mockedKey, playScreen), instanceOf(PlayScreen.class));
			verify(playScreenKeys, times(1)).moveBy(coordinate.x, coordinate.y, coordinate.z, player);
		}
	}
	
	@Test
	public void testRespondToGreaterThanSymbol(){
		when(world.getTile(player.x(), player.y(), player.z())).thenReturn(new DownStairsTile());
		when(mockedKey.getKeyChar()).thenReturn('>');
		assertThat(playScreenKeys.respondToUserInput(mockedKey, playScreen), instanceOf(PlayScreen.class));
		verify(playScreenKeys, times(1)).goDown(world, player);
	}
	
	@Test
	public void testRespondToLessThanSymbol(){
		when(mockedKey.getKeyChar()).thenReturn('<');
		when(world.getTile(player.x(), player.y(), player.z())).thenReturn(new UpStairsTile());
		assertThat(playScreenKeys.respondToUserInput(mockedKey, playScreen), instanceOf(PlayScreen.class));
		verify(playScreenKeys, times(1)).goUp(world, player);
	}
	
	@Test
	public void testMoveDown(){
		playScreen.setPlayer(player);
		DownStairsTile tile = new DownStairsTile();
		playScreen.setWorld(world);
		when(world.getTile(player.x(), player.y(), player.z())).thenReturn(tile);
		playScreenKeys.goDown(world, player);
		verify(player, times(1)).useStairs(tile);
	}
	
	@Test
	public void testMoveDownOnUpTileFails(){
		UpStairsTile tile = new UpStairsTile();
		when(world.getTile(player.x(), player.y(), player.z())).thenReturn(tile);
		playScreenKeys.goDown(world, player);
		verify(player, times(0)).useStairs(tile);
	}
	
	@Test
	public void testMoveUp(){
		UpStairsTile tile = new UpStairsTile();
		when(world.getTile(player.x(), player.y(), player.z())).thenReturn(tile);
		playScreenKeys.goUp(world, player);
		verify(player, times(1)).useStairs(tile);
	}
	
	@Test
	public void testMoveUpOnDownTileFails(){
		playScreen.setPlayer(player);
		DownStairsTile tile = new DownStairsTile();
		playScreen.setWorld(world);
		when(world.getTile(player.x(), player.y(), player.z())).thenReturn(tile);
		playScreenKeys.goUp(world, player);
		verify(player, times(0)).useStairs(tile);
	}

}
