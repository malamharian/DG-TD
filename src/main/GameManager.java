package main;

import gamestate.GameStateManager;
import gamestate.PlayState;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import object.Enemy;
import object.GuiText;
import tilemap.Tile;
import tilemap.TileMap;
import util.Node;
import util.PlayerStatus;

public class GameManager {
	
	private boolean fighting;
	private int timeBetweenWaves;
	private long timeBetweenEnemies;
	private int enemyCount;
	private int enemySpawned;
	private int killedEnemy;
	private int currentEnemyHealth;
	
	private GuiText txtTimer, txtEnemiesLeft;
	private int currentSeconds;
	private Timer waveTimer, spawnTimer;
	private Stack<Point> shortestPath;
	private PlayState playState;
	
	public boolean isFighting()
	{
		return fighting;
	}
	
	public void stopAllTimer()
	{
		if(spawnTimer != null)
		{
			spawnTimer.cancel();
			spawnTimer.purge();
		}
		if(waveTimer != null)	
		{
			waveTimer.cancel();
			waveTimer.purge();
		}
	}
	
	public Stack<Point> getShortestPath()
	{
		return shortestPath;
	}
	
	public void generateShortestPath()
	{
		TileMap tileMap = playState.getTileMap();
		int maxRows = tileMap.getNumRows();
		int maxCols = tileMap.getNumCols();
		Node map[][] = new Node[maxRows][maxCols];
		for(int i = 0; i<maxRows; i++)
		{
			for(int j = 0; j<maxCols; j++)
			{
				map[i][j] = new Node(j,i, tileMap.getTile(j,i));
			}
		}
		
		Point startPoint = tileMap.getStartPoint();
		Point endPoint = tileMap.getEndPoint();
		
		ArrayList<Point> points = new ArrayList<Point>();
		points.add(new Point(startPoint.x, startPoint.y));
		map[startPoint.y][startPoint.x].from = new Point(-1,-1);
		
		while(!points.isEmpty())
		{
			Point curr = points.get(0);
			
			if(curr.x == endPoint.x && curr.y == endPoint.y)
			{
				shortestPath.clear();
				Point fromPoint = map[endPoint.y][endPoint.x].from;
				shortestPath.push(endPoint);
				
				while(fromPoint.x >= 0 && fromPoint.y >= 0)
				{
					shortestPath.push(fromPoint);
					fromPoint = map[fromPoint.y][fromPoint.x].from;
				}
				shortestPath.pop();
				return;
			}
			
			if((curr.x + curr.y) % 2 == 0)
			{
				
				if(curr.x+1 < maxCols)
				{
					Point next = new Point(curr.x+1, curr.y);
					
					if(map[next.y][next.x].code == Tile.NORMAL && map[next.y][next.x].from == null)
					{
						points.add(next);
						map[next.y][next.x].from = curr;
					}
				}
				if(curr.x-1 >= 0)
				{
					Point next = new Point(curr.x-1, curr.y);
					
					if(map[next.y][next.x].code == Tile.NORMAL && map[next.y][next.x].from == null)
					{
						points.add(next);
						map[next.y][next.x].from = curr;
					}
				}
				if(curr.y+1 < maxRows)
				{
					Point next = new Point(curr.x, curr.y+1);
					
					if(map[next.y][next.x].code == Tile.NORMAL && map[next.y][next.x].from == null)
					{
						points.add(next);
						map[next.y][next.x].from = curr;
					}
				}
				if(curr.y-1 >= 0)
				{
					Point next = new Point(curr.x, curr.y-1);
					
					if(map[next.y][next.x].code == Tile.NORMAL && map[next.y][next.x].from == null)
					{
						points.add(next);
						map[next.y][next.x].from = curr;
					}
				}
			}
			else
			{
				if(curr.y+1 < maxRows)
				{
					Point next = new Point(curr.x, curr.y+1);
					
					if(map[next.y][next.x].code == Tile.NORMAL && map[next.y][next.x].from == null)
					{
						points.add(next);
						map[next.y][next.x].from = curr;
					}
				}
				if(curr.y-1 >= 0)
				{
					Point next = new Point(curr.x, curr.y-1);
					
					if(map[next.y][next.x].code == Tile.NORMAL && map[next.y][next.x].from == null)
					{
						points.add(next);
						map[next.y][next.x].from = curr;
					}
				}
				if(curr.x+1 < maxCols)
				{
					Point next = new Point(curr.x+1, curr.y);
					
					if(map[next.y][next.x].code == Tile.NORMAL && map[next.y][next.x].from == null)
					{
						points.add(next);
						map[next.y][next.x].from = curr;
					}
				}
				if(curr.x-1 >= 0)
				{
					Point next = new Point(curr.x-1, curr.y);
					
					if(map[next.y][next.x].code == Tile.NORMAL && map[next.y][next.x].from == null)
					{
						points.add(next);
						map[next.y][next.x].from = curr;
					}
				}				
			}
			points.remove(0);
		}
		
		shortestPath.clear();
		
	}
	
