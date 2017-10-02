package pl.lps.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.DateUtils;

import pl.lps.entity.EventType;
import pl.lps.entity.Place;
import pl.lps.entity.Room;
import pl.lps.entity.Series;
import pl.lps.entity.SeriesDTO;
import pl.lps.entity.User;
import pl.lps.data.ControllerData;
import pl.lps.entity.Event;
import pl.lps.repository.EventTypeRepository;
import pl.lps.repository.PlaceRepository;
import pl.lps.repository.RoomRepository;
import pl.lps.repository.SeriesRepository;
import pl.lps.repository.UserRepository;
import pl.lps.repository.EventRepository;

@Controller
@RequestMapping("/series")
public class SeriesController extends SessionedController {

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
			ArrayList<Event> events = (ArrayList<Event>) series.getEvents();

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
				venuesOfEventSeries.add(event.getRoom().getPlace() + " " + event.getRoom().getNumber());
				startHoursOfEventSeries.add(event.getHour());
			}
			seriesDto.setSeriesHours((ArrayList<Date>) startHoursOfEventSeries);
			seriesDto.setSeriesPlacesAndRooms((ArrayList<String>) venuesOfEventSeries);
			seriesDTOList.add(seriesDto); 
		}

		model.addAttribute("allSeries", seriesDTOList);
		User userCurrent = repoUser.findOneById(id);
		model.addAttribute("user", userCurrent);

		if (userCurrent.getUserName().equals("admin")) {
			model.addAttribute("events", repoEvent.findAll());
			return "events";
		} else {
			model.addAttribute("events", repoEvent.findAllBySeriesUserId(id));
		}
		model.addAttribute("eventType", repoEventType.findAll());
		model.addAttribute("addEventInfo", "Usunięto zdarzenie");
		return "userPanel";

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
