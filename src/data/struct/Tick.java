package data.struct;

import helper.DateTimeHelper;

public class Tick {
	public int no;
	public int time;
	public float price;
	public float volume;
	public float openInterest;
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(DateTimeHelper.Long2Ldt(time));
		sb.append("\t" + price);
		sb.append("\t" + volume);
		sb.append("\t" + openInterest);
		return sb.toString();
	}
}
