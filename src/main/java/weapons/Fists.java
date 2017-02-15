package weapons;

import java.util.LinkedList;
import java.util.List;

import creatures.Creature;
import effects.BashingDamage;
import effects.Effect;

public class Fists implements Weapon {
	
	private List<Effect> effects;
	@Override
	public List<Effect> getEffects() {return effects;}
	public void setEffects(List<Effect> effects){this.effects = effects;}
	
	private String name;
	public String getName(){return name;}
	public void setName(String name){this.name = name;}
	
	public Fists(){
		effects = new LinkedList<Effect>();
		effects.add(new BashingDamage(5, 10));
		name = "Fists";
	}
	
	public Fists(int min, int max){
		effects = new LinkedList<Effect>();
		effects.add(new BashingDamage(min, max));
		name = "Fists";
	}
	
	@Override
	public void applyEffects(Creature other){
		for(Effect effect : effects){
			effect.applyEffects(other);
		}
	}
	
	@Override
	public int[] totalDamage() {
		int totalMin = 0, totalMax = 0;
		for(Effect effect : effects){
			totalMin += effect.getMinDamage();
			totalMax += effect.getMaxDamage();
		}
		int[] totalDamages = {totalMin, totalMax};
		return totalDamages;
	}
}
