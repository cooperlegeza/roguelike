package worldBuilding;

import tiles.FloorTile;
import tiles.Tile;
import tiles.WallTile;

public class Noise extends LayerMethod {
	
	private int numOfTilesInTileAndSurroundingTiles = 9;
	private int width;
	private int height;
	private CavernBuilder builder;
	Tile[][] tiles;
	
	public Noise(CavernBuilder builder){
		this.builder = builder;
		tiles = builder.getTiles();
		this.width = tiles.length;
		this.height = tiles[0].length;
	}
	

	public Noise smooth(int times){
		Tile[][] tiles2 = new Tile[this.width][this.height];
		for(int count = 0; count < times; count++){
			
			for(int x = 0; x < this.width; x++){
				for(int y = 0; y < this.height; y++){
					int walls = checkSurroundingTilesForWalls(x, y);
					int floors = numOfTilesInTileAndSurroundingTiles - walls;
					tiles2[x][y] = floors >= walls ? new FloorTile() : new WallTile();
				}
			}
			tiles = tiles2;
		}
		return this;
	}
	
	public int checkSurroundingTilesForWalls(int x, int y){
		int numOfWalls = 0;
		for(int modX = -1; modX < 2; modX++){
			for(int modY = -1; modY < 2; modY++){
				if(isTileWall(x + modX, y + modY)){
					numOfWalls++;
				}
			}
		}
		return numOfWalls;
	}
	
	public boolean isTileWall(int x, int y){
		boolean isWall = false;
		if(x >= 0 && x < this.width && y >= 0 && y < this.height){
			Tile tile = builder.tiles[x][y];
			isWall = tile.getSymbol() == '#';
		}
		return isWall;
	}
	

	public Noise randomizeAllTiles(){
		for(int x = 0; x < this.width; x++){
			for(int y = 0; y < this.height; y++){
				tiles[x][y] = randomizeTile(x, y);
			}
		}
		
		return this;
	}
	

	public Tile randomizeTile(int x, int y){
		return Math.random() < 0.5 ? new FloorTile() : new WallTile();
	}

	@Override
	public Tile[][] makeCaves() {
		this.randomizeAllTiles().smooth(100);
		return this.tiles;
	}
	
	public void changeTile(int x, int y, Tile newTile) {
		tiles[x][y] = newTile;
	}

}
