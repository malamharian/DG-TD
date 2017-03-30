package main;

import gamestate.GameStateManager;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener{
	
	public static final int WIDTH = 400;
	public static final int HEIGHT = 320;
	public static final int SCALE = 2;
	public static final int SCALED_WIDTH = WIDTH*SCALE;
	public static final int SCALED_HEIGHT = HEIGHT*SCALE;
	
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long interval = 1000/FPS;
	
	private BufferedImage canvas;
	private Graphics2D g;
	
	private GameStateManager gsm;
	
	public void init()
	{
		canvas = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) canvas.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		running = true;
	}
	
	public GamePanel() {
		super();
		gsm = GameStateManager.getInstance();
		MouseAdapter ma = new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent arg0) {
				// TODO Auto-generated method stub
				super.mouseMoved(arg0);
				gsm.mouseMoved(arg0.getX(), arg0.getY());
			}
			public void mouseDragged(MouseEvent arg0)
			{
				super.mouseMoved(arg0);
				gsm.mouseMoved(arg0.getX(), arg0.getY());
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				super.mouseClicked(arg0);
				if(arg0.getButton() == MouseEvent.BUTTON1)
					gsm.leftMouseClicked(arg0.getX(), arg0.getY());
			}
		};
		addMouseMotionListener(ma);
		addMouseListener(ma);
		addKeyListener(this);
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		setFocusable(true);
		requestFocus();
	}
	
	@Override
	public void addNotify() {
		// TODO Auto-generated method stub
		super.addNotify();
		if(thread == null)
		{
			thread = new Thread(this);
			
			thread.start();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		init();
		
		long start;
		long elapsed;
		long wait;
		
		while(running)
		{
			start = System.nanoTime();
			
			update();
			draw();
			drawToScreen();
			
			elapsed = System.nanoTime()-start;
			
			wait = interval - elapsed/1000000;
			if(wait > 0)
			{
				try {
					Thread.sleep(wait);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void update()
	{
		gsm.update();
	}
	
	private void draw()
	{
		gsm.draw(g);
	}
	
	private void drawToScreen()
	{
		Graphics g2 = this.getGraphics();
		g2.drawImage(canvas,0,0,SCALED_WIDTH,SCALED_HEIGHT,null);
		g2.dispose();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		gsm.keyPressed(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		gsm.keyReleased(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
