package com.omnicrola.panoptes.data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.NONE)
public class DateWrapper {

	private final static SimpleDateFormat SHORT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	@XmlAttribute(name = "year")
	private int year;
	@XmlAttribute(name = "month")
	private int month;
	@XmlAttribute(name = "day")
	private int day;

	@SuppressWarnings("unused")
	private DateWrapper() {
		setXmlFields(new GregorianCalendar());
	}

	public DateWrapper(Date date) {
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(date);
		setXmlFields(gregorianCalendar);
	}

	private void setXmlFields(GregorianCalendar gregorianCalendar) {
		this.year = gregorianCalendar.get(Calendar.YEAR);
		this.month = gregorianCalendar.get(Calendar.MONTH);
		this.day = gregorianCalendar.get(Calendar.DAY_OF_MONTH);
	}

	public Date getDate() {
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.set(Calendar.YEAR, this.year);
		gregorianCalendar.set(Calendar.MONTH, this.month);
		gregorianCalendar.set(Calendar.DAY_OF_MONTH, this.day);
		return gregorianCalendar.getTime();
	}

	@Override
	public String toString() {
		return SHORT_DATE_FORMAT.format(getDate());
	}

}
