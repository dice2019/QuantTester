package strategy;

import strategy.template.BarBasedStrategy;
import strategy.template.IEveryOHLC;

public class FollowYesterdayStrategy extends BarBasedStrategy implements IEveryOHLC {

	private int days;
	private boolean follow;

	public FollowYesterdayStrategy(Integer days, Boolean follow) {
		super();
		this.days = days;
	}

	@Override
	public float onOpen() {
		if (current_index >= days) {
			boolean was_up = Close[current_index - days] > Open[current_index - days];
			if ((was_up && follow) || (!was_up && !follow)) {
				position = 1;
			} else {
				position = -1;
			}
		} else {
			position = 0;
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
		position = 0;
		return Close[current_index];
	}
}
