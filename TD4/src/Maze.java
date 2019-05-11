
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import java.io.IOException;

import java.nio.charset.StandardCharsets;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Maze {
	private int height, width;
	private Cell[][] grid;

	public boolean searchPath() {
		return searchPath(0, 0);
	}

	public boolean isEnd(int i, int j) {
		if (i == this.height - 1 && j == this.width - 1)
			return true;
		return false;
	}

	public boolean searchPath(int i, int j) {
		slow();
		this.grid[i][j].mark();
		if (this.isEnd(i, j))
			return true;
		for (Direction dir : Direction.values()) {
			if (this.grid[i][j].hasDirection(dir) && !(this.grid[i + dir.di()][j + dir.dj()].marked())) {
				if (searchPath(i + dir.di(), j + dir.dj()))
					return true;
			}
		}
		this.grid[i][j].unmark();
		return false;
	}

	public void generateRec() {
		generateRec(0, 0);
	}

	public void generateRec(int i, int j) {
		slow();

		List<Direction> dirs = Arrays.asList(Direction.values());
		Collections.shuffle(dirs);

		for (Direction dir : dirs) {
			if (areValidCoords(i + dir.di(), j + dir.dj()) && this.grid[i + dir.di()][j + dir.dj()].isolated()) {
				breakWall(i, j, dir);
				generateRec(i + dir.di(), j + dir.dj());
			}
		}
	}

	public void generateIter(SelectionMethod meth) {
		LinkedList<Coordinate> coords = new LinkedList<Coordinate>();
		coords.add(new Coordinate(0, 0));

		while (!coords.isEmpty()) {
			slow();
			int ind = meth.nextIndex(coords.size());
			Coordinate C = coords.get(ind);

			List<Direction> dirs = Arrays.asList(Direction.values());
			Collections.shuffle(dirs);

			for (Direction dir : dirs) {
				if (areValidCoords(C.i + dir.di(), C.j + dir.dj())
						&& this.grid[C.i + dir.di()][C.j + dir.dj()].isolated()) {
					breakWall(C.i, C.j, dir);
					Coordinate x = new Coordinate(C.i + dir.di(), C.j + dir.dj());
					coords.addLast(x);
				}
			}
			coords.remove(C);
		}
	}

	public void generateEller() {
		UnionFind classes = new UnionFind(height * width);
		Random rnd = new Random();

		for (int i = 0; i < height - 1; ++i) {
			// merge classes randomly in current line
			List<Integer> js = new ArrayList<Integer>(width);
			for (int j = 0; j < width - 1; ++j) {
				js.add(j);
			}
			Collections.shuffle(js, rnd);

			for (int j : js) {
				slow();
				if (!classes.sameClass(coordToInt(i, j), coordToInt(i, j + 1)) && rnd.nextBoolean()) {
					breakWall(i, j, Direction.East);

					classes.union(coordToInt(i, j), coordToInt(i, j + 1));
				}
			}

			
		}

		// TODO : Exercice 4

	}

	public void breakWall(int i, int j, Direction dir) {
		if (!areValidCoords(i, j) || !areValidCoords(i + dir.di(), j + dir.dj()))
			throw new IndexOutOfBoundsException("invalid coordinate");

		grid[i][j].addDirection(dir);
		grid[i + dir.di()][j + dir.dj()].addDirection(dir.opposite());
	}

	public Coordinate intToCoord(int x) {
		if (x < 0 || x >= height * width)
			throw new IndexOutOfBoundsException();

		return new Coordinate(x / width, x % width);
	}

	public int coordToInt(Coordinate coord) {
		if (coord.i < 0 || coord.i >= height || coord.j < 0 || coord.j >= width)
			throw new IndexOutOfBoundsException();

		return coord.i * width + coord.j;
	}

	public int coordToInt(int i, int j) {
		return coordToInt(new Coordinate(i, j));
	}

	public boolean areValidCoords(int i, int j) {
		return !(i < 0 || i >= height || j < 0 || j >= width);
	}

	private MazeFrame frame;
	private static final int step = 20;

	private void slow() {
		try {
			Thread.sleep(15);
			if (frame != null)
				frame.repaint();
		} catch (InterruptedException e) {
		}
	}

	public Maze(int height, int width) {
		this(height, width, true);
	}

	public Maze(int height, int width, boolean window) {
		if ((height <= 0) || (width <= 0))
			throw new IllegalArgumentException("height and width of a Maze must be positive");

		this.height = height;
		this.width = width;

		grid = new Cell[height][width];

		for (int i = 0; i < height; ++i)
			for (int j = 0; j < width; ++j)
				grid[i][j] = new Cell();

		if (window)
			frame = new MazeFrame(grid, height, width, step);
	}

	public Maze(String path) throws IOException {
		this(Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8));
	}

	public Maze(String path, boolean window) throws IOException {
		this(Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8), window);
	}

	public Maze(List<String> lines) {
		this(lines, true);
	}

	public Maze(List<String> lines, boolean window) {
		// check number of lines
		if (lines.size() < 2)
			throw new IllegalArgumentException("too few lines");

		this.height = Integer.parseInt(lines.get(0));
		this.width = Integer.parseInt(lines.get(1));

		this.grid = new Cell[height][width];

		int i = 0;
		int j = 0;

		for (String line : lines.subList(2, lines.size())) {
			grid[i][j] = new Cell();

			for (int k = 0; k < line.length(); ++k) {
				switch (line.charAt(k)) {
				case 'N':
					grid[i][j].addDirection(Direction.North);
					break;
				case 'E':
					grid[i][j].addDirection(Direction.East);
					break;
				case 'S':
					grid[i][j].addDirection(Direction.South);
					break;
				case 'W':
					grid[i][j].addDirection(Direction.West);
					break;
				case '*':
					grid[i][j].mark();
					break;
				default:
					throw new IllegalArgumentException("illegal character");
				}
			}

			++j;
			if (j >= width) {
				j = 0;
				++i;
			}
		}

		if (window)
			frame = new MazeFrame(grid, height, width, step);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(height);
		sb.append('\n');
		sb.append(width);
		sb.append('\n');

		for (int i = 0; i < height; ++i) {
			for (int j = 0; j < width; ++j) {
				sb.append(grid[i][j]);
				sb.append('\n');
			}
		}

		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (!(o instanceof Maze))
			return false;
		Maze that = (Maze) o;

		if (this.height != that.height || this.width != that.width)
			return false;
		for (int i = 0; i < this.height; ++i)
			for (int j = 0; j < this.width; ++j)
				if (!this.grid[i][j].equals(that.grid[i][j]))
					return false;
		return true;
	}

	public String textImage() {
		StringBuilder sb = new StringBuilder();

		// first line
		sb.append('\u254b');
		for (int j = 0; j < width; ++j) {
			sb.append("\u2501\u2501\u2501\u254b");
		}
		sb.append('\n');

		for (int i = 0; i < height; ++i) {
			// east exits
			sb.append('\u2503');
			for (int j = 0; j < width; ++j) {
				if (grid[i][j].marked())
					sb.append(" * ");
				else
					sb.append("   ");

				if (grid[i][j].hasDirection(Direction.East))
					sb.append(" ");
				else
					sb.append("\u2503");
			}
			sb.append('\n');

			// south exits
			sb.append('\u254b');
			for (int j = 0; j < width; ++j) {
				if (grid[i][j].hasDirection(Direction.South))
					sb.append("   \u254b");
				else
					sb.append("\u2501\u2501\u2501\u254b");
			}
			sb.append('\n');
		}

		return sb.toString();
	}

	public boolean isPerfect() {
		UnionFind uf = new UnionFind(height * width);

		// union find cycle detection
		for (int i = 0; i < height; ++i) {
			// horizontal edges
			for (int j = 0; j < width - 1; ++j) {
				if (grid[i][j].hasDirection(Direction.East)) {
					if (uf.sameClass(coordToInt(i, j), coordToInt(i, j + 1)))
						return false;
					uf.union(coordToInt(i, j), coordToInt(i, j + 1));
				}
			}

			// there are no vertical edges in last row, so we're done
			if (i == height - 1)
				continue;

			// vertical edges
			for (int j = 0; j < width; ++j) {
				if (grid[i][j].hasDirection(Direction.South)) {
					if (uf.sameClass(coordToInt(i, j), coordToInt(i + 1, j)))
						return false;
					uf.union(coordToInt(i, j), coordToInt(i + 1, j));
				}
			}
		}

		// check if connected
		return (uf.getSize(0) == height * width);
	}
}
