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

import screens.PlayScreen;
import screens.WinScreen;

@RunWith(MockitoJUnitRunner.class)
public class WinScreenKeysTest {

	
	@Mock private static KeyEvent mockedKey;
	@Mock private static WinScreen winScreen;
	
	private static WinScreenKeys winScreenKeys;
	
	@Before
	public void initialize(){
		winScreenKeys = new WinScreenKeys();
	}
	

	@Test
	public void testRespondToUserInputEscape(){
		@SuppressWarnings("rawtypes") // Don't actually need to add the raw types here, it doesn't add anything.
		Map<Integer, Class> keyNums = new HashMap<Integer, Class>();
		keyNums.put(KeyEvent.VK_ENTER, PlayScreen.class);
		keyNums.put(KeyEvent.VK_CONTROL, WinScreen.class);
		
		for(int key : keyNums.keySet()){
			when(mockedKey.getKeyCode()).thenReturn(key);
			assertThat(winScreenKeys.respondToUserInput(mockedKey, winScreen), instanceOf(keyNums.get(key)));
		}
	}


}
