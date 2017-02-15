package tiles;

import java.awt.Color;

import asciiPanel.AsciiPanel;

public class OutOfBoundsTile implements Tile {

	private char symbol;
	private Color color;
	private boolean ground;
	private boolean stairs;
	
	public OutOfBoundsTile(){
		symbol = '#';
		color = AsciiPanel.brightWhite;
		ground = false;
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
	
	public boolean isGround(){
		return ground;
	}


	@Override
	public boolean isStairs() {
		return stairs;
	}

}
