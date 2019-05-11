import java.awt.Color;
import java.io.*;
import java.util.*;

// Implementation d'un graphe
public class Graph {
	final static int cHash = 8; // constante utilisee dans la fonction de hachage
	final int nbVertices; // nombre de sommets
	final private int nbEdges; // nombre d'arcs
	final private ArrayList<Integer>[] succ; // successeurs de chaque sommet
	final private ArrayList<Integer>[] pred; // predecesseurs de chaque sommet
	final private HashMap<Edge, Integer> weights; // poids des aretes	
	private double[][] coordinates = null; // coordonnees des sommets pour affichage

	// constructeurs
	@SuppressWarnings("unchecked")
	public Graph(int nbVertices, int nbEdges) {
		this.nbVertices = nbVertices;
		this.nbEdges = nbEdges;
		this.succ = new ArrayList[nbVertices];
		this.pred = new ArrayList[nbVertices];
		this.weights = new HashMap<Edge, Integer>(cHash * nbVertices);

		for (int i = 0; i < nbVertices; i++) {
			succ[i] = new ArrayList<Integer>();
			pred[i] = new ArrayList<Integer>();
		}
	}

	public Graph(int nbVertices, int nbEdges, 
			ArrayList<Integer>[] succ, ArrayList<Integer>[] pred, HashMap<Edge, Integer> weights) {
		this.nbVertices = nbVertices;
		this.nbEdges = nbEdges;
		this.succ = succ;
		this.pred = pred;
		this.weights = weights;
	}

	@SuppressWarnings("unchecked")
	public Graph(int nbVertices, double p, int max) {
		this.nbVertices = nbVertices;
		int tmpNbEdges = 0;
		succ = new ArrayList[nbVertices];
		pred = new ArrayList[nbVertices];
		this.weights = new HashMap<Edge, Integer>(cHash * nbVertices);

		for (int i = 0; i < nbVertices; i++) {
			succ[i] = new ArrayList<Integer>();
			pred[i] = new ArrayList<Integer>();
		}

		for (int i = 0; i < nbVertices; i++) {
			for (int j = 0; j < nbVertices; j++) {
				if (Math.random() < p) {
					tmpNbEdges++;
					int val = (int) (max * Math.random());
					succ[i].add(j);
					pred[j].add(i);
					this.addWeightedArc(i, j, val);
				} else {
				}
			}
		}

		this.nbEdges = tmpNbEdges;
	}

	@SuppressWarnings("unchecked")
	public Graph(String file) throws Exception {
		System.out.print("Loading road networks from file " + file + " ... ");
		BufferedReader br = new BufferedReader(new FileReader(file));

		String dataLine = br.readLine();
		while (dataLine.charAt(0) != 'p')
			dataLine = br.readLine();

		String[] tokens = dataLine.split("\\s");
		this.nbVertices = Integer.parseInt(tokens[2]);
		this.nbEdges = Integer.parseInt(tokens[3]);

		succ = new ArrayList[nbVertices];
		pred = new ArrayList[nbVertices];
		this.weights = new HashMap<Edge, Integer>(cHash * nbVertices);

		for (int i = 0; i < nbVertices; i++) {
			succ[i] = new ArrayList<Integer>();
			pred[i] = new ArrayList<Integer>();
		}

		while ((dataLine = br.readLine()) != null) {
			tokens = dataLine.split("\\s");
			if (tokens[0].equals("a")) {
				int i = Integer.parseInt(tokens[1]);
				int j = Integer.parseInt(tokens[2]);
				int v = Integer.parseInt(tokens[3]);
				succ[i - 1].add(j - 1);
				pred[j - 1].add(i - 1);
				this.addWeightedArc(i - 1, j - 1, v);
			}
		}
		br.close();
		System.out.println("done");
	}

	// mise en place des coordonnees des sommets
	public void setCoordinates(String file) throws Exception {
		System.out.print("Loading geometric coordinates from file " + file + " ... ");
		BufferedReader br = new BufferedReader(new FileReader(file));

		String dataLine = br.readLine();
		while (dataLine.charAt(0) != 'p')
			dataLine = br.readLine();

		String[] tokens = dataLine.split("\\s");
		int nPoints = Integer.parseInt(tokens[4]);
		if (nPoints != this.nbVertices){
			br.close();
			throw new Error("The number of points does not match the number of nodes in the graph");
		}

		this.coordinates = new double[this.nbVertices][2];

		while ((dataLine = br.readLine()) != null) {
			tokens = dataLine.split("\\s");
			if (tokens[0].equals("v")) {
				int node = Integer.parseInt(tokens[1]);
				double x = Double.parseDouble(tokens[2]);
				double y = Double.parseDouble(tokens[3]);
				this.coordinates[node - 1][0] = x / 1000000.;
				this.coordinates[node - 1][1] = y / 1000000.;
			}
		}
		br.close();
		System.out.println("done");
	}

