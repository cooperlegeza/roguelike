package tiles;

import java.awt.Color;

import asciiPanel.AsciiPanel;

public class WallTile implements Tile {
	
	private char symbol;
	private Color color;
	private boolean ground;
	
	public WallTile(){
		this.symbol = '#';
		this.color = AsciiPanel.brightWhite;
		this.ground = false;
	}

	@Override
	public char getSymbol() {
		return this.symbol;
	}

	@Override
	public Color getColor() {
		return this.color;
	}

	@Override
	public boolean isGround() {
		return ground;
	}

}
