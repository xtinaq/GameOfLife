package net.skaskiw.gameoflife;


public class CellIsBorn implements Rule {

	@Override
	public LivingState validate(LivingState state, int numberOfNeighbors) {
		LivingState nextState = LivingState.DEAD;
		if (numberOfNeighbors == 3)
			nextState = LivingState.ALIVE; 
		return nextState;
	}

	@Override
	public boolean isApplicable(LivingState state) {
		return (state == LivingState.DEAD);
	}

}
