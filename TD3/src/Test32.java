/**
 * TD3
 * Test 3.2
 * Test de la classe Network
 * methode getSize()
 */
public class Test32{

    public static void main(String[] args){

	Network net = new Network();

	System.out.println("Test 3.2");
	int[] tab_calls = new int[] {200007, 100053, 600183, 500439, 600863, 701497, 602383, 103563, 5079, 106973, 209287, 112063, 615343, 519169, 623583, 728627, 634343, 140773, 47959, 155943, 264767, 174473, 685103, 596699, 709303, 822957, 737703, 253583, 170639, 288913, 408447, 329283, 851463, 775029, 900023, 26487, 954463, 483993, 415119, 547883, 682327, 618493, 156423, 96159, 237743, 381217, 326623, 874003, 823399, 974853, 128407, 84103, 641983, 602089, 764463, 529290, 951516, 375212, 400462};
	
	int call = 0;
	for (int i = 0; i < tab_calls.length/2; i++){
	    net.nextCall();
	    int a = net.getCaller();
	    int b = net.getCalled();
	    // test numeros appels
	    assert (a == tab_calls[2*i] && b == tab_calls[2*i+1]) : i + "-th appel devrait etre entre " + tab_calls[2*i] + " et " + tab_calls[2*i+1] + " mais est entre " + a + " et " + b +"\n";
	    // test mise a jour de union-find : size
	    int sa = net.getSize(a);
	    int sb = net.getSize(b);
	    assert (sa == 2) :  i + "-th appel, " + a + " et " + b + " sont en relation et getSize() devrait renvoyer 2, mais getSize("+ a + ") = "+sa+"\n";
	    assert (sb == 2) :  i + "-th appel, " + a + " et " + b + " sont en relation et getSize() devrait renvoyer 2, mais getSize("+ b + ") = "+sb+"\n";
	}
	System.out.println("Fin du Test 3.2, "+ tab_calls.length/2 + " appels testés");
    }
}
