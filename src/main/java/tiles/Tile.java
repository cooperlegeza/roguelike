package tiles;

import java.awt.Color;

public interface Tile {
	public char getSymbol();
	public Color getColor();
	public boolean isGround();
	public boolean isStairs();
	public Tile getPartner();
	public int[] getPartnerLoc();
	public boolean blocksVision();
	public boolean isLit();
	public void changeLitState();
}
