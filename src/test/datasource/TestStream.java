package test.datasource;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import data.IDataSource;
import data.TIME_FRAME;
import data.foxtrade.KTExportFutures;
import data.sinyee.SinYeeDataSource;
import data.struct.Bar;
import data.struct.BarSeries;
import helper.StreamHelper;

@SuppressWarnings("unused")
public class TestStream {

	private static final String[] mon = {"99", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };

	public static void main(String[] args) {
		
		final int i = 0;
		final TIME_FRAME tf = TIME_FRAME.MIN1;
		
		IDataSource ds = new SinYeeDataSource("cu", EnumSet.of(tf), str -> str.endsWith(mon[i]));
		
		// IDataSource ds = new KTExportFutures("cu", EnumSet.of(tf));
		
		BarSeries bars = ds.getBarSeries(i, tf);
		int size = bars.closes.length;
		double[] price = new double[size];
		for (int j = 0; j < size; j++) {
			price[j] = bars.closes[j];
		}
		for (int j = 0; j < 20; j++) {
			System.out.println(Arrays.stream(price).sorted().map(Math::cos).average());
		}
		
		Integer[] time = new Integer[size];
		for (int j = 0; j < size; j++) {
			time[j] = bars.times[j];
		}
		Arrays.asList(time);
		
		System.out.println(Arrays.stream(bars.times).average());
		
		/**
		 * @see http://stackoverflow.com/questions/23106093/how-to-get-a-stream-from-a-float
		 */
		DoubleStream dstream = IntStream.range(0, bars.closes.length)
				.mapToDouble(j -> bars.closes[j]);
		
		dstream.sorted().map(Math::cbrt);
		
		DoubleSummaryStatistics dss = StreamHelper.getFloatSummaryStatistics(bars.closes);
		System.out.println(dss.getMax());
		System.out.println(dss.getMin());
	}
}
