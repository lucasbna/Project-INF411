import java.io.*;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.Random;

class Decoder {
	Predictor pred;
	private BinaryOut out;

	Decoder(BinaryOut out) {
		pred = new Predictor(16);
		this.out = out;
	}

	int readToken(BinaryIn in) {
		try {
			// TODO
			return -1;
		} catch (java.util.NoSuchElementException e) {
			// Si on atteint la fin du flux en essayant de lire quelque chose,
			// on revoie -1.
			return -1;
		}
	}

	void decode(BinaryIn in) {
		// TODO
		out.discardBuffer();
	}

	void close() {
		out.close();
	}

	/*
	 * TESTS
	 */
	
	static void test_readToken(boolean b, int k) {
		ByteArrayOutputStream data = new ByteArrayOutputStream();
		BinaryOut out = new BinaryOut(data);
		Random rnd = new Random();

		out.write(b);
		if(!b)
			out.write(k, 3);
		for(int i = 0; i < 30; i++)
			out.write(rnd.nextBoolean());
	
		out.close();
		BinaryIn in = new BinaryIn(new ByteArrayInputStream(data.toByteArray()));
		
		Decoder dec = new Decoder(out);
		int rt = dec.readToken(in);
		
		if (!b) {
			assert in.totalBitsRead() == 4 : "Si le premier bit lu est 0, readToken doit lire 4 bits au total.";
			assert rt == k : "Si le premier bit lu est 0, readToken doit renvoyer l'entier codé par les 3 suivants.";
		} else {			
			assert in.totalBitsRead() == 1 : "Si le premier bit lu est 1, readToken ne doit pas lire plus que celui-ci.";
			assert rt == 8 : "Si le premier bit lu est 1, readToken doit renvoyer 8.";
		}
	}
	
	static void test_readTokenEnd(boolean b1, boolean b2) {
		ByteArrayOutputStream data = new ByteArrayOutputStream();
		BinaryOut out = new BinaryOut(data);

		out.write(1, 6);
		out.write(b1);
		out.write(b2);
		out.close();
		BinaryIn in = new BinaryIn(new ByteArrayInputStream(data.toByteArray()));
		
		Decoder dec = new Decoder(out);
		assert 1 == in.readInt(6);

		int rt = dec.readToken(in);
		if (b1) {
			assert in.totalBitsRead() == 7 : "Si le premier bit lu est 1, readToken ne doit pas lire plus que celui-ci.";
			assert rt == 8 : "Si le premier bit lu est 1, readToken doit renvoyer 8.";
		} else {
			assert in.totalBitsRead() == 8;
			assert rt == -1: "Si readToken ne peut pas lire les 3 bits dont il a besoin, il doit renvoyer -1.";
		}
	}
	
	static void test_decode(byte[] in) throws IOException {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (java.security.NoSuchAlgorithmException e) {
			throw new AssertionError();
		}
		md.update(in);
		byte[] sha = md.digest();

		ByteArrayInputStream clear_in = new ByteArrayInputStream(in);
		PipedOutputStream enc_out = new PipedOutputStream();
		PipedInputStream enc_in = new PipedInputStream(enc_out, 3 * in.length);
		ByteArrayOutputStream dec_out = new ByteArrayOutputStream();

		System.out.print("Encoding... ");
		BinaryOut enc_binout = new BinaryOut(enc_out);
		(new Encoder(enc_binout)).encode(new BinaryIn(clear_in));
		enc_binout.close();
		System.out.println("Done.");

		System.out.print("Decoding... ");
		BinaryOut dec_binout = new BinaryOut(dec_out);
		(new Decoder(dec_binout)).decode(new BinaryIn(enc_in));
		dec_binout.close();
		System.out.println("Done.");

		md.update(dec_out.toByteArray());

		assert MessageDigest.isEqual(sha, md.digest()) : "Le fichier décoder ne coincide pas avec le fichier original.";

	}

	public static void main(String[] args) throws IOException {
		try {
			assert false;
			System.out.println("Assertions désactivées.");
			return;
		} catch (AssertionError e) {
		}

		
		System.out.print("Test de readToken... ");
		test_readToken(false, 0);
		test_readToken(false, 1);
		test_readToken(false, 2);
		test_readToken(false, 3);
		test_readToken(false, 4);
		test_readToken(false, 5);
		test_readToken(false, 6);
		test_readToken(false, 7);
		test_readToken(true, 2);
		System.out.println("\t\t\t\t\tOK.");
	
		System.out.print("Test de readToken (comportement en fin de flux)... ");
		test_readTokenEnd(false, true);
		test_readTokenEnd(true, true);
		test_readTokenEnd(false, false);
		test_readTokenEnd(true, false);
		System.out.println("\tOK.");
		
		System.out.println("Test de decode... ");
		test_decode(Files.readAllBytes(new File("LICENSE").toPath()));
		test_decode(Files.readAllBytes(new File("random500").toPath()));

		System.out.println("OK");
	}

}
