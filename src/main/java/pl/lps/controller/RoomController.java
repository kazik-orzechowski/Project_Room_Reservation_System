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

	@Autowired
	RoomRepository repoRoom;

	@Autowired
	PlaceRepository repoPlace;
	
	@RequestMapping("")
	public String allRooms(Model model) {
		
		if(!SessionValidation.isSessionValid()) {
			return "main";
		}
		
		model.addAttribute("rooms", repoRoom.findAll());
		System.out.println(repoRoom.findAll().toString());
		return "rooms";
	}

	@RequestMapping("/forUser")
	public String allRoomsForUser(Model model) {
		
		if(!SessionValidation.isSessionValid()) {
			return "main";
		}
		
		model.addAttribute("rooms", repoRoom.findAll());
		System.out.println(repoRoom.findAll().toString());
		return "roomsForUser";
	}
	
	@GetMapping("/add")
	public String addRoom(Model model) {
		
		if(!SessionValidation.isSessionAdmin()) {
			return "main";
		}
		
		Room room = new Room();
		model.addAttribute("room", room);
		return "addRoom";
	}

	@PostMapping("/add")
	public String addRoomPost(@Valid Room room, BindingResult result, Model model) {
		if (result.hasErrors()) {
			System.err.println(result);
			return "addRoom";
		}
		repoRoom.save(room);
		model.addAttribute("rooms", repoRoom.findAll());
		model.addAttribute("place", repoPlace.findAll());
		return "rooms";

	}

	@GetMapping("/{id}/delete")
	public String delRoom(@PathVariable Long id, Model model) {
		
		if(!SessionValidation.isSessionAdmin()) {
			return "main";
		}
		repoRoom.deleteById(id);
		return "rooms";
	}

	@GetMapping("/{id}/edit")
	public String editRoom(@PathVariable Long id, Model model) {
		
		if(!SessionValidation.isSessionAdmin()) {
			return "main";
		}
		model.addAttribute("room", repoRoom.findOneById(id));
		return "editRoom";
	}

	@PostMapping("/{id}/edit")
	public String editRoomPost(@Valid Room room, BindingResult result, Model model) {
		if (result.hasErrors()) {
			System.err.println(result);
			return "editRoom";
		}
		repoRoom.save(room);
		model.addAttribute("rooms", repoRoom.findAll());
		return "rooms"; // room.toString();

	}

	@ModelAttribute ("ourPlaces")
	public List<Place> getPlaces(){
		List<Place> places = this.repoPlace.findAll();
		return places;
	}	
	
}
