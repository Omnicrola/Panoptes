package com.omnicrola.panoptes.data;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateWrapper {

    private final static SimpleDateFormat SHORT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private final Date date;

    @SuppressWarnings("unused")
    private DateWrapper() {
        this.date = new Date();
    }

    public DateWrapper(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return (Date) this.date.clone();
    }

    @Override
    public String toString() {
        return SHORT_DATE_FORMAT.format(this.date);
    }

}
