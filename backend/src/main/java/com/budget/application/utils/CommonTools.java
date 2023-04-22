package com.budget.application.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public interface CommonTools {

    static LocalDateTime getLocalDateTimeFromISODate(String ISODate) {
        try {
            Timestamp timestampFromIsoDate = CommonTools.getTimestampFromISODate(ISODate);
            return timestampFromIsoDate.toLocalDateTime();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static Timestamp getTimestampFromISODate(String iSODate) {
        try {
            ZonedDateTime date = ZonedDateTime.parse(iSODate);
            LocalDateTime localDateTime = date.toLocalDateTime();
            return Timestamp.valueOf(localDateTime);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
