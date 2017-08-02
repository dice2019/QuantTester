package test.strategy;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import data.TIME_FRAME;
import helper.DateTimeHelper;
import strategy.FollowFirstKLineStrategy;
import tester.AbstractStrategyTester;

public class TestFollowFirstKLine {

	private static final Logger logger = LogManager.getLogger();
	
	public static void main(String[] args) {
		AbstractStrategyTester st = new tester.SimpleStrategyTester("v", TIME_FRAME.MIN15);
		LocalDateTime start_date = LocalDateTime.of(2008, 1,  1, 0, 0, 0);
		LocalDateTime   end_date = LocalDateTime.of(2016, 1, 25, 0, 0, 0);
		st.setTestDateRange((int) DateTimeHelper.Ldt2Long(start_date), (int) DateTimeHelper.Ldt2Long(end_date));
		st.setStrategyParam(FollowFirstKLineStrategy.class);
		st.evaluate();
		logger.info(st.getPerformances());
		st.drawDailyBalance(FollowFirstKLineStrategy.class.getSimpleName() + ".png");
	}
}
