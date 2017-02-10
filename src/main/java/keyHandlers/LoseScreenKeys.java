package keyHandlers;

import java.awt.event.KeyEvent;

import screens.PlayScreen;
import screens.Screen;

public class LoseScreenKeys implements KeysHandler {

	@Override
	public Screen respondToUserInput(KeyEvent key, Screen screen) {
		switch(key.getKeyCode()){
			case KeyEvent.VK_ENTER: return new PlayScreen();
			default: return screen;
		}
	}

}
