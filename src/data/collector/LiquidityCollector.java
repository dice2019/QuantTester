package data.collector;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import data.TIME_FRAME;
import data.struct.DetailedTick;

public class LiquidityCollector extends TickCollector<DetailedTick, Byte> {

	private LiquidityCollector(TIME_FRAME time_frame) {
		super(time_frame);
	}

	public static Collector<DetailedTick, ?, Map<Integer, Byte>> groupingBy(TIME_FRAME time_frame) {
		return Collectors.groupingBy((tick) -> trim_time(tick.time, time_frame), new LiquidityCollector(time_frame));
	}
	
	@Override
	public Function<List<DetailedTick>, Byte> finisher() {
		return new Function<List<DetailedTick>, Byte>() {

			@Override
			public Byte apply(List<DetailedTick> t) {
				byte barLiq = 0;
				for (DetailedTick tick : t) {
					if (tick.buyVolume > 0.0f) {
						barLiq |= 0x01; // Sellable
					}
					if (tick.sellVolume > 0.0f) {
						barLiq |= 0x02; // Buyable
					}
					if (barLiq == 3) {
						break;
					}
				}
				return barLiq;
			}
		};
	}

}