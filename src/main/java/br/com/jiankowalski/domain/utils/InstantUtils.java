package br.com.jiankowalski.domain.utils;

import br.com.jiankowalski.domain.exceptions.DomainException;
import br.com.jiankowalski.domain.validation.Error;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public final class InstantUtils {

    public static final String ERROR_CONVERTER_DATA = "Erro ao converter data: %s";

    private static final DateTimeFormatter FORMAT_DATE_TIME = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private static final ZoneOffset ZONE_ID = ZoneOffset.UTC;

    private InstantUtils() {
    }

    public static Instant now() {
        return Instant.now().truncatedTo(ChronoUnit.MICROS);
    }

    public static boolean isAfterDate(final Instant startAt, final Instant finishAt) {
        if (startAt == null || finishAt == null) {
            return false;
        }
        final var minDate = asLocalDate(startAt);
        final var maxDate = asLocalDate(finishAt);
        return minDate.isAfter(maxDate);
    }

    public static String asString(Instant date) {
        if (date == null) {
            return null;
        }
        return FORMAT_DATE_TIME.format(date.atZone(ZONE_ID).toLocalDateTime());
    }

    public static LocalDate asLocalDate(final Instant instant) {
        if (instant == null) {
            return null;
        }
        return LocalDate.ofInstant(instant, ZONE_ID);
    }

    public static Instant asInstant(final String date) {
        if (date == null || date.isEmpty()) {
            return null;
        }
        try {
            return LocalDateTime.parse(date, FORMAT_DATE_TIME).toInstant(ZONE_ID);
        } catch (DateTimeParseException exception) {
            throw DomainException.with(new Error(ERROR_CONVERTER_DATA.formatted(date)));
        }
    }

}
