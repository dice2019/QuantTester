package test.strategy;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import data.TIME_FRAME;
import helper.DateTimeHelper;
import strategy.FollowYesterdayVolumeStrategy;
import tester.AbstractStrategyTester;
import tester.SimpleStrategyTester;

public class TestFollowYesterdayVolume {

	private static final Logger logger = LogManager.getLogger();
	
	public static void main(String[] args) {
		AbstractStrategyTester st = new SimpleStrategyTester("al", TIME_FRAME.DAY);
		LocalDateTime start_date = LocalDateTime.of(2016, 1, 1, 0, 0, 0);
		LocalDateTime   end_date = LocalDateTime.of(2016, 9, 5, 0, 0, 0);
		st.setTestDateRange((int) DateTimeHelper.Ldt2Long(start_date), (int) DateTimeHelper.Ldt2Long(end_date));
		st.setStrategyParam(FollowYesterdayVolumeStrategy.class);
		st.evaluate();
		logger.info(st.getPerformances());
		st.drawDailyBalance(FollowYesterdayVolumeStrategy.class.getSimpleName() + ".png");
	}
}
