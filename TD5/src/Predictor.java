import java.util.Random;

class Predictor {
	private int wordsize;
	private int bits;
	private int data;
	private Fenwick freq;

	Predictor(int wordsize) {
		this.wordsize = wordsize;
		freq = new Fenwick(0, 1 << wordsize);
		bits = 0;
		data = 0;
	}

	private int mask(int i) {
		return i%(2<<this.wordsize-1) & i;
	}

	void write(boolean bit) {
		bits++;
		if (bit)
			this.data = mask(this.data << 1) + 1 ;
		else if (bit == false)
			this.data = mask(this.data << 1);
		if (bits == 8) {
			int i = this.data;
			freq.inc(i);
			this.bits = 0;
		}
	}

	boolean predictBit() {
		
	}

	boolean encode(boolean bit) {
		// TODO
		return true;
	}

	boolean decode(boolean bit) {
		// TODO
		return true;
	}

	/*
	 * TESTS
	 */

	static void test_mask() {
		Predictor p = new Predictor(7);
		assert p.mask(p.wordsize) == p.wordsize : String.format("Avec wordsize=%d, mask(%d) != %d.", p.wordsize, p.wordsize, p.mask(p.wordsize));
		assert p.mask(1 << p.wordsize) == 0 :  String.format("Avec wordsize=%d, mask(%d) != %d.", p.wordsize, 1 << p.wordsize, p.mask(1 << p.wordsize));
		assert p.mask(1+(3 << p.wordsize)) == 1 :  String.format("Avec wordsize=%d, mask(%d) != %d.", p.wordsize, (3 << p.wordsize)+1, p.mask((3 << p.wordsize)+1));

	}

	static void test_write() {
		Predictor p = new Predictor(16);
		int data = 0;
		for (int i = 0; i < 32 ; i++) {
			p.write(true);
			data = (2*data+1) % (1 << p.wordsize);
			assert data == p.data : "La variable data n'est pas bien maintenue.";
			assert p.bits == (i+1)%8 : "Le compteur bits n'est pas bien maintenu : il doit être incrémenté à chaque appel de write et réinitialisé quand il atteint 8.";
		}

		assert p.freq.cumulative(1) == 0;
		assert p.freq.cumulative(1 << 8) == 1;
		assert p.freq.cumulative(1 << 16) == 4;

	}

	static void test_encode() {
		Predictor p = new Predictor(17);

		for(int i = 0 ; i < 1000 ; i++)
			p.encode(false);
		assert !p.encode(true);
		assert p.encode(false);

		p = new Predictor(17);
		for(int i = 0 ; i < 1000 ; i++)
			p.encode(true);
		assert p.encode(true);
		assert !p.encode(false);

	}

	static void test_decode() {
		Predictor p = new Predictor(17);
		Predictor q = new Predictor(17);
		Random r = new Random();
		for (int i = 0; i < 1000; i++) {
			boolean b = r.nextBoolean();
			assert q.decode(p.encode(b)) == b;
		}
	}

	static void test_predict() {
		// Test singleton
		Predictor p = new Predictor(18);
		p.freq.inc(0b11001100);
		assert p.predictBit();
		p.write(true);
		assert p.predictBit();
		p.write(true);
		assert !p.predictBit();
		p.write(false);
		assert !p.predictBit();
		p.write(false);
		assert p.predictBit();
		p.write(true);
		assert p.predictBit();
		p.write(true);
		assert !p.predictBit();
		p.write(false);
		assert !p.predictBit();


		// Test 11111111111...
		p = new Predictor(15);
		for (int i = 0; i < 800; i++) {
			p.write(true);
		}
		assert p.predictBit();

		// Test 101010101010...
		p = new Predictor(15);
		for (int i = 0; i < 1000; i++) {
			p.write(true);
			p.write(false);
		}
		assert p.predictBit();
		p.write(false);
		assert !p.predictBit();

		// Test 100001000010000...
		p = new Predictor(12);
		for (int i = 0; i < 1000; i++) {
			p.write(true);
			p.write(false); p.write(false); p.write(false); p.write(false);
		}
		assert p.predictBit();
	}

	static void test_quality() {
		BinaryIn in = new BinaryIn("poincare.html");
		Predictor p = new Predictor(16);
		
		long startTime = System.currentTimeMillis();

		int good = 0, bad = 0;
		while(!in.isEmpty()) {
			boolean n = in.readBoolean();
			if(n == p.predictBit()) {
				good++;
			} else {
				bad++;
			}
			p.write(n);
		}
		

		long endTime = System.currentTimeMillis();
		System.out.println(String.format("%d bits prédits en %d millisecondes (%d kb/s).", good+bad, endTime-startTime,
				(good+bad)/(endTime-startTime)));

		float ratio = (float)good/(good+bad);
		System.out.printf("Taux de succès des prédictions : %.2f%%.\n", 100*ratio);
		assert ratio > .7;
	}

	public static void main(String[] args) {
		try {
			assert false;
			System.out.println("Assertions désactivées.");
			return;
		} catch (AssertionError e) {
		}

		
		System.out.print("Test de mask... ");
		test_mask();
		System.out.println("\t\t\t\tOK.");

		System.out.print("Test de write... ");
		test_write();
		System.out.println("\t\t\t\tOK.");

		System.out.print("Test de predict... ");
		test_predict();
		System.out.println("\t\t\t\tOK.");

		System.out.print("Test de la qualité de la prédiction... \n");
		test_quality();
		System.out.println("\t\t\t\t\t\tOK.");
		
		System.out.println("Partie 2 \t\t\t\t\tOK.\n\n");
		
		System.out.print("Test de encode... ");
		test_encode();
		System.out.println("\t\t\t\tOK.");

		System.out.print("Test de decode... ");
		test_decode();
		System.out.println("\t\t\t\tOK.");

	}
}
