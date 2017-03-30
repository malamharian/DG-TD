package gamestate;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import main.GameManager;
import main.GamePanel;
import object.Enemy;
import object.GameObject;
import object.GuiText;
import object.Tower;
import actionstate.ActionStateManager;
import tilemap.TileMap;
import util.PlayerStatus;

public class PlayState extends GameState{
	
	public static final int INGAMETILESIZE = 50;
	public static Point cursor;
	public static double camX, camY;
	
	private TileMap tileMap;
	private boolean moveLeft, moveRight, moveDown, moveUp;
	private ActionStateManager asm;
	private PlayerStatus pStatus;
	private ArrayList<GameObject> gameObjects;
	private ArrayList<GuiText> guiTexts;
	private ArrayList<Enemy> enemies;
	private ArrayList<Tower> towers;
	private GameManager gameManager;
	
	public PlayState(GameStateManager gsm)
	{
		towers = new ArrayList<Tower>();
		cursor = new Point();
		this.gsm = gsm;
		guiTexts = new ArrayList<GuiText>();
		gameObjects = new ArrayList<GameObject>();
		enemies = new ArrayList<Enemy>();
		moveLeft = moveRight = moveDown = moveUp = false;
		tileMap = new TileMap(100, INGAMETILESIZE);
		tileMap.loadTiles("/tiles/tilesnew.png");
		tileMap.setPosition(100, 0);
		pStatus = new PlayerStatus(this);
		asm = new ActionStateManager(this);
		gameManager = new GameManager(10, 2000);
		
	}

	public ArrayList<Tower> getTowers()
	{
		return towers;
	}
	
	public void addTower(Tower tower)
	{
		towers.add(tower);
		gameObjects.add(tower);
	}
	
	public void addEnemy(Enemy enemy)
	{
		enemies.add(enemy);
		gameObjects.add(enemy);
	}
	
	public void addGuiText(GuiText text)
	{
		synchronized (guiTexts) {
			guiTexts.add(text);
		}
	}
	
	public void removeGuiText(GuiText text)
	{
		synchronized (guiTexts) {
			guiTexts.remove(text);
		}
	}
	
	public void addObject(GameObject obj)
	{
		synchronized (gameObjects) 
		{
			gameObjects.add(obj);
			obj.init();
		}		
	}
	
	public void disableAction()
	{
		asm.setState(ActionStateManager.EMPTYSTATE);
	}
	
	public void removeObject(int index)
	{
		synchronized (gameObjects) {
			gameObjects.remove(index);
		}
	}
	
	public void removeObject(GameObject obj)
	{
		synchronized (gameObjects) {
			gameObjects.remove(obj);
		}
	}
	
	public ArrayList<Enemy> getEnemies()
	{
		return enemies;
	}
	
	public void removeEnemy(Enemy enemy)
	{
		gameObjects.remove(enemy);
		enemies.remove(enemy);
	}
	
	private void clearObjects()
	{
		enemies.clear();
		gameObjects.clear();
		towers.clear();
		guiTexts.clear();
	}
	
	@Override
	public void init() {
		asm.setState(ActionStateManager.SELECTTOWERSTATE);
		
		clearObjects();
		camX = camY = 0;
		
		pStatus.init();
		gameManager.init();
		pStatus.setLife(10);
		pStatus.setMoney(1000);
		pStatus.setScore(0);
		pStatus.setLevel(1);
		tileMap.loadMap("/maps/map1.txt");
		
		gameManager.startWaveTimer();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		for(int i = gameObjects.size()-1; i>=0; i--)
		{
			gameObjects.get(i).update();
		}
		if(moveUp)
			tileMap.setPosition(tileMap.getX(), tileMap.getY()+20);
		if(moveDown)
			tileMap.setPosition(tileMap.getX(), tileMap.getY()-20);
		if(moveRight)
			tileMap.setPosition(tileMap.getX()-20, tileMap.getY());
		if(moveLeft)
			tileMap.setPosition(tileMap.getX()+20, tileMap.getY());
		camX = tileMap.getX();
		camY = tileMap.getY();
		asm.update();
	}

	public void drawCursor(Graphics2D g, Color color)
	{
		g.setStroke(new BasicStroke(3));
		g.setColor(color);
		g.drawOval(cursor.x/GamePanel.SCALE-5, cursor.y/GamePanel.SCALE-5, 10, 10);
	}
	
	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		g.setColor(Color.white);
		g.fillRect(0, 0, GamePanel.SCALED_WIDTH, GamePanel.SCALED_HEIGHT);
		
		tileMap.draw(g);
		asm.draw(g);
		GameObject temp;
		for(int i = gameObjects.size()-1; i>= 0; i--)
		{
			temp = gameObjects.get(i);
			if(temp.isOnScreen())
				temp.draw(g);
		}
		
		g.setFont(new Font("Arial", Font.BOLD, 15));
		g.setColor(Color.black);
		for(int i = guiTexts.size()-1; i>=0; i--)
			guiTexts.get(i).draw(g);
		
		drawCursor(g, Color.magenta);
	}

	public GameManager getGameManager()
	{
		return gameManager;
	}
	
	public TileMap getTileMap()
	{
		return tileMap;
	}
	
	public PlayerStatus getPlayerStatus()
	{
		return pStatus;
	}
	
	public void keyAction(int k, boolean pressed)
	{
		switch(k)
		{
		case KeyEvent.VK_D:
			moveRight = pressed;
			break;
		case KeyEvent.VK_A:
			moveLeft = pressed;
			break;
		case KeyEvent.VK_S:
			moveDown = pressed;
			break;
		case KeyEvent.VK_W:
			moveUp = pressed;
			break;		
		}
	}
	
	@Override
	public void keyPressed(int k) {
		// TODO Auto-generated method stub
		asm.keyPressed(k);
		keyAction(k, true);
		switch(k)
		{
		case KeyEvent.VK_ESCAPE:
			gsm.setState(GameStateManager.MENUSTATE);
			break;
		case KeyEvent.VK_1:
			if(!gameManager.isFighting())
				asm.setState(ActionStateManager.PUTWALLSTATE);
			break;
		case KeyEvent.VK_2:
			if(!gameManager.isFighting())
				asm.setState(ActionStateManager.PUTTOWERSTATE);
			break;
		case KeyEvent.VK_3:
			asm.setState(ActionStateManager.SELECTTOWERSTATE);
			break;
		}
	}

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub
		keyAction(k, false);
	}
	
	@Override
	public void mouseMoved(int x, int y) {
		// TODO Auto-generated method stub
		asm.mouseMoved(x, y);
		cursor.x = x;
		cursor.y = y;
	}

	@Override
	public void leftMouseClicked(int x, int y) {
		// TODO Auto-generated method stub
		asm.leftMouseClicked(x, y);
	}
	
	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		gameManager.stopAllTimer();
	}
}
