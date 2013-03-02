package net.skaskiw.gameoflife;
import static java.lang.Math.*;

import java.util.ArrayList;
import java.util.List;

public class Position {
	private final int x;
	private final int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int xCoord() {
		return this.x;
	}

	public int yCoord() {
		return this.y;
	}

	public boolean isSamePosition(Position otherPosition) {
		return this.distanceTo(otherPosition) == 0;
	}

	public int distanceTo(Position otherPosition) {
		int xDistance = abs(this.xCoord() - otherPosition.xCoord());
		int yDistance = abs(this.yCoord() - otherPosition.yCoord());
		int distance = max(xDistance, yDistance);
		return distance;
	}

	public List<Position> getNeighboringPositions() {
		List<Position> neighborPositions = new ArrayList<Position>();
		for (int x = this.xCoord() - 1; x <= this.xCoord() + 1; x++)
			for (int y = this.yCoord() - 1; y <= this.yCoord() + 1; y++)
				if (!(x == this.xCoord() && y == this.yCoord()))
					neighborPositions.add(new Position(x, y));
		return neighborPositions;
	}

	public String toString() {
		String coordinates = "(" + this.xCoord() + "," + this.yCoord() + ")";
		return coordinates;
	}
}
