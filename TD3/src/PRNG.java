
/**
 * Generateur de nombres pseudo-aleatoires Lagged Fibonacci Generator
 */

public class PRNG {
	private int[] state;
	private final int stateSize = 55;
	private final int modulus;
	private int index = 0;

	// constructeur
	public PRNG() {
		this(1000000); // valeur par defaut: 10^6
	}

	public PRNG(int modulus) {
		this.modulus = modulus;
		this.state = new int[this.stateSize];
	}

	// calcule le terme suivant, met a jour l'etat interne, met a jour l'indice, et
	// renvoie ce terme
	public int getNext() {
		if(this.index < 55) {
			this.state[this.index] = (((((300007*this.index+900021)%this.modulus)*this.index+700018)%this.modulus)*this.index+200007)% this.modulus;
		}
		if (this.index >= 55) {
				this.state[this.index % 55] = (this.state[(this.index-24)%55]+this.state[(this.index-55)%55])%1000000;
			}
		this.index ++;
		return this.state[(this.index-1)%55];
		}

	// affichage de l'etat interne
	public void PrintState() {
		System.out.print("state = [");
		for (int i = 0; i < this.stateSize - 1; i++) {
			System.out.print(this.state[i] + ", ");
		}
		System.out.print(this.state[this.stateSize - 1] + "];\n");
	}
}
