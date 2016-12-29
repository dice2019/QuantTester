package test.optimize;

import java.util.Map.Entry;
import java.time.LocalDateTime;
import java.util.Set;

import data.TIME_FRAME;
import helper.DateTimeHelper;
import performance.Performances;
import strategy.ChannelBreakStrategy;
import tester.StrategyOptimizer;

public final class OptimizeChannelBreak {

	public static void main(String[] args) {
		StrategyOptimizer so = new StrategyOptimizer(tester.RealStrategyTester.class);
		so.setInstrumentParam("cu", TIME_FRAME.DAY);
		LocalDateTime start_date = LocalDateTime.of(1998, 1,  1,  0, 0, 0);
		LocalDateTime   end_date = LocalDateTime.of(2016, 9, 16, 17, 0, 0);
		so.setTestDateRange((int) DateTimeHelper.Ldt2Long(start_date), (int) DateTimeHelper.Ldt2Long(end_date));
		int num = so.setStrategyParamRange(ChannelBreakStrategy.class, new Integer[]{4, 800, 2});
		System.out.println(num);
		so.StartOptimization();
		Set<Entry<Object[],Performances>> entryset = so.result_db.entrySet();
		for (Entry<Object[],Performances> entry : entryset) {
			for (Object obj : entry.getKey()) {
				System.out.print(obj + ",\t");
			}
			System.out.println("ProfitRatio: " + entry.getValue().ProfitRatio + "\tMaxDrawDown: " + entry.getValue().MaxDrawDown);
		}
	}
}
