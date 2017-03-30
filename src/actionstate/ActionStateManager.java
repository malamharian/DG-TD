package actionstate;

import gamestate.GameState;
import gamestate.PlayState;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class ActionStateManager {
	
	public static final int PUTWALLSTATE = 0;
	public static final int PUTTOWERSTATE = 1;
	public static final int EMPTYSTATE = 2;
	public static final int SELECTTOWERSTATE = 3;
	
	private ArrayList<GameState> actionStates;
	private int currentState;
	
	public ActionStateManager(PlayState playState)
	{
		actionStates = new ArrayList<GameState>();
		currentState = 0;
		
		actionStates.add(new PutWallState(playState));
		actionStates.add(new PutTowerState(playState));
		actionStates.add(new EmptyState());
		actionStates.add(new SelectTowerState());
	}
	
	public GameState getState(int state)
	{
		return actionStates.get(state);
	}
	
	public void setState(int state)
	{
		actionStates.get(currentState).onExit();
		this.currentState = state;
		actionStates.get(currentState).init();
	}
	
	public void update()
	{
		actionStates.get(currentState).update();
	}
	
	public void draw(Graphics2D g)
	{
		actionStates.get(currentState).draw(g);
	}
	
	public void keyPressed(int k)
	{
		actionStates.get(currentState).keyPressed(k);
	}
	
	public void keyReleased(int k)
	{
		actionStates.get(currentState).keyReleased(k);
	}
	
	public void mouseMoved(int x, int y)
	{
		actionStates.get(currentState).mouseMoved(x, y);
	}
	
	public void leftMouseClicked(int x, int y)
	{
		actionStates.get(currentState).leftMouseClicked(x, y);
	}
}
