package helper;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class DateTimeHelper {
	
	// 处理所有时间都不考虑时差, 参数ZoneOffset使用ZoneOffset.UTC
	
	public final static LocalDateTime Long2Ldt(final long time) {
		return LocalDateTime.ofEpochSecond(time, 0, ZoneOffset.UTC);
	}
	public final static long Ldt2Long(final LocalDateTime ldt) {
		return ldt.toEpochSecond(ZoneOffset.UTC);
	}

	public final static int getYear(final long time) {
		return Long2Ldt(time).getYear();
	}
	public final static int getMonthValue(final long time) {
		return Long2Ldt(time).getMonthValue();
	}
	
	public final static int getDayOfMonth(final long time) {
		return Long2Ldt(time).getDayOfMonth();
	}
	public final static int getDayOfYear(final long time) {
		return Long2Ldt(time).getDayOfYear();
	}

	public final static int getHour(final long time) {
		return Long2Ldt(time).getHour();
	}
	public final static int getMinute(final long time) {
		return Long2Ldt(time).getMinute();
	}
	public final static int getSecond(final long time) {
		return Long2Ldt(time).getSecond();
	}
}
