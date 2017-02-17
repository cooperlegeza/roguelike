package tiles;

import java.awt.Color;

import asciiPanel.AsciiPanel;

public class FloorTile implements Tile {
	
	private char symbol;
	private Color color;
	private boolean ground;
	private boolean stairs;
	private boolean isLit;
	
	public FloorTile(){
		symbol = '.';
		color = AsciiPanel.brightWhite;
		ground = true;
		stairs = false;
		isLit = false;
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
	
	@Override
	public Tile getPartner(){
		return null;
	}
	
	@Override
	public int[] getPartnerLoc(){
		return null;
	}

	@Override
	public boolean blocksVision() {
		return false;
	}

	@Override
	public boolean isLit() {
		return isLit;
	}

	@Override
	public void changeLitState() {
		isLit = !isLit;
	}

}
