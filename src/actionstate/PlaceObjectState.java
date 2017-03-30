package actionstate;

import java.awt.Point;

import main.GamePanel;
import tilemap.TileMap;
import gamestate.GameState;
import gamestate.PlayState;

public abstract class PlaceObjectState extends GameState{

	protected boolean validPath;
	protected Point prevCursorIndex;
	protected Point cursorIndex, cursor;
	protected TileMap tileMap;
	protected boolean draw;
	protected PlayState playState;
	protected int realX, realY;
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		cursor.x = PlayState.cursor.x;
		cursor.y = PlayState.cursor.y;
	}

	@Override
	public void mouseMoved(int x, int y) {
		// TODO Auto-generated method stub
		realX = x / GamePanel.SCALE;
		realY = y / GamePanel.SCALE;
		cursorIndex.x = (-tileMap.getX() + realX) / PlayState.INGAMETILESIZE;
		cursorIndex.y = (-tileMap.getY() + realY) / PlayState.INGAMETILESIZE;
	}
	
	@Override
	public void onExit() {
		draw = false;
	}
}