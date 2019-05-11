public class Test3 {
	public static void main(String[] args) {
		//Pour s'assurer que les assert's sont activés
		if (!Test3.class.desiredAssertionStatus()) {
			System.err.println("Vous devez activer l'option -ea de la JVM");
		        System.err.println("(Run As -> Run configurations -> Arguments -> VM Arguments)");
			System.exit(1);
		}

		HashTable t = new HashTable();

		assert(t.buckets != null) : "\nfield \"buckets\" not initialized in the constructor!";

		System.out.print("testing methods add and find... ");

		Row r1 = new Row(new int[]{0,0,1,0,1});
		Row r2 = new Row(new int[]{1,0,1,0,1});
		Row r3 = new Row(new int[]{0,0,1,1,0});
		Row r4 = new Row(new int[]{0,1,1,1,0});

		Quadruple q;

		q = t.find(r1, r2, 2);
		assert (q == null) : "\nfound an entry in an empty table!";

		t.add(r1, r2, 2, 23);
		t.add(r3, r4, 3, 42);

		q = t.find(r1, r2, 2);
		assert (q != null && q.result == 23) : "\nexisting entry not found in table!";
		q = t.find(r3, r4, 3);
		assert (q != null && q.result == 42) : "\nexisting entry not found in table!";

		q = t.find(r3, r4, 2);
		assert (q == null) : "\nfound an entry with the wrong height!";

		// tests avec des collisions nombreuses
		Row[] rows = new Row[1024];
		for (int i = 0; i < 1024; i++) {
			int[] bits = new int[10];
			for (int j = 0; j < 10; j++)
				bits[j] = (i >> j) & 1;
			rows[i] = new Row(bits);
		}
		for (int i1 = 0; i1 < 1024; i1++)
			for (int i2 = 0; i2 < 1024; i2++)
				t.add(rows[i1], rows[i2], i1, i2);
		for (int i1 = 0; i1 < 1024; i1++)
			for (int i2 = 0; i2 < 1024; i2++) {
				q = t.find(rows[i1], rows[i2], i1);
				assert (q != null && q.result == i2) : "\nexisting entry not found in table";
			}

		// tests qu'on utilise bien equals
		r1 = new Row(new int[] {0});
		t.add(r1, r1, 2, 1);
		r2 = new Row(new int[] {0});
		assert (t.find(r2, r2, 2) != null) : "\nPlease use equals, not ==, for finding entries";

		System.out.println("\t\t[OK]");
	}
}
