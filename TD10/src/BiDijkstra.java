
// Algorithme de Dijkstra bidirectionnel
public class BiDijkstra {
	final Graph g; // le graphe de travail
	final int source; // source du plus court chemin recherche
	final int dest; // destination du plus court chemin recherche
	private final Dijkstra forward; // recherche de plus courts chemins depuis la source
	private final Dijkstra backward; // recherche de plus courts chemins depuis la destination
	private Dijkstra currentDijkstra, otherDijkstra; // sens de la prochaine iteration et celui oppose

	private int last; // sommet traite par la derniere iteration

	private Fenetre f; // fenetre pour la visualisation

	/* Methodes à compléter */

	// constructeur
	BiDijkstra(Graph g, int source, int dest) {
		this.g= g;
		this.source =source;
		this.dest = dest;
		this.forward = new Dijkstra(g,source,dest);
		this.backward = new Dijkstra(g.reverse(),dest,source);
		this.currentDijkstra = forward;
		this.otherDijkstra = backward;
		this.last = source;
	}

	// changer la direction de recherche
	void flip() {
		Dijkstra d = currentDijkstra;
		currentDijkstra = otherDijkstra;
		otherDijkstra = d;
	}

	// une iteration de Dijkstra bidirectionnel
	void oneStep() {
		last = currentDijkstra.oneStep();
		
	}

	// test de terminaison
	boolean isOver() {
		return (currentDijkstra.settled[last] && otherDijkstra.settled[last]);
	}

	// renvoyer la longueur du plus court chemin
	int getMinPath() {
		for( boolean x : forward.settled) {
			for(boolean y : backward.settled) {
				if(x && y) 
			}
		}
		return forward.dist[last] + backward.dist[last];
	}

	// algorithme de Dijkstra bidirectionnel
	int compute() {
		while(!isOver()) {
			oneStep();
			flip();
		}
		return getMinPath();
		
	}

	// calcule le nombre de pas exécuté par l'algorithme 
	public int getSteps() {
		throw new Error("a completer");
	}

	/* Methodes à ne pas modifier */

	int getLast() {
		return last;
	}

	public void setFenetre(Fenetre f) {
		this.f = f;
		forward.setFenetre(f);
		backward.setFenetre(f);
	}

	public void draw() {
		g.drawPath(f, forward.pred, backward.pred, last, last, last);
		g.drawSourceDestination(f, source, dest);
	}
}
