package worldBuilding;

import tiles.Tile;
import tiles.WallTile;
import world.World;

public class CavernBuilder {
	
	private int width;
	private int height;
	protected Tile[][] tiles;
	private LayerMethod method;
	
	public CavernBuilder(int width, int height){
		this.width = width;
		this.height = height;
		this.tiles = new Tile[this.width][this.height];
		this.method = new Flood();
		this.wallUp();
		method.setTiles(tiles);
	}
	
	public CavernBuilder(int width, int height, LayerMethod method){
		this.width = width;
		this.height = height;
		this.tiles = new Tile[this.width][this.height];
		this.method = method;
		this.wallUp();
		this.method.setTiles(tiles);
	}
	
	public Tile[][] getTiles(){
		return this.tiles;
	}
	
	public World build(){
		tiles = method.makeCaves();
		return new World(tiles);
	}

	public void wallUp() {
		for(int x = 0; x < this.width; x++){
			for(int y = 0; y < this.height; y++){
				tiles[x][y] = new WallTile();
			}
		}
	}

	public void changeTile(int x, int y, Tile newTile) {
		tiles[x][y] = newTile;
	}

	public void setTiles(Tile[][] tiles){
		this.tiles = tiles;
	}
	
}
