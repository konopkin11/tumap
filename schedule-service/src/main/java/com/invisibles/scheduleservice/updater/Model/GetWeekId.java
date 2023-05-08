package com.invisibles.scheduleservice.updater.Model;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

import com.invisibles.scheduleservice.updater.constants.Constants;

public class GetWeekId {

    public static int Get(String date) {
        LocalDate currentDate = LocalDate.parse(date);
        LocalDate firstDate = LocalDate.parse(Constants.FIRST_WEEK_ID);
        long difference = ChronoUnit.DAYS.between(firstDate, currentDate);
        double num = (double) difference / 7;
        long floor = (long) num;
        if ((num - floor) > 0.9) {
            num++;
        }
        return (int) Math.floor(num);
    }

    public static Date Get(int weekNumber) {
        LocalDate firstDate = LocalDate.parse(Constants.FIRST_WEEK_ID);
        LocalDate weekStartDate = firstDate.plusWeeks(weekNumber).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        return Date.from(weekStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date AddDaysToDate(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    public static LocalDate stringToDate(String dateString) {
        String dateFormat = "dd.MM.yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        return LocalDate.parse(dateString, formatter);
    }

}
