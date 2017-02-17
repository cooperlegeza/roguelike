package tiles;

import java.awt.Color;

import asciiPanel.AsciiPanel;

public class WallTile implements Tile {
	
	private char symbol;
	private Color color;
	private boolean ground;
	private boolean stairs;
	private boolean isLit;
	
	public WallTile(){
		this.symbol = '#';
		this.color = AsciiPanel.brightWhite;
		this.ground = false;
		stairs = false;
		isLit = false;
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
		return true;
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
