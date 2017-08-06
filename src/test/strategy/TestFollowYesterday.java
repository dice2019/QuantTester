package test.strategy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import data.TIME_FRAME;
import helper.DateTimeHelper;
import strategy.FollowYesterdayStrategy;
import test.CommonParam;
import test.ParamManager;
import tester.AbstractStrategyTester;

public class TestFollowYesterday {

	private static final Logger logger = LogManager.getLogger();
	
	public static void main(String[] args) {
		final CommonParam cp = ParamManager.getCommonParam("rb", TIME_FRAME.DAY, "20160512 000000", "20160905 000000");
		final Object[] pp = ParamManager.getParticularParam(FollowYesterdayStrategy.class, 3, false);
		
		AbstractStrategyTester st = new tester.RealStrategyTester(cp.instrument, cp.tf);
		st.setTestDateRange((int) DateTimeHelper.Ldt2Long(cp.start_date), (int) DateTimeHelper.Ldt2Long(cp.end_date));
		st.setStrategyParam(FollowYesterdayStrategy.class, pp);
		st.evaluate();
		logger.info(st.getPerformances());
		st.drawDailyBalance(FollowYesterdayStrategy.class.getSimpleName() + ".png");
	}
}
