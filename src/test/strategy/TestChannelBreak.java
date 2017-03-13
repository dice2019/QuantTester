package test.strategy;

import java.time.LocalDateTime;

import data.TIME_FRAME;
import helper.DateTimeHelper;
import strategy.ChannelBreakStrategy;
import tester.AbstractStrategyTester;

public class TestChannelBreak {

	public static void main(String[] args) {
		Integer channel_period = 58;
		AbstractStrategyTester st = new tester.SimpleStrategyTester("cu", TIME_FRAME.DAY);
		LocalDateTime start_date = LocalDateTime.of(1998, 1,  1,  0, 0, 0);
		LocalDateTime   end_date = LocalDateTime.of(2016, 9, 16, 17, 0, 0);
		st.setTestDateRange((int) DateTimeHelper.Ldt2Long(start_date), (int) DateTimeHelper.Ldt2Long(end_date));
		st.setStrategyParam(ChannelBreakStrategy.class, channel_period);
		st.evaluate();
		st.getPerformances().printAll();
		st.drawDailyBalance(ChannelBreakStrategy.class.getSimpleName() + ".png");
	}
}
