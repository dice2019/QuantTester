package test;

import java.time.LocalDateTime;

import data.TIME_FRAME;

public class CommonParam {
	public String instrument;
	public TIME_FRAME tf;
	public Integer Period;
	public LocalDateTime start_date;
	public LocalDateTime end_date;
	
	public CommonParam(String instrument, TIME_FRAME tf, LocalDateTime start_date, LocalDateTime end_date) {
		this.instrument = instrument;
		this.tf = tf;
		this.start_date = start_date;
		this.end_date = end_date;
	}
	
	@Override
	public String toString() {
		final String endl = System.getProperty("line.separator");
		StringBuilder paramInfo = new StringBuilder();
		paramInfo.append("Instrument = " + instrument + endl);
		paramInfo.append("Time frame = " + tf + endl);
		paramInfo.append("Start Date = " + start_date + endl);
		paramInfo.append("End Date = " + end_date + endl);
		return paramInfo.toString();
	}
}
