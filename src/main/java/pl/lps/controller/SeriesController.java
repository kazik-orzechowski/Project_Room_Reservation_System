package pl.lps.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.lps.data.ControllerData;
import pl.lps.entity.Event;
import pl.lps.entity.Series;
import pl.lps.entity.SeriesDTO;
import pl.lps.entity.User;
import pl.lps.repository.EventRepository;
import pl.lps.repository.EventTypeRepository;
import pl.lps.repository.SeriesRepository;
import pl.lps.repository.UserRepository;

@Controller
@RequestMapping("/series")
public class SeriesController extends SessionedController {

	/**
	 * Name of model attribute passing result of add / edit event to event views.
	 */
	protected static final String ADD_EVENT_INFO_ATTRIBUTE = "addEventInfo";

	
	public static final long HOUR = 3600 * 1000;
	public static final long DAY = 3600 * 1000 * 24;
	private static final String MAIN_VIEW = ControllerData.getMainView();

	@Autowired
	EventRepository repoEvent;

	@Autowired
	EventTypeRepository repoEventType;

	@Autowired
	UserRepository repoUser;

	@Autowired
	SeriesRepository repoSeries;

	@Autowired
	SessionValidation sv;

	@RequestMapping("")
	public String allSeries(Model model) {

		if (!SessionValidation.isSessionAdmin()) {
			return MAIN_VIEW;
		}

		model.addAttribute("series", repoEvent.findAll());
		model.addAttribute("user", repoUser.findOneByUserName("admin"));
		return "series";
	}

	/**
	 * User series list display request
	 * 
	 * @param id
	 *            - user id
	 * @param model
	 * @return userSeriesPanel.html view
	 */
	@Transactional
	@RequestMapping("/{id}")
	public String getSeries(@PathVariable Long id, Model model) {

		if (!SessionValidation.isSessionUser(id)) {
			return "main";
		}

		List<Series> seriesList = repoSeries.findAllByUserId(id);
		List<SeriesDTO> seriesDTOList = new ArrayList<SeriesDTO>();

		for (Series series : seriesList) {

			SeriesDTO seriesDto = new SeriesDTO();
			seriesDto.setSeries(series);
			Hibernate.initialize(series.getEvents());
			List<Event> events = (List<Event>) series.getEvents();
System.err.println(series.toString());
			Collections.sort(events, new Comparator<Event>() {
				public int compare(Event e1, Event e2) {
					return e1.getDate().compareTo(e2.getDate());
				}
			});
System.err.println(events.toString());
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
System.err.println("Checkpoint 3");
		model.addAttribute("allSeries", seriesDTOList);
		User userCurrent = repoUser.findOneById(id);
		model.addAttribute("user", userCurrent);
		model.addAttribute("user", userCurrent);
		model.addAttribute(ADD_EVENT_INFO_ATTRIBUTE, "");

//		if (userCurrent.getUserName().equals("admin")) {
//			model.addAttribute("allSeries", repoEvent.findAll());
//			return "events";
//		} else {
//			model.addAttribute("allSeries", repoEvent.findAllBySeriesUserId(id));
//		}

		model.addAttribute("addSeriesInfo", "");
		return "userSeriesPanel";

	}
}

//
// @RequestMapping("/{id}")
// public String allSeries(@PathVariable Long id, Model model) {
//
// if(!SessionValidation.isSessionUser(id)) {
// return "main";
// }
//
// public String getSeries(@PathVariable Long id, Model model) {
//
// if(!SessionValidation.isSessionUser(id)) {
// return "main";
// }
//
// List<Series> seriesList = repoSeries.findAllByUserId(id);
//
// for(Series series : seriesList)
// {
//
//
// ArrayList<Event> events = (ArrayList<Event>) series.getEvents();
// Long numberOfEvents = (long) events.size();
// List<Date>
// for(Event event : events) {
//
// }
//
// SeriesDTO seriesDto = new SeriesDTO
// }
//
// model.addAttribute("series", seriesList);
// User userCurrent = repoUser.findOneById(id);
// model.addAttribute("user", userCurrent);
//
//
//
// if (userCurrent.getUserName().equals("admin")) {
// model.addAttribute("events", repoEvent.findAll());
// return "events";
// } else {
// model.addAttribute("events", repoEvent.findAllBySeriesUserId(id));
// }
// model.addAttribute("eventType", repoEventType.findAll());
// model.addAttribute("addEventInfo", "Usunięto zdarzenie");
// return "userPanel";
//
// }
//
//
//
// @GetMapping("/{id}/delete/{ide}")
// public String delSeries(@PathVariable Long id, @PathVariable Long ide, Model
// model) {
//
// if(!SessionValidation.isSessionUser(id)) {
// return "main";
// }
//
// repoEvent.deleteById(ide);
// model.addAttribute("events", repoEvent.findAll());
// User userCurrent = repoUser.findOneById(id);
// model.addAttribute("user", userCurrent);
// if (userCurrent.getUserName().equals("admin")) {
// model.addAttribute("events", repoEvent.findAll());
// return "events";
// } else {
// model.addAttribute("events", repoEvent.findAllBySeriesUserId(id));
// }
// model.addAttribute("eventType", repoEventType.findAll());
// model.addAttribute("addEventInfo", "Usunięto zdarzenie");
// return "userPanel";
//
// }
//
//
//
// }
