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
import org.springframework.web.bind.annotation.GetMapping;
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
	 * Passes the name of user view, containing list of user's series.
	 */
	private static final String USER_SERIES_PANEL_VIEW = ControllerData.getUserSeriesPanelView();
	/**
	 * Name of model attribute passing information regarding displayed series to user event view.
	 */

	private static final String ADD_SERIES_INFO_ATTRIBUTE = "addSeriesInfo";

	/**
	 * Name of model attribute passing result of add / edit event to event views.
	 */
	protected static final String ADD_EVENT_INFO_ATTRIBUTE = "addEventInfo";
	/**
	 * Passes the name of home page of this application.
	 */
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

	/**
	 * Maps the request of admin or user to display a list of this user's series of
	 * events.
	 * 
	 * @param id
	 *            - user id
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 * @return for user userPanel.html view containing all of this user series. For
	 *         admin returns series.html view containing series of events of all
	 *         users.
	 */
	@Transactional
	@RequestMapping("/{id}")
	public String getSeries(@PathVariable Long id, Model model) {

		if (!SessionValidation.isSessionUser(id)) {
			return MAIN_VIEW;
		}

		prepareSeriesView(id, model);

		return adminVsUserSeriesViewRedirect(id);

	}

	/**
	 * Maps the request of admin or user to delete the selected series of events.
	 * 
	 * @param id
	 *            - user id
	 * @param ids
	 *            - id of the series to be deleted
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 * @return for user userPanel.html view containing all of this user series. For
	 *         admin returns series.html view containing series of events of all
	 *         users.
	 */
	@GetMapping("/{id}/delete/{ids}")
	public String delSeries(@PathVariable Long id, @PathVariable Long ids, Model model) {

		if (!SessionValidation.isSessionUser(id)) {
			return MAIN_VIEW;
		}

		repoSeries.deleteById(ids);
		prepareSeriesView(id, model);

		return adminVsUserSeriesViewRedirect(id);

	}

	/**
	 * Selects admin or user view based on passed id
	 * 
	 * @param id
	 *            - user id
	 * @return for user userPanel.html view, for admin return series.html view.
	 */

	String adminVsUserSeriesViewRedirect(Long id) {
		if (repoUser.findOneById(id).getUserName().equals("admin")) {
			return "series";
		} else {
			return USER_SERIES_PANEL_VIEW;

		}
	}

	/**
	 * Prepares a list of SeriesDTO objects containing all data regarding event
	 * series stored in series objects and event objects.
	 * 
	 * @param id - user id
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 */

	void prepareSeriesView(Long id, Model model) {
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

		model.addAttribute("allSeries", seriesDTOList);

		model.addAttribute(ADD_EVENT_INFO_ATTRIBUTE, "");
		model.addAttribute(ADD_SERIES_INFO_ATTRIBUTE, "");
		model.addAttribute("user", repoUser.findOneById(id));
	}

}
