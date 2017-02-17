package tiles;

import java.awt.Color;

import asciiPanel.AsciiPanel;

public class DoorTile implements Tile {
	
	private char symbol;
	private Color color;
	private boolean open;
	private boolean ground;
	private boolean stairs;
	private boolean isLit;
	
	public DoorTile(){
		symbol = '+';
		color = AsciiPanel.red;
		open = false;
		ground = open;
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
		return !open;
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
