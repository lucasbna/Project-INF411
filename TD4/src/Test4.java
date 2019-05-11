
public class Test4 {
	private static void assertPerfect(Maze m) {
		assert(m.isPerfect()) : "maze is not perfect";
	}

	public static void main(String[] args) {
		//Pour s'assurer que les assert's sont activés
		if (!Test1.class.desiredAssertionStatus()) {
			System.err.println("Vous devez activer l'option -ea de la JVM");
		        System.err.println("(Run As -> Run configurations -> Arguments -> VM Arguments)");
			System.exit(1);
		}

		System.out.print("testing generateEller()... ");
		Maze m = new Maze(25, 25);
		m.generateEller();
		assertPerfect(m);
		for(int k = 0; k < 10; ++k) {
			m = new Maze(5, 5, false);
			m.generateRec();
			assertPerfect(m);
		}
		System.out.println("\t\t[OK]");
	}
}
