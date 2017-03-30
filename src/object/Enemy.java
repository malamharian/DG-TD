package object;

import gamestate.GameStateManager;
import gamestate.PlayState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Stack;

import main.GameManager;
import util.ImageContainer;
import util.Vector2d;

public class Enemy extends GameObject{
	
	private double health, maxHealth;
	public boolean dead;
	private BufferedImage model;
	private Stack<Point> shortestPath;
	private double tolerance;
	private Point nextPoint;
	private Vector2d nextVector, direction;
	private GameManager gameManager;
	private double healthPercentage;
	private double widthDouble; 
	
	public Enemy(int x, int y, Stack<Point> shortestPath, int health) {
		dead = false;
		this.maxHealth = health;
		tolerance = 2;
		model = ImageContainer.getInstance().getEnemyImg();
		this.shortestPath = (Stack<Point>)shortestPath.clone();
		this.gameManager = GameStateManager.getInstance().getPlayState().getGameManager();
		position.x = x;
		position.y = y;
		width = height =PlayState.INGAMETILESIZE;
		widthDouble = width;
		setHealth(maxHealth);
	}
	
	public void setHealth(double health)
	{
		healthPercentage = health/maxHealth;
		this.health = health;
	}
	
	public double getHealth()
	{
		return health;
	}
	
	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		int realPosX = (int)(position.x + PlayState.camX);
		int realPosY = (int)(position.y + PlayState.camY);
		g.setColor(Color.white);
		g.fillRect(realPosX, realPosY, width, height/6);
		g.setColor(Color.red);
		g.fillRect(realPosX, realPosY, (int)(widthDouble*healthPercentage), height/6);
		g.drawImage(model, realPosX, realPosY, width, height, null);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(!shortestPath.isEmpty())
			nextPoint = shortestPath.peek();
		else
		{
			gameManager.killEnemy(this, false);
			GameStateManager.getInstance().getPlayState().removeEnemy(this);
			return;
		}
		nextVector = new Vector2d(nextPoint.x * PlayState.INGAMETILESIZE, nextPoint.y * PlayState.INGAMETILESIZE);
		direction = Vector2d.sub(nextVector, position);
		if(direction.mag() < tolerance)
		{
			position = nextVector;
			shortestPath.pop();
		}
		else
		{
			direction.normalize();
			position.add(direction);
		}
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
}
