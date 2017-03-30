package tilemap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.GamePanel;

import util.Vector2d;

public class Background {

	private BufferedImage image;
	
	private Vector2d loc;
	private Vector2d vel;
	
	private double moveScale;
	
	public Background(String s, double ms)
	{
		loc = new Vector2d();
		vel = new Vector2d();
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(s));
			moveScale = ms;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setPosition(Vector2d vec)
	{
		loc.x = vec.x*moveScale % GamePanel.WIDTH;
		loc.y = vec.y*moveScale % GamePanel.HEIGHT;
	}
	
	public void setVelocity(Vector2d vec)
	{
		vel.x = vec.x;
		vel.y = vel.y;
		
		vel.mult(moveScale);
	}
	
	public void update()
	{
		loc.add(vel);
		if(loc.x < -GamePanel.WIDTH)
			loc.x = 0;
	}
	
	public void draw(Graphics2D g)
	{
		
		g.drawImage(image, (int)loc.x, (int)loc.y, GamePanel.WIDTH, GamePanel.HEIGHT,  null);
		g.drawImage(image, (int)loc.x+GamePanel.WIDTH, (int)loc.y, GamePanel.WIDTH, GamePanel.HEIGHT, null);
	}
}
