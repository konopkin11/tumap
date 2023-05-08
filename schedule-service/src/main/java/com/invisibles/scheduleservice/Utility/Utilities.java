package com.invisibles.scheduleservice.Utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;


public class Utilities {

    public static LocalDate stringToDate(String dateString) {
        String dateFormat = "dd.MM.yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        return LocalDate.parse(dateString, formatter);
    }
    public static LocalDate[] getMonthStartEnd(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate inputDate = LocalDate.parse(date, formatter);

        LocalDate startOfMonth = inputDate.withDayOfMonth(1);
        LocalDate endOfMonth = inputDate.with(TemporalAdjusters.lastDayOfMonth());

        return new LocalDate[]{startOfMonth, endOfMonth};
    }
}
