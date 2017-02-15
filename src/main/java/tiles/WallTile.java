package tiles;

import java.awt.Color;

import asciiPanel.AsciiPanel;

public class WallTile implements Tile {
	
	private char symbol;
	private Color color;
	private boolean ground;
	private boolean stairs;
	
	public WallTile(){
		this.symbol = '#';
		this.color = AsciiPanel.brightWhite;
		this.ground = false;
		stairs = false;
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

	@Override
	public boolean isStairs() {
		return stairs;
	}

}
