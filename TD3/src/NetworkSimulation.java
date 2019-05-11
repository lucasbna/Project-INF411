
public class NetworkSimulation {
	public static void main(String[] args) {
		Network n = new Network();
		int s = 0;
		int r = 0;
		while (n.getCalled() != 524287 && n.getCaller() != 524287) {
				s++;
				n.nextCall();
				if (n.getCalled() == n.getCaller())
					r++;
			}
			if (n.getCalled() == 524287)
			System.out.println(" Appel recu apres " + s + " appels dont " + r + " appels au repondeur");
			else
			 System.out.println(" Appel emis apres " + s + " appels dont " + r + "appels au repondeur");

			 System.out.println(" Il y a " + n.getSize(524287) + " personnes en relation avec le president");
		
		while (n.getSize(524287) < 990000) {
			if (n.getCalled() != 524287 || n.getCaller() != 524287) {
			s++;
			n.nextCall();
				}
			}
		System.out.println(s);
	}
}
