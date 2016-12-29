package test.datasource;

import java.util.EnumSet;
import java.util.List;

import data.ITickDataSource;
import data.TIME_FRAME;
import data.struct.BarSeries;
import strategy.TickBarStrategy;

public class CheckTickData {

	private static void CheckPrice(float[] prices) {
		for (int i = 0; i < prices.length; i++) {
			float price = prices[i];
			if (price <= 0.0f) {
				System.out.println("Invalid price <= 0, i = " + i + ", length = " + prices.length);
			}
			if (Float.compare(price, Float.NaN) == 0) {
				System.out.println("Invalid price NaN");
			}
		}
	}
	
	public static void main(String[] args) {
		// ITickDataSource export = new data.foxtrade.KTExportFutures("al", 0, TIME_FRAME.TICK);
		ITickDataSource export = new data.sinyee.SinYeeDataSource("pp", EnumSet.of(TIME_FRAME.TICK), anystr -> true);
		
		TickBarStrategy strategy = new TickBarStrategy(132, 9);
		strategy.setDataSource(export);

		List<BarSeries> ohlcvs = strategy.get_daily_tick_bar();
		System.out.println(ohlcvs.size());

		strategy = new TickBarStrategy(73, 8);
		strategy.setDataSource(export);

		ohlcvs = strategy.get_daily_tick_bar();
		System.out.println(ohlcvs.size());
		
		for (BarSeries ohlcv : ohlcvs) {
			float[] Open = ohlcv.opens;
			float[] High = ohlcv.highs;
			float[] Low = ohlcv.lows;
			float[] Close = ohlcv.closes;
			
			CheckPrice(Open);
			CheckPrice(High);
			CheckPrice(Low);
			CheckPrice(Close);
		}
	}
}
