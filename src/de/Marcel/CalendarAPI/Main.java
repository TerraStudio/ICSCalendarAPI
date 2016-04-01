package de.Marcel.CalendarAPI;

import java.io.File;

import de.Marcel.CalendarAPI.CalendarAPI.EVENT;

public class Main {
	public static void main(String[] args) {
		CalendarAPI calendarAPI = new CalendarAPI(new File("calendar.ics"));

		// print each event
		for(EVENT event : calendarAPI.getEvents()) {
			System.out.println(event);
		}
	}
}
