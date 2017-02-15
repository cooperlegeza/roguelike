package effects;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import creatures.Creature;
import utils.RogueMath;
import world.Layer;

@RunWith(MockitoJUnitRunner.class)
public class BashingDamageTest {
	
	BashingDamage damage;
	int minDamage;
	int maxDamage;
	RogueMath math;
	
	@Mock Creature creature;
	@Mock Layer layer;
	
	@Before
	public void initialize(){
		minDamage = 3;
		maxDamage = 10;
		math = Mockito.spy(new RogueMath());
		damage = new BashingDamage(minDamage, maxDamage, math);
	}

	@Test
	public void testMinDamageGettersAndSetters() {
		int newMinDamage = 5;
		damage.setMinDamage(newMinDamage);
		assertEquals(newMinDamage, damage.getMinDamage());
	}
	
	@Test
	public void testMaxDamageGettersAndSetters(){
		int newMaxDamage = 20;
		damage.setMaxDamage(newMaxDamage);
		assertEquals(newMaxDamage, damage.getMaxDamage());
	}
	
	@Test
	public void testApplyEffectsCallsCreaturesModifyHPMax(){
		when(math.random()).thenReturn(.999999);
		int maxDamage = 10;
		damage.applyEffects(creature);
		verify(creature, times(1)).modifyHP(-maxDamage);
	}
	
	@Test
	public void testApplyEffectsCallCreaturesModifyHPMin(){
		when(math.random()).thenReturn(0.0);
		int minDamage = 3;
		damage.applyEffects(creature);
		verify(creature, times(1)).modifyHP(-minDamage);
	}

}
