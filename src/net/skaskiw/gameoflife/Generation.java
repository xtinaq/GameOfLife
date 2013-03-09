package net.skaskiw.gameoflife;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generation {

	private List<Cell> cells = new ArrayList<Cell>();
	private List<Rule> rules;
	private int sequenceNumber = 0;

	public Generation(List<Rule> gameRules) {
		rules = gameRules;
	}

	public void add(Cell cell) {
		if (!isCellPopulated(cell))
			cells.add(cell);
	}

	public Cell getCellAt(Position position) {
		Cell soughtCell = null;
		for (Cell cell : cells) {
			if (cell.isSameCell(new Cell(position)))
				soughtCell = cell;
		}
		return soughtCell;
	}

	public List<Cell> getCells() {
		return this.cells;
	}

	public int size() {
		return cells.size();
	}

	public Generation createSeedPopulation(int size, double density) {
		cells = new ArrayList<Cell>();
		int centerOffset = Math.round(size / 2);
		Random densitySeed = new Random();
		for (int x = 0; x < size; x++)
			for (int y = 0; y < size; y++)
				if (densitySeed.nextFloat() < density)
					this.add(new Cell(new Position(x - centerOffset, y
							- centerOffset)));
		return this;
	}

	public Generation next() {
		List<Cell> nextGenerationCells = keepSurvivors();
		nextGenerationCells = addNewBorns(nextGenerationCells);
		cells = nextGenerationCells;
		sequenceNumber++;
		return this;
	}

	public boolean isExtinct() {
		return (size() == 0);
	}

	public boolean isCellPopulated(Cell cell) {
		boolean cellExists = false;
		for (Cell existingCell : cells) {
			if (existingCell.isSameCell(cell))
				cellExists = true;
		}
		return cellExists;
	}

	public LivingState getLivingState(Cell cell) {
		LivingState livingState = LivingState.DEAD;
		if (isCellPopulated(cell))
			livingState = LivingState.ALIVE;
		return livingState;
	}

	public int countNeighbors(Cell cell) {
		int count = 0;
		for (Cell candidateNeighbor : cells) {
			if (candidateNeighbor.isNeighbor(cell)) {
				count++;
			}
		}
		return count;
	}

	public List<Cell> getUnborns() {
		Generation unbornGeneration = new Generation(new ArrayList<Rule>());
		List<Position> neighborPositions = new ArrayList<Position>();
		Cell unbornCell;
		for (Cell cell : cells) {
			neighborPositions = cell.getPosition().getNeighboringPositions();
			for (Position position : neighborPositions) {
				unbornCell = new Cell(position);
				if (!this.isCellPopulated(unbornCell))
					unbornGeneration.add(unbornCell);
			}
		}
		return unbornGeneration.cells;
	}

	public int sequenceNumber() {
		return sequenceNumber;
	}

	public String toString() {
		String generationCoordinates = "";
		for (Cell cell : cells)
			generationCoordinates = generationCoordinates + cell.toString();
		return generationCoordinates;
	}

	private List<Cell> keepSurvivors() {
		List<Cell> nextGenerationCells = new ArrayList<Cell>();
		for (Cell thisGenerationCell : cells) {
			if (cellSurvives(thisGenerationCell))
				nextGenerationCells.add(thisGenerationCell);
		}
		return nextGenerationCells;
	}

	private List<Cell> addNewBorns(List<Cell> nextGenerationCells) {
		List<Cell> unBorns = this.getUnborns();
		for (Cell unBorn : unBorns) {
			if (cellIsBorn(unBorn))
				nextGenerationCells.add(unBorn);
		}
		return nextGenerationCells;
	}

	private boolean cellSurvives(Cell cell) {
		boolean isSurvivor = true;
		for (Rule rule : rules) {
			if (rule.isApplicable(getLivingState(cell)))
				isSurvivor = isSurvivor
						&& (LivingState.ALIVE == rule.validate(
								getLivingState(cell), countNeighbors(cell)));
		}
		return isSurvivor;
	}

	private boolean cellIsBorn(Cell cell) {
		boolean isNewLife = true;
		for (Rule rule : rules) {
			if (rule.isApplicable(getLivingState(cell)))
				isNewLife = isNewLife
						&& (LivingState.ALIVE == rule.validate(
								getLivingState(cell), countNeighbors(cell)));
		}
		return isNewLife;
	}
}
