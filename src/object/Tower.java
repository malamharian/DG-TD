package object;

import gamestate.GameStateManager;
import gamestate.PlayState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.GameManager;
import main.GamePanel;

import util.ImageContainer;
import util.Vector2d;

public class Tower extends GameObject{

	private int damage, level;
	private long fireRate, nextFire;
	private BufferedImage imgHead, imgBase, imgStar;
	private double radius;
	private ArrayList<Enemy> enemies;
	private Vector2d centerPos, enemyCenterPos, nearestEnemyCenterPos;
	private Enemy target;
	private double angle;
	private Vector2d rotatePoint;
	private boolean shooting;
	public boolean selected;
	private PlayState playState;
	private static GameManager gameManager;
	
	
	public Tower(Vector2d pos, int damage, double fireRate)
	{
		gameManager = GameStateManager.getInstance().getPlayState().getGameManager();
		selected = false;
		nearestEnemyCenterPos = new Vector2d();
		position = pos;
		width = height = PlayState.INGAMETILESIZE;
		centerPos = new Vector2d(position.x+width/2, position.y+height/2);
		rotatePoint = new Vector2d(centerPos.x, centerPos.y);
		this.damage = damage;
		this.fireRate = (long)(fireRate*1000);
		nextFire = 0;
		angle = 0;
		radius = 100;
		playState = GameStateManager.getInstance().getPlayState();
		level = 1;
		
		loadImage();
	}
	
	public double getRadius()
	{
		return radius;
	}
	
	public void loadImage()
	{
		try{
			imgStar = ImageContainer.getInstance().getStarImg();
			imgHead = ImageContainer.getInstance().getTurretHeadImg();
			imgBase = ImageContainer.getInstance().getTurretBaseImg();
		}catch(Exception e){}
	}
	
	public void setDamage(int damage)
	{
		this.damage = damage;
	}
	
	public int getDamage()
	{
		return damage;
	}
	
	public void setFireRate(double fireRate)
	{
		this.fireRate = (long)(fireRate*1000);
	}
	
	public double getFireRate()
	{
		return fireRate;
	}
	
	private void detectNearestTarget()
	{
		target = null;
		enemies = playState.getEnemies();
		double nearestDistance = Double.POSITIVE_INFINITY;
		enemyCenterPos = new Vector2d();
		synchronized (enemies) {
			for(int i = enemies.size()-1; i>=0; i--)
			{
				Enemy enemy = enemies.get(i);
				enemyCenterPos.set(enemy.position.x + enemy.getWidth()/2, enemy.position.y + enemy.getHeight()/2);
				double distance = centerPos.distance(enemyCenterPos);
				
				if(distance < radius && distance < nearestDistance)
				{
					target = enemy;
					nearestDistance = distance;
					nearestEnemyCenterPos = enemyCenterPos.copy();
				}
			}
			if(target != null)
			{
				angle = Math.atan2(nearestEnemyCenterPos.x - centerPos.x, nearestEnemyCenterPos.y - centerPos.y) - Math.PI/2;
			}
		}
	}

	public void shoot()
	{
		detectNearestTarget();
		
		if(target != null && System.currentTimeMillis() > nextFire)
		{
			shooting = true;
			nextFire = System.currentTimeMillis() + fireRate;
		}
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	@Override
	public boolean isOnScreen() {
		// TODO Auto-generated method stub
		int posX = (int)(position.x + PlayState.camX);
		int posY = (int)(position.y + PlayState.camY);
		if(selected)
		{
			if(posX+radius < 0 || posY+radius < 0 || posX-radius > GamePanel.WIDTH || posY-radius > GamePanel.HEIGHT)
				draw = false;
			else
				draw = true;
		}
		else
		{
			if(posX+width < 0 || posY+height < 0 || posX > GamePanel.WIDTH || posY > GamePanel.HEIGHT)
				draw = false;
			else
				draw = true;
		}
		
		
		return draw;
	}
	
	@Override
	public void draw(Graphics2D g) {
		AffineTransform prev = g.getTransform();
		
		g.drawImage(imgBase, (int)(position.x+PlayState.camX), (int)(position.y+PlayState.camY), width, height, null);
		if(shooting && target != null)
		{
			target.setHealth(target.getHealth()-damage);
			if(target.getHealth() <= 0)
			{
				gameManager.killEnemy(target, true);
			}
			g.setColor(Color.blue);
			g.drawLine((int)(centerPos.x+PlayState.camX), (int)(centerPos.y+PlayState.camY), (int)(target.position.x+target.getWidth()/2+PlayState.camX), (int)(target.position.y+target.getHeight()/2+PlayState.camY));
			shooting = false;
		}
		g.rotate(-angle, rotatePoint.x+PlayState.camX, rotatePoint.y+PlayState.camY);
		g.drawImage(imgHead, (int)(position.x+PlayState.camX), (int)(position.y+PlayState.camY), width, height, null);		
		
		g.setTransform(prev);
		for(int i = 0; i<level; i++)
		{
			g.drawImage(imgStar, (int)(position.x+(i*width/5)+PlayState.camX), (int)(position.y+PlayState.camY), width/5, width/5, null);
		}
	}

	@Override
	public void update() {
		shoot();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

}
