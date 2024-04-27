package app;

import javafx.util.StringConverter;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("unused")
public class IntegerStringConverter extends StringConverter<String> {

	  @Override
	    public String toString(String object) {
	        return object; // Simply return the string as it is
	    }

	    @Override
	    public String fromString(String string) {
	        try {
	            Integer value = Integer.parseInt(string);
	            return value.toString(); // Convert back to string
	        } catch (NumberFormatException e) {
	            // Handle invalid input here, for example, return the original string or display an error message
	            return string;
	        }
	    }
}

