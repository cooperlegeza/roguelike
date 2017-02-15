package weapons;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import creatures.Creature;
import effects.BashingDamage;
import effects.Effect;

public class FistsTest {
	
	Fists fists;
	
	@Before
	public void initialize(){
		fists = new Fists();
	}

	@Test
	public void testEffectsGettersAndSetters() {
		List<Effect> newEffects = new LinkedList<Effect>();
		newEffects.add(new BashingDamage(5, 10));
		fists.setEffects(newEffects);
		assertEquals(newEffects, fists.getEffects());
	}
	
	@Test
	public void testApplyEffectsAppliesEffectsForEachEffect(){
		List<Effect> newEffects = new LinkedList<Effect>();
		newEffects.add(Mockito.mock(BashingDamage.class));
		fists.setEffects(newEffects);
		Creature other = Mockito.mock(Creature.class);
		fists.applyEffects(other);
		for(Effect effect : newEffects){
			verify(effect, times(1)).applyEffects(other);
		}
	}
	
	@Test
	public void testTotalDamages(){
		List<Effect> newEffects = new LinkedList<Effect>();
		newEffects.add(new BashingDamage(10, 20));
		newEffects.add(new BashingDamage(3, 8));
		int totalMin = 13;
		int totalMax = 28;
		fists.setEffects(newEffects);
		int[] damages = fists.totalDamage();
		assertEquals(totalMin, damages[0]);
		assertEquals(totalMax, damages[1]);
	}
}
