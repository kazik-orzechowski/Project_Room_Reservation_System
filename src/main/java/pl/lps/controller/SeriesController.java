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

import pl.lps.data.ControllerAttributesData;
import pl.lps.data.ControllerData;
import pl.lps.entity.Event;
import pl.lps.entity.Series;
import pl.lps.entity.SeriesDTO;
import pl.lps.model.SessionValidation;
import pl.lps.model.SessionedController;
import pl.lps.repository.EventRepository;
import pl.lps.repository.EventTypeRepository;
import pl.lps.repository.SeriesRepository;
import pl.lps.repository.UserRepository;

/**
 * SeriesController is a class used to map and process users and admins requests
 * regarding series of events, especially displaying, editing and deleting. All
 * methods are mapped at base "/series" browser path. SeriesController returns
 * and feeds series related views: userSeriesPanel (user's view responsible for
 * display of all user's series). Series editing is performed on userPanel.html
 * view, by deleting or editing particular event of series.
 * 
 * @author kaz
 *
 */

@Controller
@RequestMapping("/series")
public class SeriesController extends SessionedController {

	
	/**
	 * Name of model attribute passing selected user to event related views.
	 */
	private static final String USER_ATTRIBUTE = "user";

	/**
	 * Name of model attribute passing list of series to be displayed in series
	 * view.
	 */
	private static final String ALL_SERIES_ATTRIBUTE = "allSeries";

	/**
	 * Name of series view of this application.
	 */
	private static final String SERIES_VIEW = ControllerData.getSeriesView();
	/**
	 * Name of user view, containing list of user's series.
	 */
	private static final String USER_SERIES_PANEL_VIEW = ControllerData.getUserSeriesPanelView();
	/**
	 * Id of the series that should be displayed (0 for all series)
	 */
	private static final String SERIES_DISPLAYED_ATTRIBUTE = ControllerAttributesData.getSeriesDisplayedAttribute();

	/**
	 * Name of model attribute passing an to the user panel view information
	 * regarding the series of events added to the event repository.
	 */
	private static final String REQUESTED_EVENT_SERIES_ATTRIBUTE = ControllerAttributesData
			.getRequestedEventSeriesAttribute();

	/**
	 * Name of model attribute passing information regarding displayed series to
	 * user event view.
	 */

	private static final String ADD_SERIES_INFO_ATTRIBUTE = ControllerAttributesData.getAddSeriesInfoAttribute();

	/**
	 * Name of model attribute passing result of add / edit event to event views.
	 */
	private static final String ADD_EVENT_INFO_ATTRIBUTE = ControllerAttributesData.getAddEventInfoAttribute();
	/**
	 * Name of home page of this application.
	 */
	private static final String MAIN_VIEW = ControllerData.getMainView();

	/**
	 * Name of model attribute passing list of all or selected events to particular
	 * views.
	 */
	protected static final String ALL_EVENTS_ATTRIBUTE = ControllerAttributesData.getAllEventsAttribute();
	/**
	 * Name of model attribute passing an information to the user panel view
	 * regarding the current series (all or name} being displayed
	 */
	private static final String SERIES_DISPLAYED_INFO_ATTRIBUTE = ControllerAttributesData
			.getSeriesDisplayedInfoAttribute();

	
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

		SeriesService seriesServiceView = new SeriesService();
		prepareSeriesView(id, model);

		return adminVsUserSeriesViewRedirect(id, model);

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

		SeriesService seriesServiceView = new SeriesService();

		prepareSeriesView(id, model);

		model.addAttribute("eventCycleLength", "null");
		model.addAttribute("followingEvents", "null");

		return adminVsUserSeriesViewRedirect(id, model);

	}

	/**
	 * Selects admin or user view based on passed id
	 * 
	 * @param id
	 *            - user id
	 * @return for user userPanel.html view, for admin return series.html view.
	 */

	String adminVsUserSeriesViewRedirect(Long id, Model model) {
		model.addAttribute(ADD_EVENT_INFO_ATTRIBUTE, "null");
		model.addAttribute(REQUESTED_EVENT_SERIES_ATTRIBUTE, "null");
		model.addAttribute(SERIES_DISPLAYED_ATTRIBUTE, 0);
		model.addAttribute(ALL_EVENTS_ATTRIBUTE, repoEvent.findAllBySeriesUserId(id));
		model.addAttribute(SERIES_DISPLAYED_INFO_ATTRIBUTE, " - wszystkie serie");

		if (repoUser.findOneById(id).getUserName().equals("admin")) {
			return SERIES_VIEW;
		} else {
			model.addAttribute("activeMenuItem", "series");
			return USER_SERIES_PANEL_VIEW;

		}
	}

	public void prepareSeriesView(Long id, Model model) {
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

		model.addAttribute(ALL_SERIES_ATTRIBUTE, seriesDTOList);

		model.addAttribute(ADD_EVENT_INFO_ATTRIBUTE, "null");
		model.addAttribute(ADD_SERIES_INFO_ATTRIBUTE , "null");
		model.addAttribute(USER_ATTRIBUTE, repoUser.findOneById(id));
	}

	
}
