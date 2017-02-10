package creatureAIs;

import creatures.Creature;
import tiles.Tile;

public class PlayerAI extends CreatureAI {
	
	public PlayerAI(Creature creature){
		super(creature);
	}
	
	public void onEnter(int x, int y, Tile tile){
		if(tile.isGround()){
			creature.setX(x);
			creature.setY(y);
		}
	}

}
