import java.util.LinkedList;
import java.util.Vector;

public class HashTable {
	final static int M = 50000;
	Vector<LinkedList<Quadruple>> buckets;

	public HashTable() {
		this.buckets = new Vector<LinkedList<Quadruple>>(M);
		for (int i = 0; i < M; i++) {

			this.buckets.add(new LinkedList<Quadruple>());
		}
	}

	public int hashCode(Row r1, Row r2, int height) {
		return (r1.hashCode() + r2.hashCode()) * height;
	}

	public int bucket(Row r1, Row r2, int height) {
		int h = hashCode(r1, r2, height);
		int v = h % M;
		if (v < 0)
			v = v + M;
		return v;
	}
	
	public void add(Row r1, Row r2, int height, long result) {
		Quadruple Q = new Quadruple(r1,r2,height,result);
		int s = bucket(r1,r2,height);
		this.buckets.get(s).add(Q);
	}
	
	public Quadruple find(Row r1, Row r2, int height) {
		int s = bucket(r1,r2,height);
		for (Quadruple Q : this.buckets.get(s)) {
			if(Q.r1.equals(r1) && Q.r2.equals(r2) && Q.height ==height) return Q;
		}
		return null;
	}
}
