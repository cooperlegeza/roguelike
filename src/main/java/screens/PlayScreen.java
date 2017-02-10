package screens;

import java.awt.event.KeyEvent;
import java.util.List;

import asciiPanel.AsciiPanel;
import creatures.Creature;
import creatures.CreatureFactory;
import keyHandlers.KeysHandler;
import keyHandlers.PlayScreenKeys;
import world.World;
import worldBuilding.CavernBuilder;
import worldBuilding.Wanderer;

public class PlayScreen implements Screen {
	
	private String commands = "Press [enter] to win or [escape] to lose.";
	private KeysHandler keys;
	private World world;
	private int worldHeight;
	private int worldWidth;
    private int centerX;
    private int centerY;
    private int screenWidth;
    private int screenHeight;
    private CavernBuilder cavernBuilder;
    private CreatureFactory factory;
    private Creature player;
	
	public PlayScreen(){
		setUp();
	}
	
	public PlayScreen(CavernBuilder builder){
		setUp(builder);
	}
	
	public PlayScreen(KeysHandler keys){
		setUp(keys);
	}
	
	private void setUp(){
		this.keys = new PlayScreenKeys();
		setUpNums();
		this.cavernBuilder = new CavernBuilder(worldWidth, worldHeight, new Wanderer(worldWidth, worldHeight));
        createWorld();
        factory = new CreatureFactory(world);
        player = factory.newPlayer();
	}
	
	private void setUp(KeysHandler keys){
		this.keys = keys;
		setUpNums();
		this.cavernBuilder = new CavernBuilder(worldWidth, worldHeight);
		createWorld();
        factory = new CreatureFactory(world);
        player = factory.newPlayer();
	}
	
	private void setUp(CavernBuilder builder){
		this.cavernBuilder = builder;
		this.keys = new PlayScreenKeys();
		
		setUpNums();
		
		this.cavernBuilder = builder;
		createWorld();
        factory = new CreatureFactory(world);
        player = factory.newPlayer();
	}
	
	private void setUpNums(){
		worldHeight = 31;
		worldWidth = 90;
		centerX = worldWidth/2;
		centerY = worldHeight/2;
		screenWidth = 80;
        screenHeight = 21;
	}
	
	public void displayOutput(AsciiPanel terminal) {
		int left = getScrollX();
        int top = getScrollY();
   
        displayTiles(terminal, left, top);

		terminal.writeCenter(commands, 22);
		displayCreatures(left, top, terminal);
	}

	public Screen respondToUserInput(KeyEvent key) {
		Screen returnScreen = this.keys.respondToUserInput(key, this);
		return returnScreen;
	}
	
	public void createWorld(){
		world = cavernBuilder.build();
	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public int getScrollX() {return Math.max(0, Math.min(player.x() - screenWidth / 2, world.getWidth() - screenWidth));}
	public int getScrollY() {return Math.max(0, Math.min(player.y() - screenHeight / 2, world.getHeight() - screenHeight));}

	public void displayTiles(AsciiPanel terminal, int left, int top) {
		for(int x = 0; x < this.screenWidth; x++){
			for(int y = 0; y < this.screenHeight; y++){
				int writtenX = x + left;
				int writtenY = y + top;
				terminal.write(world.getGlyph(writtenX, writtenY), x, y, world.getColor(writtenX, writtenY));
			}
		}
		
	}

	public void scrollBy(int moveX, int moveY) {
		centerX = Math.max(0, Math.min(centerX + moveX, world.getWidth() - 1));
		centerY = Math.max(0, Math.min(centerY + moveY, world.getHeight() - 1));
	}
	
	public void moveBy(int moveX, int moveY){
		player.moveBy(moveX, moveY);
	}
	
	public Creature getPlayer(){
		return this.player;
	}
	
	public void setPlayer(Creature newPlayer){
		this.player = newPlayer;
	}
	
	public void displayCreatures(int left, int top, AsciiPanel terminal){
		List<Creature> creatures = world.getCreatures();
		for(Creature creature : creatures){
			if(creature.x() >= left && creature.x() < left+worldWidth 
					&& creature.y() >= top && creature.y() < top+worldHeight){
				terminal.write(creature.glyph(), creature.x() - left, creature.y() - top, creature.color());
			}
		}
	}
	
	public World getWorld(){
		return world;
	}
	
	public void setWorld(World world){
		this.world = world;
	}
}
