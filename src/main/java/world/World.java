package world;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import creatures.Creature;
import tiles.OutOfBoundsTile;
import tiles.Tile;

public class World {
	
	private Tile[][] tiles;
	private int width;
	private int height;
	private List<Creature> creatures;
	
	public World(Tile[][] tiles){
		this.tiles = tiles;
		this.height = tiles[0].length;
		this.width = tiles.length;
		this.creatures = new LinkedList<Creature>();
	}
	
	public int getHeight(){
		return this.height;
	}
	
	public int getWidth(){
		return this.width;
	}
	
	public Tile[][] getTiles(){
		return this.tiles;
	}
	
	public Tile getTile(int x, int y){
		if (x < 0 || x >= this.width || y >= this.width || y < 0){
			return new OutOfBoundsTile();
		} else {
			return tiles[x][y];
		}
		
	}
	
	public char getGlyph(int x, int y){
		return getTile(x, y).getSymbol();
	}
	
	public Color getColor(int x, int y){
		return getTile(x, y).getColor();
	}
	
	public void addAtEmptyLocation(Creature creature){
		int x;
		int y;
		do {
			x = (int)(Math.random() * width);
			y = (int)(Math.random() * height);
		} while (!getTile(x, y).isGround());
		
		setCreatureAt(x, y, creature);
	}
	
	public Creature getCreatureAt(int x, int y){
		for(Creature creature : creatures){
			if(creature.x() == x && creature.y() == y){return creature;}
		}
		return null;
	}
	
	public List<Creature> getCreatures(){
		return this.creatures;
	}
	
	public void setCreatureAt(int x, int y, Creature creature){
		creature.setX(x);
		creature.setY(y);
		creatures.add(creature);
	}
	
	public void remove(Creature creature){
		creatures.remove(creature);
	}
	
	public void update(){
		List<Creature> toUpdate = new LinkedList<Creature>(creatures);
	    for (Creature creature : toUpdate){
	        creature.update();
	    }
	}

}
