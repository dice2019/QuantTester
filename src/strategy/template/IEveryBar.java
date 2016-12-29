package strategy.template;

import trade.ITradeable;

public interface IEveryBar extends IStrategy {
	default public void tradeOneBar(ITradeable trader) {
		trader.setPosition(onNewBar(), getPosition());
	}
	public float onNewBar();
}
