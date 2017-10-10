package pl.lps.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import pl.lps.data.ControllerAttributesData;
import pl.lps.data.ControllerData;
import pl.lps.entity.Event;
import pl.lps.entity.EventType;
import pl.lps.entity.Place;
import pl.lps.entity.Room;
import pl.lps.entity.Series;
import pl.lps.entity.User;
import pl.lps.model.SessionValidation;
import pl.lps.model.SessionedController;
import pl.lps.repository.EventRepository;
import pl.lps.repository.EventTypeRepository;
import pl.lps.repository.PlaceRepository;
import pl.lps.repository.RoomRepository;
import pl.lps.repository.SeriesRepository;
import pl.lps.repository.UserRepository;

/**
 * EventController is a class used to map and process all users requests
 * regarding events. All methods are mapped at base "/events" browser path.
 * EventController returns and feeds event related views: userPanel (main user's
 * view responsible for display of all user's events), addEvent and edit event
 * responsible for event adding and editing.
 * 
 * @author kaz
 *
 */
@Controller
@RequestMapping("/events")
public class EventController extends SessionedController {

	/**
	 * Name of model attribute passing selected user to event related views.
	 */
	protected static final String USER_ATTRIBUTE = ControllerAttributesData.getUserAttribute();
	/**
	 * Name of model attribute passing selected event to add event and edit event
	 * views.
	 */
	protected static final String EVENT_ATTRIBUTE = ControllerAttributesData.getEventAttribute();
	/**
	 * Name of model attribute passing list of all or selected events to particular
	 * views.
	 */
	protected static final String ALL_EVENTS_ATTRIBUTE = ControllerAttributesData.getAllEventsAttribute();
	/**
	 * Name of model attribute passing event type attribute of selected event to
	 * event views.
	 */
	protected static final String EVENT_TYPE_ATTRIBUTE = ControllerAttributesData.getEventTypeAttribute();
	/**
	 * Name of model attribute passing a list all places to event views.
	 */
	protected static final String ALL_PLACES_ATTRIBUTE = ControllerAttributesData.getAllPlacesAttribute();

	/**
	 * Name of model attribute passing a list all places to event views.
	 */
	protected static final String REQUESTED_EVENT_ATTRIBUTE = ControllerAttributesData.getRequestedEventAttribute();
	/**
	 * Name of model attribute passing result of add / edit event to event views.
	 */

	protected static final String ADD_EVENT_INFO_ATTRIBUTE = ControllerAttributesData.getAddEventInfoAttribute();
	/**
	 * Name of model attribute passing an to the user panel view information
	 * regarding the series of events added to the event repository.
	 */
	private static final String REQUESTED_EVENT_SERIES_ATTRIBUTE = ControllerAttributesData
			.getRequestedEventSeriesAttribute();
	/**
	 * Id of the series that should be displayed (0 for all series)
	 */
	private static final String SERIES_DISPLAYED_ATTRIBUTE = ControllerAttributesData.getSeriesDisplayedAttribute();
	/**
	 * Name of model attribute passing an information to the user panel view
	 * regarding the current series (all or name} being displayed
	 */
	private static final String SERIES_DISPLAYED_INFO_ATTRIBUTE = ControllerAttributesData
			.getSeriesDisplayedInfoAttribute();

	/**
	 * The long value that represents an hour in milliseconds
	 */
	public static final long HOUR = 3600L * 1000;
	/**
	 * The long value that represents a day in milliseconds
	 */
	public static final long DAY = 3600L * 1000 * 24;
	/**
	 * Passes the name of home page of this application.
	 */
	private static final String MAIN_VIEW = ControllerData.getMainView();
	/**
	 * Passes the name of add event view.
	 */
	private static final String ADD_EVENT_VIEW = ControllerData.getAddEventView();
	/**
	 * Passes the name of edit event view.
	 */
	private static final String EDIT_EVENT_VIEW = ControllerData.getEditEventView();
	/**
	 * Passes the name of event list view used by admin.
	 */
	private static final String EVENTS_VIEW = ControllerData.getEventsView();
	/**
	 * Passes the name of user home page, containing list of reserved events .
	 */
	private static final String USER_PANEL_VIEW = ControllerData.getUserPanelView();

	/**
	 * Instantiation of Room instance, that will be initialized with first free room
	 * attributes if such is found for defined event series criteria
	 */
	Room roomPossible = new Room();

	/**
	 * Event repository instance injection
	 */
	@Autowired
	EventRepository repoEvent;

