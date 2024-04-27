package app;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.util.StringConverter;

public class LocalTimeStringConverter extends StringConverter<LocalTime>{

	private final DateTimeFormatter formatter;
	
	public LocalTimeStringConverter(String pattern) {
        this.formatter = DateTimeFormatter.ofPattern(pattern);
    }

    public LocalTimeStringConverter() {
        this.formatter = DateTimeFormatter.ISO_LOCAL_TIME;
    }
	
	@Override
	public LocalTime fromString(String arg0) {
		  if (arg0 == null || arg0.trim().isEmpty()) {
	            return null;
	        }
	        return LocalTime.parse(arg0, formatter);
	    }
	

	@Override
	public String toString(LocalTime arg0) {
		if (arg0 == null) {
            return "";
        }
        return formatter.format(arg0);
	}
	
}
