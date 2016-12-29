package data.sinyee;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.ILittleEndianDataReader;
import data.struct.FutureBar;

class SinYeeBarInputStream extends BufferedInputStream implements ILittleEndianDataReader, ISinYeeDateTime {

	@SuppressWarnings("unused")
	private int date_for_debug;		// 存放文件名中显示的时间
	
	SinYeeBarInputStream(InputStream in) {
		this(in, 16384);
	}
	
	SinYeeBarInputStream(InputStream in, int size) {
		super(in, size);
		// date_for_debug = getDateFromIn(in);
	}

	Map<String, Map.Entry<Integer, Integer>[]> getAvailableContracts() throws IOException {
		Map<String, Map.Entry<Integer, Integer>[]> available_contracts = new HashMap<>();
		short contract_num = readShort();
		for (int i = 0; i < contract_num; i++) {
			byte strlen = (byte) read();
			byte[] contract_name_bytes = new byte[strlen];
			read(contract_name_bytes, 0, strlen);
			String contract_name = new String(contract_name_bytes);

			skip(29 - strlen); // 跳过Unicode版的contract_name

			@SuppressWarnings("unchecked")
			Map.Entry<Integer, Integer>[] offset_nums = new Map.Entry[8];
			for (int j = 0; j < 8; j++) {
				offset_nums[j] = new AbstractMap.SimpleEntry<>(readInt(), readInt());
			}
			available_contracts.put(contract_name, offset_nums);
		}
		return available_contracts;
	}

	List<FutureBar> readBars(int bar_num) throws IOException {
		List<FutureBar> barlist = new ArrayList<>(bar_num);
		for (int i = 0; i < bar_num; i++) {
			barlist.add(readBarEx());
		}
		return barlist;
	}

	FutureBar readBarEx() throws IOException {
		FutureBar bar = new FutureBar();
		bar.time = readInt() + date_diff_int;
		// assert (bar.mTime / (24 * 3600) * (24 * 3600) == this.date_for_debug) : "日期与文件名不符";
		bar.open = readFloat();
		bar.high = readFloat();
		bar.low = readFloat();
		bar.close = readFloat();
		bar.settlement = readFloat();
		bar.volume = readFloat();
		bar.openInterest = readFloat();
		bar.contractMonth = 0;
		bar.amount = 0.0f;

		return bar;
	}
}
