package strategy;

import strategy.template.BarBasedStrategy;
import strategy.template.IEveryOHLC;

public class FollowFirstKLineStrategy extends BarBasedStrategy implements IEveryOHLC {

	@Override
	public float onOpen() {
		if (current_index >= 1 && isTheFirstKLineOfTradingDay(current_index - 1)) {
			boolean was_up = Close[current_index - 1] > Open[current_index - 1];
			if (was_up) {
				position = 1;
			} else {
				position = -1;
			}
		}
		return Open[current_index];
	}

	@Override
	public float onHigh() {
		return High[current_index];
	}

	@Override
	public float onLow() {
		return Low[current_index];
	}

	@Override
	public float onClose() {
		if (current_index < Time.length - 1 && isTheFirstKLineOfTradingDay(current_index + 1)) {
			position = 0;
		}
		return Close[current_index];
	}
}
