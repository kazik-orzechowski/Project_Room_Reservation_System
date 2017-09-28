package pl.lps.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.lps.entity.Place;
import pl.lps.repository.PlaceRepository;

@Controller
@RequestMapping("/places")
public class PlaceController extends SessionedController {

	private static final String EDIT_PLACE_VIEW = "editPlace";
	private static final String ADD_PLACE_VIEW = "addPlace";
	private static final String MAIN_VIEW = "main";
	private static final String PLACES_VIEW = "places";
	private static final String ALL_PLACES_ATTRIBUTE = "allPlaces";
	private static final String PLACE_ATTRIBUTE = "place";
	@Autowired
	PlaceRepository repoPlace;

	@RequestMapping("")
	public String allPlaces(Model model) {
		
		if(!SessionValidation.isSessionValid()) {
			return MAIN_VIEW;
		}
		
		model.addAttribute(ALL_PLACES_ATTRIBUTE, repoPlace.findAll());
		System.out.println(repoPlace.findAll().toString());
		return PLACES_VIEW;
	}

	@GetMapping("/add")
	public String addPlace(Model model) {
		
		if(!SessionValidation.isSessionAdmin()) {
			return MAIN_VIEW;
		}
		
		Place place = new Place();
		model.addAttribute(PLACE_ATTRIBUTE, place);
		return ADD_PLACE_VIEW;
	}

	@PostMapping("/add")
	public String addPlacePost(@Valid Place addedPlace, BindingResult result, Model model) {
		if (result.hasErrors()) {
			System.err.println(result);
			return ADD_PLACE_VIEW;
		}
		repoPlace.save(addedPlace);
		model.addAttribute(ALL_PLACES_ATTRIBUTE, repoPlace.findAll());
		return PLACES_VIEW;

	}

	@GetMapping("/{id}/delete")
	public String delPlace(@PathVariable Long id, Model model) {
		
		if(!SessionValidation.isSessionAdmin()) {
			return MAIN_VIEW;
		}
		
		repoPlace.deleteById(id);
		return "redirect: /"+PLACES_VIEW;
	}

	@GetMapping("/{id}/edit")
	public String editPlace(@PathVariable Long id, Model model) {
		
		if(!SessionValidation.isSessionAdmin()) {
			return MAIN_VIEW;
		}
		
		model.addAttribute(PLACE_ATTRIBUTE, repoPlace.findOneById(id));
		return EDIT_PLACE_VIEW;
	}

	
	@PostMapping("/{id}/edit")
	public String editPlacePost(@Valid Place editedPlace, BindingResult result, Model model) {
		if (result.hasErrors()) {
			System.err.println(result);
			return EDIT_PLACE_VIEW;
		}
		repoPlace.save(editedPlace);
		model.addAttribute(ALL_PLACES_ATTRIBUTE, repoPlace.findAll());
		return PLACES_VIEW;

	}

}
