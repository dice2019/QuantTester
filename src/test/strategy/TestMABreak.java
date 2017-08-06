package test.strategy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import data.TIME_FRAME;
import helper.DateTimeHelper;
import indicator.APPLIED_PRICE;
import indicator.MA;
import strategy.MABreakStrategy;
import test.CommonParam;
import test.ParamManager;
import tester.AbstractStrategyTester;

public class TestMABreak {

	private static final Logger logger = LogManager.getLogger();
	
	public static void main(String[] args) {
		final CommonParam cp = ParamManager.getCommonParam("cu", TIME_FRAME.DAY, "20100101 000000", "20160101 170000");
		final Object[] pp = ParamManager.getParticularParam(MABreakStrategy.class, 80, MA.MODE_EMA, APPLIED_PRICE.PRICE_CLOSE);
		
		AbstractStrategyTester st = new tester.RealStrategyTester(cp.instrument, cp.tf);
		st.setTestDateRange((int) DateTimeHelper.Ldt2Long(cp.start_date), (int) DateTimeHelper.Ldt2Long(cp.end_date));
		st.setStrategyParam(MABreakStrategy.class, pp);
		st.evaluate();
		
		logger.info(st.getPerformances());
		st.drawDailyBalance(MABreakStrategy.class.getSimpleName() + ".png");
	}
}
