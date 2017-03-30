package object;

import gamestate.PlayState;

import java.awt.Graphics2D;

import main.GamePanel;
import util.Vector2d;

public abstract class GameObject {

	public Vector2d position, velocity;
	protected int width, height;
	protected double angle;
	protected boolean alive, draw;
	
	public GameObject() {
		alive = true;
		draw = true;
		position = new Vector2d(0,0);
		velocity = new Vector2d(0,0);
		width = height = 0;
		angle = 0;
	}
	
	public double getAngle()
	{
		return angle;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public void killObject()
	{
		alive = false;
	}
	
	public boolean isOnScreen()
	{
		int posX = (int)(position.x + PlayState.camX);
		int posY = (int)(position.y + PlayState.camY);
		if(posX+width < 0 || posY+height < 0 || posX > GamePanel.WIDTH || posY > GamePanel.HEIGHT)
			draw = false;
		else
			draw = true;
		
		return draw;
	}
	
	public abstract void init();
	public abstract void draw(Graphics2D g);
	public abstract void update();
}
