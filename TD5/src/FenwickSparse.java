import java.util.Random;

class FenwickSparse {
	private FenwickSparse left;
	private FenwickSparse right;
	private final int lo;
	private final int hi;
	int acc;

	FenwickSparse(int lo, int hi) {
		this.lo = lo;
		this.hi = hi;
		acc = 0;
		left = null;
		right = null;
	}

	void inc(int i) {
		// TODO
	}

	int cumulative(int i) {
		// TODO
		return 0;
	}

	/*
	 * TESTS
	 */

	private void test_wellFormed() {
		if (left == null && right == null && (hi == lo + 1 || acc == 0))
			return;

		assert left != null && right != null : String.format("Le nœud interne [%d, %d[ n'a qu'un seul fils.", lo, hi);
		assert left.lo == lo && right.hi == hi && left.hi == right.lo : String.format(
				"Le nœud [%d, %d[ est mal recouvert par ses nœuds fils [%d, %d[ et [%d, %d[.", lo, hi, left.lo,
				right.hi, right.lo, right.hi);

		assert acc > 0 : String.format("Le nœud [%d, %d[ contient zero, il ne devrait pas avoir de fils.", lo, hi);
		left.test_wellFormed();
		right.test_wellFormed();
	}

	private void test_wellMaintained() {
		if (left != null && acc > 0) {
			assert acc == left.acc + right.acc : String.format(
					"L'accumulateur de l'intervalle [%d,%d[ n'est pas égal\n"
							+ "à la sommes des accumulateurs des fils : %d != %d + %d",
					lo, hi, acc, left.acc, right.acc);
			left.test_wellMaintained();
			right.test_wellMaintained();
		}
	}

	static void test_constructor(int size, int offset) {
		FenwickSparse t = new FenwickSparse(offset, offset + size);

		t.test_wellFormed();
	}

	private FenwickSparse _get(int i) {
		return lo <= i && i < hi
				? (left == null ? this : (left._get(i) == null) ? right._get(i) : left._get(i))
				: null;
	}

	static void test_inc(int size, int offset) {
		FenwickSparse t = new FenwickSparse(offset, offset + size);

		t.test_wellFormed();

		long startTime = System.currentTimeMillis();

		int[] acc = new int[size];
		Random r = new Random();
		int nb = size;

		for (int i = 0; i < nb; i++) {
			int s = r.nextInt(size);
			acc[s]++;
			t.inc(offset + s);
		}

		long endTime = System.currentTimeMillis();
		System.out.println(String.format("%d appels à 'inc' dans [%d, %d[ en %d millisecondes.", nb, t.lo,
				t.hi, endTime - startTime));

		if (endTime > startTime + 80)
			System.out.println("L'implémentation de 'inc' est lente.");

		assert endTime < startTime + 660 : "L'implémentation de inc n'a probablement pas une complexité logarithmique.";

		t.test_wellFormed();
		t.test_wellMaintained();

		for (int i = 0; i < size; i++) {
			assert (acc[i] == 0) || (acc[i] == t._get(offset + i).acc) : String
					.format("La valeur de l'accumulateur à la feuille %d est incorrecte.", offset + i);
		}
	}

	static void test_cumulative(int size, int offset) {
		FenwickSparse t = new FenwickSparse(offset, offset + size);

		t.test_wellFormed();
		t.test_wellMaintained();

		int[] acc = new int[size];
		Random r = new Random();
		int nb = 100000;
		for (int i = 0; i < nb; i++) {
			int s = r.nextInt(size);
			acc[s]++;
			t.inc(offset + s);
		}

		t.test_wellFormed();
		t.test_wellMaintained();

		assert t.cumulative(offset) == 0 : "cumulative(lo) doit être égal à zéro.";
		assert t.cumulative(
				offset + size + 10) == nb : "Si i >= hi, cumulative(i) doit renvoyer la somme de tous les éléments.";

		for (int i = 0; i < size; i++) {
			assert acc[i] == t.cumulative(offset + i + 1)
					- t.cumulative(offset + i) : "Mauvaise valeur de retour pour cumulative.";
		}
	}

	public static void main(String[] args) {
		try {
			assert false;
			System.out.println("Assertions désactivées.");
			System.exit(1);
		} catch (AssertionError e) {
		}

		System.out.print("Test du constructeur... ");
		test_constructor(16, 0);
		test_constructor(31, 0);
		test_constructor(32, 0);
		test_constructor(33, 0);
		test_constructor(145, 12);
		System.out.println("\t\tOK.");

		System.out.print("Test de inc... \n");
		test_inc(16, 0);
		test_inc(31, 0);
		test_inc(32, 0);
		test_inc(33, 0);
		test_inc(145, 12);
		test_inc(1000, 0);
		test_inc(10000, 0);
		test_inc(100000, 0);
		System.out.println("\t\t\t\t\tOK.");

		System.out.print("Test de cumulative... ");
		test_cumulative(16, 0);
		test_cumulative(31, 0);
		test_cumulative(32, 0);
		test_cumulative(33, 0);
		test_cumulative(145, 12);
		test_cumulative(1000, 0);
		test_cumulative(10000, 0);
		test_cumulative(100000, 0);
		System.out.println("\t\t\tOK.");

	}
}
