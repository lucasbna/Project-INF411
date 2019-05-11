import java.io.*;
import java.nio.file.*;
import java.security.MessageDigest;

class Encoder {
	Predictor pred;
	private BinaryOut out;

	Encoder(BinaryOut out) {
		pred = new Predictor(16);
		this.out = out;
	}
	
	void encode(BinaryIn in) {
		// TODO
	}

	void close() {
		out.close();
	}

	/*
	 * TESTS
	 */
	
	
	public static void main(String[] args) throws java.io.IOException {
		try {
			assert false;
			System.out.println("Assertions désactivées.");
			return;
		} catch(AssertionError e) {
		}
		
		BinaryIn in = new BinaryIn("LICENSE");
		BinaryOut out = new BinaryOut(new FileOutputStream("output", false));

		long startTime = System.currentTimeMillis();
		Encoder enc = new Encoder(out);
		enc.encode(in);
		enc.close();
		long endTime = System.currentTimeMillis();

		System.out.println("Compression faite en " + (endTime - startTime) + " millisecondes.");
		System.out.printf("%d bits lus, %d bits émis.\n", in.totalBitsRead(), out.totalBitsWritten());
		System.out.printf("Taille compressé : %.2f%% de la taille originale.\n",
				(double) 100 * out.totalBitsWritten() / in.totalBitsRead());

		assert in.totalBitsRead() == 281192 : "L'entrée n'a pas été lue entièrement.";
		
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (java.security.NoSuchAlgorithmException e) {
			throw new AssertionError();
		}
		
		md.update(Files.readAllBytes(new File("output").toPath()));
		byte[] sha = md.digest();

		md.update(Files.readAllBytes(new File("output.ref").toPath()));
		byte[] sharef = md.digest();
		
		assert MessageDigest.isEqual(sha, sharef) : "La sortie n'est pas identique à la sortie de référence.";
		
		System.out.println("OK");
	}

}
