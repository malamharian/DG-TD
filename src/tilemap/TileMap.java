package tilemap;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import util.Vector2d;

public class TileMap {
	
	private Vector2d location;
	
	private int xmin, xmax, ymin, ymax;
	private Point startPoint, endPoint;
	
	private double tween;
	
	private int[][] map;
	private boolean[][] isOccupied;
	private int tileSize;
	private int numRows;
	private int numCols;
	private int width;
	private int height;
	
	private BufferedImage tileSet;
	private int numTilesAcross;
	private int numTilesVertical;
	private Tile[][] tiles;
	
	private int rowOffset;
	private int colOffset;
	private int numRowsToDraw;
	private int numColsToDraw;
	private int inGameTileSize;
	
	public TileMap(int tileSize, int inGameTileSize) {
		startPoint = new Point(0,0);
		location = new Vector2d();
		this.inGameTileSize = inGameTileSize;
		this.tileSize = tileSize;
		numRowsToDraw = GamePanel.HEIGHT/inGameTileSize + 2;
		numColsToDraw = GamePanel.WIDTH/inGameTileSize + 2;
		tween = 0.1;
	}
	
	public void loadTiles(String s)
	{
		try {
			tileSet = ImageIO.read(getClass().getResourceAsStream(s));
			numTilesAcross = tileSet.getWidth() / tileSize;
			numTilesVertical = tileSet.getHeight() / tileSize;
			tiles = new Tile[numTilesVertical][numTilesAcross];
			
			BufferedImage subImage;
			for(int i = 0; i<numTilesVertical; i++)
			{
				for(int j = 0; j<numTilesAcross; j++)
				{
					subImage = tileSet.getSubimage(j*tileSize, i*tileSize, tileSize, tileSize);
					tiles[i][j] = new Tile(subImage, Tile.NORMAL);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadMap(String s)
	{
		try {
			
			InputStream is = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			numRows = Integer.parseInt(br.readLine());
			numCols = Integer.parseInt(br.readLine());
			
			endPoint = new Point(numRows-1, numCols-1);
			
			xmax = ymax = 0;
			xmin = -(numRows * inGameTileSize - GamePanel.WIDTH);
			ymin = -(numCols * inGameTileSize - GamePanel.HEIGHT);
			
			
			map = new int[numRows][numCols];
			isOccupied = new boolean[numRows][numCols];
			width = numCols * tileSize;
			height = numRows * tileSize;
			
			String delims = " ";
			for(int i = 0; i<numRows; i++)
			{
				String line = br.readLine();
				String[] tokens = line.split(delims);
				for(int j = 0; j<numCols; j++)
				{
					map[i][j] = Integer.parseInt(tokens[j]);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Point getStartPoint()
	{
		return startPoint;
	}
	
	public Point getEndPoint()
	{
		return endPoint;
	}
	
	public boolean isOccupied(int x, int y)
	{
		return isOccupied[y][x];
	}
	
	public void setOccupied(int x, int y)
	{
		isOccupied[y][x] = true;
	}
	
	public void placeWall(int x, int y)
	{
		map[y][x] = 1;
	}
	
	public boolean existsExitpath(int x, int y)
	{
		if((x == startPoint.x && y == startPoint.y) || (x == endPoint.x && y == endPoint.y))
			return false;
		ArrayList<Point> points = new ArrayList<Point>();
		boolean visited[][] = new boolean[numCols][numRows];
		points.add(0, startPoint);
		visited[startPoint.y][startPoint.x] = true;
		int prev = map[y][x];
		map[y][x] = Tile.BLOCKED;
		while(!points.isEmpty())
		{
			Point curr = points.get(0);
			points.remove(0);
			if(curr.x == endPoint.x && curr.y == endPoint.y)
			{
				map[y][x] = prev;
				return true;
			}
			if(curr.x - 1 >= 0)
			{
				Point next = new Point(curr.x-1, curr.y);
				
				if(map[next.y][next.x] == Tile.NORMAL && !visited[next.y][next.x])
				{
					points.add(0, next);
					visited[next.y][next.x] = true;
				}
			}
			if(curr.x + 1 < numRows)
			{
				Point next = new Point(curr.x+1, curr.y);
				
				if(map[next.y][next.x] == Tile.NORMAL && !visited[next.y][next.x])
				{
					points.add(0, next);
					visited[next.y][next.x] = true;
				}
			}
			if(curr.y - 1 >= 0)
			{
				Point next = new Point(curr.x, curr.y-1);
				
				if(map[next.y][next.x] == Tile.NORMAL && !visited[next.y][next.x])
				{
					points.add(0, next);
					visited[next.y][next.x] = true;
				}
			}
			if(curr.y + 1 < numCols)
			{
				Point next = new Point(curr.x, curr.y+1);
				
				if(map[next.y][next.x] == Tile.NORMAL && !visited[next.y][next.x])
				{
					points.add(0, next);
					visited[next.y][next.x] = true;
				}
			}
		}
		map[y][x] = prev;
		return false;
	}
	
	public int getTile(int x, int y)
	{
		return map[y][x];
	}
	
	public int getTileSize()
	{
		return tileSize;
	}
	
	public int getX()
	{
		return (int)location.x;
	}
	
	public int getY()
	{
		return (int)location.y;
	}
	
	public int getNumRows()
	{
		return numRows;
	}
	
	public int getNumCols()
	{
		return numCols;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public int getType(int row, int col)
	{
		int rc = map[row][col];
		int r = rc/numTilesAcross;
		int c = rc%numTilesAcross;
		
		return tiles[r][c].getType();
	}
	
	public void setPosition(double x, double y)
	{
		location.x += (x-location.x)*tween;
		location.y += (y-location.y)*tween;
		
		fixBounds();
		colOffset = (int) -location.x/inGameTileSize;
		rowOffset = (int) -location.y/inGameTileSize;
	}
	
	private void fixBounds()
	{
		if( location.x < xmin ) location.x = xmin;
		if( location.y < ymin ) location.y = ymin;
		if( location.x > xmax ) location.x = xmax;
		if( location.y > ymax ) location.y = ymax;
	}
	
	public void draw(Graphics2D g)
	{
		for(int i = rowOffset; i<rowOffset+numRowsToDraw; i++)
		{
			if(i >= numRows)
				break;
			for(int j = colOffset; j<colOffset+numColsToDraw; j++)
			{
				if(j >= numCols)
					break;
				
				int rc = map[i][j];
				int r = rc/numTilesAcross;
				int c = rc%numTilesAcross;
				
				g.drawImage(tiles[r][c].getImage(), (int)location.x + j*inGameTileSize,
						(int)location.y + i*inGameTileSize,inGameTileSize, inGameTileSize, null);
			}
		}
	}
}
