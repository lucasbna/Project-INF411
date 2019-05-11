
// Test de l'algorithme de Dijkstra basique 
public class Test3 {
	public static void main(String args[]) throws Exception {
		System.out.println("Test 3 : test de l'algorithme de Dijkstra basique ...");
	    
		// petit graphe
		Graph g = new Graph("mini.gr");
		Dijkstra d = new Dijkstra(g, 2, 7);
		d.update(4, 2);
		assert (d.dist[4] == 2): "\npetit graphe\n" + g 
				+ "\nDistance mal calculée après une itération de update : "
				+ "vérifier l'ordre des arguments dans l'appel à weight()";
		
		d = new Dijkstra(g, 2, 7);	
		int result = d.compute();
		assert (result == 11) : "\npetit graphe\n" + g 
				+ "Le plus court chemin entre 2 et 7 devrait etre 11 et non pas " + result;
		
		//System.out.println("plus court chemin entre 2 et 7 = " + d.compute());
		d = new Dijkstra(g, 7, 2);
		result = d.compute();	
		assert(result == -1): "\npetit graphe\n" + g 
				+ "Il n'y a pas de chemin entre 7 et 2 et vous en trouvez un de longueur " + result;

		// gros graphe
		//System.out.println("\ngros graphe");
		g = new Graph("USA-road-d-NY.gr");
	    d = new Dijkstra(g, 190637, 187333);
		result = d.compute();	
		assert (result == 39113) : "\nLe plus court chemin entre 190637 et 187333 devrait etre 39113 et non pas "
				+ result;

		System.out.println("[OK]");
	  }
}
