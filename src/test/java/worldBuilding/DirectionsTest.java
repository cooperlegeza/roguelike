package worldBuilding;

import static org.junit.Assert.*;

import org.junit.Test;

public class DirectionsTest {
	
	Directions[] directions = {Directions.NORTH, Directions.EAST, Directions.SOUTH, Directions.WEST};
	int[] mods = {0, 1, 2, 3};
	String[] strings = {"north", "east", "south", "west"};
	
	
	

	@Test
	public void testToString() {
		for(int index = 0; index < 4; index++){
			assertEquals(directions[index].toString(), strings[index]);
		}
	}
	
	@Test
	public void testMod(){
		for(int index = 0; index < 4; index++){
			assertEquals(directions[index].mod(), mods[index]);
		}
	}
}
