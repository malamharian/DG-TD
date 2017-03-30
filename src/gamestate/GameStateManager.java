package gamestate;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class GameStateManager {
	
	private static GameStateManager gsm;
	
	public static GameStateManager getInstance()
	{
		if(gsm == null)
			gsm = new GameStateManager();
		return gsm;
	}
	
	public static final int MENUSTATE = 0;
	public static final int PLAYSTATE = 1;
	
	private MenuState menuState;
	private PlayState playState;
	
	private ArrayList<GameState> gameStates;
	private int currentState;
	
	public GameStateManager()
	{
		gameStates = new ArrayList<GameState>();
		
		currentState = MENUSTATE;
		
		menuState = new MenuState(this);
		playState = new PlayState(this);
		
		gameStates.add(menuState);
		gameStates.add(playState);
	}
	
	public int getCurrentState()
	{
		return currentState;
	}
	
	public PlayState getPlayState()
	{
		return playState;
	}
	
	public GameState getState(int state)
	{
		return gameStates.get(state);
	}
	
	public void setState(int state)
	{
		gameStates.get(currentState).onExit();
		currentState = state;
		gameStates.get(currentState).init();
	}
	
	public void update()
	{
		gameStates.get(currentState).update();
	}
	
	public void draw(Graphics2D g)
	{
		gameStates.get(currentState).draw(g);
	}
	
	public void keyPressed(int k)
	{
		gameStates.get(currentState).keyPressed(k);
	}
	
	public void keyReleased(int k)
	{
		gameStates.get(currentState).keyReleased(k);
	}
	
	public void mouseMoved(int x, int y)
	{
		gameStates.get(currentState).mouseMoved(x, y);
	}
	
	public void leftMouseClicked(int x, int y)
	{
		gameStates.get(currentState).leftMouseClicked(x, y);
	}
}
