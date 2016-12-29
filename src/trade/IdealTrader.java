package trade;

import strategy.Portfolio;

public class IdealTrader implements ITradeable {

	protected int position = 0;
	protected final float vol_unit;
	protected final Portfolio portfolio;

	public IdealTrader(Portfolio portfolio, float vol_unit) {
		this.portfolio = portfolio;
		this.vol_unit = vol_unit;
	}

	public IdealTrader(Portfolio portfolio) {
		this(portfolio, 10.0f);
	}

	@Override
	public void setPosition(float price, int new_position) {
		if (new_position == position) {
			return;
		}

		if (new_position >= 0 && position < 0) {
			closeShort(price, -position);
			if (position != 0) {
				return;
			}
		} else if (new_position <= 0 && position > 0) {
			closeLong(price, position);
			if (position != 0) {
				return;
			}
		}

		int diff = new_position - position;

		if (diff < 0 && new_position > 0) {
			closeLong(price, -diff);
		} else if (0 >= position &&  diff < 0) {
			openShort(price, -diff);
		} else if (0 <= position &&  diff > 0) {
			openLong(price, diff);
		} else if (diff > 0 && new_position < 0) {
			closeShort(price, diff);
		}
	}

	protected void openLong(float price, int vol_num) {
		if (portfolio.openLong(price, vol_num * vol_unit)) {
			position += vol_num;
		}
	}

	protected void openShort(float price, int vol_num) {
		if (portfolio.openShort(price, vol_num * vol_unit)) {
			position -= vol_num;
		}
	}

	protected void closeLong(float price, int vol_num) {
		if (portfolio.closeLong(price, vol_num * vol_unit)) {
			position -= vol_num;
		}
	}

	protected void closeShort(float price, int vol_num) {
		if (portfolio.closeShort(price, vol_num * vol_unit)) {
			position += vol_num;
		}
	}
}
