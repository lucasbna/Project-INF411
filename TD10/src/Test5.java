// Test de l'algorithme de Dijkstra bidirectionnel naif
public class Test5 {
	public static void main(String args[]) throws Exception {
		System.out.println("Test 5 : test de l'algorithme de Dijkstra bidirectionnel naif");

		// algorithmes de Dijkstra dans les deux sens
		Graph g = new Graph("USA-road-d-NY.gr");
		int source = 190637, destination = 187333;
		Dijkstra forward = new Dijkstra(g, source, destination);
		int forwardPath = forward.compute();
		Dijkstra backward = new Dijkstra(g.reverse(), destination, source);
		int backwardPath = backward.compute();

		// algorithme de Dijkstra bidirectionnel naif
		g.setCoordinates("USA-road-d-NY.co");
		
	    BiDijkstra bothways = new BiDijkstra(g, source, destination);
	    bothways.oneStep();
	    bothways.oneStep();
	    assert (bothways.getLast() != source): "\nlast n'a pas changé après deux itérations : "
	    		+ "\nsource = " + source 
	    		+ "\nlast = " + bothways.getLast () 
	    		+ "\nlast devrait être différent de source"
	    		+ "\nn'oubliez pas de le mettre à jour dans oneStep()";
	    
	    Fenetre f;
	    f = new Fenetre("NY_Metropolitan.png", "Dijkstra", -73.9987, -73.9437, 40.7523, 40.78085);
	    g.drawGraph(f);
	    f.repaint();

	    bothways = new BiDijkstra(g, source, destination);
		bothways.setFenetre(f);
	    int bothwaysPath = bothways.compute();
	    assert(bothwaysPath == 39113): (bothwaysPath == 39122 ? "N'oubliez pas que le graphe de backward doit être retourné" : "BiDijkstra incorrect");

	    bothways.draw();

	    // resultats
		System.out.println("longueur chemin forward    entre " + source + " et " + destination + " = " + forwardPath);
		System.out.println("longueur chemin backward   entre " + source + " et " + destination + " = " + backwardPath);
		System.out.println("longueur chemin bidijkstra entre " + source + " et " + destination + " = " + bothwaysPath);
	}
}
