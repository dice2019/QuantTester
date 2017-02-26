package data.struct;

import java.util.Iterator;
import java.util.List;

public final class FutureBarSeries extends BarSeries implements Iterable<Bar> {
	public float openInterests[];
	public int   contractMonths[];
	public float settlements[];

	public FutureBarSeries(List<? extends FutureBar> bars) {
		super(bars);

		if (bars == null || bars.isEmpty()) {
			openInterests = null;
			contractMonths = null;
			settlements = null;
			return;
		}

		this.openInterests = new float[array_size];

		for (int i = 0; i < array_size; i++) {
			this.openInterests[i] = bars.get(i).openInterest;
		}

		if (bars.get(0).contractMonth > 0) {
			this.contractMonths = new int[array_size];
			for (int i = 0; i < array_size; i++) {
				this.contractMonths[i] = bars.get(i).contractMonth;
			}
		} else {
			this.contractMonths = null;
		}

		if (bars.get(0).settlement > 0.0f) {
			this.settlements = new float[array_size];
			for (int i = 0; i < array_size; i++) {
				this.settlements[i] = bars.get(i).settlement;
			}
		} else {
			this.settlements = null;
		}
	}

	/**
	 * 由于数据质量问题, 结算价可能为0
	 */
	public void checkAndFixSettlement() {
		if (settlements != null) {
			for (int j = 0; j < times.length; j++) {
				if (settlements[j] < lows[j] || settlements[j] > highs[j]) {
					// TODO use log4j
					System.out.println("Fixed settlement price! j = " + j);
					settlements[j] = closes[j] + 0.003f;	// 加上 0.003f 作为标记
				}
			}
		}
	}

	public float getCloseByTime(int close_time) {
		if (settlements != null) {
			for (int j = 0; j < times.length; j++) {
				if (times[j] >= close_time) {	// FIXME 由于数据质量问题, 某些日期可能无数据
					return closes[j];
				}
			}
		}
		return -0.1f;
	}

	public float getSettlementByTime(int close_time) {
		if (settlements != null) {
			for (int j = 0; j < times.length; j++) {
				if (times[j] >= close_time) {	// FIXME 由于数据质量问题, 某些日期可能无数据
					return settlements[j];
				}
			}
		}
		return -0.1f;
	}

	@Override
	public Iterator<Bar> iterator() {
		return new Iterator<Bar>() {

			private int index = 0;

			@Override
			public boolean hasNext() {
				return times != null && index < times.length;
			}

			@Override
			public Bar next() {
				FutureBar bar = new FutureBar();
				bar.time = times[index];
				bar.open = opens[index];
				bar.high = highs[index];
				bar.low = lows[index];
				bar.close = closes[index];
				bar.volume = volumes[index];
				bar.amount = amounts[index];
				bar.openInterest = openInterests[index];
				if (contractMonths != null) {
					bar.contractMonth = contractMonths[index];
				} else {
					bar.contractMonth = 0;
				}
				if (settlements != null) {
					bar.settlement = settlements[index];
				} else {
					bar.settlement = 0.0f;
				}

				index++;
				return bar;
			}
		};
	}
}
