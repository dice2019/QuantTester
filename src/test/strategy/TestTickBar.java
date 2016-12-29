package test.strategy;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import javax.imageio.ImageIO;

import data.ITickDataSource;
import data.TIME_FRAME;
import helper.ImageHelper;
import performance.DailyPerformances;
import performance.Performances;
import strategy.Portfolio;
import strategy.TickBarStrategy;

public class TestTickBar {

	static class ParamSet {
		int TickNum;
		int BarNum;
		Performances result;

		public ParamSet(int tickNum, int barNum, Performances result) {
			this.TickNum = tickNum;
			this.BarNum = barNum;
			this.result = result;
		}
	}

	private static ITickDataSource export = null;
	private static List<ParamSet> result_set = null;
	private static int result_set_index = 0;
	private static int completed_threads = 0;

	public static void main(String[] args) {
		// export = new data.foxtrade.KTExportFutures("ni", 0, TIME_FRAME.TICK);
		export = new data.sinyee.SinYeeDataSource("al", EnumSet.of(TIME_FRAME.TICK), str -> true);
		
		int MaxTickNum = 550;
		int MaxBarNum = 9;

		result_set = new ArrayList<>();
		for (int TickNum = 20; TickNum < MaxTickNum; TickNum++) {
			for (int BarNum = 2; BarNum < MaxBarNum; BarNum++) {
				result_set.add(new ParamSet(TickNum, BarNum, null));
			}
		}
		result_set_index = 0;
		completed_threads = 0;
		final int thread_num = 4;
		for (int i = 0; i < thread_num; i++) {
			new Thread(() -> EvaluateStrategyThread()).start();
		}
		
		synchronized (result_set) {
			while (completed_threads < thread_num) {
				try {
					result_set.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		Collections.sort(result_set, (arg0, arg1) -> Float.compare(arg0.result.ProfitRatio, arg1.result.ProfitRatio));
		OutputResults(result_set, MaxTickNum, MaxBarNum);
	}

	private static void EvaluateStrategyThread() {
		while (true) {
			ParamSet ps = null;
			synchronized (result_set) {
				if (result_set_index >= result_set.size()) {
					completed_threads++;
					result_set.notifyAll();
					return;
				}
				ps = result_set.get(result_set_index);
				result_set_index++;
			}
			EvaluateTickBarStrategy(ps);
		}
	}

	private static void EvaluateTickBarStrategy(ParamSet ps) {
		TickBarStrategy strategy = new TickBarStrategy(ps.TickNum, ps.BarNum);
		strategy.setDataSource(export);

		Portfolio portfolio = new Portfolio(100_0000.0f);
		portfolio.setCommission_ratio(0.0006f);
		portfolio.setMargin_ratio(1.0f);

		int days = strategy.getDays();
		float[] daily_balance = new float[days];
		for (int i = 0; i < days; i++) {
			strategy.day(portfolio, i);
			daily_balance[i] = portfolio.margin;
		}

		DailyPerformances dp = new DailyPerformances(null, daily_balance);
		dp.calculateAll();
		ps.result = dp;
	}

	private static int getColor(float value) {
		if (value <= 0.0f || Float.compare(value, Float.NaN) == 0)
			return 0;
		value = (float) Math.cbrt(Math.cbrt(value * 2.00));
		value *= 255.0f;

		int R = (int) (value);
		int G = 0;
		int B = 0;// (int) (256.0 - value);
		return ((R << 16) + (G << 8) + B);
	}

	private static void OutputResults(List<ParamSet> result_set, int MaxTickNum, int MaxBarNum) {
		BufferedImage bi = new BufferedImage(MaxTickNum, MaxBarNum, BufferedImage.TYPE_INT_RGB);
		for (int TickNum = 0; TickNum < MaxTickNum; TickNum++) {
			for (int BarNum = 0; BarNum < MaxBarNum; BarNum++) {
				bi.setRGB(TickNum, BarNum, Color.white.getRGB());
			}
		}
		
		for (ParamSet ps : result_set) {
			System.out.println("Ticks = " + String.format("%3d", ps.TickNum) + ", Bars = " + ps.BarNum + ", Result = " + ps.result.ProfitRatio);
			bi.setRGB(ps.TickNum, ps.BarNum, getColor(ps.result.ProfitRatio));
		}

		try {
			ImageIO.write(ImageHelper.flipImage(bi), "png", new File("E:\\tickbar.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
