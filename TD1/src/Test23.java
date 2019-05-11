public class Test23 {

	private static int countNonZero(int [] a){
		int count = 0;
		for(int x:a){if(x!=0) count++;}
		return count;
	}
	
	public static int countOutOf(int a, int b, Deck d) {
		int count = 0;
		int nbVals = d.size() / 4;
		int[] da = d.toArray();
		int[] met = new int[nbVals];
		for (int i = 0; i < da.length - (b - 1); i++) {
			for (int j = 0; j < met.length; j++)
				met[j] = 0; // réinitialise
			for (int j = 0; j < b; j++) {
				met[da[i + j] - 1]++;
			}
			if (countNonZero(met) <= a) {
				count++;
				/*
				if((a==2) && (b==8)){
				for (int k = 0; k < b; k++)
					System.out.print((da[i + k])+" ");
				System.out.println("");
				}
				*/
			}
		}
		return count;
	}

	/*
	 * Dans un paquet supposé mélangé, il ne doit pas y avoir
	 * plus de 14 paires de cartes consécutives identiques
	 * plus de 2 séquences de 4 cartes identiques
	 * plus d'une séquence de 8 cartes dans laquelle n'apparait que 2 valeurs  
	*/
	
	
	private static boolean isPoorlyMixed(Deck d) {
		int count = 0;
		int threshold_twins = 13 ; 
		int threshold_1_out_of_4 = 2; 
		int threshold_2_out_of_8 = 1;
		int a = 1;
		int b = 2;
		count = countOutOf(a, b, d);
		if (count > threshold_twins) {
			System.out.println(b + " cartes consécutives identiques à " + count
					+ " reprises dans le deck\n" + d.toString());
			return true;
		}
		a = 1;
		b = 4;
		count = countOutOf(a, b, d);
		if (count > threshold_1_out_of_4) {
			System.out.println(b + " cartes consécutives contiennent au plus "
					+ a + " valeurs à " + count + " reprises dans le deck\n"
					+ d.toString());
			return true;
		}
		a = 2;
		b = 8;
		count = countOutOf(a, b, d);
		if (count > threshold_2_out_of_8) {
			System.out.println(b + " cartes consécutives contiennent au plus "
					+ a + " valeurs à " + count + " reprises dans le deck\n"
					+ d.toString());
			return true;
		}
		return false;
	}

	public static void main(String[] args) {

		if (!Test23.class.desiredAssertionStatus()) {
	        System.err.println("Vous devez passer l'option -ea à la machine virtuelle Java.");
	        System.err.println("Voir la section « Activer assert » du préambule du TD.");
	        System.exit(1);
	      }
		
		int bound = 1000000;

		int count_poorly_mixed = 0;

		for (int i = 0; i < bound; i++) {
			Deck d = new Deck(13); // un nouveau jeu
			d.riffleShuffle(7); // on mélange
			if (isPoorlyMixed(d))
				count_poorly_mixed++;
			if (count_poorly_mixed >= 5){
				System.out.println("Le mélange n'est peut-être pas très équitable");
				return;}
		}
		System.out.println("La méthode riffleShuffle a passé les tests avec succès");
	}

}
