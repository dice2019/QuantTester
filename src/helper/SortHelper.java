package helper;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class SortHelper {
	public final static int[] getOrdinals(final float[] values, final boolean polaris) {
		final int size = values.length;
		Float[] Fvalues = new Float[size];
		
		for (int i = 0; i < size; i++) {
			Fvalues[i] = values[i];
		}
		
		return getOrdinals(Fvalues, polaris);
	}

	public final static <T extends Comparable<? super T>> int[] getOrdinals(final T[] values, final boolean polaris) {
		final int size = values.length;
		int[] ordinals = new int[size];
		
		List<AbstractMap.SimpleEntry<Integer, T>> it_list = new ArrayList<>();
		
		for (int i = 0; i < size; i++) {
			AbstractMap.SimpleEntry<Integer, T> entry = new AbstractMap.SimpleEntry<>(i, values[i]);
			it_list.add(entry);
		}
		
		if (polaris) {
			Collections.sort(it_list, (arg0, arg1) -> arg0.getValue().compareTo(arg1.getValue()));
		} else {
			Collections.sort(it_list, (arg0, arg1) -> arg1.getValue().compareTo(arg0.getValue()));
		}
		
		for (int i = 0; i < size; i++) {
			ordinals[i] = it_list.get(i).getKey();
		}
		return ordinals;
	}
}
