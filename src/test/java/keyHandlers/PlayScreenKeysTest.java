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
import org.mockito.runners.MockitoJUnitRunner;

import screens.LoseScreen;
import screens.PlayScreen;
import screens.WinScreen;

@RunWith(MockitoJUnitRunner.class)
public class PlayScreenKeysTest {
	
	@Mock private static KeyEvent mockedKey;
	@Mock private static PlayScreen playScreen;
	
	private static PlayScreenKeys playScreenKeys;
	
	@Before
	public void initialize(){
		playScreenKeys = new PlayScreenKeys();
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
	public void testRespondToUserInputArrowKeys(){
		@SuppressWarnings("rawtypes") // Don't actually need to add the raw types here, it doesn't add anything.
		Map<Integer, Class> keyNums = new HashMap<Integer, Class>();
		keyNums.put(KeyEvent.VK_LEFT, PlayScreen.class);
		keyNums.put(KeyEvent.VK_UP, PlayScreen.class);
		keyNums.put(KeyEvent.VK_DOWN, PlayScreen.class);
		keyNums.put(KeyEvent.VK_RIGHT, PlayScreen.class);
		
		int times = 1;
		
		for(int key : keyNums.keySet()){
			when(mockedKey.getKeyCode()).thenReturn(key);
			assertThat(playScreenKeys.respondToUserInput(mockedKey, playScreen), instanceOf(keyNums.get(key)));
			verify(playScreen, times(times)).moveBy(anyInt(), anyInt());
			times++;
		}
	}

}
