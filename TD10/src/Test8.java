
// comparaisons du nombre d'etapes
public class Test8 {
	public static void main(String args[]) throws Exception {
		System.out.println("Test 8 : comparaisons du nombre d'étapes");

		Graph g = new Graph("USA-road-d-NY.gr");
	    int source = 190636, destination = 187333;
	    Dijkstra forward = new Dijkstra(g,source,destination);
	    System.out.println("longueur chemin forward    entre " + source + " et " + destination + " = " + forward.compute() + "    nombre d'étapes = " + forward.getSteps());
	    Dijkstra backward = new Dijkstra(g.reverse(),destination,source);
	    System.out.println("longueur chemin backward   entre " + source + " et " + destination + " = " + backward.compute() + "    nombre d'étapes = " + backward.getSteps());
		BiDijkstra bothways = new BiDijkstra(g,source,destination);
	    System.out.println("longueur chemin bidijkstra entre " + source + " et " + destination + " = " + bothways.compute() + "    nombre d'étapes = " + bothways.getSteps());
	  }
}
