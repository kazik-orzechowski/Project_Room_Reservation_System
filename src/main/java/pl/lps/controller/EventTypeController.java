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

import pl.lps.data.ControllerData;
import pl.lps.entity.EventType;
import pl.lps.repository.EventTypeRepository;

@Controller
@RequestMapping("/eventTypes")
public class EventTypeController extends SessionedController {

	
	/**
	 * Name of model attribute passing selected event type to add event type and edit event type views.
	 */
	private static final String EVENT_TYPE_ATTRIBUTE = "eventTypes";
	
	/**
	 * Name of model attribute passing list of all event types to particular views.
	 */
	private static final String ALL_EVENT_TYPES_ATTRIBUTE = "allEventTypes";
	/**
	 * Passes the name of home page view of this application.
	 */
	private static final String MAIN_VIEW = ControllerData.getMainView();	
	/**
	 * Passes the name of all event types view used by admin.
	 */
	private static final String EVENT_TYPES_VIEW = ControllerData.getEventTypesView();
	/**
	 * Passes the name of add event type view used by admin.
	 */
	private static final String ADD_EVENT_TYPE_VIEW = ControllerData.getAddEventTypeView();
	/**
	 * Passes the name of edit event type view used by admin.
	 */
	private static final String EDIT_EVENT_TYPE_VIEW = ControllerData.getEditEventTypeView();
	
	@Autowired
	EventTypeRepository repoEventType;

	@RequestMapping("")
	public String allEventTypes(Model model) {
		
		if(!SessionValidation.isSessionValid()) {
			return MAIN_VIEW;
		}
		
		model.addAttribute(ALL_EVENT_TYPES_ATTRIBUTE, repoEventType.findAll());
		System.out.println(repoEventType.findAll().toString());
		return EVENT_TYPES_VIEW;
	}

	@GetMapping("/add")
	public String addEventType(Model model) {
		
		if(!SessionValidation.isSessionAdmin()) {
			return MAIN_VIEW;
		}
		
		EventType eventType = new EventType();
		model.addAttribute(EVENT_TYPE_ATTRIBUTE, eventType);
		return ADD_EVENT_TYPE_VIEW;
	}

	@PostMapping("/add")
	public String addEventTypePost(@Valid EventType eventType, BindingResult result, Model model) {
		if (result.hasErrors()) {
			System.err.println(result);
			return ADD_EVENT_TYPE_VIEW;
		}
		repoEventType.save(eventType);
		model.addAttribute(ALL_EVENT_TYPES_ATTRIBUTE, repoEventType.findAll());
		return EVENT_TYPES_VIEW;

	}

	@GetMapping("/{id}/delete")
	public String delEventType(@PathVariable Long id, Model model) {
		
		if(!SessionValidation.isSessionAdmin()) {
			return MAIN_VIEW;
		}
		repoEventType.deleteById(id);
		return "redirect: /"+EVENT_TYPES_VIEW;
	}

	@GetMapping("/{id}/edit")
	public String editEventType(@PathVariable Long id, Model model) {
		
		if(!SessionValidation.isSessionAdmin()) {
			return MAIN_VIEW;
		}
		model.addAttribute(EVENT_TYPE_ATTRIBUTE, repoEventType.findOneById(id));
		return EDIT_EVENT_TYPE_VIEW;
	}

	@PostMapping("/{id}/edit")
	public String editEventTypePost(@Valid EventType eventType, BindingResult result, Model model) {
		if (result.hasErrors()) {
			System.err.println(result);
			return EDIT_EVENT_TYPE_VIEW;
		}
		repoEventType.save(eventType);
		model.addAttribute(ALL_EVENT_TYPES_ATTRIBUTE, repoEventType.findAll());
		return EVENT_TYPES_VIEW;

	}

}
