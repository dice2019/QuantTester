package test.strategy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import data.TIME_FRAME;
import helper.DateTimeHelper;
import strategy.FollowYesterdayVolumeStrategy;
import test.CommonParam;
import test.ParamManager;
import tester.AbstractStrategyTester;
import tester.SimpleStrategyTester;

public class TestFollowYesterdayVolume {

	private static final Logger logger = LogManager.getLogger();
	
	public static void main(String[] args) {
		final CommonParam cp = ParamManager.getCommonParam("al", TIME_FRAME.DAY, "20160101 000000", "20160905 000000");
		
		AbstractStrategyTester st = new SimpleStrategyTester(cp.instrument, cp.tf);
		st.setTestDateRange((int) DateTimeHelper.Ldt2Long(cp.start_date), (int) DateTimeHelper.Ldt2Long(cp.end_date));
		st.setStrategyParam(FollowYesterdayVolumeStrategy.class);
		st.evaluate();
		
		logger.info(st.getPerformances());
		st.drawDailyBalance(FollowYesterdayVolumeStrategy.class.getSimpleName() + ".png");
	}
}
