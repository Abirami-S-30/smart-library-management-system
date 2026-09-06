package com.library.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class FineCalculator {

    // Fine amount per day
    public static final double FINE_PER_DAY = 10.0;

    public static double calculateFine(LocalDate dueDate,
                                       LocalDate returnDate) {

        if (returnDate.isBefore(dueDate) || returnDate.isEqual(dueDate)) {
            return 0;
        }

        long lateDays = ChronoUnit.DAYS.between(dueDate, returnDate);

        return lateDays * FINE_PER_DAY;
    }
}