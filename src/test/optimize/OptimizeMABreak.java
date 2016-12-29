package test.optimize;

import java.time.LocalDateTime;
import java.util.Map.Entry;
import java.util.Set;

import data.TIME_FRAME;
import helper.DateTimeHelper;
import indicator.MA;
import performance.Performances;
import strategy.MABreakStrategy;
import tester.StrategyOptimizer;

public final class OptimizeMABreak {

	public static void main(String[] args) {
		StrategyOptimizer so = new StrategyOptimizer(tester.RealStrategyTester.class);
		so.setInstrumentParam("cu", TIME_FRAME.MIN15);
		LocalDateTime start_date = LocalDateTime.of(2008, 1, 1,  0, 0, 0);
		LocalDateTime   end_date = LocalDateTime.of(2016, 1, 1, 17, 0, 0);
		so.setTestDateRange((int) DateTimeHelper.Ldt2Long(start_date), (int) DateTimeHelper.Ldt2Long(end_date));
		int num = so.setStrategyParamRange(MABreakStrategy.class, new Integer[]{8, 800, 2}, MA.MA_MODES);
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
