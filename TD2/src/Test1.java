public class Test1 {
	private static void test_equals(boolean e, Row r1, Row r2){
		boolean b = r1.equals(r2);
		assert (e == b) : "\nRow\n" + r1 + (e ? "is " : "is not ") + "equal to row\n" + r2;
	}

	private static void test_isValid(boolean e, Row r){
		boolean b = r.isValid();
		assert (e == b) : "\nRow\n" + r + (e ? "is " : "is ") + "valid\n";
	}

	private static void test_areStackable(boolean e, Row r1, Row r2, Row r3){
		boolean b = r1.areStackable(r2, r3);
		assert (e == b) : "\nRows\n" + r1 + r2 + r3 + (e ? "are " : "are not ") + "stackable\n";
	}

	public static void main(String[] args) {
		//Pour s'assurer que les assert's sont activés
		if (!Test1.class.desiredAssertionStatus()) {
			System.err.println("Vous devez activer l'option -ea de la JVM");
		        System.err.println("(Run As -> Run configurations -> Arguments -> VM Arguments)");
			System.exit(1);
		}

		System.out.print("testing method addFruit... ");

		Row r = new Row(new int[]{0,0,1,0,1});

		r = r.addFruit(1);
		test_equals(true, r, new Row(new int[]{0,0,1,0,1,1}));
		test_equals(false, r, new Row(new int[]{0,0,1,0,1}));
		test_equals(false, r, new Row(new int[]{0,0,1,0}));

		r = r.addFruit(0);
		test_equals(true, r, new Row(new int[]{0,0,1,0,1,1,0}));
		test_equals(false, r, new Row(new int[]{0,0,1,0,1,1,1}));
		test_equals(false, r, new Row(new int[]{0,0,1,0,1,1}));
		test_equals(false, r, new Row(new int[]{0,0,1,0,1}));

		System.out.println("\t\t[OK]");

		System.out.print("testing method isValid...   ");

		test_isValid(true, new Row(new int[]{}));
		test_isValid(true, new Row(new int[]{1}));
		test_isValid(true, new Row(new int[]{1,0}));
		test_isValid(true, new Row(new int[]{1,1}));
		test_isValid(true, new Row(new int[]{1,1,0}));
		test_isValid(true, new Row(new int[]{0,1,0}));
		test_isValid(true, new Row(new int[]{0,1,1}));
		test_isValid(true, new Row(new int[]{0,1,1}));
		test_isValid(false, new Row(new int[]{1,1,1}));
		test_isValid(false, new Row(new int[]{0,0,0}));
		test_isValid(false, new Row(new int[]{0,0,0,1}));
		test_isValid(false, new Row(new int[]{0,0,1,1,1}));
		test_isValid(true, new Row(new int[]{0,1,0,1,0,1}));
		test_isValid(false, new Row(new int[]{1,0,0,0}));
		test_isValid(true, new Row(new int[]{1,0,0,1}));
		test_isValid(true, new Row(new int[]{1,0,1,1}));

		System.out.println("\t\t[OK]");

		System.out.print("testing method areStackable... ");

		// taille differente
		test_areStackable(false,
				new Row(new int[]{1,0,1}),
				new Row(new int[]{0,1,1,0}),
				new Row(new int[]{1,1,0,0}));
		//test de la 1re colonne (au cas ou une boucle for commence a 1 au lieu de 0)
		test_areStackable(false,
				new Row(new int[]{1}),
				new Row(new int[]{1}),
				new Row(new int[]{1}));
		test_areStackable(false,
				new Row(new int[]{0}),
				new Row(new int[]{0}),
				new Row(new int[]{0}));

		test_areStackable(true,
				new Row(new int[]{1,0,1,1}),
				new Row(new int[]{0,1,1,0}),
				new Row(new int[]{1,1,0,0}));
		test_areStackable(false,
				new Row(new int[]{1,0,1,1}),
				new Row(new int[]{0,1,1,0}),
				new Row(new int[]{1,1,1,0}));
		test_areStackable(true,
				new Row(new int[]{0,0,0,1}),
				new Row(new int[]{0,1,1,0}),
				new Row(new int[]{1,1,0,0}));
		test_areStackable(false,
				new Row(new int[]{1,0,1,0}),
				new Row(new int[]{0,1,1,0}),
				new Row(new int[]{1,1,0,0}));
		test_areStackable(false,
				new Row(new int[]{1,0,1,0}),
				new Row(new int[]{0,1,1,0}),
				new Row(new int[]{1,1,1,0}));

		System.out.println("\t\t[OK]");
	}
}
