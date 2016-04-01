package de.Marcel.CalendarAPI;

import java.io.BufferedReader;
import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/*
 * CalendarAPI class - by Marcel F.
 */
public class CalendarAPI {
	private ArrayList<EVENT> events;

	// constructor | needs file of calendar, you want to use
	public CalendarAPI(File icsFile) {
		// read file and extract events
		readAndExtractData(icsFile);
	}

	@SuppressWarnings("deprecation")
	private void readAndExtractData(File icsFile) {
		// catch exception of scanner
		try {
			// read file into string
			String calendarString = "";

			// create scanner to read lines
			Scanner scanner = new Scanner(icsFile);

			// interate each line, while there is still a line
			while (scanner.hasNext()) {
				// add line to calendarString and add line break
				calendarString += scanner.next() + "\n";
			}

			// cut to begin
			calendarString = calendarString.substring(calendarString.indexOf("BEGIN:VEVENT") + 12);

			// split to get each event
			String[] events = calendarString.split("END:VEVENT");

			// init event arrayList
			this.events = new ArrayList<EVENT>();

			// itarate through each eventstring
			for (int i = 0; i < events.length; i++) {
				// create bufferedreader of current string (contains an event)
				BufferedReader reader = new BufferedReader(new StringReader(events[i]));
				String line = "";

				String summary = "";
				Date dstart = null, dend = null;

				// iterate again through each line of the event
				while ((line = reader.readLine()) != null) {
					// extract summary
					if (line.startsWith("SUMMARY")) {
						summary = line;
					}

					try {
						// if line starts with dstart, information of events
						// follow
						if (line.startsWith("DTSTART")) {
							// extract year of event
							String year = line.substring(line.lastIndexOf(":") + 1).split("T")[0].substring(0, 4);

							// extract month of event
							String month = line.substring(line.lastIndexOf(":") + 1).split("T")[0].substring(4, 6);

							// extract day of event
							String day = line.substring(line.lastIndexOf(":") + 1).split("T")[0].substring(6, 8);

							// if event also contains time
							if (line.substring(line.lastIndexOf(":") + 1).contains("T")) {
								// extract time - hour of event
								String hour = line.substring(line.lastIndexOf(":") + 1).split("T")[1].substring(0, 2);

								// extract time - minutes of event
								String minutes = line.substring(line.lastIndexOf(":") + 1).split("T")[1].substring(2,
										4);

								// create date with extracted information about
								// event start (also with time)
								dstart = new Date(Integer.valueOf(year) - 1900, Integer.valueOf(month) - 1,
										Integer.valueOf(day), Integer.valueOf(hour), Integer.valueOf(minutes));
							} else {
								// create date with extracted information about
								// event start (without time)
								dstart = new Date(Integer.valueOf(year) - 1900, Integer.valueOf(month) - 1,
										Integer.valueOf(day));
							}
						}

						// extract event end date
						if (line.startsWith("DTEND")) {
							// extract year of event
							String year = line.substring(line.lastIndexOf(":") + 1).split("T")[0].substring(0, 4);

							// extract month of event
							String month = line.substring(line.lastIndexOf(":") + 1).split("T")[0].substring(4, 6);

							// extract day of event
							String day = line.substring(line.lastIndexOf(":") + 1).split("T")[0].substring(6, 8);

							// if end event also contains time
							if (line.substring(line.lastIndexOf(":") + 1).contains("T")) {
								// extract time - hour
								String hour = line.substring(line.lastIndexOf(":") + 1).split("T")[1].substring(0, 2);

								// extrat time - minutes
								String minutes = line.substring(line.lastIndexOf(":") + 1).split("T")[1].substring(2,
										4);

								// create date of extracted information about
								// event end (also with time)
								dend = new Date(Integer.valueOf(year) - 1900, Integer.valueOf(month) - 1,
										Integer.valueOf(day), Integer.valueOf(hour), Integer.valueOf(minutes));
							} else {
								// create date of extracted information about
								// event end without time
								dend = new Date(Integer.valueOf(year) - 1900, Integer.valueOf(month) - 1,
										Integer.valueOf(day));
							}
						}
					} catch (StringIndexOutOfBoundsException e) {
						// don't interrupt if error occurs
					}
				}

				// create event (of extracted summary, eventStart, eventEnd) and
				// add to list
				this.events.add(new EVENT(summary, dstart, dend));

				// close scanner
				scanner.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// get events
	public ArrayList<EVENT> getEvents() {
		return this.events;
	}

	// get all Events of excat dare - needs: year, month, day
	public ArrayList<EVENT> getEventsOf(int year, int month, int day) {
		// list of all events of given date
		ArrayList<EVENT> trueEvents = new ArrayList<EVENT>();

		// iterate through each event and check if it happens on given date
		for (EVENT event : events) {
			@SuppressWarnings("deprecation")
			int dateMargin = getZeroTimeDate(event.getStartDate())
					.compareTo(getZeroTimeDate(new Date(year - 1900, month - 1, day)));
			if (dateMargin == 0) {
				trueEvents.add(event);
			}
		}

		return trueEvents;
	}

	// returns date and resets time to zero
	private Date getZeroTimeDate(Date fecha) {
		Date res = fecha;
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(fecha);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		res = calendar.getTime();

		return res;
	}

	/*
	 * EVENT class to store event of calendar
	 */
	class EVENT {
		private String summary;
		private Date startDate, endDate;

		public EVENT(String summary, Date startDate, Date endDate) {
			this.summary = summary;
			this.startDate = startDate;
			this.endDate = endDate;
		}

		// --- getters ----

		public Date getEndDate() {
			return endDate;
		}

		public Date getStartDate() {
			return startDate;
		}

		public String getSummary() {
			return summary;
		}

		// override toString
		@Override
		public String toString() {
			return "Event - Summary: " + getSummary() + " StartDate: (" + getStartDate().toString() + ") EndDate: ("
					+ getEndDate().toString() + ")";
		}
	}
}
