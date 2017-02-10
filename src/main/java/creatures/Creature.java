package creatures;

import java.awt.Color;

import creatureAIs.CreatureAI;
import world.World;

public class Creature {
	private World world;
	
	private int x;
	public int x() {return x;}
	public void setX(int x) {this.x = x;}
	
	private int y;
	public int y() {return y;}
	public void setY(int y) {this.y = y;}
	
	private char glyph;
	public char glyph() {return glyph;}
	
	private Color color;
	public Color color() {return color;}
	
	private CreatureAI ai;
	public void setCreatureAI(CreatureAI ai){this.ai = ai;}
	public CreatureAI ai(){return this.ai;}

	public Creature(World world, char glyph, Color color){
		this.world = world;
		this.glyph = glyph;
		this.color = color;
	}
	
	public void moveBy(int xDistance, int yDistance){
		int newX = x+xDistance;
		int newY = y+yDistance;
		if(newX >= 0 && newX < world.getWidth() && newY >= 0 && newY < world.getHeight()){
			checkForObstaclesAndReactAccordingly(newX, newY);
		}
	}
	
	public void checkForObstaclesAndReactAccordingly(int newX, int newY){
		Creature other = world.getCreatureAt(newX, newY);
		if(other == null ){
			ai.onEnter(newX, newY, world.getTile(newX, newY));
		} else {
			attack(other);
		}
	}
	
	public void attack(Creature other){
		world.remove(other);
	}
	
	public void update(){
		ai.onUpdate();
	}
}
