public class Test41 {
	public static void main(String[] args) {
		try {
			assert false;
			System.err.println("Vous devez activer l'option -ea de la JVM");
			System.err.println("(Run As -> Run configurations -> Arguments -> VM Arguments)");
			System.exit(1);
		} catch (AssertionError e) {
		}

		System.out.println("Test de la methode compressionRate... ");
	
		System.out.print("\tTaux de compression pour Mondrian1:");
		double res = EfficiencyMeasure.compressionRate(new BinaryImage(
				"images/Mondrian1.png"));
		System.out.print(" " + res+'\n');
		test_compression(res, 0.1672, "images/Mondrian1.png");
		
		System.out.print("\tTaux de compression pour Mondrian2:");
		res = EfficiencyMeasure
				.compressionRate(new BinaryImage("images/Mondrian2.png"));
		System.out.print(" " + res+'\n');
		test_compression(res, 0.5698, "images/Mondrian2.png");

		System.out.print("\tTaux de compression pour Pollock1:");
		res = EfficiencyMeasure
				.compressionRate(new BinaryImage("images/Pollock1.png"));
		System.out.print(" " + res+'\n');
		test_compression(res, 0.388, "images/Pollock1.png");

		System.out.print("\tTaux de compression pour Pollock2:");
		res = EfficiencyMeasure
				.compressionRate(new BinaryImage("images/Pollock2.png"));
		System.out.print(" " + res +'\n');
		test_compression(res, 0.7155, "images/Pollock2.png");
//		
//		System.out.print("\tTaux de compression pour Random:");
//		res = EfficiencyMeasure
//				.compressionRate(EfficiencyMeasure.randomBinaryImage(512, (double) 0.5));
//		System.out.print(" " + res +'\n');
//		
		System.out.println("[OK]");

	}

	private static void test_compression(double res, double t, String s) {
		assert ((res - t < 0.001) && (res - t > -0.001)) : "\nVous obtenez un taux de compression egal a \n"
				+ res + "\nau lieu de \n" + t + "\n pour l'image " + s;
	}
}
