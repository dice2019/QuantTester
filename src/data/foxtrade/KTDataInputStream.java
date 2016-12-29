package data.foxtrade;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import data.ILittleEndianDataReader;
import data.struct.Bar;
import data.struct.FutureBar;

class KTDataInputStream extends BufferedInputStream implements ILittleEndianDataReader {

	KTDataInputStream(InputStream in) {
		super(in);
	}

	KTDataInputStream(InputStream in, int size) {
		super(in, size);
	}

	Bar readSTKData() throws IOException {
		Bar bar = new Bar();
		bar.time = readInt() + 8 * 3600;
		bar.open = readFloat();
		bar.high = readFloat();
		bar.low = readFloat();
		bar.close = readFloat();
		bar.volume = readFloat();
		bar.amount = readFloat();
		readInt();
		return bar;
	}

	FutureBar readSTKDataEx(boolean contain_settlement) throws IOException {
		FutureBar bar = new FutureBar();
		bar.time = readInt() + 8 * 3600;
		bar.open = readFloat();
		bar.high = readFloat();
		bar.low = readFloat();
		bar.close = readFloat();
		bar.volume = readFloat();
		bar.openInterest = readFloat();
		bar.contractMonth = readInt();
		bar.amount = readFloat();
		if (contain_settlement) {
			bar.settlement = readFloat();
		} else {
			bar.settlement = 0.0f;
		}

		return bar;
	}
}