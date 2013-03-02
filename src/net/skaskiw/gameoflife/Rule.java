package net.skaskiw.gameoflife;


public interface Rule {
	public boolean isApplicable(LivingState state);
	public LivingState validate(LivingState state, int numberOfNeighbors);
}
