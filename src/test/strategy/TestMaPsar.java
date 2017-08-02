package test.strategy;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import data.TIME_FRAME;
import helper.DateTimeHelper;
import indicator.APPLIED_PRICE;
import indicator.MA;
import strategy.MaPsarStrategy;
import tester.AbstractStrategyTester;

public class TestMaPsar {

	private static final Logger logger = LogManager.getLogger();
	
	public static void main(String[] args) {
		Integer Period = 16;
		AbstractStrategyTester st = new tester.RealStrategyTester("m", TIME_FRAME.DAY);
		LocalDateTime start_date = LocalDateTime.of(2008, 1, 1,  0, 0, 0);
		LocalDateTime   end_date = LocalDateTime.of(2016, 1, 1, 17, 0, 0);
		st.setTestDateRange((int) DateTimeHelper.Ldt2Long(start_date), (int) DateTimeHelper.Ldt2Long(end_date));
		st.setStrategyParam(MaPsarStrategy.class, Period, MA.MODE_EMA, APPLIED_PRICE.PRICE_TYPICAL, 0.02f, 0.18f);
		st.evaluate();
		logger.info(st.getPerformances());
		st.drawDailyBalance(MaPsarStrategy.class.getSimpleName() + ".png");
	}
}
