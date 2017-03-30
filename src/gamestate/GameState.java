package gamestate;

import java.awt.Graphics2D;

public abstract class GameState {

	protected GameStateManager gsm;
	
	public abstract void init();
	public void update(){}
	public abstract void draw(Graphics2D g);
	public void keyPressed(int k){}
	public void keyReleased(int k){}
	public void mouseMoved(int x, int y){}
	public void leftMouseClicked(int x, int y){}
	public void onExit(){}
}
