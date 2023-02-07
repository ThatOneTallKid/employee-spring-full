package com.increff.pos.util;

import com.increff.pos.service.ApiException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public class DateValidatorUtil {
    public static void isValidDateTimeRange(LocalDateTime start, LocalDateTime end) throws ApiException {
        if (start.isAfter(end)) {
            throw new ApiException("Start date cannot be after end date");
        }
        Period period = Period.between(start.toLocalDate(), end.toLocalDate());
        if(period.getDays() > 30) {
            throw new ApiException("Date range cannot be more than 30 days");
        }
    }
    public static void isValidDateRange(LocalDate start, LocalDate end) throws ApiException {
        if (start.isAfter(end)) {
            throw new ApiException("Start date cannot be after end date");
        }
        Period period = Period.between(start, end);
        if(period.getDays() > 30) {
            throw new ApiException("Date range cannot be more than 30 days");
        }
    }
}
