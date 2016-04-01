# ICSCalendarAPI

ICSCalendarAPI is small api for reading .ics files and extracting the events of it in java

# Example

##Using the API:
```java
public class Main {
	public static void main(String[] args) {
		CalendarAPI calendarAPI = new CalendarAPI(new File("calendar.ics");
		
		//print each event
		for(EVENT event : calendarAPI.getEvents()) {
		   System.out.println(event);
		}
	}
}
```
(This example reads an .ics file called "calendar.ics" and prints each event of it

##Download
Jar: http://www89.zippyshare.com/v/ZWPVWwv9/file.html

##Explanation


######Class
Currently there is only one class:

| Class                          | Explanation                                                           |
| ------------------------------ | --------------------------------------------------------------------- |
| CalendarAPI                    | main class which can read a .ics file and extracts the events of it   |

##License

Copyright 2016 Marcel Franzen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
