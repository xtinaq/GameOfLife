import static org.junit.Assert.*;

import org.junit.Test;

public class RuleTest {

	@Test
	public void cellDiesWithOnlyOneNeighbor() throws Exception {
		Rule rule = new LivingCellDiesFromUnderPopulation();
		assertTrue(rule.isApplicable(LivingState.ALIVE));
		assertFalse(rule.isApplicable(LivingState.DEAD));
		assertEquals(LivingState.DEAD, rule.validate(LivingState.ALIVE, 1));
		assertEquals(LivingState.DEAD, rule.validate(LivingState.DEAD, 1));
		assertEquals(LivingState.ALIVE, rule.validate(LivingState.ALIVE, 2));
		assertEquals(LivingState.DEAD, rule.validate(LivingState.DEAD, 2));
	}
	
	@Test
	public void cellLivesOnWithTwoOrThreeNeighbors() throws Exception {
		Rule rule = new LivingCellLivesOn();
		assertTrue(rule.isApplicable(LivingState.ALIVE));
		assertFalse(rule.isApplicable(LivingState.DEAD));
		assertEquals(LivingState.ALIVE, rule.validate(LivingState.ALIVE, 2));
		assertEquals(LivingState.DEAD, rule.validate(LivingState.ALIVE, 4));
		assertEquals(LivingState.DEAD, rule.validate(LivingState.DEAD, 3));
		assertEquals(LivingState.DEAD, rule.validate(LivingState.DEAD, 4));
	}
	
	@Test
	public void cellIsBornWithThreeNeighbors() throws Exception {
		Rule rule = new CellIsBorn();
		assertFalse(rule.isApplicable(LivingState.ALIVE));
		assertTrue(rule.isApplicable(LivingState.DEAD));
		assertEquals(LivingState.ALIVE, rule.validate(LivingState.DEAD, 3));
		assertEquals(LivingState.DEAD, rule.validate(LivingState.DEAD, 4));
		assertEquals(LivingState.DEAD, rule.validate(LivingState.DEAD, 2));
		assertEquals(LivingState.ALIVE, rule.validate(LivingState.ALIVE, 3));
	}
	
	@Test
	public void cellDiesWithMoreThanThreeNeighbors() throws Exception {
		Rule rule = new LivingCellDiesFromOverCrowding();
		assertTrue(rule.isApplicable(LivingState.ALIVE));
		assertFalse(rule.isApplicable(LivingState.DEAD));
		assertEquals(LivingState.DEAD, rule.validate(LivingState.ALIVE, 4));
		assertEquals(LivingState.ALIVE, rule.validate(LivingState.ALIVE, 3));
		assertEquals(LivingState.DEAD, rule.validate(LivingState.DEAD, 3));
	}
}
