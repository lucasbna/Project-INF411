
import java.util.Random;

public enum SelectionMethod {
	Newest {
		@Override
		public int nextIndex(int bound) {
			return bound-1;
		}
	}, Oldest {
		@Override
		public int nextIndex(int bound) {
			return 0;
		}
	}, Middle {
		@Override
		public int nextIndex(int bound) {
			return bound/2;
		}
	}, Random {
		@Override
		public int nextIndex(int bound) {
			return rnd.nextInt(bound);
		}
	};

	public abstract int nextIndex(int bound);

	private static Random rnd = new Random();
}
