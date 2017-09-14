package pl.lps.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import org.thymeleaf.util.DateUtils;

import pl.lps.entity.EventType;
import pl.lps.entity.Place;
import pl.lps.entity.Room;
import pl.lps.entity.Series;
import pl.lps.entity.User;
import pl.lps.entity.Event;
import pl.lps.repository.EventTypeRepository;
import pl.lps.repository.PlaceRepository;
import pl.lps.repository.RoomRepository;
import pl.lps.repository.SeriesRepository;
import pl.lps.repository.UserRepository;
import pl.lps.repository.EventRepository;

@Controller
@RequestMapping("/events")
public class EventController {

	public static final long HOUR = 3600 * 1000;
	public static final long DAY = 3600 * 1000 * 24;

	@Autowired
	EventRepository repoEvent;

	@Autowired
	EventTypeRepository repoEventType;

	@Autowired
	UserRepository repoUser;

	@Autowired
	PlaceRepository repoPlace;

	@Autowired
	RoomRepository repoRoom;

	@Autowired
	SeriesRepository repoSeries;

//	@RequestMapping("")
//	public String allEvents(Model model) {
//		model.addAttribute("events", repoEvent.findAll());
//		System.out.println(repoEvent.findAll().toString());
//		return "events";
//	}

	@RequestMapping("/{id}")
	public String allEvents(@PathVariable Long id, Model model) {
		model.addAttribute("events", repoEvent.findAllBySeriesUserId(id));
		model.addAttribute("user", repoUser.findOneById(id));
		System.out.println(repoEvent.findAll().toString());
		return "userPanel";
	}

	@GetMapping("/{id}/add")
	public String addEvent(@PathVariable Long id, Model model) {

		model.addAttribute("user", repoUser.findOneById(id));
		model.addAttribute("allPlaces", repoPlace.findAll());

		return "addEvent";
	}

	@PostMapping("/{id}/add")
	public String addEventPost(@RequestParam @DateTimeFormat(pattern = "MM/dd/yyyy") Date dateOfFirstEvent,
			@RequestParam @DateTimeFormat(pattern = "HH:mm") Date hour, @RequestParam Long numberOfEvents,
			@RequestParam Long eventDuration, @RequestParam Long eventSeats, @RequestParam Long placeId,
			@RequestParam Long eventTypeId, @RequestParam Long eventCycleLength, @RequestParam String client,
			@PathVariable Long id, Model model) throws ParseException {

		Date endHour = new Date(hour.getTime() + eventDuration * HOUR);

		User userCurrent = repoUser.findOneById(id);
		ArrayList<Room> rooms = new ArrayList<Room>();
		ArrayList<Event> events = new ArrayList<Event>();
		ArrayList<Date> requestedDates = new ArrayList<Date>();
		// ArrayList<Room> possibleRooms = new ArrayList<Room>();
		Room possibleRoom = new Room();
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

			if (repoEvent.findNotCollidingManyEvents(requestedDates, room.getId(), hour, endHour).isEmpty()) {
				System.err.println("*** IF " + room.toString());

				// if (repoEvent.findNotCollidingEvents(dateOfFirstEvent, room.getId(), hour,
				// endHour).isEmpty()) {
				// System.err.println("*** IF " + room.toString());
				// rooms.add(room);

				possibleRoom = room;

				System.err.println("*** IF OK " + room.toString());
				model.addAttribute("addEventInfo", "Dodano zdarzenie");

				break;
			} else {
				System.err.println("*** ELSE " + room.toString());
				model.addAttribute("addEventInfo", "Brak wolnych sal");
			}

		}

		System.err.println("***** Checkpoint 3 *******");

		if (possibleRoom != null) {

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
				event.setRoom(repoRoom.findOneById(possibleRoom.getId()));
				event.setSeries(series);
				repoEvent.save(event);

				if (i == 1) {
					model.addAttribute("requestedEvent", event);
				}
			}
		}

		System.err.println("***** Checkpoint LAST *******");
		model.addAttribute("user", userCurrent);
		
		// User 0 to administrator - ma dostęp do wszystkich zdarzeń
		if (repoUser.findOneById(id).getUserName().equals("admin")) {
			model.addAttribute("events", repoEvent.findAll());
		} else {
			model.addAttribute("events", repoEvent.findAllBySeriesUserId(id));
		}
		model.addAttribute("eventType", repoEventType.findAll());
		
		return "userPanel";
	}

	@GetMapping("/{id}/delete/{ide}")
	public String delEvent(@PathVariable Long id, @PathVariable Long ide, Model model) {
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
		return "userPanel";

	}

	@GetMapping("/{id}/edit/{ide}")
	public String editEvent(@PathVariable Long ide, @PathVariable Long id,  Model model) {
		model.addAttribute("user", repoUser.findOneById(id));
		model.addAttribute("event", repoEvent.findOneById(ide));
		return "editEvent";
	}

	@PostMapping("/{id}/edit/{ide}")
	public String editEventPost(@PathVariable Long ide, @PathVariable Long id, 
								@Valid Event event, BindingResult result, Model model) {
		if (result.hasErrors()) {
			System.err.println(result);
			return "editEvent";
		}
		repoEvent.save(event);
		model.addAttribute("events", repoEvent.findAll());
		model.addAttribute("user", repoUser.findOneById(id));
		return "events"; // event.toString();

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
