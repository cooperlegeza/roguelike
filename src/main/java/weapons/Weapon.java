package weapons;

import java.util.List;

import creatures.Creature;
import effects.Effect;

public interface Weapon {
	
	public List<Effect> getEffects();
	
	public String getName();
	
	public void applyEffects(Creature other);
	
	public int[] totalDamage();

}
