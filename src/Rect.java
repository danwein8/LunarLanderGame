import java.awt.Color;
import java.awt.Graphics;

public class Rect
{
	// Position
	double x;
	double y;
	
	// Size
	int w;
	int h;
	
	// Velocity
	double vx;
	double vy;
	
	
	public Rect(double x, double y, int w, int h)
	{
		this.x = x;
		this.y = y;
		
		this.w = w;
		this.h = h;
	}
	
	public void setY(double y)
	{
		this.y = y;
	}
	
	public void moveBy(double dx, double dy)
	{
		x += dx;
		y += dy;
	}
	
	public void moveUp(double dy)
	{
		y -= dy;
	}
	
	public void moveDown(double dy)
	{
		y += dy;
	}
	
	public void moveLeft(double dx)
	{
		x -= dx;
	}
	
	public void moveRight(double dx)
	{
		x += dx;
	}
	

	
	public void move()
	{
		x += vx;
		y += vy;
	}
	
	public void setVelocity(double vx, double vy)
	{
		this.vx = vx;
		this.vy = vy;
	}
	
	public void bounceOffVerticalSurface()
	{
		vx = -vx;
	}
	
	public void bounceOffHorizontalSurface()
	{
		vy = -vy;
	}
	
	public void draw(Graphics g)
	{
		g.setColor(Color.white);
		g.fillRect((int)x,  (int)y,  w,  h);
	}
	
	public boolean overlaps(Rect r)
	{
		return	(  x +   w >= r.x) &&
				(r.x + r.w >=   x) &&
				(r.y + r.h >=   y) &&
				(  y +   h >= r.y);
	}

}