package tiles;

import java.awt.Color;

import asciiPanel.AsciiPanel;

public class UpStairsTile implements Tile {
	
	private char symbol;
	private Color color;
	private boolean isGround;
	private Tile partner;
	
	public UpStairsTile(){
		symbol = '<';
		color = AsciiPanel.brightYellow;
		isGround = true;
	}

	@Override
	public char getSymbol() {return symbol;}

	@Override
	public Color getColor() {return color;}

	@Override
	public boolean isGround() {return isGround;}
	
	public void setPartner(Tile partner) {this.partner = partner;}
	public Tile getPartner() {return partner;}

}
