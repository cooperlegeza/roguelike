package rooms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tiles.Tile;
import utils.Coordinate;

public class BasicRoom implements Room {
	
	private Map<Coordinate, List<Tile>> roomMap;
	
	public BasicRoom(){
		roomMap = new HashMap<Coordinate, List<Tile>>();
	}

	@Override
	public Map<Coordinate, List<Tile>> getRoomMap() {
		return roomMap;
	}

	@Override
	public void makeRoom() {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkIfRoomCanBeMade() {
		// TODO Auto-generated method stub

	}

}
