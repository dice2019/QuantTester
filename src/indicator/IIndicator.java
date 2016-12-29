package indicator;

public interface IIndicator {
	public void calculate(float[] open, float[] high, float[] low, float[] close);
	public float[] getBufferById(int id);
}
