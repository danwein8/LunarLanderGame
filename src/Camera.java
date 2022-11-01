
public class Camera 
{
	
	static int x;
	static int y;
	static int z = 1;
	
	public static void moveUp(int dy)
	{
		y -= dy;
	}
	
	public static void moveDown(int dy)
	{
		y += dy;
	}
	
	public static void moveLeft(int dx)
	{
		x -= dx;
	}
	
	public static void moveRight(int dx)
	{
		x += dx;
	}
	
	public static void move(int dx)
	{
		x += dx;
	}
	
	public static void moveIn(int dz)
	{
		z -= dz;
	}
	
	public static void moveOut(int dz)
	{
		y += dz;
	}

}
