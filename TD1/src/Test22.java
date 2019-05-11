public class Test22 {
	
	private static Integer missing(int[] cards){
		for(int i = 1 ; i < cards.length ; i++){
			if (cards[i] < 4) return i;
		}
		return null;
	}

	private static Integer overload(int[] cards){
		if (cards[0] > 0) return 0;
		for(int i = 1 ; i < cards.length ; i++){
			if (cards[i] > 4) return i;
		}
		return null;
	}
	
	public static void main(String[] args) {
		if (!Test22.class.desiredAssertionStatus()) {
	        System.err.println("Vous devez passer l'option -ea à la machine virtuelle Java.");
	        System.err.println("Voir la section « Activer assert » du préambule du TD.");
	        System.exit(1);
	      }
		Deck d = null;
		Deck s = null;
		Deck a = null;
		Deck b = null;
		int nbTests = 100 ;
		for (int i = 0; i < nbTests; i++) {
			d = new Deck(13);
			s = d.split();
			a = s.copy(); //duplicata
			b = d.copy(); //duplicata
			d.riffleWith(s);
			//a-t-on bien toutes les cartes
			String errorMsg = "\nriffle = "+d.toString();
			Integer x = (missing(d.countCards(13))) ;
			assert (x == null) : "il manque une carte de valeur " + x + errorMsg ;
			x = (overload(d.countCards(13))) ;
			if(x != null){
			assert (x != 0) : "Il y a un intrus" + errorMsg ;
			assert (x == null) : "il y a un carte de valeur " + x + " en trop" + errorMsg ;}
			//assert (d.isFullDeck(13)) : "riffle buggé\na=" + a.toString()
			//		+ "\nb=" + b.toString() + "\nriffle result=" + d.toString();
			//l'ordre induit sur les deux sous-blocs est-il respecté ?
			assert (a.extractSubdeck(d)) : "On ne retrouve pas le paquet\n"+a.toString()+"\ndans"+errorMsg ;
			assert (b.equals(d)) : "On ne retrouve pas le paquet\n"+b.toString()+"\ndans"+errorMsg ;
		}
		System.out.println("La méthode riffleWith a passé "+nbTests+" tests avec succès");
	}

}
