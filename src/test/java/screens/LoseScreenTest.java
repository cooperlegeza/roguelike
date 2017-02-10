package screens;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.awt.event.KeyEvent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import asciiPanel.AsciiPanel;
import keyHandlers.LoseScreenKeys;

@RunWith(MockitoJUnitRunner.class)
public class LoseScreenTest {
	
	@Mock AsciiPanel mockedAsciiPanel;
	@Mock KeyEvent mockedKey;
	@Mock LoseScreenKeys mockedKeysHandler;
	
	LoseScreen loseScreen;
	
	@Before
	public void initialize(){
		loseScreen = new LoseScreen(mockedKeysHandler);
	}

	@Test
	public void testDisplayOutput() {
		loseScreen.displayOutput(mockedAsciiPanel);
		
		verify(mockedAsciiPanel, times(2)).writeCenter(anyString(), anyInt());
	}
	
	@Test
	public void testRespondToUserInput(){
		when(mockedKey.getKeyCode()).thenReturn(KeyEvent.VK_ENTER);
		loseScreen.respondToUserInput(mockedKey);
		
		verify(mockedKeysHandler, times(1)).respondToUserInput(mockedKey, loseScreen);
		
	}

}
