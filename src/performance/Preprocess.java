package performance;

import java.util.ArrayList;

import helper.DateTimeHelper;

final class Preprocess {
	
	private Preprocess() {
		// Never instantiate
	}
	
	private static int calcMonth(int Time) {
		int year = DateTimeHelper.getYear(Time);
		int month = DateTimeHelper.getMonthValue(Time);	// 1 to 12
		return (year * 100) + month;	// 20YYMM
	}
	
	public static float[] getMonthlyBalance(int[] Time, float[] balance) {
		ArrayList<Float> monthly_balance = new ArrayList<>();
		
		int last_month = calcMonth(Time[0]);
		for (int i = 1; i < Time.length; i++)  {
			int month = calcMonth(Time[i]);
			if (month > last_month) {
				last_month = month;
				monthly_balance.add(balance[i - 1]);
			}
		}

		monthly_balance.add(balance[Time.length - 1]);

		// TODO Use helper
		float[] ret_balance = new float[monthly_balance.size()];
		for (int i = 0; i < ret_balance.length; i++) {
			ret_balance[i] = monthly_balance.get(i);
		}
		return ret_balance;
	}
}
