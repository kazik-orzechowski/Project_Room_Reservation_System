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

import pl.lps.entity.EventType;
import pl.lps.repository.EventTypeRepository;

@Controller
@RequestMapping("/eventTypes")
public class EventTypeController {

	@Autowired
	EventTypeRepository repoEventType;

	@RequestMapping("")
	public String allEventTypes(Model model) {
		model.addAttribute("eventTypes", repoEventType.findAll());
		System.out.println(repoEventType.findAll().toString());
		return "eventTypes";
	}

	@GetMapping("/add")
	public String addEventType(Model model) {
		EventType eventType = new EventType();
		model.addAttribute("eventType", eventType);
		return "addEventType";
	}

	@PostMapping("/add")
	public String addEventTypePost(@Valid EventType eventType, BindingResult result, Model model) {
		if (result.hasErrors()) {
			System.err.println(result);
			return "addEventType";
		}
		repoEventType.save(eventType);
		model.addAttribute("eventTypes", repoEventType.findAll());
		return "eventTypes";

	}

	@GetMapping("/{id}/delete")
	public String delEventType(@PathVariable Long id, Model model) {
		repoEventType.deleteById(id);
		return "redirect: /eventTypes";
	}

	@GetMapping("/{id}/edit")
	public String editEventType(@PathVariable Long id, Model model) {
		model.addAttribute("eventType", repoEventType.findOneById(id));
		return "editEventType";
	}

	@PostMapping("/{id}/edit")
	public String editEventTypePost(@Valid EventType eventType, BindingResult result, Model model) {
		if (result.hasErrors()) {
			System.err.println(result);
			return "editEventType";
		}
		repoEventType.save(eventType);
		model.addAttribute("eventTypes", repoEventType.findAll());
		return "eventTypes"; // eventType.toString();

	}

}
