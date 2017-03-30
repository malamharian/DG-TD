package util;

import java.awt.Point;

public class Node {
	
	public int x;
	public int y;
	public int code;
	public Point from;
	
	public Node() {
		x = y = code = 0;
		from = new Point();
	}

	public Node(int x, int y, int code)
	{
		this.x = x;
		this.y = y;
		this.code = code;
		from = null;
	}
}
