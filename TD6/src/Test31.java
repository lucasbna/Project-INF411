
public class Test31 {

	public static void main(String[] args) {
		// Pour s'assurer que les assert's sont activés
		try {
			assert false;
			System.err.println("Vous devez activer l'option -ea de la JVM");
			System.err.println("(Run As -> Run configurations -> Arguments -> VM Arguments)");
			System.exit(1);
		} catch (AssertionError e) {
		}

		QTree Q1 = new QTree(BinaryImage.BLACK);
		QTree Q2 = new QTree(BinaryImage.WHITE);
		QTree Q3 = new QTree(Q2,new QTree(Q2,Q1,Q1,Q2),Q1,new QTree(Q2,Q2,Q2,Q1));
		
		if (!Q1.toString().equals("b")) { assert false;
		System.out.println("code incorrect");
		}
		if (!Q2.toString().equals("w")) { assert false;
		System.out.println("code incorrect");
		}
		if (!Q3.toString().equals("*w*wbbwb*wwwb")) { assert false;
		System.out.println(Q3.toString() + "code incorrect");
		}
		else System.out.println(Q3.toString() + " : code bon");
	}
}



