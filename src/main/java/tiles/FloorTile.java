package tiles;

import java.awt.Color;

import asciiPanel.AsciiPanel;

public class FloorTile implements Tile {
	
	private char symbol;
	private Color color;
	private boolean ground;
	private boolean stairs;
	
	public FloorTile(){
		symbol = '.';
		color = AsciiPanel.brightWhite;
		ground = true;
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
