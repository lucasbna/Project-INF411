import java.util.PriorityQueue;

// Algorithme de Dijkstra
public class Dijkstra {
	final Graph g; // le graphe de travail
	final int source; // source du plus court chemin recherche
	final int dest;
	int[] dist;// destination du plus court chemin recherche
	private Fenetre f; // fenetre pour la visualisation
	int[] pred;
	boolean[] settled;
	PriorityQueue<Node> unsettled;

	/* Méthodes à compléter */

	// constructeur
	Dijkstra(Graph g, int source, int dest) {
		this.dist = new int[g.nbVertices];
		this.g = g;
		this.source = source;
		this.dest = dest;
		this.pred = new int[g.nbVertices];
		this.settled = new boolean[g.nbVertices];
		for( int i =0; i < g.nbVertices; i++) {
			this.pred[i] =-1;
			this.settled[i] = false;
			this.dist[i] = Integer.MAX_VALUE;
		}
		this.dist[source] = 0;
		this.pred[source] = this.source;
		this.unsettled = new PriorityQueue<Node>();
		unsettled.add(new Node(source,0));
	}

	// mise a jour de la distance, de la priorite, et du predecesseur d'un sommet
	void update(int succ, int current) {
		if (dist[succ] > dist[current] +g.weight(succ,current)) {
			dist[succ] = dist[current] +g.weight(succ,current);
			unsettled.add(new Node(succ,dist[succ]));
			g.drawUnsettledPoint(f, succ);
			this.pred[succ] = current;
		}
		
	}

	// trouve le prochain sommet de unvisited non traite
	int nextNode() {
		if(unsettled.isEmpty()) return -1;
		for(Node x : unsettled) {
			if(settled[x.id]) unsettled.poll();
			return x.id;
		}
		return -1;
	}

	// une etape de l'algorithme Dijkstra
	int oneStep() {
		slow();
		int x = nextNode();
		if (x == -1) return -1;
		settled[x] = true;
		g.drawSettledPoint(f, x);
		for (int succ : g.successors(x)) update(succ,x);
		return x;
	}

	// algorithme de Dijkstra complet
	int compute() {
		int current = 0;
		while(current != dest && !unsettled.isEmpty()) {
			current = oneStep();
		}
		if (current == dest) return dist[current];
		return -1;
	}
	

	// calcule le nombre de pas exécuté par l'algorithme 
	public int getSteps() {
		throw new Error("a completer");
	}

	/* Méthodes à ne pas changer */

	void setFenetre(Fenetre f) {
		this.f = f;
	}

	public void draw() {
		g.drawSourceDestination(f, source, dest);
		g.drawPath(f, pred, dest);
	}

	// ralentisseur visualisation
	void slow() {
		if (f == null)
			return;
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
		}
	}
}
