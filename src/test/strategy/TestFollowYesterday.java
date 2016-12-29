package test.strategy;

import java.time.LocalDateTime;

import data.TIME_FRAME;
import helper.DateTimeHelper;
import strategy.FollowYesterdayStrategy;
import tester.AbstractStrategyTester;

public class TestFollowYesterday {

	public static void main(String[] args) {
		AbstractStrategyTester st = new tester.RealStrategyTester("rb", TIME_FRAME.DAY);
		LocalDateTime start_date = LocalDateTime.of(2016, 5, 12, 0, 0, 0);
		LocalDateTime   end_date = LocalDateTime.of(2016, 9, 5, 0, 0, 0);
		st.setTestDateRange((int) DateTimeHelper.Ldt2Long(start_date), (int) DateTimeHelper.Ldt2Long(end_date));
		st.setStrategyParam(FollowYesterdayStrategy.class, 3, false);
		st.evaluate();
		st.getPerformances().printAll();
		st.drawDailyBalance("E:\\" + FollowYesterdayStrategy.class.getSimpleName() + ".png");
	}
}
