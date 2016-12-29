package performance;

public class DailyPerformances extends Performances {

	private static final long serialVersionUID = 1L;
	
	private final float[] daily_balance;	// 标准化日结余
	private final float[] monthly_balance;	// 标准化月结余

	public int ProfitDays = 0;
	public int LossDays = 0;
	
	public DailyPerformances(final int[] daily_time, final float[] daily_balance) {
		this.daily_balance = daily_balance;
		if (daily_time == null) {
			this.monthly_balance = null;
		} else {
			this.monthly_balance = Preprocess.getMonthlyBalance(daily_time, daily_balance);
		}
	}
	
	public void calculateTotal_Days() {
		if (daily_balance == null) {
			return;
		}
		
		Total_Days = daily_balance.length;
	}
	
	public void calculateProfitRatio() {
		if (daily_balance == null) {
			return;
		}
		
		float Profit = daily_balance[daily_balance.length - 1] - daily_balance[0];
		ProfitRatio = Profit / daily_balance[0];
	}
	
	// TODO Need UT
	public void calculateMaxDrawDown() {
		if (daily_balance == null) {
			return;
		}
		
		MaxDrawDown = 0.0f;
		for (int i = 0; i < daily_balance.length; i++) {
			if (i > 0 && daily_balance[i] <= daily_balance[i - 1]) {
				continue;
			}
			for (int j = i; j < daily_balance.length; j++) {
				float drawdown = (daily_balance[i] - daily_balance[j]); //  / daily_balance[i];
				if (drawdown > MaxDrawDown) {
					MaxDrawDown = drawdown;
				}
			}
		}
	}
	
	// FIXME 如果固定交易手数, 计算结果无法反映真实的夏普比率
	public void calculateSharpeRatio() {
		if (monthly_balance == null) {
			return;
		}
		
		float[] monthly_return_ratio = new float[monthly_balance.length - 1];
		for (int i = 0; i < monthly_balance.length - 1; i++) {
			monthly_return_ratio[i] = monthly_balance[i + 1] / monthly_balance[i] - 1;
		}
		float ERP = helper.MathHelper.Average(monthly_return_ratio);
		float ERF = 0.04f / 12.0f;
		float SD = helper.MathHelper.SD(monthly_return_ratio);
		
		SharpeRatio = (ERP - ERF) / SD;
	}

	@Override
	public void calculateGrossProfit() {
		if (daily_balance == null) {
			return;
		}
		
		GrossProfit = 0.0f;
		GrossLoss = 0.0f;
		ProfitDays = 0;
		LossDays = 0;
	
		for (int i = 1; i < daily_balance.length; i++) {
			float diff = daily_balance[i] - daily_balance[i - 1];
			if (diff > 0.0f) {
				GrossProfit += diff;
				ProfitDays ++;
			} else if (diff < 0.0f) {
				GrossLoss -= diff;
				LossDays ++;
			}
		}
	}

	@Override
	public void calculateGrossLoss() {
		// Nothing to do
	}

	public void calculateProfitDays() {
		// Nothing to do
	}
	
	public void calculateLossDays() {
		// Nothing to do
	}
}
