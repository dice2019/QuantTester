package test.datasource;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.EnumSet;

import data.IDataSource;
import data.TIME_FRAME;
import data.foxtrade.KTExportFutures;
import data.sinyee.SinYeeDataSource;
import data.struct.Bar;

@SuppressWarnings("unused")
public class CheckBar {

	private static final String[] mon = {"99", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };

	public static void main(String[] args) {
		
		final int i = 7;
		final TIME_FRAME tf = TIME_FRAME.DAY;
		
		IDataSource ds = new SinYeeDataSource("rb", EnumSet.of(tf), str -> str.endsWith(mon[i]));
		
		// IDataSource ds = new KTExportFutures("cu", EnumSet.of(tf));

		for (Bar bar : ds.getBarSeries(i, tf)) {
			if (bar.time >= LocalDateTime.of(2008, 1, 5, 1, 0).toEpochSecond(ZoneOffset.UTC) &&
				bar.time < LocalDateTime.of(2010, 1, 20, 0, 0).toEpochSecond(ZoneOffset.UTC)) {
				System.out.println(bar);
			}
		}
	}
}
