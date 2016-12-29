package test.datasource;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import data.ITickDataSource;
import data.TIME_FRAME;
import data.collector.LiquidityCollector;
import data.struct.DetailedTick;
import data.struct.Tick;
import helper.DateTimeHelper;

public class CheckLimit {

	private static void checkLimit(List<? extends Tick> ticks) {
		@SuppressWarnings("unchecked")
		List<DetailedTick> detailedTicklist = (List<DetailedTick>) ticks;
		
		Map<Integer, Byte> result = detailedTicklist.stream().collect(LiquidityCollector.groupingBy(TIME_FRAME.MIN15));
		// Map<Integer, List<DetailedTick>> result = detailedTicklist.stream().collect(Collectors.groupingBy((tick) -> tick.mTime / (60 * 15)));
		
		for (Integer i : result.keySet())
			System.out.println(DateTimeHelper.Long2Ldt(i) + ": " + result.get(i));
		
		for (Tick tick: ticks) {
			if (tick.time >= LocalDateTime.of(2013, 2, 20, 0, 0).toEpochSecond(ZoneOffset.UTC)
				&& tick.time < LocalDateTime.of(2013, 2, 20, 9, 40).toEpochSecond(ZoneOffset.UTC)
				) {
				System.out.println(tick);
			}
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		ITickDataSource export = new data.sinyee.SinYeeDataSource("rb", EnumSet.of(TIME_FRAME.TICK), str -> true || str.endsWith("1609"));

		for (List<? extends Tick> ticks : export.getDailyTicks()) {
			if (ticks.get(0).time >= LocalDateTime.of(2013, 2, 20, 0, 0).toEpochSecond(ZoneOffset.UTC)
				&& ticks.get(0).time < LocalDateTime.of(2013, 2, 21, 0, 0).toEpochSecond(ZoneOffset.UTC)
				) {
				checkLimit(ticks);
			}
		}
	}
}
