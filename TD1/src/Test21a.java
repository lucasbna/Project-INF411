
public class Test21a {
	public static void main(String[] args) {  
		if (!Test21a.class.desiredAssertionStatus()) {
	        System.err.println("Vous devez passer l'option -ea à la machine virtuelle Java.");
	        System.err.println("Voir la section « Activer assert » du préambule du TD.");
	        System.exit(1);
	      }
		// n=taille du jeu, m=nombre de coupes aleatoires
		int n=52, m=100000;
    	double[] hist = new double[n+1];
    	Deck d=new Deck(13);
    	// on calcule m coupes aleatoires selon la loi binomiale
    	for (int i=0; i<m; i++)
    		hist[d.cut()]++;
    	// on calcule la deviation de la distribution obtenue par rapport a la vraie loi binomiale en norme sup
    	double max = 0;
    	for (int i=0; i<n; i++) {
    		// normalisation de la distribution obtenue
    		hist[i] /= m;
    		// calcul du coefficient binomial
    		double coeff = 1;
    		for (int j=1; j<=i; j++)
    			coeff *= (double)(n+1-j)/j/2;
    		coeff /= (long)1<<(n-i);
    		// comparaison avec le nombre d'occurences trouve
    		max = Math.max(max, (double)Math.abs(coeff-hist[i]));
    	}
    	//System.out.println("Deviation from binomial law: " + max);
    	//max = 0.0031 ;
    	assert(max < 0.003):("\nPour n=52 et m=100000, la déviation en norme sup ("+ max +") dépasse 0.003 .\n"
    			+ "Il y a sans doute un problème");
    	assert(max < 0.0025):("\nPour n=52 et m=100000, la déviation en norme sup ("+ max +") dépasse 0.0025 .\n"
    			+ "Ça peut arriver mais c'est rare. Recommencez le test.");
    	System.out.println("Déviation en norme sup = "+max);
    	System.out.println("La méthode cut a passé les tests");
    }
}
