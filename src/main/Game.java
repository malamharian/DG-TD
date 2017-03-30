package main;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Game {
	
	BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
	Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
		    cursorImg, new Point(0, 0), "blank cursor");
	
	public Game()
	{
		JFrame frame = new JFrame("DG TD");
		frame.setContentPane(new GamePanel());
		frame.getContentPane().setCursor(blankCursor);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIgnoreRepaint(true);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public static void main(String[] args)
	{
		new Game();
	}
}
