package creatureAIs;

import java.util.List;

import creatures.Creature;
import tiles.Tile;

public class PlayerAI extends CreatureAI {
	
	private List<String> messages;
	public List<String> getMessages(){return messages;}
	
	public PlayerAI(Creature creature){
		super(creature);
	}
	
	public PlayerAI(Creature creature, List<String> messages){
		super(creature);
		this.messages = messages;
	}
	
	public void onEnter(int x, int y, int z, Tile tile){
		if(tile.isGround()){
			creature.setX(x);
			creature.setY(y);
			creature.setZ(z);
		}
	}
	
	@Override
	public void onNotify(String message){
		messages.add(message);
	}

}
