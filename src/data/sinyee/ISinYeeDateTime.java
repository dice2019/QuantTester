package data.sinyee;

import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import helper.DateTimeHelper;
import helper.ReflectHelper;

interface ISinYeeDateTime {
	static final LocalDateTime data_date_base = LocalDateTime.of(1988, 1, 1, 0, 0, 0);
	static final LocalDateTime real_date_base = LocalDateTime.of(2008, 1, 1, 0, 0, 0);
	static final int date_diff_int = (int) DateTimeHelper.Ldt2Long(real_date_base) - (int) DateTimeHelper.Ldt2Long(data_date_base);
	
	static int getDateFromIn(final InputStream in) {
		if (in instanceof FileInputStream) {
			String path = (String) ReflectHelper.getPrivateField(in, "path");
			int path_len = path.length();
			String file_date = path.substring(path_len - 12, path_len - 4);
			LocalDate ld = LocalDate.parse(file_date, DateTimeFormatter.BASIC_ISO_DATE);
			return (int) DateTimeHelper.Ldt2Long(ld.atStartOfDay());
		} else {
			return 0;
		}
	}
}
