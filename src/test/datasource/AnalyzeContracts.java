package test.datasource;

import java.util.AbstractMap;
import java.util.EnumSet;
import java.util.Map;

import data.IDataSource;
import data.TIME_FRAME;
import data.foxtrade.KTExportFutures;
import data.sinyee.SinYeeDataSource;
import data.struct.BarSeries;
import helper.DateTimeHelper;
import helper.SortHelper;

@SuppressWarnings("unused")
public class AnalyzeContracts {

	public static void main(String[] args) {
		// IDataSource ds = new KTExportFutures("if", EnumSet.of(TIME_FRAME.DAY));
		IDataSource ds = new SinYeeDataSource("c", EnumSet.of(TIME_FRAME.DAY), str -> true);

		Map.Entry<Integer, float[]>[] time_vol_array = analyzeContracts(ds);
		
		for (int i = 0; i < time_vol_array.length; i++) {
			int[] ordinal = SortHelper.getOrdinals(time_vol_array[i].getValue(), false);
			System.out.println(DateTimeHelper.Long2Ldt(time_vol_array[i].getKey()) + "\t" + ordinal[0]
					+ "\t" + ordinal[1] + "\t" + ordinal[2] + "\t" + ordinal[3]);
		}
	}
	
	public static Map.Entry<Integer, float[]>[] analyzeContracts(IDataSource ds) {
		BarSeries[] daily_klines = new BarSeries[12];
		int[] first_date = new int[12], last_date = new int[12];
		for (int i = 0; i < 12; i++) {
			daily_klines[i] = ds.getBarSeries(i + 1, TIME_FRAME.DAY);
			if (daily_klines[i] != null && daily_klines.length != 0 && daily_klines[i].times != null && daily_klines[i].times.length != 0) {
				first_date[i] = daily_klines[i].times[0];
				last_date[i] = daily_klines[i].times[daily_klines[i].times.length - 1];
				// System.out.println("i = " + i + ", " + daily_klines[i].Time.length);
			}
		}
		BarSeries main99 = ds.getBarSeries(0, TIME_FRAME.DAY);

		int[] curindex = new int[12];
		int[] lengthtbl = new int[12];
		for (int i = 0; i < 12; i++) {
			curindex[i] = 0;
			if (daily_klines[i] != null && daily_klines.length != 0 && daily_klines[i].times != null && daily_klines[i].times.length != 0) {
				lengthtbl[i] = daily_klines[i].times.length;
			} else {
				lengthtbl[i] = 0;
			}
		}

		int time_length = main99.times.length;
		@SuppressWarnings("unchecked")
		Map.Entry<Integer, float[]>[] daily_vol_array = new Map.Entry[time_length];

		for (int i = 0; i < time_length; i++) {
			float[] volumes = new float[12];
			for (int j = 0; j < 12; j++) {
				volumes[j] = 0.0f;
				for (; curindex[j] < lengthtbl[j]; curindex[j]++) {
					if (daily_klines[j].times[curindex[j]] > main99.times[i]) {
						break;
					} else if (daily_klines[j].times[curindex[j]] == main99.times[i]) {
						volumes[j] = daily_klines[j].volumes[curindex[j]];
					}
				}
			}

			AbstractMap.SimpleEntry<Integer, float[]> entry = new AbstractMap.SimpleEntry<>(main99.times[i], volumes);
			daily_vol_array[i] = entry;
		}

		return daily_vol_array;
	}
}
