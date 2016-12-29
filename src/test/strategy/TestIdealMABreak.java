package test.strategy;

import java.time.LocalDateTime;

import data.TIME_FRAME;
import helper.DateTimeHelper;
import indicator.APPLIED_PRICE;
import indicator.MA;
import strategy.IdealMABreakStrategy;
import tester.AbstractStrategyTester;

public class TestIdealMABreak {

	public static void main(String[] args) {
		Integer Period = 345;
		AbstractStrategyTester st = new tester.SimpleStrategyTester("al", TIME_FRAME.MIN15, 100_0000.0f, 0.03f);
		LocalDateTime start_date = LocalDateTime.of(2010, 1, 1,  0, 0, 0);
		LocalDateTime   end_date = LocalDateTime.of(2016, 1, 1, 17, 0, 0);
		st.setTestDateRange((int) DateTimeHelper.Ldt2Long(start_date), (int) DateTimeHelper.Ldt2Long(end_date));
		st.setStrategyParam(IdealMABreakStrategy.class, Period, MA.MODE_SMMA, APPLIED_PRICE.PRICE_TYPICAL);
		st.evaluate();
		st.getPerformances().printAll();
		st.drawDailyBalance("E:\\" + IdealMABreakStrategy.class.getSimpleName() + ".png");
	}
}
