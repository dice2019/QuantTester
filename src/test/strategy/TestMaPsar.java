package test.strategy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import data.TIME_FRAME;
import helper.DateTimeHelper;
import indicator.APPLIED_PRICE;
import indicator.MA;
import strategy.MaPsarStrategy;
import test.CommonParam;
import test.ParamManager;
import tester.AbstractStrategyTester;

public class TestMaPsar {

	private static final Logger logger = LogManager.getLogger();
	
	public static void main(String[] args) {
		final CommonParam cp = ParamManager.getCommonParam("m", TIME_FRAME.DAY, "20080101 000000", "20160101 170000");
		final Object[] pp = ParamManager.getParticularParam(MaPsarStrategy.class, 16, MA.MODE_EMA, APPLIED_PRICE.PRICE_TYPICAL, 0.02f, 0.18f);
		
		AbstractStrategyTester st = new tester.RealStrategyTester(cp.instrument, cp.tf);
		st.setTestDateRange((int) DateTimeHelper.Ldt2Long(cp.start_date), (int) DateTimeHelper.Ldt2Long(cp.end_date));
		st.setStrategyParam(MaPsarStrategy.class, pp);
		st.evaluate();
		
		logger.info(st.getPerformances());
		st.drawDailyBalance(MaPsarStrategy.class.getSimpleName() + ".png");
	}
}
