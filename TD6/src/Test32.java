
public class Test32 {
	
	public static void main(String[] args) {
		// Pour s'assurer que les assert's sont activés
		try {
			assert false;
			System.err.println("Vous devez activer l'option -ea de la JVM");
			System.err.println("(Run As -> Run configurations -> Arguments -> VM Arguments)");
			System.exit(1);
		} catch (AssertionError e) {
		}
	
		System.out.print("Test de la methode QTreeFromString... ");
		test_decoding("b");
		test_decoding("w");
		test_decoding("*wbbw");
		test_decoding("*w*wbbwb*wwwb");
		
		System.out.println("\t\t[OK]");
	}
	
	private static void test_decoding(String t){
		assert (t.equals((QTree.ofString(t)).toString())) : "\nLe quadtree que vous obtenez correspond à la chaine \""
				+ (QTree.ofString(t)).toString() + "\"\nalors que le résultat devrait correspondre à la chaine \""
				+ t + "\"";
	}
}
