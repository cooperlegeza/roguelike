package creatures;

import asciiPanel.AsciiPanel;
import creatureAIs.FungusAI;
import creatureAIs.PlayerAI;
import world.World;

public class CreatureFactory {
	
	private World world;
	
	public CreatureFactory(World world){
		this.world = world;
	}
	
	public Creature newPlayer(){
		Creature player = new Creature(world, '@', AsciiPanel.red);
		PlayerAI ai = new PlayerAI(player);
		player.setCreatureAI(ai);
		world.addAtEmptyLocation(player);
		return player;
	}
	
	public Creature newFungus(){
		Creature fungus = new Creature(world, 'f', AsciiPanel.green);
		FungusAI ai = new FungusAI(fungus, new CreatureFactory(world));
		fungus.setCreatureAI(ai);
		world.addAtEmptyLocation(fungus);
		return fungus;
	}

}
