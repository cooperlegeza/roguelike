package creatureAIs;

import creatures.Creature;
import creatures.CreatureFactory;
import utils.RogueMath;

public class FungusAI extends CreatureAI {
	private CreatureFactory womb;
	private int spreadCount;
	private RogueMath math;

	public FungusAI(Creature creature, CreatureFactory factory) {
		super(creature);
		this.womb = factory;
		math = new RogueMath();
		spreadCount = 0;
	}
	
	public FungusAI(Creature creature, CreatureFactory factory, RogueMath math) {
		super(creature);
		this.womb = factory;
		this.math = math;
		spreadCount = 0;
	}
	
	public void onUpdate(){
		if(canSpread()){
			spread();
		}
	}
	
	private boolean canSpread(){
		return math.random() <= .01 && spreadCount < 5;
	}
	
	public void spread(){
		int newX = getGoodXLocation();
		int newY = getGoodYLocation();
		if(creature.canEnter(newX, newY)){
			womb.newFungus(newX, newY);
			spreadCount++;
		}
	}
	
	public int getSpreadCount(){
		return this.spreadCount;
	}
	
	public void setSpreadCount(int newSpreadCount){
		this.spreadCount = newSpreadCount;
	}
	
	public int getGoodXLocation(){
		return creature.x() + ((int)(math.random()*11) - 5);
	}
	
	public int getGoodYLocation(){
		return creature.y() + ((int)(math.random()*11) - 5);
	}

}