	/**
	 * EventType repository instance injection
	 */
	@Autowired
	EventTypeRepository repoEventType;

	/**
	 * User repository instance injection
	 */

	@Autowired
	UserRepository repoUser;

	/**
	 * Place repository instance injection
	 */

	@Autowired
	PlaceRepository repoPlace;

	/**
	 * Room repository instance injection
	 */

	@Autowired
	RoomRepository repoRoom;

	/**
	 * Series repository instance injection
	 */

	@Autowired
	SeriesRepository repoSeries;

	/**
	 * SessionValidation class instance injection
	 */

	@Autowired
	SessionValidation sv;

	/**
	 * Maps admin request to display all events in application database
	 * 
	 * @param model
	 * @return events.html view fed with all events in application database
	 */
	@RequestMapping("")
	public String allEvents(Model model) {

		if (!SessionValidation.isSessionAdmin()) {
			return MAIN_VIEW;
		}

		model.addAttribute(ALL_EVENTS_ATTRIBUTE, repoEvent.findAll());
		model.addAttribute(USER_ATTRIBUTE, repoUser.findOneByUserName("admin"));
		return EVENTS_VIEW;
	}

	/**
	 * Maps request made by concerning display of this user's list of events !!!!!
	 * not mapped in this version (now it is allEventsBySeriesId) - to be deleted
	 * after verification of links !!!!!
	 * 
	 * @param id
	 *            - this user id
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 * @return userPanel.html view fed with this user's event list and this user
	 *         object
	 */

	@RequestMapping("/{id}")
	public String allEvents(@PathVariable Long id, Model model) {

		if (!SessionValidation.isSessionUser(id)) {
			return MAIN_VIEW;
		}
		model.addAttribute(ALL_EVENTS_ATTRIBUTE, repoEvent.findAllBySeriesUserId(id));
		model.addAttribute(USER_ATTRIBUTE, repoUser.findOneById(id));
		return USER_PANEL_VIEW;
	}

	/**
	 * Maps request made by user concerning display of this user's home page
	 * containing the list of events of this user. The list may include all events
	 * for ids parameter = 0 or be limited to one of this user's series for ids
	 * parameter > 0.
	 * 
	 * @param id
	 *            - this user id
	 * @param ids
	 *            - id of series from the origin view (when the edit link was
	 *            clicked on). 0 gives all events of this user, while other ids
	 *            values narrow list to the events belonging to the series with
	 *            specified id (ids).
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 * @return userPanel.html view fed with this user's event list and this user
	 *         object
	 */

	@RequestMapping("/{id}/series/{ids}")
	public String allEventsBySeriesId(@PathVariable Long id, @PathVariable Long ids, Model model) {

		if (!SessionValidation.isSessionUser(id)) {
			return MAIN_VIEW;
		}
		model.addAttribute(USER_ATTRIBUTE, repoUser.findOneById(id));
		model.addAttribute(SERIES_DISPLAYED_ATTRIBUTE, ids);
		model.addAttribute(REQUESTED_EVENT_SERIES_ATTRIBUTE, "null");
		model.addAttribute(ADD_EVENT_INFO_ATTRIBUTE, "null");
		
		if (ids == 0) {
			model.addAttribute(ALL_EVENTS_ATTRIBUTE, repoEvent.findAllBySeriesUserId(id));
			model.addAttribute(SERIES_DISPLAYED_INFO_ATTRIBUTE, " - wszystkie serie");
			
		} else {
			model.addAttribute(ALL_EVENTS_ATTRIBUTE, repoEvent.findAllBySeriesId(ids));
			model.addAttribute(SERIES_DISPLAYED_INFO_ATTRIBUTE,
					" " + repoSeries.getOne(ids).getEventType().getName() + " " + repoSeries.getOne(ids).getClient());
		}
		model.addAttribute("activeMenuItem", "home");
		
		return USER_PANEL_VIEW;

	}

	/**
	 * Maps get request made by user with specified id concerning display of this
	 * user add event form. !!!!! Possibility of adding event to an existing series
	 * to be created!!!!!
	 * 
	 * @param id
	 *            - this user id
	 * @param ids
	 *            - id of series from the origin view (when the edit link was
	 *            clicked on). 0 for all user's series.
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 * @return addEvent.html view fed with this user object and list of all
	 *         available places
	 */
	@GetMapping("/{id}/add/{ids}")
	public String addEvent(@PathVariable Long id, @PathVariable Long ids, Model model) {

		if (!SessionValidation.isSessionUser(id)) {
			return MAIN_VIEW;
		}

		model.addAttribute(USER_ATTRIBUTE, repoUser.findOneById(id));
		model.addAttribute(ALL_PLACES_ATTRIBUTE, repoPlace.findAll());
		model.addAttribute(ADD_EVENT_INFO_ATTRIBUTE, "null");
		model.addAttribute(SERIES_DISPLAYED_ATTRIBUTE, ids);
		model.addAttribute("activeMenuItem", "addEvent");

		
		return ADD_EVENT_VIEW;
	}

