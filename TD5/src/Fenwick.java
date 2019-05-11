import java.util.Random;

class Fenwick {
	private Fenwick left;
	private Fenwick right;
	private final int lo;
	private final int hi;
	int acc;

	Fenwick(int lo, int hi) {
		this.lo = lo;
		this.hi = hi;
		this.acc = 0;
		if (lo == hi - 1) {
			this.left = null;
			this.right = null;
		} else {
			int a = (lo + hi) / 2;
			this.left = new Fenwick(lo, a);
			this.right = new Fenwick(a, hi);
		}
	}

	Fenwick get(int i) {
		if (i < this.lo || i >= this.hi)
			return null;

		if (this.left == null && this.right == null)
			return this;
		else {
			if (i < ((this.lo + this.hi) / 2)) {
				return this.left.get(i);
			}
			return this.right.get(i);
		}
	}

	void inc(int i) {
		if (i < this.lo || i >= this.hi)
			return;
		if (this.left == null && this.right == null)
			this.acc += 1;
		else {
			if (i < ((this.lo + this.hi) / 2))
				this.left.inc(i);
			else
				this.right.inc(i);
			this.acc = this.left.acc + this.right.acc;
		}
	}

	int cumulative(int i) {
		if (i-1 < this.lo)
			return 0;
		if (i>this.hi)
			return this.acc;
		if (this.left == null && this.right == null)
			return this.acc;
		else {
			if (i < ((this.lo + this.hi) / 2))
				return this.left.cumulative(i);
			else
				return this.left.acc + this.right.cumulative(i);
		}
	}

	/*
	 * TESTS
	 */

	private int nbLeafs() {
		if (left == null) {
			return 1;
		} else {
			return left.nbLeafs() + right.nbLeafs();
		}
	}

	private int depth() {
		if (left == null) {
			return 0;
		} else {
			return 1 + Math.max(left.depth(), right.depth());
		}
	}

	private void test_wellFormed() {
		if (left == null && right == null && hi == lo + 1)
			return;

		assert left != null && right != null : String.format("Le nœud interne [%d, %d[ n'a qu'un seul fils.", lo, hi);
		assert left.lo == lo && right.hi == hi && left.hi == right.lo : String.format(
				"Le nœud [%d, %d[ est mal recouvert par ses nœuds fils [%d, %d[ et [%d, %d[.", lo, hi, left.lo,
				right.hi, right.lo, right.hi);

		left.test_wellFormed();
		right.test_wellFormed();
	}

	private void test_wellMaintained() {
		if (left != null) {
			assert acc == left.acc + right.acc : String.format(
					"L'accumulateur de l'intervalle [%d,%d[ n'est pas égal\n"
							+ "à la sommes des accumulateurs des fils : %d != %d + %d",
					lo, hi, acc, left.acc, right.acc);
			left.test_wellMaintained();
			right.test_wellMaintained();
		}
	}

	private Fenwick _get(int i) {
		return lo <= i && i < hi ? (left == null ? this : (left._get(i) == null) ? right._get(i) : left._get(i)) : null;
	}

	static void test_constructor(int size, int offset) {
		Fenwick t = new Fenwick(offset, offset + size);

		t.test_wellFormed();
		assert t.nbLeafs() == size : "Le nombre de feuilles n'est pas égal à la taille demandée.";
		assert (1 << t.depth()) < 2 * size : "La profondeur de l'abre est trop grande.";
	}

	static void test_get(int size, int offset) {
		Fenwick t = new Fenwick(offset, offset + size);
		t.test_wellFormed();

		for (int i = -3; i < size + 5; i++) {
			assert t.get(i) == t._get(i) : String
					.format("Mauvaise valeur de retour de get(%d) dans un arbre Fenwick(%d, %d)", i, t.lo, t.hi);
		}
	}

	static void test_inc(int size, int offset) {
		Fenwick t = new Fenwick(offset, offset + size);

		t.test_wellFormed();

		long startTime = System.currentTimeMillis();

		int[] acc = new int[size];
		Random r = new Random();
		int nb = 100000;

		for (int i = 0; i < nb; i++) {
			int s = r.nextInt(size);
			acc[s]++;
			t.inc(offset + s);
		}

		long endTime = System.currentTimeMillis();
		System.out.println(String.format("%d appels à 'inc' dans [%d, %d[ en %d millisecondes (%d appels/ms).", nb,
				t.lo, t.hi, endTime - startTime, (nb) / (endTime - startTime)));

		if (endTime > startTime + 80)
			System.out.println("L'implémentation de 'inc' est lente.");

		assert endTime < startTime + 400 : "L'implémentation de inc n'a probablement pas une complexité logarithmique.";

		t.test_wellFormed();
		t.test_wellMaintained();

		for (int i = 0; i < size; i++)
			assert acc[i] == t._get(offset + i).acc : "La valeur de l'accumulateur aux feuilles est incorrecte.";
	}

	static void test_cumulative(int size, int offset) {
		Fenwick t = new Fenwick(offset, offset + size);

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

		long startTime = System.currentTimeMillis();

		for (int i = 0; i < size; i++) {
			assert acc[i] == t.cumulative(offset + i + 1)
					- t.cumulative(offset + i) : "Mauvaise valeur de retour pour cumulative.";
		}

		long endTime = System.currentTimeMillis();
		System.out.println(String.format("%d appels à 'cumulative' dans [%d, %d] en %d millisecondes.", 2 * size, t.lo,
				t.hi, endTime - startTime));

		if (endTime > startTime + 60)
			System.out.println("L'implémentation de 'cumulative' est lente.");

		assert endTime < startTime
				+ 200 : "L'implémentation de cumulative n'a probablement pas une complexité logarithmique.";

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

		System.out.print("Test de get... ");
		test_get(16, 0);
		test_get(31, 0);
		test_get(32, 0);
		test_get(33, 0);
		test_get(100, 12);
		System.out.println("\t\t\t\tOK.");

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

		System.out.print("Test de cumulative... \n");
		test_cumulative(16, 0);
		test_cumulative(31, 0);
		test_cumulative(32, 0);
		test_cumulative(33, 0);
		test_cumulative(145, 12);
		test_cumulative(1000, 0);
		test_cumulative(10000, 0);
		test_cumulative(100000, 0);
		System.out.println("\t\t\t\t\tOK.");

	}
}
