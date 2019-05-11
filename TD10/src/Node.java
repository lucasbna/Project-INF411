
@SuppressWarnings("rawtypes")
// Noeud d'un graphe
class Node implements Comparable {

  final int id; // identifiant
  final int val; // valeur

  // constructeur
  Node(int id, int val){
    this.id = id;
    this.val = val;
  }

  // fonction de comparaison
  public int compareTo(Object o) {
    Node that = (Node) o;
    
    if (this.val == that.val)
    	return (this.id - that.id);
    
    return this.val - that.val;
  }
}
