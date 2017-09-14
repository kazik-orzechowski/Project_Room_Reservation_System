package pl.lps.controller;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.lps.repository.EventRepository;

@Controller
@RequestMapping("/test")
public class ControllerDate {

	@Autowired
	EventRepository repoEvent;

	ArrayList<Date> requestedDates = new ArrayList<Date>();
	Date date = new Date();

	@RequestMapping("")

	public String allEvents() {

		requestedDates.add(date);
		requestedDates.add(date);

		// if (repoEvent.findNotCollidingEvents(date, 1L, date, date).isEmpty()) {
		if (repoEvent.findNotCollidingManyEvents(requestedDates, 1L, date, date).isEmpty()) {

			System.out.println("DZIAŁA");
		}
		return "users";

	}
}
