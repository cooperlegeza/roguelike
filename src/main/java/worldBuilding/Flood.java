package worldBuilding;

import tiles.FloorTile;
import tiles.Tile;

public class Flood extends LayerMethod {
	
	private int width;
	private int height;
	private int area;
	private Tile[][] tiles;
	

	public Flood(){
	}
	
	public void changeTile(int x, int y, Tile newTile) {
		tiles[x][y] = newTile;
		this.width = tiles.length;
		this.height = tiles[0].length;
		this.area = this.width * this.height;
	}
	
	@Override
	public Tile[][] makeCaves(){
		this.flood();
		return tiles;
	}
	
	public void flood(){
		this.changeTile(width/2, height/2, new FloorTile());
		int max = 2*(area/3);
		int count = 1;
		while(count < max){
			count += erode();
		}
	}

	public int erode() {
		int numOfFloorsMade = 0;
		for(int x = 0; x < this.width; x++){
			for(int y = 0; y < this.height; y++){
				numOfFloorsMade += this.erodeWall(x, y);
			}
		}
		
		return numOfFloorsMade;
	}

	public int erodeWall(int x, int y) {
		int numOfFloorsMade = 0;
		int[] orthogonal = {-1, 1};
		
		for(int modX : orthogonal){
			for(int modY : orthogonal){
				numOfFloorsMade += changeWall(x+modX, y+modY);
			}
		}
		
		return numOfFloorsMade;
	}
	
	public int changeWall(int x, int y){
		if(x >= 0 && x < this.width && y >= 0 && y < this.height){
			if(Math.random() < 0.01){
				tiles[x][y] = new FloorTile();
				return 1;
			}
		}
		return 0;
	}

	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}
	
	public int onlyOrthogonal(){
		return 1;
		
	}
}
