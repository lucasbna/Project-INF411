
public class Test0 {
	public static void main(String[] args) {
		// creating an image
		BinaryImage createdImg = new BinaryImage(256);
		createdImg.fill(0, 128, 128, BinaryImage.WHITE);
		createdImg.fill(128, 0, 128, BinaryImage.WHITE);
		new ImageViewer(createdImg);

		// loading an image
		BinaryImage loadedImg = new BinaryImage("images/Example256.png");
		new ImageViewer(loadedImg);
	}
}
