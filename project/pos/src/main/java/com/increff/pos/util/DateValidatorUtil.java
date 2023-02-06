package com.increff.pos.util;

import com.increff.pos.service.ApiException;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateValidatorUtil {
    public static void isValidDateTimeRange(LocalDateTime start, LocalDateTime end) throws ApiException {
        if (start.isAfter(end)) {
            throw new ApiException("Start date cannot be after end date");
        }
    }
    public static void isValidDateRange(LocalDate start, LocalDate end) throws ApiException {
        if (start.isAfter(end)) {
            throw new ApiException("Start date cannot be after end date");
        }
    }
}
