package strategy;

import strategy.template.BarBasedStrategy;
import strategy.template.IEveryBar;

public class BuyAndHoldStrategy extends BarBasedStrategy implements IEveryBar {

	@Override
	public float onNewBar() {
		this.position = 1;
		return Open[current_index];
	}
}
