package screens;

import static org.mockito.Mockito.*;

import java.awt.event.KeyEvent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import junit.framework.TestCase;
import keyHandlers.StartScreenKeys;
import asciiPanel.AsciiPanel;

@RunWith(MockitoJUnitRunner.class)
public class StartScreenTest extends TestCase {
	
	@Mock private static AsciiPanel mockedAsciiPanel;
	@Mock private static KeyEvent mockedKey;
	@Mock private static StartScreenKeys mockedStartScreenKeys;
	
	private static StartScreen startScreen;
	
	@Before
	public void initialize(){
		startScreen = new StartScreen(mockedStartScreenKeys);
	}
	
	
	@Test
	public void testDisplayOutput(){
		startScreen.displayOutput(mockedAsciiPanel);
		
		verify(mockedAsciiPanel, times(1)).write(anyString(), anyInt(), anyInt());
		verify(mockedAsciiPanel, times(1)).writeCenter(anyString(), anyInt());
	}
	
	@Test
	public void testRespondToUserInput(){
		when(mockedKey.getKeyCode()).thenReturn(KeyEvent.VK_ESCAPE);
		startScreen.respondToUserInput(mockedKey);
		
		verify(mockedStartScreenKeys, times(1)).respondToUserInput(mockedKey, startScreen);
	}

}
