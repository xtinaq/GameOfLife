import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class CellTest {

	@Test
	public void aCellShouldHaveAPosition() throws Exception {
		Position position = new Position(0, 0);
		Cell cell = new Cell(position);
		assertEquals(position, cell.getPosition());
	}
	
	@Test
	public void twoCellsAreNotNeighbors() throws Exception {
		Cell cell1 = populateCellAtCoordinates(0, 0);
		Cell cell2 = populateCellAtCoordinates(2, 2);
		assertFalse(cell1.isNeighbor(cell2));		
	}
	
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
	public void canDoPositiveCoordinates() throws Exception {
		assertTrue(allCellsAreNeighbors(1, 1));
	}
	
	@Test
	public void canDoNegativeCoordinates() throws Exception {
		assertTrue(allCellsAreNeighbors(0, 0));
		assertTrue(allCellsAreNeighbors(-2, -2));
	}

	@Test
	public void canGetNeighboringPositions() throws Exception {
		Position middlePosition = new Position(1, 1);
		List<Position> neighborPositions = middlePosition.getNeighboringPositions();
		assertEquals(8, neighborPositions.size());
		for (Position neighborPosition : neighborPositions)
			assertEquals(1, middlePosition.distanceTo(neighborPosition));
	}
	
	@Test
	public void cellIsSameCell() throws Exception {
		Cell cell1 = populateCellAtCoordinates(1, 1);
		Cell cell2 = populateCellAtCoordinates(1, 1);
		assertTrue(cell1.isSameCell(cell2));
		Cell cell3 = populateCellAtCoordinates(2, 2);
		assertFalse(cell1.isSameCell(cell3));
	}
	
	@Test
	public void canBeConvertedToString() throws Exception {
		Cell cell = new Cell(new Position(1, 1));
		assertEquals("(1,1)", cell.toString());
	}
	
//--supporting operations---------------------------------------------------------
	
	private Cell populateCellAtCoordinates(int x, int y) {
		Position position = new Position(x, y);
		Cell cell = new Cell(position);
		return cell;
	}
	
	private List<Cell> populateThreeByThreeAroundPosition(Position position) {
		List<Cell> cells = new ArrayList<Cell>();
		Cell cell;
		for (int x = position.xCoord() - 1; x < position.xCoord() + 1; x++)
			for (int y = position.yCoord() - 1; y < position.yCoord() + 1; y++) {
				if (!position.isSamePosition(new Position(x, y))) {
					cell = populateCellAtCoordinates(x, y);
					cells.add(cell);
				}
			}
		return cells;
	}
	
	private boolean allCellsAreNeighbors(int x, int y) {
		boolean thereAreOnlyNeighbors = true;
		Cell middleCell = populateCellAtCoordinates(x, y);
		List<Cell> cells = populateThreeByThreeAroundPosition(new Position(x, y));
		for (Cell cell : cells) 
			if (!cell.isNeighbor(middleCell))
				thereAreOnlyNeighbors = false;
		return thereAreOnlyNeighbors;
	}

}
