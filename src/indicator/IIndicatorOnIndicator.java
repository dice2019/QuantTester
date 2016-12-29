package indicator;

public interface IIndicatorOnIndicator extends IIndicator {
	public void calculate(float[] prev_data, int begin);
}
