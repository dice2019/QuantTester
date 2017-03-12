package data.foxtrade;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import data.AbstractDataSource;
import data.TIME_FRAME;
import data.struct.Bar;
import data.struct.FutureBar;

public class AbstractKTExport extends AbstractDataSource {
	protected static final EnumSet<TIME_FRAME> KT_TIME_FRAMES = EnumSet.complementOf(EnumSet.of(TIME_FRAME.MIN3, TIME_FRAME.MIN10));

	protected static List<Bar> ReadSTKData(String single_file) {
		List<Bar> bars = null;
		try (KTDataInputStream in = new KTDataInputStream(new FileInputStream(single_file))) {
			in.readInt();	// File size
			in.readInt();	// Export time
			int unit_size = in.readInt();
			int num = in.readInt();
			bars = new ArrayList<>(num);
			while (in.available() >= unit_size) {
				bars.add(in.readSTKData());
			}
		} catch (FileNotFoundException e) {
			// TODO Should print debug log
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bars;
	}

	protected static List<FutureBar> ReadSTKDataEx(String single_file) {
		List<FutureBar> bars = null;
		try (KTDataInputStream in = new KTDataInputStream(new FileInputStream(single_file))) {
			in.readInt();	// File size
			in.readInt();	// Export time
			int unit_size = in.readInt();
			int num = in.readInt();
			bars = new ArrayList<>(num);
			while (in.available() >= unit_size) {
				bars.add(in.readSTKDataEx(unit_size));
			}
		} catch (FileNotFoundException e) {
			// TODO Should print debug log
			// http://logging.apache.org/log4j/2.x/index.html
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bars;
	}
}
