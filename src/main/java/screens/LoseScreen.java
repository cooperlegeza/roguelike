package screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import keyHandlers.KeysHandler;
import keyHandlers.LoseScreenKeys;

public class LoseScreen implements Screen {
	
	private String lost = "You have lost the game!";
	private String restart = "Press [enter] to restart the game.";
	private int lostRow = 22;
	private int restartRow = 23;
	private KeysHandler keys;
	
	public LoseScreen(){
		this.keys = new LoseScreenKeys();
	}
	
	public LoseScreen(KeysHandler keys){
		this.keys = keys;
	}
	

	public void displayOutput(AsciiPanel terminal) {
		terminal.writeCenter(lost, lostRow);
		terminal.writeCenter(restart, restartRow);

	}

	public Screen respondToUserInput(KeyEvent key) {
		Screen returnScreen = keys.respondToUserInput(key, this);
		return returnScreen;
	}

}