	public void killEnemy(Enemy enemy, boolean playerKilled)
	{
		if(enemy.dead)
			return;
		enemy.dead = true;
		PlayerStatus pStatus = playState.getPlayerStatus();
		if(playerKilled)
		{
			pStatus.setMoney(pStatus.getMoney()+200);
			pStatus.setScore(pStatus.getScore()+100);
		}
		else
			pStatus.setLife(pStatus.getLife()-1);
		killedEnemy++;
		txtEnemiesLeft.setText("Enemies Left: " + (enemySpawned-killedEnemy));
		playState.removeEnemy(enemy);
		if(killedEnemy >= enemySpawned)
		{
			endWave();
		}		
	}
	
	public void startSpawnEnemies()
	{
		GameStateManager.getInstance().getPlayState().disableAction();
		spawnTimer = new Timer();
		playState.addGuiText(txtEnemiesLeft);
		spawnTimer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				
				enemyCount++;
				playState.addEnemy(new Enemy(0, 0, shortestPath, currentEnemyHealth));
				txtEnemiesLeft.setText("Enemies Left: " + (enemySpawned-killedEnemy));
				if(enemyCount >= enemySpawned)
				{
					spawnTimer.cancel();
					spawnTimer.purge();
				}
			}
		}, 0, timeBetweenEnemies);
	}
	
	public void endWave()
	{
		if(playState.getPlayerStatus().getLife() <= 0)
		{
			GameStateManager.getInstance().setState(GameStateManager.MENUSTATE);
		}
		
		playState.removeGuiText(txtEnemiesLeft);
		playState.getPlayerStatus().setLevel(playState.getPlayerStatus().getLevel()+1);
		fighting = false;
		enemySpawned += 1;
		currentEnemyHealth += 5;
		startWaveTimer();
	}
	
	public void startWaveTimer()
	{
		killedEnemy = enemyCount = 0;
		currentSeconds = timeBetweenWaves;
		playState.addGuiText(txtTimer);
		waveTimer = new Timer();
		waveTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				synchronized (this) {
					currentSeconds--;
					txtTimer.setText("Next Wave in " + currentSeconds + " seconds");
					if(currentSeconds <= 0)
					{
						playState.removeGuiText(txtTimer);
						fighting = true;
						waveTimer.cancel();
						waveTimer.purge();
						generateShortestPath();
						startSpawnEnemies();
					}
				}
			}
		}, 0, 1000);
	}
	
	public void init()
	{
		currentEnemyHealth = 10;
		killedEnemy = enemyCount = 0;
		fighting = false;
		this.playState = GameStateManager.getInstance().getPlayState();
		enemySpawned = 2;
		currentEnemyHealth = 10;
	}
	
	public GameManager(int timeBetweenWaves, long timeBetweenEnemies) {	
		this.timeBetweenEnemies = timeBetweenEnemies;
		this.timeBetweenWaves = timeBetweenWaves;
		fighting = false;
		txtTimer = new GuiText("", 100, 100, 12);
		txtEnemiesLeft = new GuiText("", 100, 30, 12);
		txtTimer.hCentered = true;
		txtEnemiesLeft.hCentered = true;
		shortestPath = new Stack<Point>();
	}
}
