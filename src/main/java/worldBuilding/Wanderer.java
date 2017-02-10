package worldBuilding;


import java.util.Arrays;

import tiles.FloorTile;
import tiles.Tile;

public class Wanderer extends LayerMethod {
	
	private int x;
	private int y;
	private int width;
	private int height;
	private Directions[] directions = {Directions.NORTH, Directions.EAST, Directions.SOUTH, Directions.WEST};
	private Directions facing;
	private double wanderChance = 0.45;
	
	public Wanderer(int width, int height){
		x = width/2;
		y = height/2;
		this.width = width;
		this.height = height;
		tiles = new Tile[0][0];
		facing = directions[1];
	}
	
	@Override
	public Tile[][] makeCaves(){
		wander(600);
		return this.tiles;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void sprint(int distance, Directions direction){
		switch (direction) {
			case EAST : sprintedTiles(x, limitSprintedTiles(x, distance, Directions.EAST), Directions.EAST); break;
			case SOUTH : sprintedTiles(y, limitSprintedTiles(y, distance, Directions.SOUTH), Directions.SOUTH); break;
			case NORTH : sprintedTiles(y, limitSprintedTiles(y, distance, Directions.NORTH), Directions.NORTH); break;
			case WEST : sprintedTiles(x, limitSprintedTiles(x, distance, Directions.WEST), Directions.WEST); break;
		}
	}
	
	public int limitSprintedTiles(int start, int distance, Directions direction){
		switch(direction) {
			case EAST : return x+distance < this.width ? distance : this.width-x-1;
			case SOUTH : return y+distance < this.height ? distance : this.height-y-1;
			case NORTH : return y-distance >= 0 ? distance : y;
			case WEST : return x-distance >= 0 ? distance : x;
			default: return 0;
		}
	}
	
	public void setTiles(Tile[][] tiles){
		this.width = tiles.length;
		this.height = tiles[0].length;
		this.tiles = tiles;
	}
	
	public Tile[][] getTiles(){
		return this.tiles;
	}
	
	public Tile getTile(int x, int y){
		return this.tiles[x][y];
	}
	
	public void changeTile(int x, int y, Tile tile){
		tiles[x][y] = tile;
	}
	
	public void sprintedTiles(int startLoc, int distance, Directions direction){
		facing = direction;
		for(int path = 0; path <= distance; path++){
			switch (direction) {
				case EAST : changeTile(startLoc+path, y, new FloorTile()); x = startLoc+path; break; 
				case SOUTH : changeTile(x, startLoc+path, new FloorTile()); y = startLoc+path; break;
				case NORTH : changeTile(x, startLoc-path, new FloorTile()); y = startLoc-path; break;
				case WEST : changeTile(startLoc-path, y, new FloorTile()); x = startLoc-path; break;
			}
		}
	}
	
	public int getWidth(){
		return this.width;
	}
	
	public int getHeight(){
		return this.height;
	}
	
	public void wander(int lifetime) {
		for(int steps = 1; steps < lifetime + 1; steps++){
			if(Math.random() < wanderChance){
				turn();
			}
			if(steps % 100 == 0){makeOpenSpace((int)(Math.random() * 16));};
			if(steps % 150 == 0){createChild(100);}
			if(steps % 50 == 0){createSprinter(30);}
			step(facing);
			changeFacingIfAtEdge();
		}
	}
	
	public void step(Directions direction) {
		sprint(1, direction);
	}
	
	public Directions getFacing(){
		return facing;
	}
	
	public void changeFacingIfAtEdge(){
		if(atAndFacingEdge()){
			turn();
		}
	}
	
	public void makeOpenSpace(int diameter) {
		if(diameter <= 0){
		} else if(diameter == 1){
			changeTile(x, y, new FloorTile());
		} else if(diameter == 2) {
			diameterTwo();
		} else if(diameter % 2 == 1){
			createOpenSpaceOddDiameterGreaterThan1(diameter);
		} else {
			createOpenSpaceEvenDiameterGreaterThan2(diameter);
		}
	}
	
	public void moveTo(int newX, int newY){
		moveToX(newX);
		moveToY(newY);
	}
	
	public void createChild(int lifetime){
		Wanderer child = new Wanderer(width, height);
		child.setTiles(tiles);
		child.wander(100);
	}
	
	public void createSprinter(int steps){
		Wanderer sprinter = new Wanderer(width, height);
		sprinter.setTiles(tiles);
		sprinter.sprint(10, Directions.EAST.randomDirection());
	}
	
	private void createOpenSpaceEvenDiameterGreaterThan2(int diameter){
		makeEvenLine(diameter, x, y);
		if(y+1 < this.height){makeEvenLine(diameter, x, y+1);}
		int yShift = 1;
		for(int newDiameter = diameter - 2; newDiameter > 0; newDiameter -= 2){
			if(y+yShift+1 < this.height){makeEvenLine(newDiameter, x, y + yShift + 1);}
			if(y-yShift >= 0){makeEvenLine(newDiameter, x, y - yShift);}
			yShift++;
		}
	}
	
	private void makeEvenLine(int diameter, int startX, int startY){
		int start = startX -(diameter/2 - 1);
		for(int at = 0; at < diameter; at++){
			if(start+at >= 0 && start+at < this.width){changeTile(start+at, startY, new FloorTile());}
		}
	}
	
	private void createOpenSpaceOddDiameterGreaterThan1(int diameter){
		makeOddLine(diameter, x, y);
		int yShift = 1;
		for(int newDiameter = diameter - 2; newDiameter > 0; newDiameter -= 2){
			if(y+yShift < this.height){makeOddLine(newDiameter, x, y + yShift);}
			if(y-yShift >= 0){makeOddLine(newDiameter, x, y - yShift);}
			yShift++;
		}
	}
	
	private void makeOddLine(int length, int startX, int startY){
		int xShift = 1;
		length--;
		changeTile(startX, startY, new FloorTile());
		for(int lengthLeft = length; lengthLeft > 0; lengthLeft -= 2){
			if(startX+xShift < this.width){changeTile(startX + xShift, startY, new FloorTile());}
			if(startX-xShift >= 0){changeTile(startX - xShift, startY, new FloorTile());}
			xShift++;
		}
	}
	
	private void moveToY(int newY){
		if(newY >= 0 && newY < this.width){
			this.y = newY;
		} else if(newY >= 0){
			this.y = this.height - 1;
		} else if(newY < this.height){
			this.y = 0;
		}
	}
	
	private void moveToX(int newX){
		if(newX >= 0 && newX < this.width){
			this.x = newX;
		} else if(newX >= 0){
			this.x = this.width - 1;
		} else if (newX < this.width){
			this.x = 0;
		}
	}
	
	private boolean atAndFacingEdge(){
		return (x == (width - 1) && facing == Directions.EAST) ||
				(y == 0 && facing == Directions.NORTH) ||
				(y == (height - 1) && facing == Directions.SOUTH) ||
				(x == 0 && facing == Directions.WEST); 
	}
	
	private void turn(){
		int facingIndex = Arrays.asList(directions).indexOf(facing);
		if(Math.random() > 0.5){
			turnLeft(facingIndex);
		} else {
			turnRight(facingIndex);
		}	
	}
	
	private void turnLeft(int index){
		if(index == 0){
			facing = directions[3];
		} else {
			facing = directions[index - 1];
		}
	}
	
	private void turnRight(int index){
		facing = directions[(index + 1) % 4];
	}
	
	private void diameterTwo(){
		for(int modX = -1; modX < 1; modX++){
			for(int modY = -1; modY < 1; modY++){
				if(x+modX >= 0 && y+modY >=0){changeTile(x + modX, y + modY, new FloorTile());}
			}
		}
	}
}
