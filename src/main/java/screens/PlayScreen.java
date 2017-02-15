package screens;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;

import asciiPanel.AsciiPanel;
import creatures.Creature;
import creatures.CreatureFactory;
import keyHandlers.KeysHandler;
import keyHandlers.PlayScreenKeys;
import world.Layer;
import world.World;
import world.WorldImpl;
import worldBuilding.CavernBuilder;
import worldBuilding.Wanderer;

public class PlayScreen implements Screen {
	
	private String commands = "Press [enter] to win or [escape] to lose.";
	private KeysHandler keys;
	private World world;
	private int worldHeight;
	private int worldWidth;
	private int worldDepth;
    private int centerX;
    private int centerY;
    private int screenWidth;
    private int screenHeight;
    private CavernBuilder cavernBuilder;
    private CreatureFactory factory;
    private Creature player;
    private List<String> messages;
	
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
		setUpNumFields();
		this.keys = new PlayScreenKeys();
		this.cavernBuilder = new CavernBuilder(worldWidth, worldHeight, new Wanderer(worldWidth, worldHeight));
		setUpMiscFields();
	}
	
	private void setUp(KeysHandler keys){
		setUpNumFields();
		this.keys = keys;
		this.cavernBuilder = new CavernBuilder(worldWidth, worldHeight);
		setUpMiscFields();
	}
	
	private void setUp(CavernBuilder builder){
		setUpNumFields();
		this.keys = new PlayScreenKeys();
		this.cavernBuilder = builder;
		setUpMiscFields();
	}
	
	private void setUpMiscFields(){
		createWorld();
        factory = new CreatureFactory(world);
        createCreatures();
	}
	
	private void setUpNumFields(){
		worldHeight = 31;
		worldWidth = 90;
		worldDepth = 10;
		centerX = worldWidth/2;
		centerY = worldHeight/2;
		screenWidth = 80;
        screenHeight = 21;
        messages = new LinkedList<String>();
	}
	
	public void displayOutput(AsciiPanel terminal) {
		int left = getScrollX();
        int top = getScrollY();
        String stats = String.format(" %3d/%3d hp", player.getHP(), player.getMaxHP());
        int[] damages = player.equippedWeapon.totalDamage();
        String weapon = String.format("%s: %d, %d", player.equippedWeapon.getName(), damages[0], damages[1]);
   
        displayTiles(terminal, left, top);

		terminal.writeCenter(commands, 22);
		terminal.write(stats, 1, 23);
		terminal.write(weapon, 10, 23);
		displayCreatures(left, top, terminal);
		displayMessages(terminal, messages);
	}

	public Screen respondToUserInput(KeyEvent key) {
		Screen returnScreen = this.keys.respondToUserInput(key, this);
		world.update();
		return returnScreen;
	}
	
	public void createWorld(){
		List<Layer> layers = new LinkedList<Layer>();
		world = new WorldImpl(layers);
		for(int layerCount = 0; layerCount < this.worldDepth; layerCount++){
			Layer layer = cavernBuilder.build(world);
			layers.add(layer);
			cavernBuilder = new CavernBuilder(worldWidth, worldHeight);
		}
	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public int getScrollX() {return Math.max(0, Math.min(player.x() - screenWidth / 2, getLayerWidth() - screenWidth));}
	private int getLayerWidth(){return world.getLayer(player.z()).getWidth();}
	public int getScrollY() {return Math.max(0, Math.min(player.y() - screenHeight / 2, getLayerHeight() - screenHeight));}
	private int getLayerHeight(){return world.getLayer(player.z()).getHeight();}
	

	public void displayTiles(AsciiPanel terminal, int left, int top) {
		for(int x = 0; x < this.screenWidth; x++){
			for(int y = 0; y < this.screenHeight; y++){
				int writtenX = x + left;
				int writtenY = y + top;
				terminal.write(getLayerGlyphAt(writtenX, writtenY), x, y, 
						getLayerColorAt(writtenX, writtenY));
			}
		}
	}
	
	private char getLayerGlyphAt(int x, int y){return world.getGlyph(x, y, player.z());}
	private Color getLayerColorAt(int x, int y){return world.getColor(x, y, player.z());}

	public void scrollBy(int moveX, int moveY) {
		centerX = Math.max(0, Math.min(centerX + moveX, getLayerWidth() - 1));
		centerY = Math.max(0, Math.min(centerY + moveY, getLayerHeight() - 1));
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
			if(creature.x() >= left && creature.x() < left+screenWidth 
					&& creature.y() >= top && creature.y() < top+screenHeight
					&& creature.z() == player.z()){
				terminal.write(creature.glyph(), creature.x() - left, creature.y() - top, creature.color());
			}
		}
	}
	
	public World getWorld(){
		return world;
	}
	
	public void setWorld(Layer layer){
		this.world = layer.getWorld();
	}
	
	public void setWorld(World world){
		this.world = world;
	}
	
	public void setCreatureFactory(CreatureFactory factory){
		this.factory = factory;
	}
	
	public CreatureFactory getCreatureFactory(){
		return this.factory;
	}
	
	public void createCreatures(){
        player = factory.newPlayer(messages);
        
        for(int fungusCount = 0; fungusCount < 8; fungusCount++){
        	factory.newFungus();
        }
	}
	
	private void displayMessages(AsciiPanel terminal, List<String> messages) {
	    int top = screenHeight - messages.size();
	    for (int i = 0; i < messages.size(); i++){
	        terminal.writeCenter(messages.get(i), top + i);
	    }
	    messages.clear();
	}
}
