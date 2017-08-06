package test.optimize;

import java.util.Map.Entry;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;

import java.util.Set;

import data.TIME_FRAME;
import helper.DateTimeHelper;
import indicator.MA;
import performance.Performances;
import strategy.MABreakStrategy;
import test.CommonParam;
import test.ParamManager;
import tester.StrategyOptimizer;

public final class OptimizeMABreak {

	public static void main(String[] args) {
		final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		final Configuration config = ctx.getConfiguration();
		config.getLoggerConfig(strategy.Portfolio.class.getName()).setLevel(Level.WARN);
		ctx.updateLoggers(config);
		
		final CommonParam cp = ParamManager.getCommonParam("cu", TIME_FRAME.MIN15, "20080101 000000", "20160101 170000");
		
		StrategyOptimizer so = new StrategyOptimizer(tester.RealStrategyTester.class);
		so.setInstrumentParam(cp.instrument, cp.tf);
		so.setTestDateRange((int) DateTimeHelper.Ldt2Long(cp.start_date), (int) DateTimeHelper.Ldt2Long(cp.end_date));
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
