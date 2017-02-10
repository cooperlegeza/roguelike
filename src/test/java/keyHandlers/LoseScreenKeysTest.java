package keyHandlers;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

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

@RunWith(MockitoJUnitRunner.class)
public class LoseScreenKeysTest {

	@Mock private static KeyEvent mockedKey;
	@Mock private static LoseScreen loseScreen;
	
	private static LoseScreenKeys loseScreenKeys;
	
	@Before
	public void initialize(){
		loseScreenKeys = new LoseScreenKeys();
	}
	

	@Test
	public void testRespondToUserInputEscape(){
		@SuppressWarnings("rawtypes") // Don't actually need to add the raw types here, it doesn't add anything.
		Map<Integer, Class> keyNums = new HashMap<Integer, Class>();
		keyNums.put(KeyEvent.VK_ENTER, PlayScreen.class);
		keyNums.put(KeyEvent.VK_CONTROL, LoseScreen.class);
		
		for(int key : keyNums.keySet()){
			when(mockedKey.getKeyCode()).thenReturn(key);
			assertThat(loseScreenKeys.respondToUserInput(mockedKey, loseScreen), instanceOf(keyNums.get(key)));
		}
	}

}