	/**
	 * Maps post request concerning adding of new event made by user via input form
	 * on addEvent.html view
	 *
	 * 
	 * @param dateOfFirstEvent
	 *            - YYYY-MM-dd date of first event in the defined event series -
	 *            input form parameter
	 * @param hour
	 *            - hh:mm hour of day of each event in the defined event series -
	 *            input form parameter
	 * @param numberOfEvents
	 *            - number of events in the defined event series - input form
	 *            parameter
	 * @param eventDuration
	 *            - duration of each event in the defined event series - input form
	 *            parameter
	 * @param eventSeats
	 *            - number of seats needed for each event in the defined event
	 *            series - input form parameter
	 * @param placeId
	 *            - id of the place of room that should be reserved for the defined
	 *            event series - input form parameter
	 * @param eventTypeId
	 *            - id of the event type assigned for the defined event series -
	 *            input form parameter
	 * @param eventCycleLength
	 *            - length of event cycle in days - input form parameter
	 * @param client
	 *            - client of the defined event series
	 * @param id
	 *            - id of user making reservation
	 * @param ids
	 *            - id of series from the origin view (when the edit link was
	 *            clicked on). 0 for all user's series.
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 * @return - userPanel.html view fed with this user's event list and this user
	 *         object for ordinary user and events.html view for admin
	 * @throws ParseException
	 */
	@PostMapping("/{id}/add/{ids}")
	public String addEventPost(@RequestParam @DateTimeFormat(pattern = "MM/dd/yyyy") Date dateOfFirstEvent,
			@RequestParam @DateTimeFormat(pattern = "HH:mm") Date hour, @RequestParam Long numberOfEvents,
			@RequestParam Long eventDuration, @RequestParam Long eventSeats, @RequestParam Long placeId,
			@RequestParam Long eventTypeId, @RequestParam Long eventCycleLength, @RequestParam String client,
			@PathVariable Long id, @PathVariable Long ids, Model model) {

		Date endHour = new Date(hour.getTime() + eventDuration * HOUR);

		User userCurrent = repoUser.findOneById(id);

		ArrayList<Date> requestedDates = new ArrayList<>();

		Series series = new Series(userCurrent, client, repoEventType.findOneById(eventTypeId));

		ArrayList<Room> rooms = roomLongListing(eventSeats, placeId);

		// Creation of date list to pass it to event search method
		for (Long i = 0L; i < numberOfEvents; i++) {
			Date nextEventDate = new Date(dateOfFirstEvent.getTime() + i * eventCycleLength * DAY);
			requestedDates.add(nextEventDate);
		}

		for (Room room : rooms) {

			if (repoEvent.findManyCollidingEvents(requestedDates, room.getId(), hour, endHour).isEmpty()) {

				roomPossible = room;
				model.addAttribute(ADD_EVENT_INFO_ATTRIBUTE, "event.added");

				break;
			} else {
				model.addAttribute(ADD_EVENT_INFO_ATTRIBUTE, "event.no.rooms");
				roomPossible = null;
			}

		}

		if (roomPossible != null) {

			Integer i = 0;
			for (Date eventStartDate : requestedDates) {
				if (i == 0) {
					repoSeries.save(series);
				}

				i++;
				Event event = new Event(eventStartDate, hour, endHour, eventDuration, eventSeats, series, roomPossible);
				repoEvent.save(event);

				
				model.addAttribute("eventCycleLength", eventCycleLength);
				model.addAttribute("followingEvents", (i - 1));

				if (i == 1) {
					model.addAttribute(REQUESTED_EVENT_SERIES_ATTRIBUTE, "null");

					model.addAttribute(REQUESTED_EVENT_ATTRIBUTE, event);
				} else if (i == 2) {
					model.addAttribute(REQUESTED_EVENT_SERIES_ATTRIBUTE, "event.following.series.2");
				} else if (i > 2 && i < 6) {
					model.addAttribute(REQUESTED_EVENT_SERIES_ATTRIBUTE, "event.following.series.3.to.5");
				} else if (i >= 6) {
					model.addAttribute(REQUESTED_EVENT_SERIES_ATTRIBUTE, "event.following.series.from.6");
				}
			}
		}

		model.addAttribute(USER_ATTRIBUTE, userCurrent);
		model.addAttribute(EVENT_TYPE_ATTRIBUTE, repoEventType.findAll());
		model.addAttribute(SERIES_DISPLAYED_ATTRIBUTE, ids);

		return userVsAdminRedirect(id, ids, model);

	}

