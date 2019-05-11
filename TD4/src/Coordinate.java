
public class Coordinate {
	public final int i,j;

	public Coordinate(int i, int j) {
		this.i = i;
		this.j = j;
	}

	@Override
	public String toString() {
		return "(" + i + "," + j + ")";
	}

	@Override
	public boolean equals(Object o) {
		if(o == null)
			return false;

		if(!(o instanceof Coordinate))
			return false;

		Coordinate that = (Coordinate)o;

		return this.i == that.i && this.j == that.j;
	}

	@Override
	public int hashCode() {
		return 31*i + j;
	}
}
