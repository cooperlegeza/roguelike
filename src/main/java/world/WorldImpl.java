package world;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import creatures.Creature;
import tiles.Tile;
import utils.RogueMath;

public class WorldImpl implements World {
	
	private List<Layer> layers;
	RogueMath math;
	private List<Creature> creatures;
	
	public WorldImpl(List<Layer> layers){
		this.layers = layers;
		math = new RogueMath();
		this.creatures = new LinkedList<Creature>();
	}
	
	public WorldImpl(List<Layer> layers, RogueMath math){
		this.math = math;
		this.layers = layers;
		this.creatures = new LinkedList<Creature>();
	}

	@Override
	public Tile getTile(int x, int y, int z) {
		return layers.get(z).getTile(x, y);
	}

	@Override
	public char getGlyph(int x, int y, int z) {
		return layers.get(z).getGlyph(x, y);
	}

	@Override
	public Color getColor(int x, int y, int z) {
		return layers.get(z).getColor(x, y);
	}

	@Override
	public void addAtEmptyLocation(Creature creature) {
		int layer = (int)(math.random()*layers.size());
		creatures.add(creature);
		layers.get(layer).addAtEmptyLocation(creature);
	}

	@Override
	public Creature getCreatureAt(int x, int y, int z) {
		return layers.get(z).getCreatureAt(x, y);
	}

	@Override
	public List<Creature> getCreatures(){
		return this.creatures;
	}

	@Override
	public void setCreatureAt(int x, int y, int z, Creature creature) {
		creature.setZ(z);
		layers.get(z).setCreatureAt(x, y, creature);
		creatures.add(creature);
	}

	@Override
	public Layer getLayer(int z) {
		return layers.get(z);
	}

	@Override
	public void remove(Creature creature){
		creatures.remove(creature);
	}
	
	public void update(){
		List<Creature> toUpdate = new LinkedList<Creature>(creatures);
	    for (Creature creature : toUpdate){
	        creature.update();
	    }
	}
	
	@Override
	public void addCreature(Creature creature){
		creatures.add(creature);
	}
	
	public int getDepth(){
		return layers.size();
	}

}
