package data.foxtrade;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.TIME_FRAME;
import data.struct.Bar;
import data.struct.BarSeries;
import global.Config;

@SuppressWarnings("unchecked")
public class KTExportStocks extends AbstractKTExport {
	{
		this.multi_time_frame_bars = new Map[1];
		this.multi_time_frame_bars[0] = new HashMap<>();
	}

	public KTExportStocks(String instrument_name, TIME_FRAME time_frame) {
		String file_path = Config.KTExportDir + time_frame.name() + File.separator + instrument_name;
		List<Bar> barList = ReadSTKData(file_path);
		multi_time_frame_bars[0].put(time_frame, new BarSeries(barList));
	}

}
