package net.skaskiw.gameoflife;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class PositionTest {

	@Test
	public void twoPositionsAreTheSame() throws Exception {
		Position position1 = new Position(1, 1);
		Position position2 = new Position(1, 1);
		assertTrue(position1.isSamePosition(position2));
	}
	
	@Test
	public void twoPositionsAreNotTheSame() throws Exception {
		Position position1 = new Position(1, 1);
		Position position2 = new Position(2, 2);
		assertFalse(position1.isSamePosition(position2));		
	}
	
	@Test
	public void distanceIsOne() throws Exception {
		Position position1 = new Position(1, 1);
		Position position2 = new Position(2, 2);
		assertEquals(1, position1.distanceTo(position2));
	}
	
	@Test
	public void distanceIsMoreThanOne() throws Exception {
		Position position1 = new Position(1, 1);
		Position position2 = new Position(3, 0);
		assertTrue(position1.distanceTo(position2) > 1);
	}
	
	@Test
	public void canGetNeighboringPositions() throws Exception {
		Position middlePosition = new Position(1, 1);
		List<Position> neighborPositions = middlePosition.getNeighboringPositions();
		assertEquals(8, neighborPositions.size());
		for (Position neighborPosition : neighborPositions)
			assertEquals(1, middlePosition.distanceTo(neighborPosition));
	}
}
