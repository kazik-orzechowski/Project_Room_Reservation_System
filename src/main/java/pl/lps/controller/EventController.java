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

import pl.lps.entity.Event;
import pl.lps.entity.EventType;
import pl.lps.entity.Place;
import pl.lps.entity.Room;
import pl.lps.entity.Series;
import pl.lps.entity.User;
import pl.lps.repository.EventRepository;
import pl.lps.repository.EventTypeRepository;
import pl.lps.repository.PlaceRepository;
import pl.lps.repository.RoomRepository;
import pl.lps.repository.SeriesRepository;
import pl.lps.repository.UserRepository;

/**
 * EventController is a class used to map and process all users requests
 * regarding events. All methods are mapped at base "/events" browser path.
 * EventController returns and feeds event related views: userPanel (main user
 * view responsible for display of all user events), addEvent and edit event
 * responsible for event adding and editing
 * 
 * @author kaz
 *
 */
@Controller
@RequestMapping("/events")
public class EventController extends SessionedController {

	/**
	 * The long value that represents an hour in milliseconds
	 */
	public static final long HOUR = 3600 * 1000;
	/**
	 * The long value that represents a day in milliseconds
	 */
	public static final long DAY = 3600 * 1000 * 24;

	/**
	 * Instantiation of Room instance, that will be initialized with first free room
	 * attributes if such is found for defined event series criteria
	 */
	Room roomPossible = new Room();

	/**
	 * Instantiation of array list of rooms, that will be used to narrow free room
	 * search to rooms meeting place and number of seats criteria
	 */

