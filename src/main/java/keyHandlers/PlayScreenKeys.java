package keyHandlers;

import java.awt.event.KeyEvent;

import creatures.Creature;
import screens.LoseScreen;
import screens.PlayScreen;
import screens.Screen;
import screens.WinScreen;
import tiles.Tile;
import world.World;

public class PlayScreenKeys implements KeysHandler {

	public Screen respondToUserInput(KeyEvent key, Screen screen) {
		Creature player = ((PlayScreen) screen).getPlayer();
		World world = ((PlayScreen) screen).getWorld();
		if(key.getKeyChar() != '>' && key.getKeyChar() != '<'){
			switch(key.getKeyCode()){
				case KeyEvent.VK_LEFT: moveBy(-1, 0, 0, player); return screen;
				case KeyEvent.VK_RIGHT: moveBy(1, 0, 0, player); return screen;
				case KeyEvent.VK_UP: moveBy(0, -1, 0, player); return screen;
				case KeyEvent.VK_DOWN: moveBy(0, 1, 0, player); return screen;
				case KeyEvent.VK_ENTER: return new WinScreen();
				case KeyEvent.VK_ESCAPE: return new LoseScreen();
				default: return screen;
			}
		} else {
			switch(key.getKeyChar()){
				case '>': goDown(world, player); return screen;
				case '<': goUp(world, player); return screen;
				default: return screen;
			}
		}
	}
	
	public void moveBy(int moveX, int moveY, int moveZ, Creature player){
		player.moveBy(moveX, moveY, moveZ);
	}
	
	public void goDown(World world, Creature player){
		Tile tile = world.getTile(player.x(), player.y(), player.z());
		if(tile.getSymbol() == '>'){
			player.useStairs(tile);
		}
	}
	
	public void goUp(World world, Creature player){
		Tile tile = world.getTile(player.x(), player.y(), player.z());
		if(tile.getSymbol() == '<'){
			player.useStairs(tile);
		}
	}

}
