import java.util.LinkedList;

public class Cell{
	int x;
	int y;
	
	Cell(int x, int y){
		this.x=x;
		this.y=y;
	}
	
	// Convention: West=(-1,0), South=(0,+1), East=(+1,0), North=(0,-1)
	Cell west(){return new Cell(x-1,y);}
	Cell south(){return new Cell(x,y+1);}
	Cell east(){return new Cell(x+1,y);}
	Cell north(){return new Cell(x,y-1);}
	
	// Renvoie la liste des voisins dans l'ordre West, South, East, North
	LinkedList<Cell> neighbors() {
		LinkedList<Cell> l=new LinkedList<Cell>();
		l.add(west());
		l.add(south());
		l.add(east());
		l.add(north());
		return l;
	}
	
	@Override
	public String toString() {
		return "("+x+","+y+")";
	} 

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Cell))
			return false;
		
		Cell that = (Cell) o;
		return (this.x == that.x) && (this.y == that.y);
	}
}
