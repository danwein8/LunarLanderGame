
public class AvoidStrategy extends Strategy
{
	
	AvoidStrategy()
	{
		super();
	}

	/**
	 * Avoid the player, high value enemy strategy, something the player wants to
	 * destroy to rack up points
	 */
	@Override
	public int recalc_strategy(int x, int y) 
	{
		return 0;
	}
	
}
