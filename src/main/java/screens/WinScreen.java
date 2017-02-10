package screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import keyHandlers.KeysHandler;
import keyHandlers.WinScreenKeys;

public class WinScreen implements Screen {
	
	private KeysHandler keys;
	private String won = "Congrats, you've won!";
	private String restart = "Press enter to restart";
	private int wonRow = 22;
	private int restartRow = 23;
	
	public WinScreen(){
		this.keys = new WinScreenKeys();
	}
	
	public WinScreen(KeysHandler keys){
		this.keys = keys;
	}

	public void displayOutput(AsciiPanel terminal) {
		terminal.writeCenter(won, wonRow);
		terminal.writeCenter(restart, restartRow);
	}

	public Screen respondToUserInput(KeyEvent key) {
		Screen returnScreen = this.keys.respondToUserInput(key, this);
		return returnScreen;
	}

}
