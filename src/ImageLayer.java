import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class ImageLayer 
{
	// if you are putting lots of stuff on the screen then your image width and height should be a power 
	// of two b/c images are stored in arrays so finding an image thats a power of 2 only requires using
	// bit shifts which is a cheap operation whereas if its not a power of 2 the computer needs to do 
	// divisions which are very expensive
	Image image;
	
	int depth;
	
	double zoom = 1;
	
	int w = 720;
	int h = 360;
	
	public ImageLayer(String fileName, int depth)
	{
		this.image = Toolkit.getDefaultToolkit().getImage(fileName);
		
		this.depth = depth;
	}
	
	
	
	public void draw(Graphics g)
	{
		
		for (int i = 0; i < 20; i++)
		{
			//g.drawImage(image, (0 + i * 720 - Camera.x / depth) * zoom, 0 - Camera.y, w/zoom, h/zoom, null);
			g.drawImage(image, 0 + i * 720 - (int)Camera.x / depth, 0 - (int)Camera.y, null);
			
		}
	}
	
}
