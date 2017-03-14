package global;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public final class Config {
	private static final Properties configFile;

	public static final String KTExportDir;
	public static final String SinYeeDataDir;
	public static final String ResultDir;
	public static final int UseThreads;

	static {
		configFile = new Properties();

		String KTExportDir_tmp = null;
		String SinYeeDataDir_tmp = null;
		String ResultDir_tmp = null;
		String UseThreads_str = null;

		try (FileInputStream in = new FileInputStream("config.ini")) {
			configFile.load(in);
			KTExportDir_tmp = configFile.get("KTExportDir").toString();
			SinYeeDataDir_tmp = configFile.get("SinYeeDataDir").toString();
			ResultDir_tmp = configFile.get("ResultDir").toString();
			UseThreads_str = configFile.get("UseThreads").toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// TODO Add File.separator if necessary
		KTExportDir = KTExportDir_tmp;
		SinYeeDataDir = SinYeeDataDir_tmp;
		ResultDir = ResultDir_tmp;

		int num = 1;
		boolean invalid_num = false;
		try {
			num = Integer.parseInt(UseThreads_str);
		} catch (NumberFormatException e) {
			invalid_num = true;
		}
		if (invalid_num || num < 1) {
			System.out.println("Invalid \"UseThreads\" value, will use 1 thread.");
			num = 1;
		}
		UseThreads = num;
	}
}
