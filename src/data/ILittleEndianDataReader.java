package data;

import java.io.IOException;

public interface ILittleEndianDataReader {
	public int read() throws IOException;
	
	/**
	 * Read 1 byte
	 * @return
	 * @throws IOException
	 */
	default byte readByte() throws IOException {
		return (byte) read();
	}
	
	/**
	 * Read 2 bytes as short with reversed byte order
	 * @return
	 * @throws IOException
	 */
	default short readShort() throws IOException {
		return (short) ((read() << 0) + (read() << 8));
	}

	/**
	 * Read 4 bytes as integer with reversed byte order
	 * @return
	 * @throws IOException
	 */
	default int readInt() throws IOException {
		return (read() << 0) + (read() << 8) + (read() << 16) + (read() << 24);
	}

	/**
	 * Read 4 bytes as float with reversed byte order
	 * @return
	 * @throws IOException
	 */
	default float readFloat() throws IOException {
		return Float.intBitsToFloat(readInt());
	}
}
