package app;

import javafx.util.StringConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BinCardStringConverters {

	public static StringConverter<Date> createDateConverter() {
		return new StringConverter<Date>() {
			private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			@Override
			public String toString(Date date) {
				if (date == null) {
					return "";
				}
				return dateFormat.format(date);
			}

			@Override
			public Date fromString(String string) {
				try {
					if (string == null || string.trim().isEmpty()) {
						return null;
					}
					return dateFormat.parse(string);
				} catch (ParseException e) {
					throw new IllegalArgumentException("Invalid date format. Please use yyyy-MM-dd");
				}
			}
		};
	}

	public static StringConverter<Double> createDoubleConverter() {
		return new StringConverter<Double>() {
			@Override
			public String toString(Double value) {
				if (value == null) {
					return "";
				}
				return String.valueOf(value);
			}

			@Override
			public Double fromString(String string) {
				try {
					if (string == null || string.trim().isEmpty()) {
						return null;
					}
					return Double.parseDouble(string);
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("Invalid double value");
				}
			}
		};
	}

}
