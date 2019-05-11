/**
 * TD3
 * Test 3.1
 * Test de la classe UnionFind
 * methode getSize()
 */
public class Test31{

    public static void main(String[] args){
	System.out.println("Test 3.1");
        UnionFind uf = new UnionFind(8);
	//System.out.println(uf);
	for (int i = 0; i < 8; i++){
	    int fi = uf.find(i);
	    assert (fi == i) : "erreur: on doit avoir find("+i+") = "+i+" mais find("+i+") = "+fi;
	    int si = uf.getSize(i);
	    assert (si == 1) : "erreur: on doit avoir getSize("+i+") = 1 mais getSize("+i+") = "+si;
	    //System.out.println(uf);
	}
	
	uf.union(1, 3);
	//System.out.println(uf);
	int[] size = new int[8];
	int[] size_check = new int[] {1,2,1,2,1,1,1,1};
	for (int i = 0; i < 8; i++){
	    size[i] = uf.getSize(i);
	}	
	for (int i = 0; i < 8; i++){
	    assert (size[i] == size_check[i]) : "erreur: on doit avoir getSize("+i+") = "+size_check[i]+" mais getSize("+i+") = "+ size[i];

	}	
	uf.union(1, 5);
	uf.union(3, 7);
	//System.out.println(uf);
	// {1,3,5,7}, {0}, {2}, {4}, {6}
	int[] size_check2 = new int[] {1,4,1,4,1,4,1,4};
	for (int i = 0; i < 8; i++){
	    size[i] = uf.getSize(i);
	}	
	//System.out.println(uf);
	for (int i = 0; i < 8; i++){
	    assert (size[i] == size_check2[i]) : "erreur: on doit avoir getSize("+i+") = "+size_check2[i]+" mais getSize("+i+") = "+ size[i];
	}	
	
	System.out.println("Fin du Test 3.1");
    }
}
