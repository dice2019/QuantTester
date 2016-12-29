package data.struct;

public final class FutureBar extends Bar {
	public float openInterest;
	public int   contractMonth;
	public float settlement;
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(super.toString());
		sb.append("\t" + openInterest);
		if (contractMonth != 0)
			sb.append("\t" + contractMonth);
		if (settlement > 0.0f)
			sb.append("\t" + settlement);
		return sb.toString();
	}
}
