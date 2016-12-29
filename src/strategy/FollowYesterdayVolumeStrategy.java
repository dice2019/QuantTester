package strategy;

import strategy.template.BarBasedStrategy;
import strategy.template.IEveryOHLC;

public class FollowYesterdayVolumeStrategy extends BarBasedStrategy implements IEveryOHLC {

	@Override
	public float onOpen() {
		if (current_index > 1) {
			boolean follow_up = Close[current_index - 1] > Close[current_index - 2] && Volume[current_index - 1] > Volume[current_index - 2];
			boolean follow_down = Close[current_index - 1] < Close[current_index - 2] && Volume[current_index - 1] > Volume[current_index - 2];
			boolean reverse_up = Close[current_index - 1] < Close[current_index - 2] && Volume[current_index - 1] < Volume[current_index - 2];
			boolean reverse_down = Close[current_index - 1] > Close[current_index - 2] && Volume[current_index - 1] < Volume[current_index - 2];
			
			if (follow_down || reverse_down) {
				position = -1;
			} else if (follow_up || reverse_up) {
				position = 1;
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
