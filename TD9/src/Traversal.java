import java.util.*;

public class Traversal {

//	static final Ocean ocean = new Ocean(0);
	static final Ocean ocean = new Ocean(1);
//	static final Ocean ocean = new Ocean(2);

	static final int pathColor = 200;
	static final int deadEndColor = 100;

	static void slow() {
		try {
			Thread.sleep(15);
		} catch (InterruptedException e) {
		}
	}

	///////////// Methodes a completer /////////////

	static void depthFirst() {
			depthFirstFrom(ocean.marlin);
	}

	static boolean depthFirstFrom(Cell c) {
		slow();
		if (ocean.isNemo(c)) {
			return true;
		}
		if (ocean.isWall(c) || ocean.isMarked(c))
			return false;
		ocean.setMark(c);
		for (Cell v : c.neighbors()) {
			if (depthFirstFrom(v)) {
				ocean.setMark(v,pathColor);
				return true;
			}
		}
		ocean.setMark(c, deadEndColor);
		return false;
	}

	static void breadthFirst() {
		slow();
		Queue<Cell> q = new LinkedList<Cell>();
		q.add(ocean.marlin);
		ocean.setMark(ocean.marlin);
		while(!q.isEmpty()) {
			Cell c = q.poll();
			if (ocean.isNemo(c)) return;
			for(Cell v : c.neighbors()) {
				if(!ocean.isWall(v) && !ocean.isMarked(v)) {
					q.add(v);
					ocean.setMark(v);
				}
			}
		}
	}

	static void breadthFirstWithDistance() {
		slow();
		Queue<Cell> q = new LinkedList<Cell>();
		q.add(ocean.marlin);
		ocean.setMark(ocean.marlin);
		while(!q.isEmpty()) {
			Cell c = q.poll();
			if (ocean.isNemo(c)) {
				System.out.println("Distance : " + ocean.getMark(c));
				return;
			}
			for(Cell v : c.neighbors()) {
				if(!ocean.isWall(v) && !ocean.isMarked(v)) {
					q.add(v);
					ocean.setMark(v,ocean.getMark(c)+1);
				}
			}
		}
	}

	// marks convention: 1 (WEST), 2(SOUTH), 3(EAST), 4(NORTH)
	static final int WEST = 1, SOUTH = 2, EAST = 3, NORTH = 4;

	static void breadthFirstWithShortestPath() {
		slow();
		Queue<Cell> q = new LinkedList<Cell>();
		q.add(ocean.marlin);
		ocean.setMark(ocean.marlin);
		while(!q.isEmpty()) {
			int j = 0;
			Cell c = q.poll();
			if (ocean.isNemo(c)) {
				backTrack();
				return;
			}
			for(Cell v : c.neighbors()) {
				j++;
				if(!ocean.isWall(v) && !ocean.isMarked(v)) {
					q.add(v);
					ocean.setMark(v,j);
				}
			}
		}
	}	
	

	static void backTrack() {
		slow();
		Cell c = ocean.nemo;
		while (!ocean.isMarlin(c)) {
			int n = ocean.getMark(c);
			ocean.setMark(c,pathColor);
			if (n == WEST) c = c.east();
			if (n==EAST) c = c.west();
			if (n == SOUTH) c = c.north();
			if (n == NORTH) c = c.south();
		}
	}

	static void avoidSharks() {
	}

	///////////// Fin des methodes a completer /////////////

	public static void main(String[] args) {
//		depthFirst();
//		breadthFirst();
//		breadthFirstWithDistance();
		breadthFirstWithShortestPath();
//		avoidSharks();
	}

}
