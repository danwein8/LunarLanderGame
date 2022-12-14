import java.awt.*;

public class GameTest2 extends GameBase 
{

	private static final long serialVersionUID = 1L;

	boolean noWin = true;

	LunarLander lander = new LunarLander(50, 50, 20, 20, 30.0, 100.0, 20, 2);

	Rect landingPlatform = new Rect(1400, 400, 40, 20);

	double gravity = 0.1;

	final Font fuelFont = new Font("Arial", Font.BOLD, 20);
	final Font font = new Font("Arial", Font.BOLD, 40);

	String lose = "YOU CRASHED AND DIED";
	String win = "YOU SUCCESSFULLY LANDED AND EVERYONE LIVED"; 

	Rect[] ground =
		{
				new Rect(0, 700, 50, 500),
				new Rect(50, 500, 50, 500),
				new Rect(100, 650, 50, 500),
				new Rect(150, 750, 50, 500),
				new Rect(200, 450, 50, 500),
				new Rect(250, 575, 50, 500),
				new Rect(300, 600, 50, 500),
				new Rect(350, 650, 50, 500),
				new Rect(400, 700, 50, 500),
				new Rect(450, 550, 50, 500),
				new Rect(500, 500, 50, 500),
				new Rect(550, 650, 50, 500),
				new Rect(600, 750, 50, 500),
				new Rect(650, 450, 50, 500),
				new Rect(700, 575, 50, 500),
				new Rect(750, 600, 50, 500),
				new Rect(800, 450, 50, 500),
				new Rect(850, 575, 50, 500),
				new Rect(900, 600, 50, 500),
				new Rect(950, 650, 50, 500),
				new Rect(1000, 700, 50, 500),
				new Rect(1050, 550, 50, 500),
				new Rect(1100, 500, 50, 500),
				new Rect(1150, 650, 50, 500),
				new Rect(1200, 750, 50, 500),
				new Rect(1250, 450, 50, 500),
				new Rect(1300, 575, 50, 500),
				new Rect(1350, 600, 50, 500),
		};

	@Override
	public void inGameLoop() 
	{
		lander.setAcceleration(0, gravity);
		while (noWin) {

			if (lander.ax > 0 || lander.ax < 0) lander.sideDecel(.9);
			if (lander.ay < 0.1) lander.gravityAccel(.06);

			if (pressing[_UP]) {
				if (lander.ay > -0.3) lander.liftAccel(8 / lander.getCurrentWeight(lander.getWeight(), lander.getFuel()));
				if (lander.fuel > 0) lander.burnFuel();
			}
			if (pressing[_RT]) {
				if (lander.ax < 0.3) lander.moveRightAccel(3 / lander.getCurrentWeight(lander.getWeight(), lander.getFuel()));
				if (lander.fuel > 0) lander.burnFuel();
			}
			if (pressing[_LT]) {
				if (lander.ax > -0.3) lander.moveLeftAccel(3 / lander.getCurrentWeight(lander.getWeight(), lander.getFuel()));
				if (lander.fuel > 0) lander.burnFuel();
			}

			lander.moveBasedOnPhysics();

			for (int i = 0; i < ground.length; i++) {
				if (lander.overlaps(ground[i])) {
					noWin = false;
				}
			}

			if (lander.overlaps(landingPlatform)) noWin = false;
		}
	}

	public void paint(Graphics g) {
		lander.draw(g);

		for (int i = 0; i < ground.length; i++) {
			g.setColor(Color.black);
			ground[i].draw(g);
			g.fillRect((int)ground[i].x, (int)ground[i].y, ground[i].w, ground[i].h);
		}
		g.setColor(Color.green);
		g.fillRect((int)landingPlatform.x, (int)landingPlatform.y, landingPlatform.w, landingPlatform.h);

		g.setColor(Color.blue);
		g.setFont(fuelFont);
		g.drawString(String.valueOf(lander.getFuel()), 10, 10);

		if (lander.overlaps(landingPlatform)) {
			g.setColor(Color.green);
			g.setFont(font);
			g.drawString(win, 300, 400);
		}

		for (int i = 0; i < ground.length; i++) {
			if (lander.overlaps(ground[i])) {
				g.setColor(Color.red);
				g.setFont(font);
				g.drawString(lose, 500, 400);
			}
		}
	}
}
