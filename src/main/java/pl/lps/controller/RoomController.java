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

import pl.lps.entity.Place;
import pl.lps.entity.Room;
import pl.lps.repository.PlaceRepository;
import pl.lps.repository.RoomRepository;

@Controller
@RequestMapping("/rooms")
public class RoomController extends SessionedController {

	private static final String ROOMS_VIEW = "rooms";
	private static final String EDIT_ROOM_VIEW = "editRoom";
	private static final String ADD_ROOM_VIEW = "addRoom";
	private static final String MAIN_VIEW = "main";

	private static final String ROOMS_FOR_USER_ATTRIBUTE = "roomsForUser";
	private static final String PLACE_ATTRIBUTE = "place";
	private static final String ROOM_ATTRIBUTE = "room";
	private static final String ALL_ROOMS_ATTRIBUTE = "allRooms";

	@Autowired
	RoomRepository repoRoom;

	@Autowired
	PlaceRepository repoPlace;

	@RequestMapping("")
	public String allRooms(Model model) {

		if (!SessionValidation.isSessionValid()) {
			return MAIN_VIEW;
		}

		model.addAttribute(ALL_ROOMS_ATTRIBUTE, repoRoom.findAll());
		System.out.println(repoRoom.findAll().toString());
		return ROOMS_VIEW;
	}

	@RequestMapping("/forUser")
	public String allRoomsForUser(Model model) {

		if (!SessionValidation.isSessionValid()) {
			return MAIN_VIEW;
		}

		model.addAttribute(ROOMS_VIEW, repoRoom.findAll());
		System.out.println(repoRoom.findAll().toString());
		return ROOMS_FOR_USER_ATTRIBUTE;
	}

	@GetMapping("/add")
	public String addRoom(Model model) {

		if (!SessionValidation.isSessionAdmin()) {
			return MAIN_VIEW;
		}

		Room addedRoom = new Room();
		model.addAttribute(ROOM_ATTRIBUTE, addedRoom);
		return ADD_ROOM_VIEW;
	}

	@PostMapping("/add")
	public String addRoomPost(@Valid Room room, BindingResult result, Model model) {
		if (result.hasErrors()) {
			System.err.println(result);
			return ADD_ROOM_VIEW;
		}
		repoRoom.save(room);
		model.addAttribute(ALL_ROOMS_ATTRIBUTE, repoRoom.findAll());
		model.addAttribute(PLACE_ATTRIBUTE, repoPlace.findAll());
		return ROOMS_VIEW;

	}

	@GetMapping("/{id}/delete")
	public String delRoom(@PathVariable Long id, Model model) {

		if (!SessionValidation.isSessionAdmin()) {
			return MAIN_VIEW;
		}
		repoRoom.deleteById(id);
		return ROOMS_VIEW;
	}

	@GetMapping("/{id}/edit")
	public String editRoom(@PathVariable Long id, Model model) {

		if (!SessionValidation.isSessionAdmin()) {
			return MAIN_VIEW;
		}
		model.addAttribute(ROOM_ATTRIBUTE, repoRoom.findOneById(id));
		return EDIT_ROOM_VIEW;
	}

	@PostMapping("/{id}/edit")
	public String editRoomPost(@Valid Room editedRoom, BindingResult result, Model model) {
		if (result.hasErrors()) {
			System.err.println(result);
			return EDIT_ROOM_VIEW;
		}
		repoRoom.save(editedRoom);
		model.addAttribute(ALL_ROOMS_ATTRIBUTE, repoRoom.findAll());
		return ROOMS_VIEW;

	}

	@ModelAttribute("ourPlaces")
	public List<Place> getPlaces() {
		return this.repoPlace.findAll();
	}

}
