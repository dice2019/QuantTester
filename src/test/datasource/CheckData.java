package test.datasource;

import java.util.EnumSet;

import data.IDataSource;
import data.TIME_FRAME;
import data.foxtrade.KTExportFutures;
import data.struct.Bar;
import data.struct.FutureBar;
import data.struct.FutureBarSeries;

public class CheckData {

	public static void main(String[] args) {
		EnumSet<TIME_FRAME> tf = EnumSet.of(TIME_FRAME.DAY, TIME_FRAME.DAY);
		System.out.println(tf.size());
		IDataSource kt_data = new KTExportFutures("al", EnumSet.range(TIME_FRAME.MIN5, TIME_FRAME.DAY));
		
		//for (Bar bar : kt_data.getBarSeries(0, TIME_FRAME.DAY)) {
			//System.out.println(bar);
		//}
		
		check1(kt_data);
		check2(kt_data);
		
		int len = main_id1.length;
		for (int i = 0; i < len; i++) {
			if (main_id2[i] != 0) {
				if (main_id1[i] + 1 != main_id2[i] % 100) {
					System.out.println("i = " + i + ", " + main_id1[i] + ", " + main_id2[i]);
				}
			}
		}
		
	}

	private static int[] main_id1 = null;
	private static int[] main_id2 = null;
	
	public static void check1(IDataSource ds) {
		FutureBarSeries tohlcva99 = (FutureBarSeries) ds.getBarSeries(0, TIME_FRAME.DAY);
		FutureBarSeries[] tohlcvas = new FutureBarSeries[12];
		
		main_id1 = new int[tohlcva99.times.length];
		int index = 0;
		
		for (int i = 0; i < 12; i++) {
			tohlcvas[i] = (FutureBarSeries) ds.getBarSeries(i + 1, TIME_FRAME.DAY);
		}
		
		for (Bar bar : tohlcva99) {
			int j = 0;
			for (; j < 12; j++) {
				int ret = tohlcvas[j].findBarIndex(bar);
				if (ret >= 0) {
					break;
				}
			}
			if (j < 12) {
				// Normal
				main_id1[index++] = j;
			} else {
				System.out.println(bar);
			}
		}
	}
	
	public static void check2(IDataSource ds) {
		FutureBarSeries tohlcva99 = (FutureBarSeries) ds.getBarSeries(0, TIME_FRAME.DAY);
		FutureBarSeries[] tohlcvas = new FutureBarSeries[12];
		
		main_id2 = new int[tohlcva99.times.length];
		int index = 0;
		
		for (int i = 0; i < 12; i++) {
			tohlcvas[i] = (FutureBarSeries) ds.getBarSeries(i + 1, TIME_FRAME.DAY);
		}
		
		for (Bar bar : tohlcva99) {
			main_id2[index++] = ((FutureBar)bar).contractMonth;
			if (((FutureBar)bar).contractMonth < 100000) {
				System.out.println(bar);
			}
		}
	}
}
