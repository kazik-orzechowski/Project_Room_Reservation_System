package pl.lps.service;

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

import pl.lps.controller.SeriesController;
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
		List<Series> seriesList = new ArrayList<>();

		if (repoUser.findOneById(id).getUserName().equals("admin")) {
			seriesList = repoSeries.findAll();
		} else {
			seriesList = repoSeries.findAllByUserId(id);
		}

		List<SeriesDTO> seriesDTOList = new ArrayList<SeriesDTO>();

		for (Series series : seriesList) {

			SeriesDTO seriesDto = new SeriesDTO();
			seriesDto.setSeries(series);
			Hibernate.initialize(series.getEvents());
			List<Event> events = (List<Event>) series.getEvents();

			Collections.sort(events, new Comparator<Event>() {
				public int compare(Event e1, Event e2) {
					return e1.getDate().compareTo(e2.getDate());
				}
			});

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

		model.addAttribute(ControllerAttributesData.getAddEventInfoAttribute(), "");
		model.addAttribute(ControllerAttributesData.getAddSeriesInfoAttribute(), "");
		model.addAttribute("user", repoUser.findOneById(id));
	}

	
}
