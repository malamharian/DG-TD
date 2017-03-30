package object;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import main.GamePanel;

public class GuiText {

	private String text;
	public boolean hCentered;
	private int x, y;
	private int size;
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setText(String text)
	{
		this.text = text;
	}
	
	public String getText()
	{
		return text;
	}
	
	public GuiText()
	{
		text = "";
		x = y = 40;
		size = 12;
	}
	
	public GuiText(String text, int x, int y, int size) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.size = size;
		hCentered = false;
	}
	
	public void draw(Graphics2D g)
	{
		if(hCentered)
		{
			FontMetrics fm = g.getFontMetrics();
			Rectangle2D rect = fm.getStringBounds(text, g);
			g.drawString(text, GamePanel.WIDTH/2-(int)rect.getWidth()/2, 
					y);
		}
		else
			g.drawString(text, x, y);
	}
}
