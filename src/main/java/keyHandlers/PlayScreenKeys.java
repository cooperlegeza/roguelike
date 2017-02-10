package keyHandlers;

import java.awt.event.KeyEvent;

import screens.LoseScreen;
import screens.PlayScreen;
import screens.Screen;
import screens.WinScreen;

public class PlayScreenKeys implements KeysHandler {

	public Screen respondToUserInput(KeyEvent key, Screen screen) {
		switch(key.getKeyCode()){
			case KeyEvent.VK_LEFT: ((PlayScreen) screen).moveBy(-1, 0); return screen;
			case KeyEvent.VK_RIGHT: ((PlayScreen) screen).moveBy(1, 0); return screen;
			case KeyEvent.VK_UP: ((PlayScreen) screen).moveBy(0, -1); return screen;
			case KeyEvent.VK_DOWN: ((PlayScreen) screen).moveBy(0, 1); return screen;
			case KeyEvent.VK_ENTER: return new WinScreen();
			case KeyEvent.VK_ESCAPE: return new LoseScreen();
			default: return screen;
		}
	}

}
