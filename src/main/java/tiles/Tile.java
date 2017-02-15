package tiles;

import java.awt.Color;

public interface Tile {
	public char getSymbol();
	public Color getColor();
	public boolean isGround();
	public boolean isStairs();
}
