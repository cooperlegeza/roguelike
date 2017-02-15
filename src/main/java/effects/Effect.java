package effects;

import creatures.Creature;

public interface Effect {
	
	public void applyEffects(Creature other);
	
	public int getMinDamage();
	
	public int getMaxDamage();

}
