
public class ChaseStrategy extends Strategy
{
	
	ChaseStrategy()
	{
		super();
	}

	/**
	 * Chase and attack the player, lower value, obstacle enemy strategy, something
	 * the player may choose to try to avoid altogether 
	 */
	@Override
	public int recalc_strategy(int x, int y) 
	{
		return 0;
	}

}
