package com.manifestcorp.theQuietWhite;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;
import screens.Screen;
import screens.StartScreen;


public class App extends JFrame implements KeyListener {
	private static final long serialVersionUID = 1422720422212626281L;
	
	private AsciiPanel terminal;
	private Screen screen;
	
	public App(){
		super();
		terminal = new AsciiPanel(80, 50, AsciiFont.NAGIDAL_32x32);
		add(terminal);
		pack();
		screen = new StartScreen();
		addKeyListener(this);
		repaint();
	}
	
	public void repaint(){
		terminal.clear();
		screen.displayOutput(terminal);
		super.repaint();
	}
	
	public void keyPressed(KeyEvent key){
		screen = screen.respondToUserInput(key);
		repaint();
	}
	
    public static void main( String[] args ){
    	App app = new App();
    	app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	app.setVisible(true);
    }

	@Override
	public void keyTyped(KeyEvent e) {
		//Here only for implementing KeyListener.
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//Here only for implementing KeyListener.
	}
    
}
