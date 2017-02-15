package tiles;

import java.awt.Color;

import asciiPanel.AsciiPanel;

public class DoorTile implements Tile {
	
	private char symbol;
	private Color color;
	private boolean open;
	private boolean ground;
	private boolean stairs;
	
	public DoorTile(){
		symbol = '+';
		color = AsciiPanel.red;
		open = false;
		ground = open;
		stairs = false;
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

	@Override
	public boolean isStairs() {
		return stairs;
	}

}
