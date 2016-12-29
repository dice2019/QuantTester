package data;

import java.util.List;

import data.struct.Tick;

public interface ITickDataSource {
	List<List<? extends Tick>> getDailyTicks();
}
