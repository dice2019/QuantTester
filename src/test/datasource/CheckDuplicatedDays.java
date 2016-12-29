package test.datasource;

import java.util.Arrays;
import java.util.EnumSet;

import data.IDataSource;
import data.TIME_FRAME;
import data.sinyee.SinYeeDataSource;
import data.struct.BarSeries;

public class CheckDuplicatedDays {

	private static final String[] mon = {"99", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };

	/**
	 * 检测每个月份的合约的各个年份之间是否有交易日重复
	 * 已知AU, A有此问题
	 * @param args
	 */
	public static void main(String[] args) {
		for (int j = 1; j <= 12; j++) {
			final int fj = j;
			IDataSource kt_data = new SinYeeDataSource("y", EnumSet.of(TIME_FRAME.DAY), str -> str.endsWith(mon[fj]));

			BarSeries t= kt_data.getBarSeries(j, TIME_FRAME.DAY);
			if (t == null || t.times == null) {
				continue;
			}
			int[] time = t.times;
			Arrays.sort(time);
			for (int i = 1; i < time.length; i++) {
				if (time[i] == time[i - 1]) {
					System.out.println(i);
				}
			}
		}
	}
}
