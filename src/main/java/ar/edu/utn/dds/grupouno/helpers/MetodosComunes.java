package ar.edu.utn.dds.grupouno.helpers;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class MetodosComunes {

	public static boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	public static DateTime convertJavatoJoda(ZonedDateTime input) {

		if (input != null)
			return new DateTime(input.toInstant().toEpochMilli(),
					DateTimeZone.forTimeZone(TimeZone.getTimeZone(input.getZone())));
		else
			return null;

		// DateTime tm;
		// if (input != null) {
		// tm = new DateTime(input.getYear(), input.getMonthValue(),
		// input.getDayOfMonth(), input.getHour(),
		// input.getMinute());
		// return tm;
		// } else
		// return null;
	}

	public static ZonedDateTime convertJodatoJava(DateTime input) {
		ZonedDateTime tm;
		if (input != null) {
			tm = input.toGregorianCalendar().toZonedDateTime();
			return tm;
		} else
			return null;
	}
}