	/**
	 * Maps get request made by user with specified id concerning display of this
	 * user add event form
	 * 
	 * @param ide
	 *            - edited event id
	 * @param id
	 *            - this user id
	 * @param ids
	 *            - id of series from the origin view (when the edit link was
	 *            clicked on). 0 for all user's series.
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 * 
	 * @return editEvent.html view fed with this user object and list of all
	 *         available places
	 * 
	 */

	@GetMapping("/{id}/edit/{ide}/{ids}")
	public String editEvent(@PathVariable Long ide, @PathVariable Long id, @PathVariable Long ids, Model model) {

		if (!SessionValidation.isSessionUser(id)) {
			return MAIN_VIEW;
		}
		model.addAttribute(USER_ATTRIBUTE, repoUser.findOneById(id));
		System.err.println("GET" + repoEvent.findOneById(ide).getRoom().getPlace().getName().toString());
		model.addAttribute(EVENT_ATTRIBUTE, repoEvent.findOneById(ide));
		model.addAttribute(SERIES_DISPLAYED_ATTRIBUTE, ids);

		return EDIT_EVENT_VIEW;
	}

	/**
	 * Maps post request concerning editing of selected event made by user or admin
	 * via input form on editEvent.html view
	 * 
	 * @param ide
	 *            - edited event id
	 * @param id
	 *            - this user id
	 * @param ids
	 *            - id of series from the origin view (when the edit link was
	 *            clicked on). 0 for all user's series.
	 * @param event
	 *            - edited event object
	 * @param result
	 *            - binding result errors
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 * @return - userPanel.html view for user request or events.html view for admin
	 *         request with updated list of events
	 */
	@PostMapping("/{id}/edit/{ide}/{ids}")
	public String editEventPost(@PathVariable Long ide, @PathVariable Long id, @PathVariable Long ids,
			@Valid Event event, @RequestParam Long placeId, BindingResult result, Model model) {
		if (result.hasErrors()) {
			System.err.println(result);
			return EDIT_EVENT_VIEW;
		}

		Date endHour = new Date(event.getHour().getTime() + event.getEventDuration() * HOUR);
		event.setEndHour(endHour);
		event.setId(ide);
		Room roomCurrent = event.getRoom();

		System.err.println("POST 1" + roomCurrent.getPlace().getName().toString());

		Long placeCurrentId = placeId;
		Long seatsRequired = event.getEventSeats();
		// event deleted temporarily from repo to not disturb in the search for free
		// rooms (e.g. in
		// the situation when user wants to move 2 hour event one hour earlier
		repoEvent.deleteById(ide);
		if (placeId == roomCurrent.getPlace().getId() && repoEvent
				.findCollidingEvents(event.getDate(), roomCurrent.getId(), event.getHour(), event.getEndHour())
				.isEmpty()) {
			roomPossible = roomCurrent;
		} else {

			ArrayList<Room> rooms = roomLongListing(placeCurrentId, seatsRequired);

			for (Room room : rooms) {
				if (repoEvent.findCollidingEvents(event.getDate(), room.getId(), event.getHour(), event.getEndHour())
						.isEmpty()) {
					roomPossible = room;
					event.setRoom(roomPossible);
					break;
				} else {
					model.addAttribute(ADD_EVENT_INFO_ATTRIBUTE, "event.no.rooms");
					model.addAttribute(EVENT_ATTRIBUTE, event);
					model.addAttribute(USER_ATTRIBUTE, repoUser.findOneById(id));
					model.addAttribute(SERIES_DISPLAYED_ATTRIBUTE, ids);

					return EDIT_EVENT_VIEW;
				}
			}

		}
		repoEvent.save(event);
		model.addAttribute(REQUESTED_EVENT_ATTRIBUTE, event);
		model.addAttribute(USER_ATTRIBUTE, repoUser.findOneById(id));

		model.addAttribute(EVENT_TYPE_ATTRIBUTE, repoEventType.findAll());
		model.addAttribute(ADD_EVENT_INFO_ATTRIBUTE, "event.changed");
		model.addAttribute(REQUESTED_EVENT_SERIES_ATTRIBUTE, "null");
		model.addAttribute(SERIES_DISPLAYED_ATTRIBUTE, ids);

		return userVsAdminRedirect(id, ids, model);
	}

