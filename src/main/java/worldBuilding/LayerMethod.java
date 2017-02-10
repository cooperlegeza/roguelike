package worldBuilding;

import tiles.Tile;

public class LayerMethod {
	
	Tile[][] tiles = new Tile[0][0];
	
	public LayerMethod(){
	}
	
	public Tile[][] makeCaves(){
		return this.tiles;
	}

	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}

}
