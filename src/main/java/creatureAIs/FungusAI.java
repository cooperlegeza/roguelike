package creatureAIs;

import creatures.Creature;
import creatures.CreatureFactory;

public class FungusAI extends CreatureAI {
	private CreatureFactory womb;
	private int spreadCount;
	private Math math;

	public FungusAI(Creature creature, CreatureFactory factory) {
		super(creature);
		this.womb = factory;
		
	}
	
	public FungusAI(Creature creature, CreatureFactory factory, Math math) {
		super(creature);
		this.womb = factory;
		this.math = math;
	}
	
	public void onUpdate(){
		
	}
	
	public void spread(){
		
	}
	
	

}
