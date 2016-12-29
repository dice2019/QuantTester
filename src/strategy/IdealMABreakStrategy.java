package strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import data.struct.BarSeries;
import helper.MathHelper;
import indicator.APPLIED_PRICE;
import strategy.template.IEveryOHLC;
import trade.ITradeable;

public class IdealMABreakStrategy extends MABreakStrategy implements IEveryOHLC {

	private int[] ma_signal = null;
	private List<Integer> signal_list = null;
	private int[] ideal_high_low = null;

	public IdealMABreakStrategy(Integer Period, String ma_method, APPLIED_PRICE applied_price) {
		super(Period, ma_method, applied_price);
	}

	public IdealMABreakStrategy(Integer Period, String ma_method) {
		super(Period, ma_method);
	}

	public IdealMABreakStrategy(Integer Period) {
		super(Period);
	}

	@Override
	public void setBarSeries(BarSeries barseries) {
		super.setBarSeries(barseries);

		final int bars = Time.length;
		ma_signal = new int[bars];
		for (int i = 0; i < period; i++) {
			ma_signal[0] = 0;
		}
		for (int i = period; i < bars; i++) {
			if (Close[i - 1] > ma_buffer[i - 1]) {
				ma_signal[i] = 1;
			} else if (Close[i - 1] < ma_buffer[i - 1]) {
				ma_signal[i] = -1;
			} else {
				ma_signal[i] = 0;
			}
		}

		signal_list = new ArrayList<>();
		int state = 0;
		for (int i = 0; i < bars; i++) {
			if (ma_signal[i] == 1 && state != 1) {
				signal_list.add(i);
				ma_signal[i] = 1;
				state = 1;
			} else if (ma_signal[i] == -1 && state != -1) {
				signal_list.add(-i);
				ma_signal[i] = -1;
				state = -1;
			} else {
				ma_signal[i] = 0;
			}
		}

		int signal_num = signal_list.size();
		ideal_high_low = new int[bars];
		for (int i = 0; i < bars; i++) {
			ideal_high_low[i] = 0;
		}
		for (int i = 0; i < signal_num - 1; i++) {
			int signal_i = signal_list.get(i);
			int signal_i1 = signal_list.get(i + 1);

			if (signal_i > 0 && signal_i1 < 0) {
				float[] High_range = Arrays.copyOfRange(High, signal_i, -signal_i1);
				int index = MathHelper.MaxIndex(High_range);
				ideal_high_low[signal_i + index] = 1;
			} else if (signal_i < 0 && signal_i1 > 0) {
				float[] Low_range = Arrays.copyOfRange(Low, -signal_i, signal_i1);
				int index = MathHelper.MinIndex(Low_range);
				ideal_high_low[-signal_i + index] = -1;
			}
		}
	}

	@Override
	public float onOpen() {
		if (ma_signal[current_index] == 1) {
			position = 1;
		} else if (ma_signal[current_index] == -1) {
			position = -1;
		}
		return Open[current_index];
	}

	@Override
	public float onHigh() {
		if (ideal_high_low[current_index] == 1) {
			position = 0;
		}
		return High[current_index];
	}

	@Override
	public float onLow() {
		if (ideal_high_low[current_index] == -1) {
			position = 0;
		}
		return Low[current_index];
	}

	@Override
	public float onClose() {
		return Close[current_index];
	}

	@Override
	public void tradeOneBar(ITradeable trader) {
		IEveryOHLC.super.tradeOneBar(trader);
	}
}
