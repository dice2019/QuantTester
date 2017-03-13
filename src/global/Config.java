package global;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Config {
	private static final Properties configFile;

	public static final String KTExportDir;
	public static final String SinYeeDataDir;
	public static final String ResultDir;

	static {
		configFile = new Properties();

		String KTExportDir_tmp = null;
		String SinYeeDataDir_tmp = null;
		String ResultDir_tmp = null;

		try (FileInputStream in = new FileInputStream("config.ini")) {
			configFile.load(in);
			KTExportDir_tmp = configFile.get("KTExportDir").toString();
			SinYeeDataDir_tmp = configFile.get("SinYeeDataDir").toString();
			ResultDir_tmp = configFile.get("ResultDir").toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// TODO Add File.separator if necessary
		KTExportDir = KTExportDir_tmp;
		SinYeeDataDir = SinYeeDataDir_tmp;
		ResultDir = ResultDir_tmp;
	}
}
