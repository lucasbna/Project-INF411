
// Test de l'algorithme de Dijkstra bidirectionnel sur un gros graphe
public class Test7 {
	public static void main(String args[]) throws Exception {
		System.out.println("Test 7 : test de l'algorithme de Dijkstra bidirectionnel");


		// algorithmes de Dijkstra dans les deux sens
		Graph g = new Graph("USA-road-d-NY.gr");
		int source = 190636, destination = 187333;
		Dijkstra forward = new Dijkstra(g, source, destination);
		int forwardPath = forward.compute();
		Dijkstra backward = new Dijkstra(g.reverse(), destination, source);
		int backwardPath = backward.compute();

		// algorithme de Dijkstra bidirectionnel
		g.setCoordinates("USA-road-d-NY.co");
		Fenetre f;
		f = new Fenetre("NY_Metropolitan.png", "Dijkstra", -73.9987, -73.9437, 40.7523, 40.78085);
		g.drawGraph(f);
		f.repaint();
		
	    BiDijkstra bothways = new BiDijkstra(g, source, destination);
		bothways.setFenetre(f);
	    int bothwaysPath = bothways.compute();
	    
	    bothways.draw();
	    
	    // resultats
		System.out.println("longueur chemin forward    entre " + source + " et " + destination + " = " + forwardPath);
		System.out.println("longueur chemin backward   entre " + source + " et " + destination + " = " + backwardPath);
		System.out.println("longueur chemin bidijkstra entre " + source + " et " + destination + " = " + bothwaysPath);
	}
}