	ArrayList<Room> rooms = new ArrayList<Room>();

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
			return "main";
		}

		model.addAttribute("events", repoEvent.findAll());
		model.addAttribute("user", repoUser.findOneByUserName("admin"));
		return "events";
	}

	/**
	 * Maps request made by user with specified id concerning display of this user
	 * list of events
	 * 
	 * @param id
	 *            - this user id
	 * @param model
	 *            - instance used to pass attributes to the views
	 * @return userPanel.html view fed with this user's event list and this user
	 *         object
	 */
	@RequestMapping("/{id}")
	public String allEvents(@PathVariable Long id, Model model) {

		if (!SessionValidation.isSessionUser(id)) {
			return "main";
		}
		model.addAttribute("events", repoEvent.findAllBySeriesUserId(id));
		model.addAttribute("user", repoUser.findOneById(id));
		System.out.println(repoEvent.findAll().toString());
		return "userPanel";
	}

	/**
	 * Maps get request made by user with specified id concerning display of this
	 * user add event form
	 * 
	 * @param id
	 *            - this user id
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 * @return addEvent.html view fed with this user object and list of all
	 *         available places
	 */
	@GetMapping("/{id}/add")
	public String addEvent(@PathVariable Long id, Model model) {

		if (!SessionValidation.isSessionUser(id)) {
			return "main";
		}

		model.addAttribute("user", repoUser.findOneById(id));
		model.addAttribute("allPlaces", repoPlace.findAll());

		return "addEvent";
	}

	/**
	 * Maps post request concerning adding of new event made by user via input form
	 * on addEvent.html view
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
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 * @return - userPanel.html view fed with this user's event list and this user
	 *         object for ordinary user and events.html view for admin
	 * @throws ParseException
	 */
	@PostMapping("/{id}/add")
	public String addEventPost(@RequestParam @DateTimeFormat(pattern = "MM/dd/yyyy") Date dateOfFirstEvent,
			@RequestParam @DateTimeFormat(pattern = "HH:mm") Date hour, @RequestParam Long numberOfEvents,
			@RequestParam Long eventDuration, @RequestParam Long eventSeats, @RequestParam Long placeId,
			@RequestParam Long eventTypeId, @RequestParam Long eventCycleLength, @RequestParam String client,
			@PathVariable Long id, Model model) throws ParseException {

		Date endHour = new Date(hour.getTime() + eventDuration * HOUR);

		User userCurrent = repoUser.findOneById(id);

		ArrayList<Event> events = new ArrayList<Event>();
		ArrayList<Date> requestedDates = new ArrayList<Date>();
		// ArrayList<Room> roomPossibles = new ArrayList<Room>();

		Series series = new Series(userCurrent, client, repoEventType.findOneById(eventTypeId));

		// Warunek wybierający zaznaczyć wszystkie lokalizacje
		if (placeId == repoPlace.findOneByName("Dowolne").getId()) {
			rooms = (ArrayList<Room>) repoRoom.findAllByRoomSize(eventSeats);
		} else {
			rooms = (ArrayList<Room>) repoRoom.findAllByPlaceAndRoomSize(placeId, eventSeats);
		}

		System.err.println("***** Checkpoint 1 *******");

		// tworzenie ciągu dat na potrzeby weryfikacji czy terminy sa zajęte
		for (Long i = 0L; i < numberOfEvents; i++) {
			Date nextEventDate = new Date(dateOfFirstEvent.getTime() + i * eventCycleLength * DAY);
			requestedDates.add(nextEventDate);
		}

		System.err.println("Requested dates to String" + requestedDates.toString());
		System.err.println(dateOfFirstEvent.toString());

		for (Room room : rooms) {

			if (repoEvent.findManyCollidingEvents(requestedDates, room.getId(), hour).isEmpty()) {

				// if (repoEvent.findNotCollidingEvents(dateOfFirstEvent, room.getId(), hour,
				// endHour).isEmpty()) {
				// System.err.println("*** IF " + room.toString());
				// rooms.add(room);

				roomPossible = room;
				model.addAttribute("addEventInfo", "Dodano zdarzenie");

				break;
			} else {
				System.err.println("*** ELSE " + room.toString());
				model.addAttribute("addEventInfo", "Brak wolnych sal");
				roomPossible = null;
			}

		}

		System.err.println("***** Checkpoint 3 *******");

		if (roomPossible != null) {

			Integer i = 0;
			for (Date date2 : requestedDates) {
				if (i == 0) {
					repoSeries.save(series);
				}

				i++;
				System.err.println("***** Checkpoint " + (i + 4) + " *******");

				Event event = new Event();
				event.setDate(date2);
				event.setHour(hour);
				event.setEventSeats(eventSeats);
				event.setEndHour(endHour);
				event.setEventDuration(eventDuration);
				event.setRoom(repoRoom.findOneById(roomPossible.getId()));
				event.setSeries(series);
				repoEvent.save(event);

				if (i == 1) {
					model.addAttribute("requestedEvent", event);
				}
			}
		}

		System.err.println("***** Checkpoint LAST *******");
		model.addAttribute("user", userCurrent);
		model.addAttribute("eventType", repoEventType.findAll());
		// User 0 to administrator - ma dostęp do wszystkich zdarzeń
		if (repoUser.findOneById(id).getUserName().equals("admin")) {
			model.addAttribute("events", repoEvent.findAll());
			return "events";
		} else {
			model.addAttribute("events", repoEvent.findAllBySeriesUserId(id));
			return "userPanel";
		}

	}

	@GetMapping("/{id}/delete/{ide}")
	public String delEvent(@PathVariable Long id, @PathVariable Long ide, Model model) {

		if (!SessionValidation.isSessionUser(id)) {
			return "main";
		}

		repoEvent.deleteById(ide);
		model.addAttribute("events", repoEvent.findAll());
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

	@GetMapping("/{id}/edit/{ide}")
	public String editEvent(@PathVariable Long ide, @PathVariable Long id, Model model) {

		if (!SessionValidation.isSessionUser(id)) {
			return "main";
		}
		model.addAttribute("user", repoUser.findOneById(id));
		model.addAttribute("event", repoEvent.findOneById(ide));
		return "editEvent";
	}

	@PostMapping("/{id}/edit/{ide}")
	public String editEventPost(@PathVariable Long ide, @PathVariable Long id, @Valid Event event, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			System.err.println(result);
			return "editEvent";
		}

		Date endHour = new Date(event.getHour().getTime() + event.getEventDuration() * HOUR);
		event.setEndHour(endHour);
		event.setId(ide);
		repoEvent.deleteById(ide);
		Room roomCurrent = event.getRoom();
		Long placeCurrentId = roomCurrent.getPlace().getId();
		Long seatsRequired = event.getEventSeats();
		if (repoEvent.findCollidingEvents(event.getDate(), roomCurrent.getId(), event.getHour()).isEmpty()) {
			roomPossible = roomCurrent;
			System.err.println("Bieżący pokój");
		} else {
			if (placeCurrentId == repoPlace.findOneByName("Dowolne").getId()) {
				rooms = (ArrayList<Room>) repoRoom.findAllByRoomSize(seatsRequired);
			} else {
				rooms = (ArrayList<Room>) repoRoom.findAllByPlaceAndRoomSize(placeCurrentId, seatsRequired);
			}

			for (Room room : rooms) {

				if (repoEvent.findCollidingEvents(event.getDate(), room.getId(), event.getHour()).isEmpty()) {
					roomPossible = room;
					event.setRoom(roomPossible);
					System.err.println("Nowy pokój");
					break;
				} else {
					model.addAttribute("addEventInfo", "Brak wolnych sal");
					model.addAttribute("event", event);
					model.addAttribute("user", repoUser.findOneById(id));
					System.err.println("Brak pokoju");
					return "editEvent";
				}
			}

		}
		System.err.println(event.toString());
		repoEvent.save(event);
		model.addAttribute("requestedEvent", event);
		model.addAttribute("user", repoUser.findOneById(id));

		model.addAttribute("eventType", repoEventType.findAll());
		// User 0 to administrator - ma dostęp do wszystkich zdarzeń
		if (repoUser.findOneById(id).getUserName().equals("admin")) {
			model.addAttribute("events", repoEvent.findAll());
			return "events";
		} else {
			model.addAttribute("addEventInfo", "Zmieniono zdarzenie");
			model.addAttribute("events", repoEvent.findAllBySeriesUserId(id));
			return "userPanel";
		}

	}

	@ModelAttribute("ourEventTypes")
	public List<EventType> getEventTypes() {
		List<EventType> eventTypes = this.repoEventType.findAll();
		return eventTypes;
	}

	@ModelAttribute("ourPlaces")
	public List<Place> getPlaces() {
		List<Place> places = this.repoPlace.findAll();
		return places;
	}

}
