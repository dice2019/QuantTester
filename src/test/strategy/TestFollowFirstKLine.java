package test.strategy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import data.TIME_FRAME;
import helper.DateTimeHelper;
import strategy.FollowFirstKLineStrategy;
import test.CommonParam;
import test.ParamManager;
import tester.AbstractStrategyTester;

public class TestFollowFirstKLine {

	private static final Logger logger = LogManager.getLogger();
	
	public static void main(String[] args) {
		final CommonParam cp = ParamManager.getCommonParam("v", TIME_FRAME.MIN15, "20080101 000000", "20160125 000000");

		AbstractStrategyTester st = new tester.SimpleStrategyTester(cp.instrument, cp.tf);
		st.setTestDateRange((int) DateTimeHelper.Ldt2Long(cp.start_date), (int) DateTimeHelper.Ldt2Long(cp.end_date));
		st.setStrategyParam(FollowFirstKLineStrategy.class);
		st.evaluate();
		
		logger.info(st.getPerformances());
		st.drawDailyBalance(FollowFirstKLineStrategy.class.getSimpleName() + ".png");
	}
}
