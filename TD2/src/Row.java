public class Row {
	private final int[] fruits;

	public Row() {
		this.fruits = new int[0];
	}

	public Row(int[] fruits) {
		this.fruits = fruits;
	}

	public Row addFruit(int fruit) {
		int s = this.fruits.length;
		int[] tab = new int[s + 1];
		for (int i = 0; i < s; i++) {
			tab[i] = this.fruits[i];
		}
		tab[s] = fruit;
		return new Row(tab);
	}

	public boolean isValid() {
		if (this.fruits.length == 0)
			return true;
		else {
			int n = this.fruits[0];
			int s = 1;
			for (int i = 1; i < this.fruits.length; i++) {
				if (this.fruits[i] == n)
					s++;
				else {
					n = this.fruits[i];
					s = 1; // Oublie remise a 1 : test 2 ne fonctionne pas mais test 1 fonctionne
				}
				if (s == 3)
					return false;
			}
			return true;
		}
	}

	public boolean areStackable(Row r1, Row r2) {
		int s = this.fruits.length;
		if (!(r1.fruits.length == s && r2.fruits.length == s))
			return false;
		for (int i = 0; i < s; i++) {
			if (this.fruits[i] == r1.fruits[i] && this.fruits[i] == r2.fruits[i])
				return false;
		}
		return true;
	}

	@Override
	public boolean equals(Object o) {
		Row that = (Row) o;
		if (this.fruits.length != that.fruits.length)
			return false;
		for (int i = 0; i < this.fruits.length; ++i) {
			if (this.fruits[i] != that.fruits[i])
				return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		for (int i = 0; i < fruits.length; ++i) {
			hash = 2 * hash + fruits[i];
		}
		return hash;
	}

	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();

		for (int i = 0; i < fruits.length; ++i)
			s.append(" _");
		s.append("\n");

		for (int i = 0; i < fruits.length; ++i)
			s.append("|" + fruits[i]);
		s.append("|\n");

		return s.toString();
	}
}
