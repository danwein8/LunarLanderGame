import java.awt.*;
import java.util.*;

public class Ceiling {

	/**
	 * all of the rectangles that make up the collidable objects in the level
	 */
	ArrayList<Rect> floor = new ArrayList<Rect>();
	ArrayList<Rect> ceiling = new ArrayList<Rect>();
	Rect endPlatform;
	
	private GraphicsDevice device;
	private DisplayMode mode;
	/**
	 * the location of the current grid on the x axis
	 */
	int x;
	/**
	 * the location of the current grid on the y axis
	 */
	int y;
	/**
	 * a scalar value used in the procedural generation
	 */
	int difficulty = 3;
	/**
	 * length and width of the level, probably wont need these values here, this would be needed where I instantiate
	 * the Level object inside of a loop that loops over all the sectors of the level
	 */
	int height;
	int width;
	int sectorsX;
	int sectorsY;
	
	/**
	 * how far out the ending platform is placed
	 */
	int endDist = 2500;
	/**
	 * the Y value, width, and height for the end platform, its always the same
	 */
	final int endPlatY = 450;
	final int endPlatW = 30;
	final int endPlatH = 10;

	/**
	 * the width of the ceiling and floor rectangles
	 */
	int w = 90;
	/**
	 * the screen offset to center the picture
	 */
	int offset = 120;
	/**
	 * the maximum variation for the tunnel wall, so if the wall is 100 high and variation is set to 10, the
	 * wall can be anything from 90 to 110 pixels, this applies to floor and ceiling
	 */
	int variation = (100 * difficulty);
	
	Point screenSectors = new Point(0, 0);

	/**
	 * initialize the graphics environment so I can get the screen size info and divide it up into sectors for
	 * making the ceiling, floor, and tunnel
	 */
	public void init() {
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		device = environment.getDefaultScreenDevice();
		mode = device.getDisplayMode();
		// I had to subtract some pixels because it cuts off some of the screen and some of the game got cut off
		height = mode.getHeight() - offset;
		width = mode.getWidth();
		/**
		 * this version actually makes the levels longer the higher the difficulty is but it does it by spacing out
		 * the rectangles that make up the ceiling and floor, it just scales the level up so to use this I need to
		 * either scale the size of the rectangles widths or add more rectangles to fill the gaps
		 */
		sectorsX = (width * difficulty) / 16;
		/**
		 * this version doesn't scale the levels based on difficulty, they're all the same size
		 */
		//sectorsX = width / 16;
		sectorsY = height / 3;
	}
	
	public Ceiling(double x, double y) {
		this.init();
		Random rand = new Random((long)((x + y)));
		for (screenSectors.x = 0; screenSectors.x < sectorsX; screenSectors.x++) {
			int a = rand.nextInt(variation);
			int b = rand.nextInt(variation);
			for (screenSectors.y = 0; screenSectors.y < 1; screenSectors.y++) {
				ceiling.add(new Rect(screenSectors.x * sectorsX, screenSectors.y * sectorsY, w * difficulty, a));
			}
			for (screenSectors.y = 2; screenSectors.y < 3; screenSectors.y++) {
				floor.add(new Rect(screenSectors.x * sectorsX, (sectorsY-b)+(screenSectors.y * sectorsY), w * difficulty, b));
			}
		}
		endPlatform = new Rect(endDist * difficulty, endPlatY, endPlatW, endPlatH);
	}

	public void draw(Graphics g) {
		for (int i = 0; i < ceiling.size(); i++) {
			g.drawRect((int)ceiling.get(i).x, (int)ceiling.get(i).y, ceiling.get(i).w, ceiling.get(i).h);
			g.drawRect((int)floor.get(i).x, (int)floor.get(i).y, floor.get(i).w, floor.get(i).h);
		}
		g.drawRect(endDist * difficulty, endPlatY, endPlatW, endPlatH);
	}

}
