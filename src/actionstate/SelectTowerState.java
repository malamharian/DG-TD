package actionstate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import main.GamePanel;

import object.Tower;
import util.PlayerStatus;

import gamestate.GameState;
import gamestate.GameStateManager;
import gamestate.PlayState;

public class SelectTowerState extends GameState{

	Tower selectedTower;
	ArrayList<Tower> towers;
	private int upgradePrice;
	
	public SelectTowerState() {
		upgradePrice = 300;
	}
	
	@Override
	public void init() {
		selectedTower = null;
	}

	@Override
	public void draw(Graphics2D g) {
		if(selectedTower != null && selectedTower.selected)
		{
			g.setColor(new Color(0,0,255,100));
			g.fillOval((int)(selectedTower.position.x + selectedTower.getWidth()/2 + PlayState.camX - selectedTower.getRadius()), 
					(int)(selectedTower.position.y + selectedTower.getHeight()/2 + PlayState.camY - selectedTower.getRadius()), 
					(int)(selectedTower.getRadius()*2), (int)(selectedTower.getRadius()*2));
		}
	}
	
	@Override
	public void keyPressed(int k) {
		// TODO Auto-generated method stub
		if(k == KeyEvent.VK_SPACE && selectedTower != null && selectedTower.getLevel() < 5)
		{
			PlayerStatus playerStatus = GameStateManager.getInstance().getPlayState().getPlayerStatus();
			if(playerStatus.getMoney() >= upgradePrice)
			{
				selectedTower.setDamage(selectedTower.getDamage()+1);
				selectedTower.setRadius(selectedTower.getRadius()+20);
				selectedTower.setLevel(selectedTower.getLevel()+1);
				playerStatus.setMoney(playerStatus.getMoney()-upgradePrice);
			}
		}
	}
	
	@Override
	public void leftMouseClicked(int x, int y) {
		// TODO Auto-generated method stub
		towers = GameStateManager.getInstance().getPlayState().getTowers();
		Tower currTower;
		int realX = x/GamePanel.SCALE - (int)PlayState.camX;
		int realY = y/GamePanel.SCALE - (int)PlayState.camY;
		for(int i = towers.size()-1; i>=0; i--)
		{
			currTower = towers.get(i);
			if(realX > currTower.position.x && realX < currTower.position.x +currTower.getWidth() &&
					realY > currTower.position.y && realY < currTower.position.y+currTower.getHeight())
			{
				if(selectedTower != null)
					selectedTower.selected = false;
				selectedTower = currTower;
				currTower.selected = true;
				break;
			}
		}
	}
	
	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		if(selectedTower != null)
		{
			selectedTower.selected = false;
		}
	}
}
