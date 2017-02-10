package creatures;

import asciiPanel.AsciiPanel;
import creatureAIs.PlayerAI;
import world.World;

public class CreatureFactory {
	
	private World world;
	
	public CreatureFactory(World world){
		this.world = world;
	}
	
	public Creature newPlayer(){
		Creature player = new Creature(world, '@', AsciiPanel.green);
		PlayerAI ai = new PlayerAI(player);
		player.setCreatureAI(ai);
		world.addAtEmptyLocation(player);
		return player;
	}

}
