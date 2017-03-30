package actionstate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import tilemap.Tile;
import util.PlayerStatus;
import gamestate.PlayState;

public class PutWallState extends PlaceObjectState{
	
	private int wallPrice;
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();
		super.mouseMoved(cursor.x, cursor.y);
		mouseMoved(cursor.x, cursor.y);
	}
	
	public PutWallState(PlayState playState)
	{
		draw = false;
		validPath = false;
		prevCursorIndex = new Point(0,0);
		cursorIndex = new Point(0,0);
		wallPrice = 50;
		cursor = new Point(0,0);
		this.playState = playState;
		this.tileMap = playState.getTileMap();
	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub		
		if(draw)
		{
			if(validPath)		
				g.setColor(Color.blue);
			else
				g.setColor(Color.red);
			g.fillRect(cursorIndex.x*PlayState.INGAMETILESIZE+tileMap.getX(), cursorIndex.y*PlayState.INGAMETILESIZE+tileMap.getY(), PlayState.INGAMETILESIZE, PlayState.INGAMETILESIZE);
		}
	}
	
	@Override
	public void mouseMoved(int x, int y) {
		// TODO Auto-generated method stub
		super.mouseMoved(x, y);
		if(cursorIndex.x >= tileMap.getNumCols() || cursorIndex.y >= tileMap.getNumRows() || cursorIndex.x < 0 || cursorIndex.y < 0)
			return;
		if(tileMap.getTile(cursorIndex.x, cursorIndex.y) == Tile.BLOCKED)
		{
			cursorIndex.x = prevCursorIndex.x;
			cursorIndex.y = prevCursorIndex.y;
		}
		else
			draw = true;
		
		cursor.x = realX;
		cursor.y = realY;
		
		if(prevCursorIndex.x != cursorIndex.x || prevCursorIndex.y != cursorIndex.y)
		{
			prevCursorIndex.x = cursorIndex.x;
			prevCursorIndex.y = cursorIndex.y;
			validPath = tileMap.existsExitpath(cursorIndex.x, cursorIndex.y);
		}
	}

	@Override
	public void leftMouseClicked(int x, int y) {
		// TODO Auto-generated method stub
		PlayerStatus pStatus = playState.getPlayerStatus();
		if(validPath && draw && pStatus.getMoney() >= wallPrice)
		{
			pStatus.setMoney(pStatus.getMoney() - wallPrice);
			tileMap.placeWall(cursorIndex.x, cursorIndex.y);
			validPath = false;
			draw = false;
		}
	}	
}
