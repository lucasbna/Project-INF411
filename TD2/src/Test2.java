public class Test2 {
	public static void main(String[] args) {
		//Pour s'assurer que les assert's sont activés
		if (!Test2.class.desiredAssertionStatus()) {
			System.err.println("Vous devez activer l'option -ea de la JVM");
		        System.err.println("(Run As -> Run configurations -> Arguments -> VM Arguments)");
			System.exit(1);
		}

		long[] nums = new long[]{16L,102L,2030L,60232L,3858082L,446672706L,101578277384L,43680343039806L,36133311325799774L};

		for(int n = 2; n <= 10; ++n) {
			System.out.print("testing size " + n + "x" + n + "... ");
			long startTime = System.nanoTime();
			long cnt = CountConfigurations.count(n);
			long endTime = System.nanoTime();
			System.out.print(cnt);
			System.out.print(" computed in " + (endTime-startTime)/1000000.0 + " ms");
			assert (cnt == nums[n-2]) : "\nThere are " + nums[n-2] + " stable configurations of size " + n + "x" + n +  "\n";

			System.out.println("\t\t[OK]");
		}


	}
}
