package test.strategy;

import java.time.LocalDateTime;

import data.TIME_FRAME;
import helper.DateTimeHelper;
import strategy.BuyAndHoldStrategy;
import tester.AbstractStrategyTester;

public class TestBuyAndHold {

	public static void main(String[] args) {
		AbstractStrategyTester st = new tester.RealStrategyTester("cu", TIME_FRAME.DAY);
		LocalDateTime start_date = LocalDateTime.of(1998, 1,  1,  0, 0, 0);
		LocalDateTime   end_date = LocalDateTime.of(2016, 9, 16, 17, 0, 0);
		st.setTestDateRange((int) DateTimeHelper.Ldt2Long(start_date), (int) DateTimeHelper.Ldt2Long(end_date));
		st.setStrategyParam(BuyAndHoldStrategy.class);
		st.evaluate();
		st.getPerformances().printAll();
		st.drawDailyBalance(BuyAndHoldStrategy.class.getSimpleName() + ".png");
	}
}
