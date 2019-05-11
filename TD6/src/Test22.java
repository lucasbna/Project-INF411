
public class Test22 {
	public static void main(String[] args) {
		System.out.println("Testing the construction of a tree from an image");

		// First test with a constructed image
		int size = 256;
		BinaryImage img = new BinaryImage(size);
		QTree black = new QTree(BinaryImage.BLACK);
		QTree white = new QTree(BinaryImage.WHITE);
		QTree B = white;
		QTree C = new QTree(white, black, black, white);
		QTree D = black;
		QTree E = new QTree(white, white, white, black);
		QTree A = new QTree(B, C, D, E);
		A.toImg(img, 0, 0, size);
		reconstructImage(img);

		// Second test with a real image
		reconstructImage(new BinaryImage("images/Example256.png"));
	}

	static void reconstructImage(BinaryImage img) {
		// Construct a new Quad-tree from an image
		QTree treeFromImage = QTree.ofImg(img);
		BinaryImage reconstructedImage = new BinaryImage(img.getSize());
		treeFromImage.toImg(reconstructedImage, 0, 0, img.getSize());

		//visualize the result
		new ImageViewer(reconstructedImage);

	}
}
