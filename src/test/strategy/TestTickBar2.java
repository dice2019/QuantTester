package test.strategy;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;

import data.ITickDataSource;
import data.TIME_FRAME;
import data.foxtrade.KTExportFutures;
import global.Config;
import helper.ImageHelper;
import helper.ReflectHelper;
import performance.DailyPerformances;
import strategy.Portfolio;
import strategy.TickBarStrategy;

public class TestTickBar2 {

	static class ParamSet {
		int TickNum;
		int BarNum;
		DailyPerformances result;

		public ParamSet(int tickNum, int barNum, DailyPerformances result) {
			super();
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
		export = new KTExportFutures("al", 0, TIME_FRAME.TICK);

		int MaxTickNum = 300;
		int MaxBarNum = 6;

		result_set = new ArrayList<>();
		for (int TickNum = 200; TickNum < MaxTickNum; TickNum++) {
			for (int BarNum = 5; BarNum < MaxBarNum; BarNum++) {
				result_set.add(new ParamSet(TickNum, BarNum, null));
			}
		}
		result_set_index = 0;
		completed_threads = 0;
		final int thread_num = Config.UseThreads;
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
		Collections.sort(result_set, (arg0, arg1) -> Float.compare(arg0.TickNum, arg1.TickNum));
		OutputResults(result_set, MaxTickNum, MaxBarNum);
		
		ParamSet ps = new ParamSet(201, 5, null);
		EvaluateTickBarStrategy(ps);
		OutputResult(ps);
//		ps = new ParamSet(202, 5, null);
//		EvaluateTickBarStrategy(ps);
//		OutputResult(ps);
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

		Portfolio portfolio = new Portfolio(1000000.0f);
		portfolio.setCommission_ratio(0.0003f);
		portfolio.setMargin_ratio(1.0f);

		float[] daily_balance = new float[strategy.getDays()];
		for (int i = 0; i < strategy.getDays(); i++) {
			if (i == 116) {
				float[] Close = strategy.get_daily_tick_bar().get(i).closes;
				for (float price: Close) {
					System.out.println(price);
				}
			}
			
			strategy.day(portfolio, i);
			daily_balance[i] = portfolio.margin;
		}

		DailyPerformances dp = new DailyPerformances(null, daily_balance);
		dp.calculateAll();
		ps.result = dp;
	}

	private static int getColor(float value) {
		if (value <= 1000000.0f || Float.compare(value, Float.NaN) == 0)
			return 0;
		value = (float) Math.log10(value);
		value *= 28.0f;

		int R = (int) (value);
		int G = 0;
		int B = 0;
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
			ImageIO.write(ImageHelper.flipImage(bi), "png", new File(Config.ResultDir + "tickbar.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void OutputResult(ParamSet ps) {
		System.out.println("Ticks = " + String.format("%3d", ps.TickNum) + ", Bars = " + ps.BarNum);
		System.out.println(ps.result);

		BufferedImage bi = new BufferedImage(ps.result.Total_Days, 1000, BufferedImage.TYPE_INT_RGB);

		float[] daily_balance = (float[]) ReflectHelper.getPrivateField(ps.result, "daily_balance");
		
		for (int i = 0; i < ps.result.Total_Days; i++) {
			bi.setRGB(i, (int) (daily_balance[i] / 2000.0f), Color.white.getRGB());
		}
		
		try {
			ImageIO.write(ImageHelper.flipImage(bi), "png", new File(Config.ResultDir + "single_time.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
