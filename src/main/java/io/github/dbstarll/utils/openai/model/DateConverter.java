package io.github.dbstarll.utils.openai.model;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.time.Duration;
import java.util.Date;

public class DateConverter extends StdConverter<Integer, Date> {
    @Override
    public Date convert(final Integer value) {
        return new Date(Duration.ofSeconds(value.longValue()).toMillis());
    }
}
