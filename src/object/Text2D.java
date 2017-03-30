package object;

import java.awt.Color;
import java.awt.Graphics2D;

public class Text2D extends GameObject{

	private String text;
	private Color fontColor;
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Color getFontColor() {
		return fontColor;
	}

	public void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
	}

	public Text2D(String text, int x, int y) {
		// TODO Auto-generated constructor stub
		this.text = text;
		fontColor = new Color(0, 0, 0);
		position.x = x;
		position.y = y;
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.black);
		g.drawString(text, (int)position.x, (int)position.y);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
	
}
