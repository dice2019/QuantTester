package data;

import data.struct.BarSeries;

public interface IDataSource {
	// TODO 实现生成模拟数据 / 将真实数据打乱
	BarSeries getBarSeries(int month, TIME_FRAME time_frame);	// 期货: month = 1 to 12, 0 表示连续, 股票: month = 0

}