	/**
	 * Maps get request made by user with specified id concerning removal of
	 * selected event
	 * 
	 * @param id
	 *            - user id
	 * @param ide
	 *            - id of event being deleted
	 * @param ids
	 *            - id of series from the origin view (when the edit link was
	 *            clicked on)
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 * @return - userPanel.html view for user request or events.html view for admin
	 *         request with updated list of events
	 */
	@GetMapping("/{id}/delete/{ide}/{ids}")
	public String delEvent(@PathVariable Long id, @PathVariable Long ide, @PathVariable Long ids, Model model) {

		if (!SessionValidation.isSessionUser(id)) {
			return MAIN_VIEW;
		}
		
		/**
		 * Series removal when the last even of the series has been deleted.
		 */
		Event event = repoEvent.findOneById(ide);
		if (event.getSeries().getEvents().size() == 1) {
			repoSeries.delete(event.getSeries());
		}
			
		repoEvent.deleteById(ide);
		
		
		
		model.addAttribute(ALL_EVENTS_ATTRIBUTE, repoEvent.findAll());
		User userCurrent = repoUser.findOneById(id);
		model.addAttribute(USER_ATTRIBUTE, userCurrent);

		model.addAttribute(EVENT_TYPE_ATTRIBUTE, repoEventType.findAll());
		model.addAttribute(ADD_EVENT_INFO_ATTRIBUTE, "event.removed");
		model.addAttribute(REQUESTED_EVENT_SERIES_ATTRIBUTE, "null");

		return userVsAdminRedirect(id, ids, model);
	}

	/**
	 * Preparation of array list of rooms, that will be used to narrow free room
	 * search to rooms meeting place and number of seats criteria
	 */

	private ArrayList<Room> roomLongListing(Long placeCurrentId, Long seatsRequired) {

		ArrayList<Room> rooms = new ArrayList<Room>();
		if (placeCurrentId == repoPlace.findOneByName("Dowolne").getId()) {
			rooms = (ArrayList<Room>) repoRoom.findAllByRoomSize(seatsRequired);
		} else {
			rooms = (ArrayList<Room>) repoRoom.findAllByPlaceAndRoomSize(placeCurrentId, seatsRequired);
		}
		return rooms;
	}

	/**
	 * Selects admin or user view based on passed id
	 * 
	 * @param id
	 *            - user id
	 * @param ids
	 *            - id of series from the origin view (when the edit link was
	 *            clicked on). 0 for all user's series.
	 * 
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 * @return admin or user view
	 */
	private String userVsAdminRedirect(Long id, Long ids, Model model) {
		if (repoUser.findOneById(id).getUserName().equals("admin")) {
			model.addAttribute(ALL_EVENTS_ATTRIBUTE, repoEvent.findAll());
			return EVENTS_VIEW;
		} else {
			model.addAttribute(SERIES_DISPLAYED_ATTRIBUTE, ids);
			if (ids == 0) {
				model.addAttribute(ALL_EVENTS_ATTRIBUTE, repoEvent.findAllBySeriesUserId(id));
				model.addAttribute(SERIES_DISPLAYED_INFO_ATTRIBUTE, " - wszystkie serie");

			} else {

				model.addAttribute(ALL_EVENTS_ATTRIBUTE, repoEvent.findAllBySeriesId(ids));
				model.addAttribute(SERIES_DISPLAYED_INFO_ATTRIBUTE, " "
						+ repoSeries.getOne(ids).getEventType().getName() + " " + repoSeries.getOne(ids).getClient());
			}
			model.addAttribute("activeMenuItem", "home");

			return USER_PANEL_VIEW;

		}
	}

	/**
	 * Sets model attribute passing all types on event to add event input form view
	 * 
	 * @return model attribute containing all types of events
	 */
	@ModelAttribute("ourEventTypes")
	public List<EventType> getEventTypes() {
		return repoEventType.findAll();
	}

	/**
	 * Sets model attribute passing all places to add event input form view
	 * 
	 * @return model attribute containing all types of events
	 */

	@ModelAttribute("ourPlaces")
	public List<Place> getPlaces() {
		return repoPlace.findAll();
	}

}
