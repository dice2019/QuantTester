package data.collector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collector;

import data.TIME_FRAME;
import data.struct.Tick;

abstract class TickCollector<T extends Tick, R> implements Collector<T, List<T>, R> {

	protected final TIME_FRAME time_frame;
	
	public TickCollector(TIME_FRAME time_frame) {
		this.time_frame = time_frame;
	}
	
	@Override
	public Supplier<List<T>> supplier() {
		return ArrayList::new;
	}

	@Override
	public BiConsumer<List<T>, T> accumulator() {
		return List::add;
	}

	@Override
	public BinaryOperator<List<T>> combiner() {
		return (left, right) -> { left.addAll(right); return left; };
	}

	@Override
	public Set<java.util.stream.Collector.Characteristics> characteristics() {
		return Collections.emptySet();	// TODO CONCURRENT?
	}

	protected static int trim_time(final int time, final TIME_FRAME tf) {
		int minutes = 0;
		switch(tf) {
		case MIN1:
			minutes = 1;
			break;
		case MIN3:
			minutes = 3;
			break;
		case MIN5:
			minutes = 5;
			break;
		case MIN10:
			// TODO
			break;
		case MIN15:
			minutes = 15;
			break;
		case MIN30:
			// TODO
			break;
		case MIN60:
			// TODO
			break;
		default:
			break;
		}
		
		if (minutes == 0) {
			return time;
		}
		int seconds = minutes * 60;
		return time / seconds * seconds;
	}
	
}
