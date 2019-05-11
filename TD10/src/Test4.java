
// Test de l'algorithme de Dijkstra basique sur un gros graphe
public class Test4 {
	public static void main(String args[]) throws Exception {
		System.out.println("Test 4 : test de l'algorithme de Dijkstra basique sur un gros graphe");
		
		// preparation de la fenetre
		Graph g=new Graph("USA-road-d-NY.gr");
		g.setCoordinates("USA-road-d-NY.co");
		Fenetre f;
		f = new Fenetre("NY_Metropolitan.png", "Dijkstra", -73.9987, -73.9437, 40.7523, 40.78085);
		g.drawGraph(f);
		f.repaint();

		// algorithme de Dijkstra
		int source = 190637, destination = 187333;
		Dijkstra d = new Dijkstra(g, source, destination);
		d.setFenetre(f);
		System.out.println("plus court chemin entre " + source + " et " + destination + " = " + d.compute());
		d.draw();
	}
}
