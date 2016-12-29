package indicator;

import data.struct.BarSeries;

@FunctionalInterface
public interface IPriceSimplifier {
	public float[] simplify(float[] open, float[] high, float[] low, float[] close);
	
	public default float[] simplify(BarSeries bars) {
		return simplify(bars.opens, bars.highs, bars.lows, bars.closes);
	}
}