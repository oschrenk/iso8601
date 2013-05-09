package com.acme.date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JodaTimeTest {

    private DateTimeFormatter parser;
    private DateTimeFormatter formatter;

    @Before
    public void setup() {
        // set the local time zone explicitly
        DateTimeZone.setDefault(DateTimeZone.forID("Europe/Amsterdam"));

        parser = ISODateTimeFormat.dateTimeParser();
        formatter = ISODateTimeFormat.dateTimeNoMillis();
    }

    @Test
    public void withDateAndTimeSeparator() {
        assertEquals("2013-05-10T16:00:00+02:00", formatter.print(parser.parseDateTime("2013-05-10T16:00:00")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void defaultISOParserFailsOnMissingDateTimeSeparator() {
        parser.parseDateTime("2013-05-10 16:00:00");
    }

    @Test
    public void timeZones() {
        DateTime dateTimeAmsterdam = parser.parseDateTime("2013-05-10T16:00:00");
        DateTime dateTimeInLondon = dateTimeAmsterdam.withZone(DateTimeZone.forID("Europe/London"));
        DateTime dateTimeInParis = dateTimeAmsterdam.withZone(DateTimeZone.forID("Europe/Paris"));

        assertEquals("2013-05-10T15:00:00+01:00", formatter.print(dateTimeInLondon));
        assertEquals("2013-05-10T16:00:00+02:00", formatter.print(dateTimeInParis));
    }
}
