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
public class PlaceController {

	@Autowired
	PlaceRepository repoPlace;

	@RequestMapping("")
	public String allPlaces(Model model) {
		model.addAttribute("places", repoPlace.findAll());
		System.out.println(repoPlace.findAll().toString());
		return "places";
	}

	@GetMapping("/add")
	public String addPlace(Model model) {
		Place place = new Place();
		model.addAttribute("place", place);
		return "addPlace";
	}

	@PostMapping("/add")
	public String addPlacePost(@Valid Place place, BindingResult result, Model model) {
		if (result.hasErrors()) {
			System.err.println(result);
			return "addPlace";
		}
		repoPlace.save(place);
		model.addAttribute("places", repoPlace.findAll());
		return "places";

	}

	@GetMapping("/{id}/delete")
	public String delPlace(@PathVariable Long id, Model model) {
		repoPlace.deleteById(id);
		return "redirect: /places";
	}

	@GetMapping("/{id}/edit")
	public String editPlace(@PathVariable Long id, Model model) {
		model.addAttribute("place", repoPlace.findOneById(id));
		return "editPlace";
	}

	@PostMapping("/{id}/edit")
	public String editPlacePost(@Valid Place place, BindingResult result, Model model) {
		if (result.hasErrors()) {
			System.err.println(result);
			return "editPlace";
		}
		repoPlace.save(place);
		model.addAttribute("places", repoPlace.findAll());
		return "places"; // place.toString();

	}

}
