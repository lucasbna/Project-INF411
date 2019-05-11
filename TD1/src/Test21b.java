public class Test21b {

	private static Deck REF = new Deck(13);

	private static int N = 100;

	public static void main(String[] args) {
		if (!Test21b.class.desiredAssertionStatus()) {
	        System.err.println("Vous devez passer l'option -ea à la machine virtuelle Java.");
	        System.err.println("Voir la section « Activer assert » du préambule du TD.");
	        System.exit(1);
	      }
		Deck a = null;
		Deck b = null;
		for (int i = 0; i < N; i++) {
			b = new Deck(13);
			a = b.split();
			Deck c = a.copy();
			Deck d = b.copy();
			a.pickAll(b);
			assert (a.equals(REF)) : ("Bug split:\na=" + c.toString() +"\nb="+d.toString());
		}

		System.out.println("La méthode split a passé les tests");
	}
}
