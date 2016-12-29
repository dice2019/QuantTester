package test.optimize;

import java.time.LocalDateTime;
import java.util.Map.Entry;
import java.util.Set;

import data.TIME_FRAME;
import helper.DateTimeHelper;
import indicator.APPLIED_PRICE;
import indicator.MA;
import performance.Performances;
import strategy.IdealMABreakStrategy;
import tester.StrategyOptimizer;

public final class OptimizeIdealMABreak {

	public static void main(String[] args) {
		StrategyOptimizer so = new StrategyOptimizer(tester.RealStrategyTester.class);
		so.setInstrumentParam("al", TIME_FRAME.MIN15, 100_0000.0f, 0.03f);
		LocalDateTime start_date = LocalDateTime.of(2010, 1, 1,  0, 0, 0);
		LocalDateTime   end_date = LocalDateTime.of(2016, 1, 1, 17, 0, 0);
		so.setTestDateRange((int) DateTimeHelper.Ldt2Long(start_date), (int) DateTimeHelper.Ldt2Long(end_date));
		int num = so.setStrategyParamRange(IdealMABreakStrategy.class, new Integer[]{30, 400, 2}, MA.MA_MODES, new APPLIED_PRICE[]{APPLIED_PRICE.PRICE_TYPICAL, APPLIED_PRICE.PRICE_CLOSE});
		System.out.println(num);
		so.StartOptimization();
		Set<Entry<Object[],Performances>> entryset = so.result_db.entrySet();
		for (Entry<Object[],Performances> entry : entryset) {
			for (Object obj : entry.getKey()) {
				System.out.print(obj + ",\t");
			}
			System.out.println("ProfitRatio: " + String.format("%.5f", entry.getValue().ProfitRatio) + "\tMaxDrawDown: " + entry.getValue().MaxDrawDown);
		}
	}
}
