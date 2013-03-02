package net.skaskiw.gameoflife;


public class LivingCellLivesOn implements Rule {

	@Override
	public LivingState validate(LivingState state, int numberOfNeighbors) {
		LivingState nextState = state;
		if (numberOfNeighbors < 2 || numberOfNeighbors > 3)
			nextState = LivingState.DEAD;
		return nextState;
	}

	@Override
	public boolean isApplicable(LivingState state) {
		return (state == LivingState.ALIVE);
	}

}
