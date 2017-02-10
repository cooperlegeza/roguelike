package tiles;

import java.awt.Color;

import asciiPanel.AsciiPanel;

public class FloorTile implements Tile {
	
	private char symbol;
	private Color color;
	private boolean ground;
	
	public FloorTile(){
		symbol = '.';
		color = AsciiPanel.brightWhite;
		ground = true;
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

}
