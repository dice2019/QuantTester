package test.optimize;

import java.time.LocalDateTime;
import java.util.Map.Entry;
import java.util.Set;

import data.TIME_FRAME;
import helper.DateTimeHelper;
import indicator.APPLIED_PRICE;
import indicator.MA;
import performance.Performances;
import strategy.DblMaPsarStrategy;
import tester.StrategyOptimizer;

public final class OptimizeDblMaPsar {

	public static void main(String[] args) {
		StrategyOptimizer so = new StrategyOptimizer(tester.RealStrategyTester.class);
		so.setInstrumentParam("m", TIME_FRAME.MIN60);
		LocalDateTime start_date = LocalDateTime.of(2008, 1, 1,  0, 0, 0);
		LocalDateTime   end_date = LocalDateTime.of(2016, 1, 1, 17, 0, 0);
		so.setTestDateRange((int) DateTimeHelper.Ldt2Long(start_date), (int) DateTimeHelper.Ldt2Long(end_date));
		int num = so.setStrategyParamRange(DblMaPsarStrategy.class, new Integer[]{3, 5, 1}, new Integer[]{10, 100, 2}, MA.MA_MODES, new APPLIED_PRICE[] {APPLIED_PRICE.PRICE_CLOSE, APPLIED_PRICE.PRICE_TYPICAL}, new Float[]{0.01f, 0.02f, 0.01f}, new Float[]{0.12f, 0.2f, 0.02f});
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
