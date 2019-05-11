
public class Triple {
	public Row r1;
	public Row r2;
	public int height;

	public Triple(Row r1, Row r2, int height) {
		this.r1 = r1;
		this.r2 = r2;
		this.height = height;
	}

	public boolean equals(Object o) {
		Triple that = (Triple) o;
		return(this.r1.equals(that.r1) && this.r2.equals(that.r2) && this.height == that.height);
	}
	
	public int hashcode() {
		
	}
}
