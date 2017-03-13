package com.omnicrola.panoptes.settings;

import java.awt.Font;
import java.io.File;
import java.util.Arrays;

import com.omnicrola.panoptes.ui.FontRepository;

public class AppSettings {

	public static final AppSettings INSTANCE = new AppSettings();

	private static final String APPLICATION_TITLE = "Panoptes";
	private static final String APPLICATION_VERSION = "1.4.1";

	private static int DAYS_IN_WEEK = 7;
	private static int BLOCK_IN_MINUTES = 15;
	private static int MINUTES_IN_HOUR = 60;
	private static int BLOCKS_PER_HOUR = MINUTES_IN_HOUR / BLOCK_IN_MINUTES;
	private static int START_HOUR = 6;
	private static int END_HOUR = 22;
	private static int HOURS = END_HOUR - START_HOUR;

	private static final int SCREEN_WIDTH = 800;
	private static final int SCREEN_HEIGHT = 640;

	private static final int HOUR_AXIS_HEIGHT = 25;
	private static final int DAY_AXIS_WIDTH = 35;

	private static final String[] TIMES = buildTimes();
	private static final String[] ROLES = new String[] { "DEV", "HTA", "QA", "PM" };
	private static final String[] DAYS = new String[] { "Saturday", "Sunday", "Monday", "Tuesday", "Wednesday",
		"Thursday", "Friday" };
	private static final String[] DAYS_SHORT = new String[] { "Sat", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri" };

	private static final String SETTINGS_FILE_LOCATION = "panoptes.settings.xml";

	private static final String ABOUT_TEXT = "<h2>Panoptes Invoice Assistant</h2><br/>" + "Version "
			+ APPLICATION_VERSION + "<br/>" + "(c) 2017 Eric Schreffler<br/>" + "www.omnicrola.com" + "<br/>&nbsp<br/>"
			+ "Comments? Suggestions? Bribes accepted in the form of baked goods and/or beer.";

	private static String[] buildTimes() {
		int x = 0;
		String[] stringArray = new String[HOURS * BLOCKS_PER_HOUR + 1];
		int i = 0;
		int j = 0;
		for (i = START_HOUR; i < END_HOUR; i++) {
			for (j = 0; j < BLOCKS_PER_HOUR; j++) {
				String display = createTimeString(i, j);
				stringArray[x++] = display;
			}
		}
		stringArray[stringArray.length - 1] = "";
		return stringArray;
	}

	private static String createTimeString(int i, int j) {
		int k = j * BLOCK_IN_MINUTES;
		int h = (i > 12) ? i - 12 : i;
		String meridian = (i < 12) ? "am" : "pm";
		String min = (k == 0) ? "00" : k + "";
		String display = h + ":" + min + " " + meridian;
		return display;
	}

	private final FontRepository fontRepository;

	private AppSettings() {
		this.fontRepository = FontRepository.INSTANCE;
	}

	public String getAboutText() {
		return ABOUT_TEXT;
	}

	public Font getAppFont(int size, int face) {
		return this.fontRepository.getFont(size, face);
	}

	public String getApplicationTitle() {
		return APPLICATION_TITLE + APPLICATION_VERSION;
	}

	public int getBlocksPerHour() {
		return BLOCKS_PER_HOUR;
	}

	public int getDayAxisWidth() {
		return DAY_AXIS_WIDTH;
	}

	public String[] getDays() {
		return Arrays.copyOf(DAYS, DAYS.length);
	}

	public int getDaysInWeek() {
		return DAYS_IN_WEEK;
	}

	public String[] getDaysShort() {
		return Arrays.copyOf(DAYS_SHORT, DAYS_SHORT.length);
	}

	public int getHourAxisHeight() {
		return HOUR_AXIS_HEIGHT;
	}

	public int getMinutesPerBlock() {
		return BLOCK_IN_MINUTES + START_HOUR;
	}

	public String[] getRoles() {
		return Arrays.copyOf(ROLES, ROLES.length);
	}

	public int getScreenHeight() {
		return SCREEN_HEIGHT;
	}

	public int getScreenWidth() {
		return SCREEN_WIDTH;
	}

	public File getSettingsSaveLocation() {
		return new File(SETTINGS_FILE_LOCATION);
	}

	public String[] getTimeIncrements() {
		return Arrays.copyOf(TIMES, TIMES.length);
	}

	public int getTotalBlocks() {
		return HOURS * BLOCKS_PER_HOUR;
	}

	public String getDefaultExportFilename() {
		return "NULL5150";
	}

	public int getCurrentFileFormatVersion() {
		return 2;
	}

}
