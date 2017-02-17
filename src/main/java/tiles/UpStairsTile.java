package tiles;

import java.awt.Color;

import asciiPanel.AsciiPanel;

public class UpStairsTile implements Tile {
	
	private char symbol;
	private Color color;
	private boolean isGround;
	private boolean stairs;
	private Tile partner;
	private int[] partnerLoc;
	private boolean isLit;
	
	public UpStairsTile(){
		symbol = '<';
		color = AsciiPanel.brightYellow;
		isGround = true;
		partnerLoc = new int[3];
		stairs = true;
		isLit = false;
	}

	@Override
	public char getSymbol() {return symbol;}

	@Override
	public Color getColor() {return color;}

	@Override
	public boolean isGround() {return isGround;}
	
	public void setPartner(Tile partner) {this.partner = partner;}
	public Tile getPartner() {return partner;}
	
	public void setPartnerLoc(int x, int y, int z){
		partnerLoc[0] = x;
		partnerLoc[1] = y;
		partnerLoc[2] = z;
	}
	public int[] getPartnerLoc(){return partnerLoc;}

	@Override
	public boolean isStairs() {
		return stairs;
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
