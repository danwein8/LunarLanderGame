import java.awt.*;
import java.awt.event.KeyEvent;

public class NewGameTest extends GameCore {
	
	public static void main(String[] args) {
		new NewGameTest().run();
	}
	
    protected GameAction exit;
    protected GameAction moveUp;
    protected GameAction moveLeft;
    protected GameAction moveRight;
    protected GameAction pause;
    protected InputManager inputManager;
    private LunarLander lander;
    private boolean paused;
    
    public void init() {
    	super.init();
    	Window window = screen.getFullScreenWindow();
    	window.createBufferStrategy(4);
    	inputManager = new InputManager(window);
    	createGameActions();
    	lander = new LunarLander(50, 50, 50, 50, 30.0, 100.0, 20, 2);
    	createSprite();
    	paused = false;
    }
    
    public boolean isPaused() {
    	return paused;
    }
    
    public void setPaused(boolean p) {
    	if (paused != p) {
    		this.paused = p;
    		inputManager.resetAllGameActions();
    	}
    }
    
    public void update(long elapsedTime) {
    	// check input that can happen whether paused or not
    	checkSystemInput();
    	
    	if (!isPaused()) {
    		// check game input
    		checkGameInput();
    		
    		// update lander
    		lander.update(elapsedTime);
    	}
    }
    
    /**
     * Checks input from GameActions that can be pressed
     * regardless of whether the game is paused or not
     */
    public void checkSystemInput() {
    	if (pause.isPressed()) {
    		setPaused(!isPaused());
    	}
    	if (exit.isPressed()) {
    		stop();
    	}
    }
    
    /**
     * Checks input from GameActions that can be pressed
     * only when the game is not paused
     */
    public void checkGameInput() {
    	float velocityX = 0;
    	float velocityY = 0;
    	if (moveLeft.isPressed()) {
    		velocityX -= LunarLander.SPEED;
    	}
    	if (moveRight.isPressed()) {
    		velocityX += LunarLander.SPEED;
    	}
    	if (moveUp.isPressed()) {
    		velocityY -= LunarLander.SPEED;
    	}
    	lander.setVelocity(velocityX, velocityY);
    }
    
    
    
    public void createGameActions() {
    	moveUp = new GameAction("moveUp");
    	exit = new GameAction("exit", GameAction.DETECT_INITAL_PRESS_ONLY);
    	moveLeft = new GameAction("moveLeft");
    	moveRight = new GameAction("moveRight");
    	pause = new GameAction("pause", GameAction.DETECT_INITAL_PRESS_ONLY);
    	
    	inputManager.mapToKey(exit, KeyEvent.VK_ESCAPE);
    	inputManager.mapToKey(pause, KeyEvent.VK_P);
    	
    	// ARROW KEYS
    	inputManager.mapToKey(moveUp, KeyEvent.VK_UP);
    	inputManager.mapToKey(moveLeft, KeyEvent.VK_LEFT);
    	inputManager.mapToKey(moveRight, KeyEvent.VK_RIGHT);
    	// WASD KEYS
    	inputManager.mapToKey(moveUp, KeyEvent.VK_W);
    	inputManager.mapToKey(moveLeft, KeyEvent.VK_A);
    	inputManager.mapToKey(moveRight, KeyEvent.VK_D);
    }
    
    public void createSprite() {
    	// lander sprite
    	lander.setSprite(loadImage("Lander1.png"));
    	
    	// flame / ignition animation sprites
    	Image flame1 = loadImage("ignition1.png");
    	Image flame2 = loadImage("ignition2.png");
    	Image flame3 = loadImage("ignition3.png");
    	
    	// crash animation sprites
    	Image crash1 = loadImage("ignition1.png");
    	Image crash2 = loadImage("ignition1.png");
    	Image crash3 = loadImage("ignition1.png");
    	
    	// flame animation
    	AnimationBk flameAnim = new AnimationBk();
    	flameAnim.addFrame(flame1, 250);
    	flameAnim.addFrame(flame2, 150);
    	flameAnim.addFrame(flame3, 150);
    	flameAnim.addFrame(flame2, 200);
    	
    	// crash animation
    	AnimationBk crashAnim = new AnimationBk();
    	crashAnim.addFrame(crash1, 250);
    	crashAnim.addFrame(crash2, 200);
    	crashAnim.addFrame(crash3, 150);
    	crashAnim.addFrame(crash2, 100);
    	
    	lander.setFlameAnim(flameAnim);
    	lander.setCrashAnim(crashAnim);
    }


	@Override
	public void draw(Graphics2D g) {
		g.drawImage(lander.landerSprite, (int)lander.x, (int)lander.y, null);
		g.drawRect((int)lander.x, (int)lander.y, lander.w, lander.h);
	}
}
