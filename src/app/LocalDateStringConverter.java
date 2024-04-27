package app;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.util.StringConverter;

public class LocalDateStringConverter extends StringConverter<LocalDate> {
	private final DateTimeFormatter formatter;

    public LocalDateStringConverter(String pattern) {
        this.formatter = DateTimeFormatter.ofPattern(pattern);
    }

    public LocalDateStringConverter() {
        this.formatter = DateTimeFormatter.ISO_DATE;
    }
    
    @Override
    public String toString(LocalDate date) {
        if (date == null) {
            return "";
        }
        return formatter.format(date);
    }

    @Override
    public LocalDate fromString(String string) {
        if (string == null || string.trim().isEmpty()) {
            return null;
        }
        return LocalDate.parse(string, formatter);
    }
}


