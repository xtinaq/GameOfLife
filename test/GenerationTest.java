import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class GenerationTest {

	private Generation generation;

	@Before
	public void setup() {
		generation = new Generation();
		generation.addRule(new LivingCellDiesFromUnderPopulation());
		generation.addRule(new LivingCellDiesFromOverCrowding());
		generation.addRule(new LivingCellLivesOn());
		generation.addRule(new UnbornCellIsBorn());
	}

	@Test
	public void canStoreCells() throws Exception {
		addCellAtCoordinates(1, 1);
		generationSizeIs(1);
		addCellAtCoordinates(1, 2);
		addCellAtCoordinates(2, 1);
		generationSizeIs(3);
		assertTrue(generation.size() != 4);
	}

	@Test
	public void onlyOneCellCanLiveOnTheSamePosition() throws Exception {
		addCellAtCoordinates(1, 1);
		addCellAtCoordinates(1, 1);
		generationSizeIs(1);
	}

	@Test
	public void canGetCellAtCoordinates() throws Exception {
		Cell cell = addCellAtCoordinates(1, 1);
		assertEquals(cell, generation.getCellAt(new Position(1, 1)));
	}

	@Test
	public void canKnowIfCellIsAliveOrDead() throws Exception {
		Cell cell = addCellAtCoordinates(1, 1);
		assertTrue(generation.isCellPopulated(cell));
		Cell cellNotAddedToGeneration = new Cell(new Position(0, 0));
		assertFalse(generation.isCellPopulated(cellNotAddedToGeneration));
	}

	@Test
	public void canAddRules() throws Exception {
		Rule rule = new LivingCellDiesFromUnderPopulation();
		generation.addRule(rule);
	}

	@Test
	public void canCountNeighbors() throws Exception {
		addCellAtCoordinates(0, 1);
		addCellAtCoordinates(2, 1);
		Cell middleCell = addCellAtCoordinates(1, 1);
		assertEquals(2, generation.countNeighbors(middleCell));

		addCellAtCoordinates(1, 0);
		addCellAtCoordinates(1, 2);
		assertEquals(4, generation.countNeighbors(middleCell));
	}

	@Test
	public void canGetAllUnbornNeighbors() throws Exception {
		addCellAtCoordinates(0, 0);
		List<Cell> unbornCells = generation.getUnborns();
		assertEquals(8, unbornCells.size());

		addCellAtCoordinates(1, 0);
		unbornCells = generation.getUnborns();
		assertEquals(10, unbornCells.size());

		addCellAtCoordinates(2, 0);
		unbornCells = generation.getUnborns();
		assertEquals(12, unbornCells.size());

		addCellAtCoordinates(1, 1);
		unbornCells = generation.getUnborns();
		assertEquals(14, unbornCells.size());

		addCellAtCoordinates(2, 1);
		unbornCells = generation.getUnborns();
		assertEquals(14, unbornCells.size());

		addCellAtCoordinates(0, 2);
		unbornCells = generation.getUnborns();
		assertEquals(17, unbornCells.size());

		assertEquals(LivingState.DEAD,
				generation.getLivingState(new Cell(new Position(0, 1))));
	}

	@Test
	public void noCellSurvivesUnderpopulation() throws Exception {
		Cell cell1 = addCellAtCoordinates(1, 0);
		Cell cell2 = addCellAtCoordinates(1, 1);
		assertEquals(2, generation.size());
		Generation nextGeneration = generation.next();
		assertFalse(nextGeneration.isCellPopulated(cell1));
		assertFalse(nextGeneration.isCellPopulated(cell2));
		assertEquals(0, nextGeneration.size());
	}

	@Test
	public void oneCellSurvivesThePenultimateGeneration() throws Exception {
		addCellAtCoordinates(0, 0);
		addCellAtCoordinates(1, 1);
		addCellAtCoordinates(2, 2);
		generationSizeIs(3);
		Generation nextGeneration = generation.next();
		assertTrue(nextGeneration.size() == 1);
	}

	@Test
	public void aCellSurvivesWithNeighbors() throws Exception {
		Cell cell1 = addCellAtCoordinates(0, 1);
		Cell cell2 = addCellAtCoordinates(1, 1);
		Cell cell3 = addCellAtCoordinates(2, 1);
		Cell cell4 = addCellAtCoordinates(1, 2);
		Generation nextGeneration = generation.next();
		assertTrue(nextGeneration.isCellPopulated(cell1));
		assertTrue(nextGeneration.isCellPopulated(cell2));
		assertTrue(nextGeneration.isCellPopulated(cell3));
		assertTrue(nextGeneration.isCellPopulated(cell4));
	}

	@Test
	public void aCellDiesFromOverCrowding() throws Exception {
		addCellAtCoordinates(0, 1);
		addCellAtCoordinates(1, 0);
		addCellAtCoordinates(2, 1);
		addCellAtCoordinates(2, 2);
		Cell middleCell = addCellAtCoordinates(1, 1);
		Generation next = generation.next();
		assertFalse(next.isCellPopulated(middleCell));
	}

	@Test
	public void aCellIsBorn() throws Exception {
		addCellAtCoordinates(0, 1);
		addCellAtCoordinates(1, 0);
		addCellAtCoordinates(2, 1);
		Generation next = generation.next();
		assertTrue(next.isCellPopulated(new Cell(new Position(1, 1))));
	}

	@Test
	public void canGetLivingState() throws Exception {
		Cell cell = addCellAtCoordinates(1, 1);
		assertEquals(LivingState.ALIVE, generation.getLivingState(cell));
		Cell notAddedCell = new Cell(new Position(2, 2));
		assertEquals(LivingState.DEAD, generation.getLivingState(notAddedCell));
	}

	@Test
	public void canConvertToString() throws Exception {
		addCellAtCoordinates(1, 1);
		assertEquals("(1,1)", generation.toString());
		addCellAtCoordinates(2, 2);
		assertEquals("(1,1)(2,2)", generation.toString());
	}

	@Test
	public void canDoManyGenerations() throws Exception {
		generation.createSeedPopulation(5, 0.2);
		System.out.println(generation.toString());
		while (!generation.isExtinct() && generation.sequenceNumber() < 100) {
			generation.next();
			System.out.println(generation.toString());
		}
	}

	public void canKnowIfExtinct() throws Exception {
		assertTrue(generation.isExtinct());
		addCellAtCoordinates(0, 0);
		assertFalse(generation.isExtinct());
	}

	@Test
	public void canYieldGenerationSequenceNumber() throws Exception {
		assertEquals(0, generation.sequenceNumber());
		generation.next();
		assertEquals(1, generation.sequenceNumber());
	}

	@Test
	public void canRunRandomGenerations() throws Exception {
		generation.createSeedPopulation(5, 0.4);
		assertFalse(generation.isExtinct());
	}

	// -------------------------------------------------------

	private Cell addCellAtCoordinates(int x, int y) {
		Position position = new Position(x, y);
		Cell cell = new Cell(position);
		generation.add(cell);
		return cell;
	}

	private void generationSizeIs(int expectedSize) {
		assertEquals(expectedSize, generation.size());
	}
}
