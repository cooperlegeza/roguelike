package tiles;

import java.awt.Color;

import asciiPanel.AsciiPanel;

public class DoorTile implements Tile {
	
	private char symbol;
	private Color color;
	private boolean open;
	private boolean ground;
	
	public DoorTile(){
		symbol = '+';
		color = AsciiPanel.red;
		open = false;
		ground = open;
	}

	@Override
	public char getSymbol() {
		return symbol;
	}

	@Override
	public Color getColor() {
		return color;
	}
	
	public void changeDoorState(){
		open = !open;
		ground = open;
		if(open){
			symbol = '/';
		} else {
			symbol = '+';
		}
	}
	
	public boolean getOpenState(){
		return open;
	}

	@Override
	public boolean isGround() {
		return ground;
	}

}
