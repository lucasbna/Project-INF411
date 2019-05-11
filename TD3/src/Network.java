
public class Network{

    // on utilise une structure de classes disjointes (UnionFind)
    private UnionFind relations;
    // generateur de nombres aleatoires (PRNG)
    private PRNG prng;
    private int validCalls = 0;
    private int misdials = 0;
    private int caller = 0;
    private int called = 0;
    
    public Network(int size){
		this.relations = new UnionFind(size);
		this.prng = new PRNG(size);
    }

    // constructeur avec taille par defaut 10^6
    public Network(){
		this(1000000);
    }
    
    // utilise le PRNG pour simuler un appel.
    // etablit le lien entre les deux numeros
    public void nextCall(){
    	int n1 = prng.getNext();
    	int n2 = prng.getNext();
    	relations.union(n1, n2);    
    	this.caller = n1;
    	this.called = n2;
    }

    public int getCaller(){
		return this.caller;
    }
	
    public int getCalled(){
		return this.called;
    }

    public int getValidCalls(){
		return this.validCalls;
    }
	
    public int getMisdials(){
		return this.misdials;
    }
    
    public int getSize(int N) {
    	return this.relations.getSize(N);
    }
    
}
