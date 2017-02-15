package world;

import java.awt.Color;
import java.util.List;

import creatures.Creature;
import tiles.Tile;

public interface World {
	
	public Tile getTile(int x, int y, int z);
	public char getGlyph(int x, int y, int z);
	public Color getColor(int x, int y, int z);
	public void addAtEmptyLocation(Creature creature);
	public Creature getCreatureAt(int x, int y, int z);
	public List<Creature> getCreatures();
	public void setCreatureAt(int x, int y, int z, Creature creature);
	public void remove(Creature creature);
	public Layer getLayer(int z);
	public void addCreature(Creature creature);
	public void update();
}
