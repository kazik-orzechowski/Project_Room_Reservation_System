package pl.lps.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import pl.lps.data.ControllerAttributesData;
import pl.lps.entity.Event;
import pl.lps.entity.Series;
import pl.lps.entity.SeriesDTO;
import pl.lps.repository.SeriesRepository;
import pl.lps.repository.UserRepository;


public class SeriesService {

	@Autowired
	UserRepository repoUser;

	@Autowired
	SeriesRepository repoSeries;

	/**
	 * Prepares a list of SeriesDTO objects containing all data regarding event
	 * series stored in series objects and event objects.
	 * 
	 * @param id
	 *            - user id
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 */

	public void prepareSeriesView(Long id, Model model) {
System.err.println("S1");
		List<Series> seriesList = new ArrayList<>();
		System.err.println("S2");
		System.err.println(repoUser.findAll().toString());
		if (repoUser.findOneById(id).getUserName().equals("admin")) {
			System.err.println("S2.1");
			seriesList = repoSeries.findAll();
		} else {
			System.err.println("S2.2");
			seriesList = repoSeries.findAllByUserId(id);
		}
		System.err.println("S3");
		List<SeriesDTO> seriesDTOList = new ArrayList<SeriesDTO>();
		System.err.println("S4");
		for (Series series : seriesList) {
			System.err.println("S5");
			SeriesDTO seriesDto = new SeriesDTO();
			seriesDto.setSeries(series);
			Hibernate.initialize(series.getEvents());
			List<Event> events = (List<Event>) series.getEvents();
			System.err.println("S6");
			Collections.sort(events, new Comparator<Event>() {
				public int compare(Event e1, Event e2) {
					return e1.getDate().compareTo(e2.getDate());
				}
			});
			System.err.println("S7");
			int numberOfEvents = (int) events.size();
			seriesDto.setSeriesStartDate(events.get(0).getDate());
			seriesDto.setSeriesEndDate(events.get(numberOfEvents - 1).getDate());
			seriesDto.setNumberOfEvents(numberOfEvents);

			// Adding all unique venues and hours of event series
			Set<String> venuesOfEventSeries = new HashSet<>();
			Set<Date> startHoursOfEventSeries = new HashSet<>();
			for (Event event : events) {
				venuesOfEventSeries.add(event.getRoom().getPlace().getName() + " sala " + event.getRoom().getNumber());
				startHoursOfEventSeries.add(event.getHour());
			}

			seriesDto.setSeriesHours(startHoursOfEventSeries);
			seriesDto.setSeriesPlacesAndRooms(venuesOfEventSeries);
			seriesDTOList.add(seriesDto);

		}

		model.addAttribute(ControllerAttributesData.getAllSeries(), seriesDTOList);

		model.addAttribute(ControllerAttributesData.getAddEventInfoAttribute(), "null");
		model.addAttribute(ControllerAttributesData.getAddSeriesInfoAttribute(), "null");
		model.addAttribute("user", repoUser.findOneById(id));
	}

}
