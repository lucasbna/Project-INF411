import java.awt.*;
import java.awt.event.*;

public class Ocean {
	private OceanCanvas ocean;
	final Cell nemo, marlin;
	
	// est-ce que (x,y) est une case valide?
	boolean isValid(Cell c){ return ocean.isValid(c); }

	// est-ce que (x,y) est un mur ?
	boolean isWall(Cell c) { return ocean.isWall(c); }

	// est-ce que (x,y) est un requin ?
	boolean isShark(Cell c) { return ocean.isShark(c); }

	// est-ce que (x,y) est gagnant ?
	boolean isNemo(Cell c) { return ocean.isNemo(c); }

	// est-ce que (x,y) est au debut ?
	boolean isMarlin(Cell c) { return ocean.isMarlin(c); }

	// teste si la cellule (x,y) comporte une marque
	boolean isMarked(Cell c) { return ocean.isMarked(c); }

	// renvoie la valeur de la marque de la cellule (x,y)
	int getMark(Cell c) { return ocean.getMark(c); }

	// pose une marque egale a 0
	void setMark(Cell c) { ocean.setMark(c); }   
    
	// meme chose, mais avec une valeur d'attribut >0
	void setMark(Cell c, int val) { ocean.setMark(c, val); }   

	// enleve toute marque
	void unMark(Cell c) { ocean.unMark(c); }   
    
	// cree une fenetre et affiche le labyrinthe dedans (l'entier n correspond a l'indice du labyrinthe)
	Ocean(int n) {
		ocean=new OceanCanvas(n);
		nemo=ocean.nemo;
		marlin=ocean.marlin;
	}

	// methode de test de la classe
	public static void main(String[] args) {
		new Ocean(1);
	}
	
};

class OceanCanvas extends Canvas {
	private static final long serialVersionUID = 1L;
	
	static final int cellSize = 10; // taille d'une cellule
	static String[] matrix;  // pour stocker la matrice du labyrinthe
	int[][] mark;  // les marques
	Cell nemo, marlin;  // les positions de Nemo et Marin
	
	
	
	// est-ce que (x,y) est une case valide?
	boolean isValid(Cell c){
		return (c.x>=0 && c.x<matrix.length && c.y>=0 && c.y<matrix.length);
	}
	
	// est-ce que (x,y) est un mur ?
	boolean isWall(Cell c) {
		if(!isValid(c))
			throw new Error("invalid coordinates: "+c);
		return matrix[c.y].charAt(c.x)=='#';
	}
	
	// est-ce que (x,y) est une porte ?
	boolean isShark(Cell c) {
		if(!isValid(c))
			throw new Error("invalid coordinates: "+c);
		return matrix[c.y].charAt(c.x)=='S';
	}
	
	// est-ce que (x,y) est gagnant ?
	boolean isNemo(Cell c) {
		if(!isValid(c))
			throw new Error("wrong coordinates: "+c);
		return c.x==nemo.x && c.y==nemo.y;
	}
	
	// est-ce que (x,y) est au debut ?
	boolean isMarlin(Cell c) {
		if(!isValid(c))
			throw new Error("wrong coordinates: "+c);
		return c.x==marlin.x && c.y==marlin.y;
	}
	
	
	// teste si la cellule (x,y) comporte une marque
	boolean isMarked(Cell c) {
		if(!isValid(c))
			throw new Error("wrong coordinates: "+c);
		return mark[c.y][c.x] >= 0;
	}
	
	// renvoie la valeur de la marque de la cellule (x,y)
	int getMark(Cell c) {
		if(!isValid(c))
			throw new Error("wrong coordinates: "+c);
		return mark[c.y][c.x];
	}
	
	// pose une marque egale a 0
	void setMark(Cell c) {
		if(!isValid(c))
			throw new Error("wrong coordinates: "+c);
		mark[c.y][c.x] = 0;
		repaint();
	}   
	
	// meme chose, mais avec une valeur d'attribut >0
	void setMark(Cell c, int val) {
		if(!isValid(c))
			throw new Error("wrong coordinates: "+c);
		mark[c.y][c.x] = val;
		repaint();
	}   
	
	// enleve toute marque
	void unMark(Cell c) {
		if(!isValid(c))
			throw new Error("wrong coordinates: "+c);
		mark[c.y][c.x] = -1;
		repaint();
	}   
    
