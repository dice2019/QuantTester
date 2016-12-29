package data.sinyee;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import data.ILittleEndianDataReader;
import data.struct.DetailedTick;

class SinYeeTickInputStream extends BufferedInputStream implements ILittleEndianDataReader, ISinYeeDateTime {

	@SuppressWarnings("unused")
	private int date_for_debug;		// 存放文件名中显示的时间
	
	SinYeeTickInputStream(InputStream in) {
		this(in, 131072);
	}
	
	SinYeeTickInputStream(InputStream in, int size) {
		super(in, size);
		// date_for_debug = getDateFromIn(in);
	}

	String[] getAvailableContracts() throws IOException {
		short contract_num = readShort();
		String[] available_contracts = new String[contract_num];
		for (int i = 0; i < contract_num; i++) {
			byte strlen = readByte();
			byte[] contract_name_bytes = new byte[strlen];
			read(contract_name_bytes, 0, strlen);
			String contract_name = new String(contract_name_bytes);

			skip(29 - strlen); // 跳过Unicode版的contract_name

			available_contracts[i] = contract_name;
		}
		return available_contracts;
	}

	List<DetailedTick> readTicks(int tick_num) throws IOException {
		List<DetailedTick> tick_list = new ArrayList<>();
		for (int i = 0; i < tick_num; i++) {
			DetailedTick tick = readDetailedTick();
			tick.no = i;
			tick_list.add(tick);
		}
		return tick_list;
	}

	DetailedTick readDetailedTick() throws IOException {
		DetailedTick tick = new DetailedTick();
		tick.time = readInt() + date_diff_int;
		// assert (bar.mTime / (24 * 3600) * (24 * 3600) == this.date_for_debug) : "日期与文件名不符";
		readShort();	// Skip 2 bytes (milliseconds?)
		tick.price = readFloat();
		tick.volume = readFloat();
		tick.buyPrice = readFloat();
		tick.buyVolume = readFloat();
		tick.sellPrice = readFloat();
		tick.sellVolume = readFloat();
		tick.openInterest = readFloat();
		tick.direction = readByte();

		return tick;
	}
}
