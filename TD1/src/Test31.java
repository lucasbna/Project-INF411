
public class Test31 {

	public static void main(String[] args) {
		if (!Test31.class.desiredAssertionStatus()) {
	        System.err.println("Vous devez passer l'option -ea à la machine virtuelle Java.");
	        System.err.println("Voir la section « Activer assert » du préambule du TD.");
	        System.exit(1);
	      }
		Battle b = new Battle(13);
		Deck d = new Deck(13);
		Deck p1 = new Deck(13);
		Deck p2 = new Deck(13);
		int nbVals = b.get_nbVals();
		for(int i = 0 ; i < 1000 ; i++){
			b = new Battle(13);
			d = new Deck();
			p1 = b.get_player1() ;
			p2 = b.get_player2() ;
			//System.out.println("p1 contains "+p1.toString());
			//System.out.println("p2 contains "+p2.toString());
			int s1 = p1.size();
			int s2 = p2.size();
			assert(s1==26):"Joueur 1 a "+s1+" cartes au lieu de "+(2 * nbVals);
			assert(s2==26):"Joueur 2 a "+s2+" cartes au lieu de "+(2 * nbVals);
			d.pickAll(p1);
			d.pickAll(p2);
			assert(d.isDeck(b.get_nbVals())):"\nCe qui suit n'est pas un paquet de cartes valide\n"+(d.toString());
		}
		System.out.println("Le constructeur Battle a passé les tests avec succès");
	}

}
