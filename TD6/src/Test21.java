
public class Test21 {
	public static void main(String[] args) {
		int size = 256;
		System.out.println("Testing the construction of an image from a tree");
		BinaryImage img = new BinaryImage(size);
		QTree black = new QTree(BinaryImage.BLACK);
		QTree white = new QTree(BinaryImage.WHITE);
		QTree B = white;
		QTree C = new QTree(white, black, black, white);
		QTree D = black;
		QTree E = new QTree(white, white, white, black);
		QTree A = new QTree(B, C, D, E);
		A.toImg(img);
		new ImageViewer(img);
	}
}
