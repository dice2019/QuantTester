package data.collector;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import data.TIME_FRAME;
import data.struct.FutureBar;
import data.struct.Tick;

public class FutureBarCollector extends TickCollector<Tick, FutureBar> {

	// 在TickBarStrategy中被使用
	public FutureBarCollector(TIME_FRAME time_frame) {
		super(time_frame);
	}

	// 暂时还没有用
	public static Collector<Tick, ?, Map<Integer, FutureBar>> groupingBy(TIME_FRAME time_frame) {
		return Collectors.groupingBy((tick) -> trim_time(tick.time, time_frame), new FutureBarCollector(time_frame));
	}

	public static FutureBar collectBar(List<Tick> t) {
		FutureBar bar = new FutureBar();
		int size = t.size();
		bar.time = t.get(0).time;
		bar.open = t.get(0).price;
		bar.high = -Float.MAX_VALUE;
		bar.low = Float.MAX_VALUE;
		bar.close = t.get(size - 1).price;
		bar.volume = 0.0f;
		bar.amount = 0.0f;
		bar.openInterest = t.get(size - 1).openInterest;
		bar.contractMonth = 0;
		for (int i = 0; i < size; i++) {
			Tick tick = t.get(i);
			if (tick.price > bar.high) {
				bar.high = t.get(i).price;
			}
			if (tick.price < bar.low) {
				bar.low = t.get(i).price;
			}
			bar.volume += tick.volume;
			bar.amount += (tick.volume * tick.price);
		}
		bar.settlement = bar.amount / bar.volume;
		return bar;
	}

	private FutureBar trim_bar_time(FutureBar inputBar) {
		inputBar.time = trim_time(inputBar.time, time_frame);
		return inputBar;
	}

	@Override
	public Function<List<Tick>, FutureBar> finisher() {
		Function<List<Tick>, FutureBar> finisher = FutureBarCollector::collectBar;
		if (time_frame == null) {
			return finisher;
		} else {
			return finisher.andThen(this::trim_bar_time);
		}
	}
}
