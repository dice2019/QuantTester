package helper;

public final class MathHelper {
	public final static float Max(final float... values) {
		if (values.length == 1) {
			return values[0];
		}
		float max = values[0];
		for (int i = 1; i < values.length; i ++) {
			if (values[i] > max) {
				max = values[i];
			}
		}
		return max;
	}

	public final static float Min(final float... values) {
		if (values.length == 1) {
			return values[0];
		}
		float min = values[0];
		for (int i = 1; i < values.length; i ++) {
			if (values[i] < min) {
				min = values[i];
			}
		}
		return min;
	}

	public final static int Max(final int... values) {
		if (values.length == 1) {
			return values[0];
		}
		int max = values[0];
		for (int i = 1; i < values.length; i ++) {
			if (values[i] > max) {
				max = values[i];
			}
		}
		return max;
	}

	public final static int Min(final int... values) {
		if (values.length == 1) {
			return values[0];
		}
		int min = values[0];
		for (int i = 1; i < values.length; i ++) {
			if (values[i] < min) {
				min = values[i];
			}
		}
		return min;
	}

	public final static int MaxIndex(final float... values) {
		if (values.length == 1) {
			return 0;
		}
		float max = values[0];
		int index = 0;
		for (int i = 1; i < values.length; i ++) {
			if (values[i] > max) {
				max = values[i];
				index = i;
			}
		}
		return index;
	}

	public final static int MinIndex(final float... values) {
		if (values.length == 1) {
			return 0;
		}
		float min = values[0];
		int index = 0;
		for (int i = 1; i < values.length; i ++) {
			if (values[i] < min) {
				min = values[i];
				index = i;
			}
		}
		return index;
	}
	
	public final static int MaxIndex(final int... values) {
		if (values.length == 1) {
			return 0;
		}
		int max = values[0];
		int index = 0;
		for (int i = 1; i < values.length; i ++) {
			if (values[i] > max) {
				max = values[i];
				index = i;
			}
		}
		return index;
	}

	public final static int MinIndex(final int... values) {
		if (values.length == 1) {
			return 0;
		}
		int min = values[0];
		int index = 0;
		for (int i = 1; i < values.length; i ++) {
			if (values[i] < min) {
				min = values[i];
				index = i;
			}
		}
		return index;
	}

	public final static float Average(final float... values) {
		float sum = 0.0f;
		for (int i = 0; i < values.length; i++) {
			sum += values[i];
		}
		return sum / values.length;
	}

	public final static float SD(final float... values) {
		float average = Average(values);
		float diff = 0.0f, diff_sq_sum = 0.0f;
		for (int i = 0; i < values.length; i++) {
			diff = values[i] - average;
			diff_sq_sum += (diff * diff);
		}
		return (float) Math.sqrt(diff_sq_sum / values.length);
	}
}
