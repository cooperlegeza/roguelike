package worldBuilding;

public enum Directions {
	NORTH(0, "north"), EAST(1, "east"), SOUTH(2, "south"), WEST(3, "west");

	private int modifier;
	private String string; 
	
	private Directions(int modifier, String string) {
		this.modifier = modifier;
		this.string = string;
	}
	
	@Override
	public String toString(){
		return this.string;
	}
	
	public int mod(){
		return this.modifier;
	}
	
	public Directions randomDirection(){
		int chance = (int)(Math.random() * 4);
		if(chance == 0){
			return NORTH;
		} else if (chance == 1){
			return EAST;
		} else if (chance == 2){
			return SOUTH;
		} else {
			return WEST;
		}
	}
}
