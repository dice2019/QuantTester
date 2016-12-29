package data.struct;

import helper.DateTimeHelper;

public class Bar {
	public int time;
	public float open;
	public float high;
	public float low;
	public float close;
	public float volume;
	public float amount;
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(DateTimeHelper.Long2Ldt(time));
		sb.append("\t" + open);
		sb.append("\t" + high);
		sb.append("\t" + low);
		sb.append("\t" + close);
		sb.append("\t" + volume);
		sb.append("\t" + amount);
		return sb.toString();
	}
}
