package test.optimize;

import java.util.Map.Entry;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;

import java.util.Set;

import data.TIME_FRAME;
import helper.DateTimeHelper;
import indicator.APPLIED_PRICE;
import indicator.MA;
import performance.Performances;
import strategy.DblMaPsarStrategy;
import test.CommonParam;
import test.ParamManager;
import tester.StrategyOptimizer;

public final class OptimizeDblMaPsar {

	public static void main(String[] args) {
		final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		final Configuration config = ctx.getConfiguration();
		config.getLoggerConfig(strategy.Portfolio.class.getName()).setLevel(Level.WARN);
		ctx.updateLoggers(config);
		
		final CommonParam cp = ParamManager.getCommonParam("m", TIME_FRAME.MIN60, "19980101 000000", "20160101 170000");
		
		StrategyOptimizer so = new StrategyOptimizer(tester.RealStrategyTester.class);
		so.setInstrumentParam(cp.instrument, cp.tf);
		so.setTestDateRange((int) DateTimeHelper.Ldt2Long(cp.start_date), (int) DateTimeHelper.Ldt2Long(cp.end_date));
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
