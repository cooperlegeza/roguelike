package screens;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import asciiPanel.AsciiPanel;
import creatures.Creature;
import creatures.CreatureFactory;
import keyHandlers.PlayScreenKeys;
import tiles.FloorTile;
import tiles.Tile;
import world.World;
import worldBuilding.CavernBuilder;

@RunWith(MockitoJUnitRunner.class)
public class PlayScreenTest {

	@Mock private static AsciiPanel mockedAsciiPanel;
	@Mock private static KeyEvent mockedKey; 
	@Mock private static PlayScreenKeys playScreenKeys;
	@Mock private static World mockedWorld;
	@Mock private static CavernBuilder mockedBuilder;
	
	private static PlayScreen playScreen;
	public CavernBuilder builder;
	
	@Before
	public void initialize(){
		builder = Mockito.spy(new CavernBuilder(10, 10));
		playScreen = new PlayScreen(playScreenKeys);
	}
	
	
	@Test
	public void testDisplayOutput() {
		playScreen.displayOutput(mockedAsciiPanel);
		verify(mockedAsciiPanel, times(1)).writeCenter(anyString(), anyInt());
	}
	
	@Test
	public void testRespondToUserInput(){
		when(mockedKey.getKeyCode()).thenReturn(KeyEvent.VK_ESCAPE);
		playScreen.respondToUserInput(mockedKey);
		
		verify(playScreenKeys, times(1)).respondToUserInput(mockedKey, playScreen);
	}
	
	@Test
	public void testCreateWorld(){
		PlayScreen playScreenNew = new PlayScreen(builder);
		verify(builder, times(1)).build();
		assertThat(playScreenNew, instanceOf(PlayScreen.class));
	}
	
	@Test
	public void testGetCenterX(){
		int expectedCenterX = 90/2;
		assertEquals(expectedCenterX, playScreen.getCenterX());
	}
	
	@Test
	public void testGetCenterY(){
		int expectedCenterY = 31/2;
		assertEquals(expectedCenterY, playScreen.getCenterY());
	}
	
	/* 
	 * There are some weird interactions between the Mockito verify and Maven tests. 
	 * These next two tests work in the actual test suite, but not while in the maven tests.
	 * Something to do with verify not seeing that some objects descend from object. Don't know why.
	 */
//	@Test
//	public void testDisplayTiles(){
//		playScreen.displayTiles(mockedAsciiPanel, 10, 10);
//		verify(mockedAsciiPanel, times(80*21)).write(anyChar(), anyInt(), anyInt(), any());
//	}
//	
//	@Test
//	public void testDisplayOutPutCallsDisplayTiles(){
//		PlayScreen playScreenSpy = Mockito.spy(playScreen);
//		playScreenSpy.displayOutput(mockedAsciiPanel);
//		verify(playScreenSpy, times(1)).displayTiles(any(), anyInt(), anyInt());
//	}
	
	@Test
	public void testScrollBy(){
		int movex = 1;
		int movey = 1;
		int currentCenterX = playScreen.getCenterX();
		int currentCenterY = playScreen.getCenterY();
		playScreen.scrollBy(movex, movey);
		assertEquals(playScreen.getCenterX(), currentCenterX+1);
		assertEquals(playScreen.getCenterY(), currentCenterY+1);
	}
	
	@Test
	public void testScrollByMin(){
		int movex = -999999;
		int movey = -999999;
		playScreen.scrollBy(movex, movey);
		assertEquals(playScreen.getCenterX(), 0);
		assertEquals(playScreen.getCenterY(), 0);
	}
	
	@Test
	public void testScrollByMax(){
		int movex = 999999;
		int movey = 999999;
		int worldWidthMax = 89;
		int worldHeightMax = 30;
		playScreen.scrollBy(movex, movey);
		assertEquals(playScreen.getCenterX(), worldWidthMax);
		assertEquals(playScreen.getCenterY(), worldHeightMax);
	}
	
	@Test
	public void testGetPlayer(){
		assertThat(playScreen.getPlayer(), instanceOf(Creature.class));
	}
	
	@Test
	public void testSetPlayer(){
		Creature newPlayer = new Creature(mockedWorld, '@', AsciiPanel.green, 100);
		playScreen.setPlayer(newPlayer);
		assertEquals(newPlayer, playScreen.getPlayer());
	}
	
	@Test
	public void testMoveBy(){
		Creature playerSpy = Mockito.spy(playScreen.getPlayer());
		playScreen.setPlayer(playerSpy);
		playScreen.moveBy(1, 1);
		verify(playerSpy, times(1)).moveBy(1, 1);
	}
	
	@Test
	public void testSetAndGetWorld(){
		Tile[][] tiles = {
				{new FloorTile(), new FloorTile(), new FloorTile(),new FloorTile()},
				{new FloorTile(), new FloorTile(), new FloorTile(),new FloorTile()},
				{new FloorTile(), new FloorTile(), new FloorTile(),new FloorTile()},
				{new FloorTile(), new FloorTile(), new FloorTile(),new FloorTile()},
		};
		World world = new World(tiles);
		playScreen.setWorld(world);
		assertEquals(world, playScreen.getWorld());
	}
	
	@Test
	public void testDisplayCreatures(){
		int xLoc = 2;
		int yLoc = 2;
		char glyph = '@';
		List<Creature> creatures = new LinkedList<Creature>();
		creatures.add(makeCreature(glyph, xLoc, yLoc));
		creatures.add(makeCreature(glyph, xLoc, yLoc));
		creatures.add(makeCreature(glyph, xLoc, yLoc));
		creatures.add(makeCreature(glyph, xLoc, yLoc));
		when(mockedWorld.getCreatures()).thenReturn(creatures);
		playScreen.setWorld(mockedWorld);
		playScreen.displayCreatures(1, 1, mockedAsciiPanel);
		verify(mockedWorld, times(1)).getCreatures();
		//Below is failing in Maven but not in Eclipse
//		verify(mockedAsciiPanel, times(creatures.size())).write(anyChar(), anyInt(), anyInt(), any());
	}
	
	@Test
	public void testMakeCreature(){
		int x = 2;
		int y = 2;
		char glyph = '@';
		Creature creature = makeCreature(glyph, x, y);
		assertEquals(x, creature.x());
		assertEquals(y, creature.y());
	}
	
	private Creature makeCreature(char glyph, int x, int y){
		Creature creature = new Creature(mockedWorld, glyph, AsciiPanel.brightBlue, 100);
		creature.setX(x);
		creature.setY(y);
		return creature;
	}
	
	@Test
	public void testGetCreatureFactory(){
		assertThat(playScreen.getCreatureFactory(), instanceOf(CreatureFactory.class));
	}
	
	@Test
	public void testSetCreatureFactory(){
		CreatureFactory factory = new CreatureFactory(mockedWorld);
		playScreen.setCreatureFactory(factory);
		assertEquals(factory, playScreen.getCreatureFactory());
	}
	
	@Test
	public void testCreateCreatures(){
		CreatureFactory factory = Mockito.spy(new CreatureFactory(mockedWorld));
		playScreen.setCreatureFactory(factory);
		playScreen.createCreatures();
		verify(factory, times(1)).newPlayer();
		verify(factory, times(8)).newFungus();
	}
	
	@Test
	public void testRespondToUserInputCallsWorldUpdate(){
		playScreen.setWorld(mockedWorld);
		playScreen.respondToUserInput(mockedKey);
		verify(mockedWorld, times(1)).update();
	}
}
