package rooms;

import java.util.List;
import java.util.Map;

import tiles.Tile;
import utils.Coordinate;

public interface Room {
	
	public Map<Coordinate, List<Tile>> getRoomMap();
	
	public void makeRoom();
	
	public void checkIfRoomCanBeMade();

}
