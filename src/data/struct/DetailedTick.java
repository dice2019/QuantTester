package data.struct;

public final class DetailedTick extends Tick {
	public float sellPrice;
	public float sellVolume;
	public float buyPrice;
	public float buyVolume;
	public byte  direction;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(super.toString());
		sb.append("\t" + sellPrice);
		sb.append("\t" + sellVolume);
		sb.append("\t" + buyPrice);
		sb.append("\t" + buyVolume);
		sb.append("\t" + direction);
		return sb.toString();
	}
}