	// cree une fenetre et affiche le labyrinthe dedans (l'entier n correspond a l'indice du labyrinthe)
	OceanCanvas(int n) {
		matrix = Data.matrices[n];
		mark = new int[matrix.length][matrix.length];
		// pas de marque a la cellule (i,j) <=> marque[i][j]<0
		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j < matrix.length; j++) {
				mark[i][j] = -1;
				switch (matrix[j].charAt(i)) {
				case 'M':
					marlin = new Cell(i, j);
					break;
				case 'N':
					nemo = new Cell(i, j);
					break;
				}
			}
		Frame f = new Frame("Finding Nemo");
		f.setBounds(100, 100, cellSize * matrix.length + 8, cellSize
				* matrix.length + 26);
		f.add(this);

		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		f.setVisible(true);
	}

	// ne pas effacer avant pour eviter que l'image ne clignote lors d'un rafraichissement de la fenetre
	public void update(Graphics g) {
		paint(g);
	}

	public void paint(Graphics g) {
		// dessiner les murs et les marques
		for (int x = 0; x < matrix.length; x++)
			for (int y = 0; y < matrix.length; y++) {
				Cell c = new Cell(x, y);
				if (isWall(c)) {
					g.setColor(Color.gray);
					g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
				} else if (isShark(c)) {
					g.setColor(Color.black);
					g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
				} else if (isNemo(c)) {
					g.setColor(Color.orange);
					g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
				} else if (isMarlin(c)) {
					g.setColor(Color.red);
					g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
				} else if (isMarked(c)) {
					// la teinte est fonction de la distance
					int h = ((int) (3 * getMark(c))) % 360; // on decompose la
															// roue chromatique
															// en 360 degres
					// conversion en rvb
					int hi = (int) Math.floor((float) h / 60) % 6;
					float f = (float) h / 60 - hi;
					float p = 0;
					float q = 1 - f;
					switch (hi) {
					case 0:
						g.setColor(new Color(1, f, p));
						break;
					case 1:
						g.setColor(new Color(q, 1, p));
						break;
					case 2:
						g.setColor(new Color(p, 1, f));
						break;
					case 3:
						g.setColor(new Color(p, q, 1));
						break;
					case 4:
						g.setColor(new Color(f, p, 1));
						break;
					case 5:
						g.setColor(new Color(1, p, q));
						break;
					default:
						throw new Error("Probleme de conversion hsv->rgb");
					}
					g.fillOval(x * cellSize + cellSize / 4, y * cellSize
							+ cellSize / 4, cellSize - cellSize / 2, cellSize
							- cellSize / 2);
				} else { // !estMarque(x,y)
					g.setColor(Color.white);
					g.fillOval(x * cellSize + cellSize / 4, y * cellSize
							+ cellSize / 4, cellSize - cellSize / 2, cellSize
							- cellSize / 2);
				}
			}
	}

	
};


class Data {
	
