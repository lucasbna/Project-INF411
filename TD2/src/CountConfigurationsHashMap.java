import java.util.LinkedList;
public class CountConfigurationsHashMap {
	
		static HashTable memo = new HashTable();

		static LinkedList<Row> allRows(int width) {
			LinkedList<Row> res = new LinkedList<Row>();

			if (width == 0) {
				res.add(new Row());
			} else {
				LinkedList<Row> allSmallerRows = allRows(width - 1);
				for (Row l : allSmallerRows) {
					Row tmp0 = l.addFruit(0);
					if (tmp0.isValid())
						res.add(tmp0);

					Row tmp1 = l.addFruit(1);
					if (tmp1.isValid())
						res.add(tmp1);
				}
			}

			return res;
		}

		static long count(Row r1, Row r2, LinkedList<Row> rows, int height) {
			if (height < 2)
				return 0;
			if (height == 2)
				return 1;
			if (memo.find(r1, r2, height) != null) {
				return memo.find(r1, r2, height).result;
			}
			long s = 0;
			for (Row r3 : rows) {
				if (r3.areStackable(r1, r2)) {
					s = s + count(r2, r3, rows, height - 1);
				}
			}
			memo.add(r1, r2, height,s);
			return s;
		}

		static long count(int n) {
			LinkedList<Row> rows = allRows(n);
			long s = 0;
			for (Row r1 : rows) {
				for (Row r2 : rows) {
					s = s + count(r1, r2, rows, n);
				}
			}
			return s;
		}
	}

}
