package test.strategy;

import java.time.LocalDateTime;

import data.TIME_FRAME;
import helper.DateTimeHelper;
import strategy.FollowFirstKLineStrategy;
import tester.AbstractStrategyTester;

public class TestFollowFirstKLine {

	public static void main(String[] args) {
		AbstractStrategyTester st = new tester.SimpleStrategyTester("v", TIME_FRAME.MIN15);
		LocalDateTime start_date = LocalDateTime.of(2008, 1,  1, 0, 0, 0);
		LocalDateTime   end_date = LocalDateTime.of(2016, 1, 25, 0, 0, 0);
		st.setTestDateRange((int) DateTimeHelper.Ldt2Long(start_date), (int) DateTimeHelper.Ldt2Long(end_date));
		st.setStrategyParam(FollowFirstKLineStrategy.class);
		st.evaluate();
		st.getPerformances().printAll();
		st.drawDailyBalance(FollowFirstKLineStrategy.class.getSimpleName() + ".png");
	}
}
