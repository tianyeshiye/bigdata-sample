package sample.tianye.tool.common.utils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.hadoop.fs.Path;

public class DirectoryUtils {

	private static final String TIME_TO_PATH = "yyyy/MM/dd/HHmm";

	public static String createDirectoryDayPathFromTime(ZonedDateTime z) {
		DateTimeFormatter f = DateTimeFormatter.ofPattern(TIME_TO_PATH);
		String[] dateElementes = z.format(f).split("/");
		return "day=" + dateElementes[0] + dateElementes[1] + dateElementes[2];
	}

	public static String createDirectoryTimePathFromTime(ZonedDateTime z) {
		DateTimeFormatter f = DateTimeFormatter.ofPattern(TIME_TO_PATH);
		String[] dateElementes = z.format(f).split("/");
		return "day=" + dateElementes[0] + dateElementes[1] + dateElementes[2] + "/hhmm=" + dateElementes[3];
	}

	public static void main(String[] args) {
		ZonedDateTime now = ZonedDateTime.now();
		final String path = new Path("rootdir", createDirectoryDayPathFromTime(now)).toString();
	}

}
