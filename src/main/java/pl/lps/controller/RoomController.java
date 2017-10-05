package pl.lps.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.lps.data.ControllerData;
import pl.lps.entity.Place;
import pl.lps.entity.Room;
import pl.lps.repository.PlaceRepository;
import pl.lps.repository.RoomRepository;
import pl.lps.repository.UserRepository;

@Controller
@RequestMapping("/rooms")
public class RoomController extends SessionedController {

	@Autowired
	UserRepository repoUser;
	
	/**
	 * Defines the name of place attribute used in room list view to show current
	 * place that list refers to.
	 */
	private static final String PLACE_ATTRIBUTE = "place";

	/**
	 * Defines the name of room attribute used in add place and edit room views.
	 */
	private static final String ROOM_ATTRIBUTE = "room";
	
	/**
	 * Defines the name of place attribute used in place list view.
	 */
	private static final String ALL_ROOMS_ATTRIBUTE = "allRooms";

	/**
	 * Passes the name of home page of this application.
	 */
	private static final String MAIN_VIEW = ControllerData.getMainView();

	/**
	 * Passes the name of all rooms available to given user for specified
	 * reservation criteria.
	 */
	private static final String ROOMS_FOR_USER_VIEW = "roomsForUser";

	/**
	 * Passes the name of room list view.
	 */
	private static final String ROOMS_VIEW = ControllerData.getRoomsView();

	/**
	 * Passes the name of add room view.
	 */
	private static final String ADD_ROOM_VIEW = ControllerData.getAddRoomView();

	/**
	 * Passes the name of edit room view.
	 */
	private static final String EDIT_ROOM_VIEW = ControllerData.getEditRoomView();

	/**
	 * Id of the series that should be displayed (0 for all series)
	 */
	private static final String SERIES_DISPLAYED_ATTRIBUTE = "displayedSeriesId";
	
	/**
	 * Name of model attribute passing an information to the user panel view
	 * regarding the current series (all or name} being displayed
	 */
	private static final String SERIES_DISPLAYED_INFO_ATTRIBUTE = "displayedSeries";


	private static final String USER_ATTRIBUTE = "user";
	
	
	@Autowired
	RoomRepository repoRoom;

	@Autowired
	PlaceRepository repoPlace;

	/**
	 * Maps admin's request to add new room.
	 * 
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 * @return rooms.html view with a list of all the room
	 */

	
	@RequestMapping("")
	public String allRooms(Model model) {

		if (!SessionValidation.isSessionValid()) {
			return MAIN_VIEW;
		}

		model.addAttribute(ALL_ROOMS_ATTRIBUTE, repoRoom.findAllListOrderByPlaceName());
		return ROOMS_VIEW;
	}

	@RequestMapping("/forUser/{id}/{ids}")
	public String allRoomsForUser(@PathVariable Long id, @PathVariable Long ids, Model model) {

		if (!SessionValidation.isSessionValid()) {
			return MAIN_VIEW;
		}
		model.addAttribute(USER_ATTRIBUTE, repoUser.findOneById(id));
		model.addAttribute(SERIES_DISPLAYED_INFO_ATTRIBUTE, " - wszystkie serie");
		model.addAttribute(SERIES_DISPLAYED_ATTRIBUTE, ids);
		model.addAttribute(ALL_ROOMS_ATTRIBUTE, repoRoom.findAllListOrderByPlaceName());
		return ROOMS_FOR_USER_VIEW;
	}


	/**
	 * Maps admin's request to add a new room.
	 * 
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 * @return addRoom.html view with a list of all the rooms
	 */




	@GetMapping("/add")
	public String addRoom(Model model) {

		if (!SessionValidation.isSessionAdmin()) {
			return MAIN_VIEW;
		}

		Room addedRoom = new Room();
		model.addAttribute(ROOM_ATTRIBUTE, addedRoom);
		return ADD_ROOM_VIEW;
	}

	/**
	 * Maps post request concerning adding a new room made by admin
	 * via input form on addRoom.html view
	 * 
	 * @param room - room instance parameterized in the input form 
	 * @param result
	 *            - binding result errors
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 * @return rooms.html view with the updated list of all the rooms
	 */

	@PostMapping("/add")
	public String addRoomPost(@Valid Room room, BindingResult result, Model model) {
		if (result.hasErrors()) {
			System.err.println(result);
			return ADD_ROOM_VIEW;
		}
		repoRoom.save(room);
		model.addAttribute(ALL_ROOMS_ATTRIBUTE, repoRoom.findAllListOrderByPlaceName());
		model.addAttribute(PLACE_ATTRIBUTE, repoPlace.findAll());
		return ROOMS_VIEW;

	}



/**
 * Maps admin request to delete a place with the selected id.
 * 
 * @param id
 *            - id of the room to be deleted
 * 
 * @param model
 *            - instance of Model class used to pass attributes to the views
 * @return rooms.html view with the list of all the rooms
 */


	@GetMapping("/{id}/delete")
	public String delRoom(@PathVariable Long id, Model model) {

		if (!SessionValidation.isSessionAdmin()) {
			return MAIN_VIEW;
		}
		repoRoom.deleteById(id);
		model.addAttribute(ALL_ROOMS_ATTRIBUTE, repoRoom.findAllListOrderByPlaceName());
		model.addAttribute(PLACE_ATTRIBUTE, repoPlace.findAll());

		return ROOMS_VIEW;
	}

	/**
	 * Maps admin's request to edit a room with the selected id.
	 * 
	 * @param id
	 *            - id of the room to be edited
	 * 
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 * @return rooms.html view with the list of all the rooms
	 */

	@GetMapping("/{id}/edit")
	public String editRoom(@PathVariable Long id, Model model) {

		if (!SessionValidation.isSessionAdmin()) {
			return MAIN_VIEW;
		}
		model.addAttribute(ROOM_ATTRIBUTE, repoRoom.findOneById(id));
		return EDIT_ROOM_VIEW;
	}


	/**
	 * Maps admin's post request concerning editing a selected room 
	 * via input form on editRoom.html view
	 * 
	 * @param editedRoom - room instance parameterized in the input form 
	 * @param result
	 *            - binding result errors
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 * @return rooms.html view with the updated list of all the rooms
	 */

	
	@PostMapping("/{id}/edit")
	public String editRoomPost(@Valid Room editedRoom, BindingResult result, Model model) {
		if (result.hasErrors()) {
			System.err.println(result);
			return EDIT_ROOM_VIEW;
		}
		repoRoom.save(editedRoom);
		model.addAttribute(ALL_ROOMS_ATTRIBUTE, repoRoom.findAllListOrderByPlaceName());
		model.addAttribute(PLACE_ATTRIBUTE, repoPlace.findAll());
		return ROOMS_VIEW;

	}

	/**
	 * Sets model attribute passing all places to add event input form view
	 * 
	 * @return model attribute containing all types of events
	 */
	
	@ModelAttribute("ourPlaces")
	public List<Place> getPlaces() {
		return this.repoPlace.findAll();
	}

}
