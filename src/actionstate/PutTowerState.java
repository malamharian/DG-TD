package actionstate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import object.Tower;

import tilemap.Tile;
import util.PlayerStatus;
import util.Vector2d;

import gamestate.PlayState;

public class PutTowerState extends PlaceObjectState{
	
	private int towerPrice;
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();
		super.mouseMoved(cursor.x, cursor.y);
		mouseMoved(cursor.x, cursor.y);
	}
	
	public PutTowerState(PlayState playState) {
		draw = false;
		validPath = false;
		prevCursorIndex = new Point(0,0);
		cursorIndex = new Point(0,0);
		cursor = new Point(0,0);
		towerPrice = 100;
		this.playState = playState;
		this.tileMap = playState.getTileMap();
	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		g.setColor(Color.blue);
		if(draw)
		{
			g.fillOval(cursorIndex.x*PlayState.INGAMETILESIZE+tileMap.getX(), cursorIndex.y*PlayState.INGAMETILESIZE+tileMap.getY(), PlayState.INGAMETILESIZE, PlayState.INGAMETILESIZE);
		}
	}
	
	@Override
	public void mouseMoved(int x, int y) {
		// TODO Auto-generated method stub
		super.mouseMoved(x, y);
		
		if(cursorIndex.x >= tileMap.getNumCols() || cursorIndex.y >= tileMap.getNumRows() || cursorIndex.x < 0 || cursorIndex.y < 0)
			return;
		if(tileMap.getTile(cursorIndex.x, cursorIndex.y) == Tile.NORMAL || tileMap.isOccupied(cursorIndex.x, cursorIndex.y))
		{
			cursorIndex.x = prevCursorIndex.x;
			cursorIndex.y = prevCursorIndex.y;			
		}
		else
			draw = true;
		
		if(cursorIndex.x != prevCursorIndex.x || cursorIndex.y != prevCursorIndex.y)
		{
			prevCursorIndex.x = cursorIndex.x;
			prevCursorIndex.y = cursorIndex.y; 
		}	
		cursor.x = realX;
		cursor.y = realY;
	}
	
	@Override
	public void leftMouseClicked(int x, int y) {
		PlayerStatus pStatus = playState.getPlayerStatus();
		if(draw && pStatus.getMoney() >= towerPrice)
		{
			pStatus.setMoney(pStatus.getMoney() - towerPrice);
			Vector2d newTowerPos = new Vector2d(cursorIndex.x*PlayState.INGAMETILESIZE, cursorIndex.y*PlayState.INGAMETILESIZE);
			playState.addTower(new Tower(newTowerPos, 1, 1));
			tileMap.setOccupied(cursorIndex.x, cursorIndex.y);
			draw = false;
		}
	}
}