	static final String[][] matrices= { 
		{
	"#########################################",
	"#M  #        # # # #       #        # # #",
	"### # #### # # # # # ### ##### ## ### # #",
	"#   # #    # #   #   #   # #    #   #   #",
	"### ### ###### ######### # ###  # # ### #",
	"#          #   #     #   #        # #   #",
	"### ##### #### # ####### ### ###### ### #",
	"# # # # #  #     #   #       #    #     #",
	"# # # # # ## ### # # # # ###### ##### ###",
	"#   # #        #   #   #   #      #     #",
	"# #        # # # #   # # # # #      # # #",
	"# # # ###### # # ### # ### # # #### ### #",
	"# # #      # #     # #     # #  # #   # #",
	"# # ###### ##### ######### # #  # # ### #",
	"#   #      # #         #   #            #",
	"########## # ##### ######### ## ####### #",
	"#   #          #   # #     #      #     #",
	"### # ### #### ##### ### ######## ##### #",
	"#   #   #            #          # #     #",
	"### #####  ##### ########### #### ##### #",
	"#   #   #  #   #       #            # # #",
	"### # ### ## ### ####### # ######## # # #",
	"#     #    #           # #        # #   #",
	"### ### ######## ########### ## # # ### #",
	"#                  #         #  # # #   #",
	"### ### ###### ##### ########## ### ### #",
	"# # #      # # #N  #       # #  #     # #",
	"# ##### # ## ###   # # # # # # ## ### ###",
	"#       #    # ### # # # #          #   #",
	"##### #### # # # # # # ######## # # #####",
	"#       #  #     #         #    # #     #",
	"# # #            # # #   #        # #   #",
	"# # # # ## ########### # # ### ###### ###",
	"# # # #    #   # # #   # # # #  #   #   #",
	"# ### #### # ### # ##### ### ## # # # ###",
	"#   # #          # # #     #      # #   #",
	"# ### ### ###### # # # # #####  # ### ###",
	"# #   #    #     #   # #        # #     #",
	"### ### # ## # ### ###########  ### # # #",
	"#     # #  # #                    # # # # ",
	"#########################################"
		},
		{
	"#########################################",
	"#M  #        # # # #       #        # # #",
	"### # #### # # # # # ### ##### ## ### # #",
	"#   # #    # #   #   #   # #    #   #   #",
	"### ### ###### ######### # ###  # # ### #",
	"#          #   #     #   #        # #   #",
	"### ##### #### # ####### ### ###### ### #",
	"# # # # #  #     #   #       #    #     #",
	"# # # # # ## ### # # # # ###### ##### ###",
	"#   # #        #   #   #   #      #     #",
	"# #        # # # #   # # # # #      # # #",
	"# # # ###### # # ### # ### # # #### ### #",
	"# # #      # #     # #     # #  # #   # #",
	"# # ###### ##### ######### # #  # # ### #",
	"#   #      # #         #   #            #",
	"########## # ##### ######### ## ####### #",
	"#   #          #   # #     #      #     #",
	"### # ### #### ##### ### ######## ##### #",
	"#   #   #            #          # #     #",
	"### #####  ##### ########### #### ##### #",
	"#   #   #  #   #       #            # # #",
	"### # ### ## ### ####### # ######## # # #",
	"#     #    #           # #        # #   #",
	"### ### ######## ########### ## # # ### #",
	"#                  #         #  # # #   #",
	"### ### ###### ##### ########## ### ### #",
	"# # #      # # #N  #       # #  #     # #",
	"# ##### # ## ###   # # # # # # ## ### ###",
	"#       #    # ### # # # #          #   #",
	"##### #### # # # # # ########## # # #####",
	"#       #  #     #         #    # #     #",
	"# # #            # # #   #        # #   #",
	"# # # # ## #######S### # # ### ###### ###",
	"# # # #    #   # # #   # # # #  #   #   #",
	"# ### #### # ### # ##### ### ## # # # ###",
	"#   # #          # # #     #      # #   #",
	"# ### ### ###### # # # # #####  # ### ###",
	"# #   #    #     #   # #        # #     #",
	"### ### # ## # ### ###########  ### # # #",
	"#     # #  # #                    # # # # ",
	"#########################################"
		},
		{ 
	"#########################################",
	"#M                                      #",
	"#S##################################### #",
	"#                                     # #",
	"# #S################################# # #",
	"# #                                 # # #",
	"# # #S############################# # # #",
	"# # # # #  #     #   #       #    # # # #",
	"# # # # # ## ### # # # # ###### ##### # #",
	"# # # #        #   #   #   #      # # # #",
	"# # #      # # # #   # # # # #      # # #",
	"# # # ###### # # ### # ### # # #### # # #",
	"# # #      # #     # #     # #  # # # # #",
	"# # ###### ##### ######### # #  # # # # #",
	"# # #      # #         #   #        # # #",
	"# # ###### # ##### ######### ## ##### # #",
	"# # #          #   # #     #      # # # #",
	"# # # ### #### ##### ### ######## ### # #",
	"# # #   #            #          # # # # #",
	"# # #####  ##### ########### #### ### # #",
	"# # #      #   #       #            # # #",
	"# # # ### ## ### ####### # ######## # # #",
	"# # # #    #           # #        # # # #",
	"# # ### ######## ########### ## # # # # #",
	"# # #              #         #  # # # # #",
	"# # ### ###### ##S## ########## ### # # #",
	"# # #      # # #N  #       # #  #   # # #",
	"# # #####S## ###   # # # # # # ## ### # #",
	"# # #   #    # #S#S# # # #          # # #",
	"# # # #### # # # # # ########## # # # # #",
	"# # #   #  #     # S       #    # # # # #",
	"# # #            # # #   #        # # # #",
	"# # # # ## #######S### # # ### ###### # #",
	"# # # #    #   # # #   # # # #  #   # # #",
	"# # # #### # ### # ##### ### ## # # # # #",
	"# # # #          # # #     #      # S # #",
	"# # #S############################### # #",
	"# #                                   # #",
	"# ##################################### #",
	"#                                       #",
	"#########################################" 
		} };


	
}




