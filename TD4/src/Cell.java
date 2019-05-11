
import java.util.HashSet;
import java.util.Set;

public class Cell {
	private Set<Direction> dirs;
	private boolean marked;

	public Cell() {
		dirs = new HashSet<Direction>();
	}

	public void addDirection(Direction dir) {
		dirs.add(dir);
	}

	public void removeDirection(Direction dir) {
		dirs.remove(dir);
	}

	public boolean hasDirection(Direction dir) {
		return dirs.contains(dir);
	}

	public boolean isolated() {
		return dirs.isEmpty();
	}

	public boolean marked() {
		return marked;
	}

	public void mark() {
		marked = true;
	}

	public void unmark() {
		marked = false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Direction dir : dirs)
			sb.append(dir);
		if(marked)
			sb.append('*');
		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if(o == null)
			return false;
		if(!(o instanceof Cell))
			return false;
		Cell that = (Cell)o;

		for(Direction dir : Direction.values())
			if(this.hasDirection(dir) != that.hasDirection(dir))
				return false;
		if(this.marked() != that.marked())
			return false;
		return true;
	}
}
