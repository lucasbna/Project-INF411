

public class Test1 {


	
	private static void test_isDeck(boolean e, int nbVals, String s){
		Deck d = new Deck(s);
		boolean b = d.isDeck(nbVals);
		assert( b == e ):"\n"+d+"\nis"+(e?" ":" not ")+"a valid deck from "+nbVals+" cards"; 
	}
	
	private static void test_pick(Integer e,String i1, String i2,String o1,String o2){
		Deck a = new Deck(i1);
		Deck b = new Deck(i2);
		Deck c = new Deck(o1);
		Deck d = new Deck(o2);
		Integer x = a.pick(b) ;
		assert( (x == null && e == null) || ( x != null && e != null && x.equals(e) ) ):"\nLa méthode pick devrait renvoyer "+e+" au lieu de "+x;
		assert( a.equals(c)):"\na devrait être\n"+c+"\nau lieu de \n"+a;
		assert( b.equals(d)):"\nb devrait être\n"+d+"\nau lieu de \n"+b;
	}

	private static void test_pickAll(String i1, String i2,String o1){
		Deck a = new Deck(i1);
		Deck b = new Deck(i2);
		Deck c = new Deck(o1);
		a.pickAll(b);
		assert( a.equals(c)):"\na devrait être\n"+c+"\nau lieu de \n"+a;
		assert( b.isEmpty()):"\nb devrait être vide au lieu de \n"+b;
	}
	
	public static void main(String[] args) {

		//Pour s'assurer que les assert's sont activés
		if (!Test1.class.desiredAssertionStatus()) {
	        System.err.println("Vous devez passer l'option -ea à la machine virtuelle Java.");
	        System.err.println("Voir la section « Activer assert » du préambule du TD.");
	        System.exit(1);
	      }
		
		//Test de la méthode isDeck
		test_isDeck(true,1,"");
		test_isDeck(true,1,"1 1");
		test_isDeck(true,1,"1 1");
		test_isDeck(false,1,"1 1 1 1 1");
		test_isDeck(false,1,"1 1 1 2");
		test_isDeck(true,2,"1 1 1 2");
		test_isDeck(false,3,"3 3 3 3 3");
		test_isDeck(true,3,"1 2 2 3 2 2 1 3 3");
		test_isDeck(true,3,"1 3 1 3 3");
		System.out.println("La méthode isDeck a passé les tests avec succès.");
		
		//Test de la méthode pick
		test_pick(null,"","","","");
		test_pick(null,"1 2","","1 2","");
		test_pick(4,"1 2 3","4 5 6","1 2 3 4","5 6");
		test_pick(1,"","1","1","");
		System.out.println("La méthode pick a passé les tests avec succès.");
		
		//Test de la méthode pickAll
		test_pickAll("","","");
		test_pickAll("1 1","","1 1");
		test_pickAll("1 2 3","4 5 6","1 2 3 4 5 6");
		test_pickAll("","1 2 3","1 2 3");
		System.out.println("La méthode pickAll a passé les tests avec succès.");
		
		
	}
}
