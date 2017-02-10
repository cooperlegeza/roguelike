package keyHandlers;

import java.awt.event.KeyEvent;

import screens.Screen;

public interface KeysHandler {
	public Screen respondToUserInput(KeyEvent key, Screen screen);

}
