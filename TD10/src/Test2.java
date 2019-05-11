// Test de l'initialisation de la classe Dijkstra
public class Test2 {
	public static void main(String args[]) throws Exception {
		// Pour s'assurer que les assert's sont activés
		if (!Test2.class.desiredAssertionStatus()) {
			System.err.println("Vous devez activer l'option -ea de la JVM");
			System.err
					.println("(Run As -> Run configurations -> Arguments -> VM Arguments)");
			System.exit(1);
		}
		System.out.println("Test 2 : initialisation de la classe Dijkstra  ....");
		Graph g = new Graph("mini.gr");
		Dijkstra d = new Dijkstra(g, 2, 7);
		assert (d.source == 2) : "\n Vous n'avez pas identifié correctement la source de Dijkstra";
		assert (d.dest == 7) : "\n Vous n'avez pas identifié correctement la destination de Dijkstra";
		for (int i = 0; i < d.dist.length; i++) {
			test_init_dist(i, d);
			test_init_pred(i, d);
			test_init_settled(i, d);
		}
		System.out.println("[OK]");
	}

	private static void test_init_dist(int i, Dijkstra d) {
		if (i != d.source) {
			assert (Integer.MAX_VALUE == d.dist[i]) : "\nVous n'avez pas bien initialisé la distance pour le sommet \n"
					+ i;
		} else {
			assert (0 == d.dist[i]) : "\nLa distance de la source n'est pas bien initialisée";
		}
	}

	private static void test_init_pred(int i, Dijkstra d) {
		if (i != d.source) {
			assert (-1 == d.pred[i]) : "\nVous n'avez pas bien initialisé le prédecesseur pour le sommet \n"
					+ i;
		} else {
			assert (i == d.pred[i]) : "\nLe prédecesseur de la source n'est pas bien initialisée";
		}
	}

	private static void test_init_settled(int i, Dijkstra d) {
		assert (d.settled[i] == false) : "\nVous n'avez pas bien initialisé le champ settled pour le sommet \n"
				+ i;

	}
}
