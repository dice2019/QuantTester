package test.strategy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import data.TIME_FRAME;
import helper.DateTimeHelper;
import strategy.BuyAndHoldStrategy;
import test.CommonParam;
import test.ParamManager;
import tester.AbstractStrategyTester;

public class TestBuyAndHold {

	private static final Logger logger = LogManager.getLogger("TestBuyAndHold");
	
	public static void main(String[] args) {
		final CommonParam cp = ParamManager.getCommonParam("cu", TIME_FRAME.DAY, "19980101 000000", "20170915 170000");
		
		AbstractStrategyTester st = new tester.RealStrategyTester(cp.instrument, cp.tf);
		st.setTestDateRange((int) DateTimeHelper.Ldt2Long(cp.start_date), (int) DateTimeHelper.Ldt2Long(cp.end_date));
		st.setStrategyParam(BuyAndHoldStrategy.class);
		st.evaluate();
		
		logger.info(st.getPerformances());
		st.drawDailyBalance(BuyAndHoldStrategy.class.getSimpleName() + ".png");
	}
}
