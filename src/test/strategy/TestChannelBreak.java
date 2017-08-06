package test.strategy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import data.TIME_FRAME;
import helper.DateTimeHelper;
import strategy.ChannelBreakStrategy;
import test.CommonParam;
import test.ParamManager;
import tester.AbstractStrategyTester;

public class TestChannelBreak {

	private static final Logger logger = LogManager.getLogger();
	
	public static void main(String[] args) {
		final CommonParam cp = ParamManager.getCommonParam("cu", TIME_FRAME.DAY, "19980101 000000", "20160916 170000");
		final Object[] pp = ParamManager.getParticularParam(ChannelBreakStrategy.class, 58);
		
		AbstractStrategyTester st = new tester.SimpleStrategyTester(cp.instrument, cp.tf);
		st.setTestDateRange((int) DateTimeHelper.Ldt2Long(cp.start_date), (int) DateTimeHelper.Ldt2Long(cp.end_date));
		st.setStrategyParam(ChannelBreakStrategy.class, pp);
		st.evaluate();
		
		logger.info(st.getPerformances());
		st.drawDailyBalance(ChannelBreakStrategy.class.getSimpleName() + ".png");
	}
}
