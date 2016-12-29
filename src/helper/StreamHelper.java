package helper;

import java.util.DoubleSummaryStatistics;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public final class StreamHelper {
	public static DoubleStream floatArrayToDoubleStream(final float... floatArray) {
		return IntStream.range(0, floatArray.length)
                .mapToDouble(i -> floatArray[i]);
	}
	
	public static DoubleSummaryStatistics getFloatSummaryStatistics(final float... floatArray) {
		return floatArrayToDoubleStream(floatArray).summaryStatistics();
	}
}
