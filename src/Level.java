import java.awt.*;

/**
 * Procedurally generated levels: the length and width of the tunnel, along with the variation of the walls
 * will be based on the difficulty.
 * Idea behind procedural generation / Invariant: the way this level generator is going to work is it will take
 * into consideration the entire rectangular space, length by width, and divide it into a grid. the grid will always
 * consist of three rows (based on the current design idea, subject to change), these rows will mainly be based on 
 * the average width of the tunnel. The average tunnel width is the width of the tunnel when the variation = 0 on
 * both ceiling and floor, higher difficulty scales this value down and the variation value up. So a tunnel of that
 * width will be put in the middle of the screen, so the y value at average tunnel width/2 == width/2. then the
 * remaining width on either side will be used for the ceiling and floor boundaries. The columns will be a set value,
 * something like 16 or 32, if I find a sprite or tile I want to use for the walls then that will be the value.
 * Now I just need to use the x and y values from the grid spots that hold walls in my random number generator
 * and then run that value thru the procedural generator algorithm and I will know exactly how much the walls vary
 * by, I can also have gaps in the wall if that looks good, procedural generation is versatile.
 * @author Daniel Weiner
 *
 */
public class Level
{
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
	int difficulty;
	/**
	 * length and width of the level, probably wont need these values here, this would be needed where I instantiate
	 * the Level object inside of a loop that loops over all the sectors of the level
	 */
	int height;
	int width;
	
	/**
	 * width and height of the wall objects, probably wont need this
	 */
	int w;
	int h;
	/**
	 * the maximum variation for the tunnel wall, so if the wall is 100 high and variation is set to 10, the
	 * wall can be anything from 90 to 110 pixels, this applies to floor and ceiling
	 */
	int variation;
	int deltaLength;
	/**
	 * average tunnel width value, when the variation is 0 on both the ceiling and the floor the tunnel will be
	 * this wide
	 */
	int avgTunnelWidth;
	/**
	 * random number generator state variable
	 */
	long nLehmer = 0;
	/**
	 * boolean state variable for making gaps in the floor and ceiling, rarely false (not a lot of gaps)
	 */
	boolean exists;
	
	/**
	 * only using the least significant 16 bits of the x and y location so it limits the size of the level before
	 * it starts looping, each axis only has 16 bit coordinate resolution. We are only worried about the x axis
	 * because we aren't traveling any significant distances up or down and 16 bits should be fine, but can be
	 * changed later if need be.
	 * @param x: pixel location on the x axis.
	 * @param y: pixel location on the y axis.
	 */
	public Level(int x, int y)
	{
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		device = environment.getDefaultScreenDevice();
		mode = device.getDisplayMode();
		// TODO: abort construction if y value is in the tunnel, this constructor needs to output a ceiling and 
		// a floor with an empty, tunnel-like middle.
		nLehmer = (x & 0xFFFF) << 16 | (y & 0xFFFF);
		
		exists = !(randInt(0, 40) == 1);
		if (!exists) return;
		
		deltaLength = randInt(variation, variation);
	}
	
	/**
	 * uses the Lehmer random number generator I got from https://www.youtube.com/watch?v=ZZY9YE7rZJw 
	 * this RNG is made to work with all 64 bits and I am returning the least significant 32 bits so it may
	 * compromise the original functionality somewhat.
	 * @return a genuinely random number based on the nLehmer state variable.
	 */
	private int Lehmer32()
	{
		nLehmer += 0xe120fc15;
		long tmp;
		tmp = (long)nLehmer * 0x4a39b70d;
		long m1 = (tmp >> 32) ^ tmp;
		tmp = m1 * 0x12fad5c9;
		long m2 = (tmp >> 32) ^ tmp;
		return (int)m2;
	}
	
	/**
	 * Utility function to return a random integer between min and max using the Lehmer32 random number generator
	 * @param min: the low end of the range of numbers the random number can be between
	 * @param max: the high end of the range of numbers the random number can be between
	 * @return a random integer number between min and max
	 */
	private int randInt(int min, int max)
	{
		return (Lehmer32() % (max - min)) + min;
	}
	
	/**
	 * Utility function to return a random double between min and max using the Lehmer32 random number generator
	 * @param min: the low end of the range of numbers the random number can be between
	 * @param max: the high end of the range of numbers the random number can be between
	 * @return a random double number between min and max
	 */
	private double randDouble(double min, double max)
	{
		return((double)Lehmer32() / (double)(0x7FFFFFFF)) * (max - min) + min;
	}
	
	public void draw(Graphics g)
	{
		// TODO: make the draw method split the screen into a grid then draw floors and ceilings where they get 
		// procedurally generated
		height = mode.getHeight();
		width = mode.getWidth();
		int sectorsX = width / 3;
		int sectorsY = height / 16;
		
		g.fillRect(x, y, 8, 8);
	}
}
