package effects;

import creatures.Creature;
import utils.RogueMath;

public class BashingDamage implements Effect {
	
	RogueMath math;
	
	private int minDamage;
	public void setMinDamage(int newMin){this.minDamage = newMin;}
	public int getMinDamage(){return this.minDamage;}
	
	private int maxDamage;
	public void setMaxDamage(int newMax){this.maxDamage = newMax;};
	public int getMaxDamage(){return this.maxDamage;}
	
	public BashingDamage(int minDamage, int maxDamage){
		this.minDamage = minDamage;
		this.maxDamage = maxDamage;
		this.math = new RogueMath();
	}
	
	public BashingDamage(int minDamage, int maxDamage, RogueMath math){
		this.minDamage = minDamage;
		this.maxDamage = maxDamage;
		this.math = math;
	}

	@Override
	public void applyEffects(Creature other) {
		int damage = (int)(math.random()*(maxDamage-minDamage+1)) + minDamage;
		other.modifyHP(damage);
	}
	
	
}
