package screens;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.event.KeyEvent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import asciiPanel.AsciiPanel;
import keyHandlers.WinScreenKeys;

@RunWith(MockitoJUnitRunner.class)
public class WinScreenTest {
	
	@Mock private static AsciiPanel mockedAsciiPanel;
	@Mock private static KeyEvent mockedKey;
	@Mock private static WinScreenKeys mockedWinScreenKeys;
	
	private static WinScreen startScreen;
	
	@Before
	public void initialize(){
		startScreen = new WinScreen(mockedWinScreenKeys);
	}
	
	
	@Test
	public void testDisplayOutput(){
		startScreen.displayOutput(mockedAsciiPanel);
		
		verify(mockedAsciiPanel, times(2)).writeCenter(anyString(), anyInt());
	}
	
	@Test
	public void testRespondToUserInput(){
		when(mockedKey.getKeyCode()).thenReturn(KeyEvent.VK_ESCAPE);
		startScreen.respondToUserInput(mockedKey);
		
		verify(mockedWinScreenKeys, times(1)).respondToUserInput(mockedKey, startScreen);
	}

}