	public ArrayList<Integer> successors(int i) {
		return succ[i];
	}

	public ArrayList<Integer> predecessors(int i) {
		return pred[i];
	}

	// ajout d'un nouvel arc pondere
	public void addWeightedArc(int i, int j, int v) {
		this.weights.put(new Edge(i, j), v);
	}

	// poids de l'arc (i,j) s'il existe, très grand sinon
	public int weight(int i, int j) {
		if (!this.weights.containsKey(new Edge(i, j)))
			return Integer.MAX_VALUE;
		return this.weights.get(new Edge(i, j));
	}
	
	// renvoie le graphe ou toutes les orientations ont ete inversees
	public Graph reverse() {
		HashMap<Edge, Integer> map = new HashMap<Edge, Integer>(cHash * nbVertices);
		for (int i = 0; i < nbVertices; i++) {
			for (Integer j : this.succ[i]) {
				int val = this.weight(i, j);
				map.put(new Edge(j, i), val);
			}
		}
		Graph rg=new Graph(nbVertices, nbEdges, pred, succ, map);
		rg.coordinates=coordinates;
		return rg;
	}

	// affichage d'un graphe en chaine
	public String toString() {
		String s = "nbVertices=" + nbVertices + '\n' + "nbEdges=" + nbEdges + '\n';
		for (int i = 0; i < nbVertices; i++) {
			s = s + "Node " + i + ":" + '\n';
			for (int j : succ[i])
				s = s + "   " + i + " --> " + j + " (" + this.weight(i, j) + ")"
						+ '\n';
		}
		return s;
	}

	// fonctions d'affichage

	// dessin du graphe
	public void drawGraph(Fenetre f) {
		if (f != null && this.coordinates != null) { // verification donnees geometriques
			for (int i = 0; i < this.nbVertices; i++) {
				double x1 = this.coordinates[i][0];
				double y1 = this.coordinates[i][1];
				for (Integer j : this.pred[i]) {
					double x2 = this.coordinates[j][0];
					double y2 = this.coordinates[j][1];
					f.addSegment(x1, y1, x2, y2, 1, Color.BLACK);
				}
				f.addPoint(x1, y1, 1, Color.BLACK);
			}
		}
	}

	// dessiner un point traite (rouge)
	public void drawSettledPoint(Fenetre f, int p){
		if (f != null && this.coordinates != null) { // verification donnees geometriques
			f.addPoint(this.coordinates[p][0], this.coordinates[p][1], 3, Color.RED);
		}
	}

	// dessiner un point visité et a traiter (vert)
	public void drawUnsettledPoint(Fenetre f, int p){
		if (f != null && this.coordinates != null) { // verification donnees geometriques
			f.addPoint(this.coordinates[p][0], this.coordinates[p][1], 3, Color.GREEN);
		}
	}

	// dessiner la source et la destination
	public void drawSourceDestination(Fenetre f, int origin, int destination) {
		if (f != null && this.coordinates != null) { // verification donnees geometriques
			f.addPoint(this.coordinates[origin][0], this.coordinates[origin][1], 6, Color.BLUE);
			f.addPoint(this.coordinates[destination][0], this.coordinates[destination][1], 6, Color.BLUE);
		}
	}
	
	// dessiner un point special
	public void drawSpecialPoint(Fenetre f, int p) {
		if (f != null && this.coordinates != null) { // verification donnees geometriques
			f.addPoint(this.coordinates[p][0], this.coordinates[p][1], 6, Color.BLUE);
		}
	}

	// dessiner le chemin en utilisant l'information contenue dans pred
	public void drawPath(Fenetre f, int[] pred, int i) {
		if (f != null && this.coordinates != null) { // verification donnees geometriques
			double x1 = this.coordinates[i][0];
			double y1 = this.coordinates[i][1];
			while (pred[i] != -1 && pred[i] != i) {
				double x2 = this.coordinates[pred[i]][0];
				double y2 = this.coordinates[pred[i]][1];
				f.addSegment(x1, y1, x2, y2, 10, Color.BLUE);
				x1 = x2;
				y1 = y2;
				i = pred[i];
			}
		}
	}

	// dessiner le chemin forme de deux morceaux se rejoignant
	public void drawPath(Fenetre f, int[] predF, int[] predB, int x, int i, int j) {
		if (f != null && this.coordinates != null) { // verification donnees geometriques
			if (i == -1) {
				drawPath(f, predF, x);			
				drawPath(f, predB, x);
				drawSpecialPoint(f, x);
			}
			else {
				f.addSegment(this.coordinates[i][0], this.coordinates[i][1], this.coordinates[j][0], this.coordinates[j][1], 10, Color.BLUE);
				drawPath(f, predF, i);
				drawPath(f, predB, j);
				drawSpecialPoint(f, i);
				drawSpecialPoint(f, j);
			}
		}
	}
}