public class Cell {
	private Position position;

	public Cell(Position position) {
		this.position = position;
	}

	public Position getPosition() {
		return position;
	}

	public boolean isNeighbor(Cell otherCell) {
		Position myPosition = this.getPosition();
		Position otherPosition = otherCell.getPosition();
		int distance = myPosition.distanceTo(otherPosition);
		return (distance == 1);
	}

	public boolean isSameCell(Cell otherCell) {
		Position otherPosition = otherCell.getPosition();
		return (otherPosition.isSamePosition(this.position));
	}

	public String toString() {
		return position.toString();
	}
}
