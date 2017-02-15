package creatures;

import java.awt.Color;

import creatureAIs.CreatureAI;
import utils.Grammar;
import weapons.Weapon;
import world.World;

public class Creature {
	private World world;
	
	private int x;
	public int x() {return x;}
	public void setX(int x) {this.x = x;}
	
	private int y;
	public int y() {return y;}
	public void setY(int y) {this.y = y;}
	
	private int z;
	public int z() {return z;}
	public void setZ(int z){this.z = z;}
	
	private char glyph;
	public char glyph() {return glyph;}
	
	private Color color;
	public Color color() {return color;}
	
	private CreatureAI ai;
	public void setCreatureAI(CreatureAI ai){this.ai = ai;}
	public CreatureAI ai(){return this.ai;}
	
	private int maxHP;
	public void setMaxHP(int newMax){this.maxHP = newMax;}
	public int getMaxHP(){return this.maxHP;}
	
	private int hp;
	public void setHP(int hp){this.hp = hp;}
	public int getHP(){return this.hp;}
	
	public Weapon equippedWeapon;
	
	private Weapon baseWeapon;
	public void setBaseWeapon(Weapon weapon){
		this.baseWeapon = weapon;
		this.equippedWeapon = this.baseWeapon;
	}
	
	public Weapon getBaseWeapon(){return this.baseWeapon;}
	
	private int damageReduction;
	public void setDamageReduction(int newDamageReduction){this.damageReduction = newDamageReduction;}
	public int getDamageReduction(){return this.damageReduction;}

	public Creature(World world, char glyph, Color color, int maxHP){
		this.world = world;
		this.glyph = glyph;
		this.color = color;
		this.maxHP = maxHP;
		this.hp = maxHP;
	}
	
	public Creature(World layer, char glyph, Color color, int maxHP, int baseDamageReduction){
		this.world = layer;
		this.glyph = glyph;
		this.color = color;
		this.maxHP = maxHP;
		this.hp = maxHP;
		this.damageReduction = baseDamageReduction;
	}
	
	public void moveBy(int xDistance, int yDistance){
		int newX = x+xDistance;
		int newY = y+yDistance;
		if(newX >= 0 && newX < world.getLayer(z).getWidth() && newY >= 0 && newY < world.getLayer(z).getHeight()){
			checkForObstaclesAndReactAccordingly(newX, newY);
		}
	}
	
	public void checkForObstaclesAndReactAccordingly(int newX, int newY){
		Creature other = world.getLayer(z).getCreatureAt(newX, newY);
		if(other == null){
			ai.onEnter(newX, newY, world.getLayer(z).getTile(newX, newY));
		} else {
			attack(other);
		}
	}
	
	public void attack(Creature other){
		doAction("attack the '%s'", other.glyph);
		equippedWeapon.applyEffects(other);
	}
	
	public void update(){
		ai.onUpdate();
	}
	
	public boolean canEnter(int x, int y){
		return (x >= 0 && x < world.getLayer(z).getWidth() && y >= 0 && y < world.getLayer(z).getHeight())
				&& world.getCreatureAt(x, y, z) == null && world.getLayer(z).getTile(x, y).isGround();
	}
	
	public void modifyHP(int amount){
		this.hp += amount;
		
		if(this.hp < 1){
			doAction("die");
			world.remove(this);
		}
	}
	
	public void notify(String message){
		ai.onNotify(message);
	}
	
	public void notify(String message, Object[] params){
		ai.onNotify(String.format(message, params));
	}
	
	public void doAction(String message, Object ... params){
        int r = 9;
        for (int ox = -r; ox < r+1; ox++){
         for (int oy = -r; oy < r+1; oy++){
             if (ox*ox + oy*oy > r*r)
                 continue;
         
             Creature other = world.getLayer(z).getCreatureAt(x+ox, y+oy);
         
             if (other == null)
                 continue;
         
             if (other == this)
                 other.notify("You " + message + ".", params);
             else
                 other.notify(String.format("The '%s' %s.", glyph, Grammar.makeSecondPerson(message)), params);
         }
    }
}
}
