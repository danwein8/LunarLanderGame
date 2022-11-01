
public abstract class Strategy 
{
	/**
	 * abstract function to recalculate A.I. strategy in inherited classes
	 * @param x: location on x axis (in pixels or sectors, whatever the game uses)
	 * @param y: location on y axis (in pixels or sectors, whatever the game uses)
	 * @return: nothing here
	 */
	public abstract int recalc_strategy(int x, int y);
	
	protected Strategy() {  }
}
