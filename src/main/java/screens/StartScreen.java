package screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import keyHandlers.KeysHandler;
import keyHandlers.StartScreenKeys;

public class StartScreen implements Screen {

	private String title = "The Quiet White";
	private String command = "-- press [enter] to start --";
	private int upLeft = 1;
	private KeysHandler keys;
	
	public StartScreen(){
		keys = new StartScreenKeys();
	}
	
	public StartScreen(KeysHandler keys){
		this.keys = keys;
	}
	
	public void displayOutput(AsciiPanel terminal) {
		terminal.write(title, upLeft, upLeft);
		terminal.writeCenter(command, 22);
	}

	public Screen respondToUserInput(KeyEvent key) {
		Screen returnScreen = this.keys.respondToUserInput(key, this);
		return returnScreen;
	}

}
