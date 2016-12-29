package test.optimize;

import java.time.LocalDateTime;
import java.util.Map.Entry;
import java.util.Set;

import data.TIME_FRAME;
import helper.DateTimeHelper;
import indicator.APPLIED_PRICE;
import indicator.MA;
import performance.Performances;
import strategy.MaPsarStrategy;
import tester.StrategyOptimizer;

public final class OptimizeMaPsar {

	public static void main(String[] args) {
		StrategyOptimizer so = new StrategyOptimizer(tester.RealStrategyTester.class);
		so.setInstrumentParam("i", TIME_FRAME.MIN15);
		LocalDateTime start_date = LocalDateTime.of(2008, 1, 1,  0, 0, 0);
		LocalDateTime   end_date = LocalDateTime.of(2016, 1, 1, 17, 0, 0);
		so.setTestDateRange((int) DateTimeHelper.Ldt2Long(start_date), (int) DateTimeHelper.Ldt2Long(end_date));
		int num = so.setStrategyParamRange(MaPsarStrategy.class, new Integer[]{12, 500, 2}, new String[]{MA.MODE_EMA, MA.MODE_SMMA}, new APPLIED_PRICE[] {APPLIED_PRICE.PRICE_CLOSE, APPLIED_PRICE.PRICE_TYPICAL}, new Float[]{0.01f, 0.02f, 0.01f}, new Float[]{0.1f, 0.2f, 0.02f});
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
