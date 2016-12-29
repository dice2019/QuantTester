package test.datasource;

import java.util.EnumSet;

import data.IDataSource;
import data.TIME_FRAME;
import data.foxtrade.KTExportFutures;
import data.sinyee.SinYeeDataSource;
import data.struct.BarSeries;
import helper.DateTimeHelper;

public class CompareKTandSinYee {

	public static void main(String[] args) {

		final String ins = "y";
		final TIME_FRAME tf = TIME_FRAME.DAY;

		IDataSource ds1 = new KTExportFutures(ins, EnumSet.of(tf));

		IDataSource ds2 = new SinYeeDataSource(ins, EnumSet.of(tf), str -> true);

		checkds(ds1, ds2);
	}

	private static void checkds(IDataSource ds1, IDataSource ds2) {
		for (int mon = 0; mon <= 12; mon++) {
			BarSeries tolhcva1 = ds1.getBarSeries(mon, TIME_FRAME.DAY);
			BarSeries tolhcva2 = ds2.getBarSeries(mon, TIME_FRAME.DAY);
			
			if (tolhcva1 == null || tolhcva2 == null || tolhcva1.times == null || tolhcva2.times == null) {
				System.out.println("Skipped month " + mon + " !!");
				continue;
			}
			
			int[] time1 = tolhcva1.times;
			int[] time2 = tolhcva2.times;

			int index1 = time1.length;
			int index2 = time2.length;
			while (index1 > 0 && index2 > 0) {
				index1 --;
				index2 --;
				
				int day_1 = (time1[index1] / (24 * 3600));
				int day_2 = (time2[index2] / (24 * 3600));
				
				if (day_1 > day_2) {
					System.out.println("SY  mon = " + mon);
					System.out.println(DateTimeHelper.Long2Ldt(time1[index1]));
					System.out.println(DateTimeHelper.Long2Ldt(time2[index2]));
					index1 --;
				} else if (day_1 < day_2) {
					System.out.println("KT  mon = " + mon);
					System.out.println(DateTimeHelper.Long2Ldt(time1[index1]));
					System.out.println(DateTimeHelper.Long2Ldt(time2[index2]));
					index2 --;
				}
			}
		}
	}
}
