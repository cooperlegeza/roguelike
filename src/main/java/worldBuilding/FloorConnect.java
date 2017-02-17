package worldBuilding;

import tiles.DownStairsTile;
import tiles.Tile;
import tiles.UpStairsTile;
import utils.Coordinate;
import utils.RogueMath;
import world.World;

public class FloorConnect {
	
	World world;
	RogueMath math;
	
	public FloorConnect(World world){
		this.world = world;
		math = new RogueMath();
	}
	
	public FloorConnect(World world, RogueMath math){
		this.world = world;
		this.math = math;
	}
	
	public Coordinate findEmptySpot(int layer){
		int x, y;
		Tile tile;
		do{
			x = (int)(math.random() * world.getLayer(layer).getWidth());
			y = (int)(math.random() * world.getLayer(layer).getHeight());
			tile = world.getTile(x, y, layer);
		} while (isNotStairsAndIsGround(tile));
		Coordinate emptyPoint = new Coordinate(x, y, layer);
		return emptyPoint;
	}
	
	private boolean isNotStairsAndIsGround(Tile tile){
		return (tile.isStairs() || !tile.isGround());
	}
	
	public void connectToLayerBelow(int layer){
		Coordinate topFloor = findEmptySpot(layer);
		Coordinate nextFloor = findEmptySpot(layer + 1);
		
		DownStairsTile down = new DownStairsTile();
		UpStairsTile up = new UpStairsTile();
		setUpAndDownStairsTogether(up, down, topFloor, nextFloor);
		world.setTileAt(topFloor.x, topFloor.y, topFloor.z, down);
		world.setTileAt(nextFloor.x, nextFloor.y, nextFloor.z, up);
	}
	
	public void setUpAndDownStairsTogether(UpStairsTile up, DownStairsTile down, Coordinate topFloor, Coordinate nextFloor){
		up.setPartner(down);
		down.setPartner(up);
		up.setPartnerLoc(topFloor.x, topFloor.y, topFloor.z);
		down.setPartnerLoc(nextFloor.x, nextFloor.y, nextFloor.z);
	}
	
	public void connectAllLayers(){
		int depth = world.getDepth();
		for(int layer = 0; layer < depth - 1; layer++){
			connectToLayerBelow(layer);
		}
	}

}
