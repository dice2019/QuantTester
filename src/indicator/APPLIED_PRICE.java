package indicator;

public enum APPLIED_PRICE implements IPriceSimplifier {
	/**
	 * Close price
	 */
	PRICE_CLOSE {
		@Override
		public float[] simplify(float[] open, float[] high, float[] low, float[] close) {
			return close;
		}
	},

	/**
	 * Open price
	 */
	PRICE_OPEN {
		@Override
		public float[] simplify(float[] open, float[] high, float[] low, float[] close) {
			return open;
		}
	},

	/**
	 * The maximum price for the period
	 */
	PRICE_HIGH {
		@Override
		public float[] simplify(float[] open, float[] high, float[] low, float[] close) {
			return high;
		}
	},

	/**
	 * The minimum price for the period
	 */
	PRICE_LOW {
		@Override
		public float[] simplify(float[] open, float[] high, float[] low, float[] close) {
			return low;
		}
	},

	/**
	 * Median price, (high + low)/2
	 */
	PRICE_MEDIAN {
		@Override
		public float[] simplify(float[] open, float[] high, float[] low, float[] close) {
			float[] price = new float[close.length];
			for (int i = 0; i < close.length; i++) {
				price[i] = (high[i] + low[i]) / 2.0f;
			}
			return price;
		}
	},

	/**
	 * Typical price, (high + low + close)/3
	 */
	PRICE_TYPICAL {
		@Override
		public float[] simplify(float[] open, float[] high, float[] low, float[] close) {
			float[] price = new float[close.length];
			for (int i = 0; i < close.length; i++) {
				price[i] = (high[i] + low[i] + close[i]) / 3.0f;
			}
			return price;
		}
	},

	/**
	 * Average price, (high + low + close + close)/4
	 */
	PRICE_WEIGHTED {
		@Override
		public float[] simplify(float[] open, float[] high, float[] low, float[] close) {
			float[] price = new float[close.length];
			for (int i = 0; i < close.length; i++) {
				price[i] = (high[i] + low[i] + close[i] + close[i]) / 4.0f;
			}
			return price;
		}
	};
}
