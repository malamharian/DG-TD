package util;

public class Vector2d {

	public double x;
	public double y;
	
	public Vector2d()
	{
		x = y = 0;
	}
	
	public static Vector2d add(Vector2d a, Vector2d b)
	{
		return new Vector2d(a.x+b.x, a.y+b.y);
	}
	
	public static Vector2d sub(Vector2d a, Vector2d b)
	{
		return new Vector2d(a.x-b.x, a.y-b.y);
	}
	
	public Vector2d(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void set(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void add(Vector2d vec)
	{
		x += vec.x;
		y += vec.y;
	}
	
	public double distance(Vector2d vec)
	{
		Vector2d temp = this.copy();
		temp.sub(vec);
		
		return temp.mag();
	}
	
	public void sub(Vector2d vec)
	{
		x -= vec.x;
		y -= vec.y;
	}
	
	public void mult(double n)
	{
		x *= n;
		y *= n;
	}
	
	public void div(double n)
	{
		x /= n;
		y /= n;
	}
	
	public Vector2d copy()
	{
		return new Vector2d(x,y);
	}
	
	public double mag()
	{
		return Math.sqrt(x*x + y*y);
	}
	
	public void normalize()
	{
		double mag = mag();
		if(mag != 0)
			div(mag);
	}
}
