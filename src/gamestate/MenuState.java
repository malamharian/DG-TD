package gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

import main.GamePanel;

import tilemap.Background;
import util.Vector2d;

public class MenuState extends GameState{

	private final int UP = -1, DOWN = 1;
	private final short CHOICEPLAY=0, CHOICEHELP=1, CHOICEEXIT=2;
	
	private Background bg;
	
	private int currentChoice = 0;
	String[] options = {"Play", "Help", "Exit"};
	
	private Color clrTitle;
	private Font fntTitle;
	
	private Font font;
	
	private String strTitle = "DG TD"; 
	
	public MenuState(GameStateManager gsm)
	{
		this.gsm = gsm;
		
		try {
			bg = new Background("/backgrounds/menubg.png", 3);
			bg.setVelocity(new Vector2d(-0.1,0));
			
			clrTitle = Color.red;
			fntTitle = new Font("Century Gothic", Font.PLAIN, 28);
			font = new Font("Arial", Font.PLAIN, 12);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		bg.update();
	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		bg.draw(g);
		
		g.setColor(clrTitle);
		g.setFont(fntTitle);
		
		{
			FontMetrics fm = g.getFontMetrics();
			Rectangle2D rect = fm.getStringBounds(strTitle, g);
			g.drawString(strTitle, GamePanel.WIDTH/2-(int)rect.getWidth()/2,
					GamePanel.HEIGHT/2-(int)rect.getHeight()/2);
		}
				
		
		//draw menu options
		g.setFont(font);
		for(int i = 0; i<options.length; i++)
		{
			if(i == currentChoice)
			{
				g.setColor(Color.red);
			}
			else
			{
				g.setColor(Color.black);
			}
			FontMetrics fm = g.getFontMetrics();
			Rectangle2D rect = fm.getStringBounds(options[i], g);
			g.drawString(options[i], GamePanel.WIDTH/2-(int)rect.getWidth()/2, 
					GamePanel.HEIGHT/2 + (i+1)*20);
		}
	}

	private void select()
	{
		if(currentChoice == CHOICEPLAY)
		{
			gsm.setState(GameStateManager.PLAYSTATE);
		}
		else if(currentChoice == CHOICEHELP)
		{
			
		}
		else if(currentChoice == CHOICEEXIT)
		{
			System.exit(0);
		}
	}
	
	public void moveChoice(int m)
	{
		currentChoice += m;
		if(currentChoice < 0)
			currentChoice = 0;
		else if(currentChoice > options.length-1)
			currentChoice = options.length-1;
	}
	
	@Override
	public void keyPressed(int k) {
		// TODO Auto-generated method stub
		switch(k)
		{
		case KeyEvent.VK_ENTER:
			select();
			break;
		case KeyEvent.VK_UP:
			moveChoice(this.UP);
			break;
		case KeyEvent.VK_DOWN:
			moveChoice(this.DOWN);
			break;
		}
	}

	@Override
	public void keyReleased(int k) {
		return;
	}
}
