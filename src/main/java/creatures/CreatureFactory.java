package creatures;

import java.util.List;

import asciiPanel.AsciiPanel;
import creatureAIs.FungusAI;
import creatureAIs.PlayerAI;
import weapons.Fists;
import world.World;

public class CreatureFactory {
	
	private World world;
	
	public CreatureFactory(World world){
		this.world = world;
	}
	
	public Creature newPlayer(){
		Creature player = new Creature(world, '@', AsciiPanel.red, 100);
		PlayerAI ai = new PlayerAI(player);
		player.setCreatureAI(ai);
		world.addAtEmptyLocation(player, 0);
		Fists fists = new Fists(5, 10);
		player.setBaseWeapon(fists);
		return player;
	}
	
	public Creature newPlayer(List<String> messages){
		Creature player = new Creature(world, '@', AsciiPanel.red, 100);
		PlayerAI ai = new PlayerAI(player, messages);
		player.setCreatureAI(ai);
		world.addAtEmptyLocation(player, 0);
		Fists fists = new Fists(5, 10);
		player.setBaseWeapon(fists);
		return player;
	}
	
	public Creature newFungus(){
		Creature fungus = new Creature(world, 'f', AsciiPanel.green, 10);
		FungusAI ai = new FungusAI(fungus, new CreatureFactory(world));
		fungus.setCreatureAI(ai);
		world.addAtEmptyLocation(fungus);
		return fungus;
	}
	
	public Creature newFungus(int x, int y, int z){
		Creature fungus = new Creature(world, 'f', AsciiPanel.green, 10);
		FungusAI ai = new FungusAI(fungus, new CreatureFactory(world));
		fungus.setCreatureAI(ai);
		world.setCreatureAt(x, y, z, fungus);
		return fungus;
	}

}
