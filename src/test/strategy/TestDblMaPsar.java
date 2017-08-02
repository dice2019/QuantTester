package test.strategy;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import data.TIME_FRAME;
import helper.DateTimeHelper;
import indicator.APPLIED_PRICE;
import indicator.MA;
import strategy.DblMaPsarStrategy;
import tester.AbstractStrategyTester;

public class TestDblMaPsar {

	private static final Logger logger = LogManager.getLogger();
	
	public static void main(String[] args) {
		AbstractStrategyTester st = new tester.RealStrategyTester("m", TIME_FRAME.DAY);
		LocalDateTime start_date = LocalDateTime.of(2008, 1, 1,  0, 0, 0);
		LocalDateTime   end_date = LocalDateTime.of(2016, 10, 1, 17, 0, 0);
		st.setTestDateRange((int) DateTimeHelper.Ldt2Long(start_date), (int) DateTimeHelper.Ldt2Long(end_date));
		st.setStrategyParam(DblMaPsarStrategy.class, 3, 16, MA.MODE_EMA, APPLIED_PRICE.PRICE_TYPICAL, 0.02f, 0.18f);
		st.evaluate();
		logger.info(st.getPerformances());
		st.drawDailyBalance(DblMaPsarStrategy.class.getSimpleName() + ".png");
	}
}
