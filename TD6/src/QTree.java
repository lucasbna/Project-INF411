import java.text.StringCharacterIterator;

public class QTree {
	private int color;
	private QTree[] children;

	QTree(int color) {
		this.color = color;
		this.children = null;
	}

	QTree(QTree topLeft, QTree topRight, QTree bottomLeft, QTree bottomRight) {
		this.color = BinaryImage.NOTACOLOR;
		this.children = new QTree[4];
		this.children[0] = topLeft;
		this.children[1] = topRight;
		this.children[2] = bottomLeft;
		this.children[3] = bottomRight;
	}

	boolean isLeaf() {
		if (this.children == null)
			return true;
		return false;
	}

	int getColor() {
		return this.color;
	}

	QTree getChild(int index) {
		return this.children[index];
	}

	void toImg(BinaryImage img, int x, int y, int size) {
		if (this.isLeaf()) {
			img.fill(x, y, size, this.color);
		} else {
			int n = size / 2;
			this.children[0].toImg(img, x, y, n);
			this.children[1].toImg(img, x + n, y, n);
			this.children[2].toImg(img, x, y + n, n);
			this.children[3].toImg(img, x + n, y + n, n);
		}
	}

	void toImg(BinaryImage img) {
		this.toImg(img, 0, 0, img.getSize());
	}

	static QTree ofImg(BinaryImage img, int x, int y, int size) {
		if (img.squareColor(x, y, size) == BinaryImage.NOTACOLOR) {
			int n = size / 2;
			QTree topLeft = ofImg(img, x, y, n);
			QTree topRight = ofImg(img, x + n, y, n);
			QTree bottomLeft = ofImg(img, x, y + n, n);
			QTree bottomRight = ofImg(img, x + n, y + n, n);
			return new QTree(topLeft, topRight, bottomLeft, bottomRight);
		} else {
			QTree Q = new QTree(img.squareColor(x, y, size));
			return Q;
		}
	}

	static QTree ofImg(BinaryImage img) {
		return (ofImg(img, 0, 0, img.getSize()));
	}

	public String toString() {
		String s = new String();
		if (this.isLeaf()) {
			if (this.color == BinaryImage.BLACK)
				s = s + "b";
			else
				s = s + "w";
		} else {
			s = s + "*";
			for (int i = 0; i < 4; i++)
				s = s + this.children[i].toString();
		}
		return s;
	}

	static QTree ofString(StringCharacterIterator it) {
		if (it.current() == ('b'))
			return new QTree(BinaryImage.BLACK);
		if (it.current() == ('w'))
			return new QTree(BinaryImage.WHITE);
		else {
			it.next();
			QTree Q1 = ofString(it);
			it.next();
			QTree Q2 = ofString(it);
			it.next();
			QTree Q3 = ofString(it);
			it.next();
			QTree Q4 = ofString(it);
			return new QTree(Q1, Q2, Q3, Q4);
		}
	}
	
	static QTree ofString(String s) {
		return ofString(new StringCharacterIterator(s));
	}
}
