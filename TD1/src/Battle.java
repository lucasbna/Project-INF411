// Battle version 2017

public class Battle {

	private int nbVals;
	private Deck trick;
	private Deck player1;
	private Deck player2;

	public Deck get_player1() {
		return player1;
	}

	public Deck get_player2() {
		return player2;
	}

	public Deck get_trick() {
		return trick;
	}
	
	public int get_nbVals() {
		return nbVals;
	}

	public String toString(){
		return
		"Joueur 1 a "+(player1.toString())+"\n"+
		"Joueur 2 a "+(player2.toString())+"\n"+
		(trick.isEmpty()?"Le pli est vide":
		"Le pli contient "+trick.toString())
		;
	}
	
	public boolean equals(Battle b){
		return player1.equals(b.player1) &&
				player2.equals(b.player2) &&
				trick.equals(b.trick)
				;
	}
	
	// La bataille sans cartes
	public Battle(){
		nbVals = 0;
		player1 = new Deck();
		player2 = new Deck();
		trick = new Deck();
	}

	// Dupliquer une partie
	public Battle copy(){
		Battle r = new Battle();
		r.nbVals = this.get_nbVals();
		r.player1 = this.player1.copy();
		r.player2 = this.player2.copy();
		r.trick = this.trick.copy();
		return r;
	}
	
	// Partie truquée (pour les tests)
	public Battle(int nbVals,Deck player1,Deck player2){
		this.nbVals = nbVals;
		this.player1 = player1;
		this.player2 = player2;
		trick = new Deck();
	}

	public Battle(int nbVals,String player1,String player2){
		this.nbVals = nbVals;
		this.player1 = new Deck(player1);
		this.player2 = new Deck(player2);
		this.trick = new Deck();
	}

	public Battle(int nbVals,Deck player1,Deck player2,Deck trick){
		this.nbVals = nbVals;
		this.player1 = player1;
		this.player2 = player2;
		this.trick = trick;
	}


	
	public Battle(int nbVals,String player1,String player2,String trick){
		this.nbVals = nbVals;
		this.player1 = new Deck(player1);
		this.player2 = new Deck(player2);
		this.trick = new Deck(trick);
	}
	
	// Question 3.1

	public Battle(int nbVals) {
		Deck player1 = new Deck(nbVals);
		player1.riffleShuffle(7);
		Deck player2 = new Deck();
		for(int i = 0; i<nbVals*2; i++) {
			player2.pick(player1);
		}
	}
	
	// Question 3.2
	
	// Question 3.3

	// Question 4.1

	// Question 4.2

}
