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

import pl.lps.data.ControllerAttributesData;
import pl.lps.data.ControllerData;
import pl.lps.entity.EventType;
import pl.lps.model.SessionValidation;
import pl.lps.model.SessionedController;
import pl.lps.repository.EventTypeRepository;

/**
 * EventTypeController is a class used to map and process all admin requests
 * regarding types of events. All methods are mapped at base "/eventTypes"
 * browser path. This controller returns and feeds event type related views:
 * eventTypes.html (admin's view responsible for display of all types of event),
 * addEventType.html and editEventType.html responsible for adding and editing
 * event types.
 * 
 * @author kaz
 *
 */

@Controller
@RequestMapping("/eventTypes")
public class EventTypeController extends SessionedController {

	/**
	 * Name of model attribute passing selected event type to add event type and
	 * edit event type views.
	 */
	private static final String EVENT_TYPE_ATTRIBUTE = ControllerAttributesData.getEventTypeAttribute();

	/**
	 * Name of model attribute passing list of all event types to particular views.
	 */
	private static final String ALL_EVENT_TYPES_ATTRIBUTE = ControllerAttributesData.getAllEventTypesAttribute();

	/**
	 * Name of edit views (place, room, event type) used to pass information if the
	 * view is used for adding new or editing current instance.
	 */
	private static final String ADD_OR_EDIT_ATTRIBUTE = ControllerAttributesData.getAddOrEditAttribute();
	
	/**
	 * Name of home page view of this application.
	 */
	private static final String MAIN_VIEW = ControllerData.getMainView();
	/**
	 * Name of all event types view used by admin.
	 */
	private static final String EVENT_TYPES_VIEW = ControllerData.getEventTypesView();
	/**
	 * Name of add event type view used by admin.
	 */
	private static final String ADD_EVENT_TYPE_VIEW = ControllerData.getAddEventTypeView();
	/**
	 * Name of edit event type view used by admin.
	 */
	private static final String EDIT_EVENT_TYPE_VIEW = ControllerData.getEditEventTypeView();

	@Autowired
	EventTypeRepository repoEventType;

	/**
	 * Maps admin's request to display all the event types.
	 * 
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 * @return eventTypes.html view
	 */

	@RequestMapping("")
	public String allEventTypes(Model model) {

		if (!SessionValidation.isSessionValid()) {
			return MAIN_VIEW;
		}

		model.addAttribute(ALL_EVENT_TYPES_ATTRIBUTE, repoEventType.findAll());
		System.out.println(repoEventType.findAll().toString());
		model.addAttribute("activeMenuItem", "eventTypes");

		return EVENT_TYPES_VIEW;
	}

	/**
	 * Maps admin's request to add new event type.
	 * 
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 * @return addEventType.html view with a list of all the event types
	 */

	@GetMapping("/add")
	public String addEventType(Model model) {

		if (!SessionValidation.isSessionAdmin()) {
			return MAIN_VIEW;
		}

		EventType eventType = new EventType();
		model.addAttribute(EVENT_TYPE_ATTRIBUTE, eventType);
		model.addAttribute(ADD_OR_EDIT_ATTRIBUTE, "add");
		model.addAttribute("activeMenuItem", "eventTypes");

		return EDIT_EVENT_TYPE_VIEW;
	}

	/**
	 * Maps post request concerning adding a new event type made by admin via input
	 * form on addEventType.html view
	 * 
	 * @param eventType
	 *            - event type instance parameterized in the input form
	 * @param result
	 *            - binding result errors
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 * @return addEventType.html if there are errors in the input form,
	 *         eventTypes.html view with the updated list of all the events types
	 */

	@PostMapping("/add")
	public String addEventTypePost(@Valid EventType eventType, BindingResult result, Model model) {
		if (result.hasErrors()) {
			System.err.println(result);
			return EDIT_EVENT_TYPE_VIEW;
		}
		repoEventType.save(eventType);
		model.addAttribute(ALL_EVENT_TYPES_ATTRIBUTE, repoEventType.findAll());
		model.addAttribute("activeMenuItem", "eventTypes");
		return EVENT_TYPES_VIEW;

	}

	/**
	 * Maps admin request to delete an event type with the selected id.
	 * 
	 * @param id
	 *            - id of the event type to be deleted
	 * 
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 * @return eventTypes.html view with the list of all the event types
	 */

	@GetMapping("/{id}/delete")
	public String delEventType(@PathVariable Long id, Model model) {

		if (!SessionValidation.isSessionAdmin()) {
			return MAIN_VIEW;
		}
		repoEventType.deleteById(id);
		model.addAttribute(ALL_EVENT_TYPES_ATTRIBUTE, repoEventType.findAll());
		model.addAttribute("activeMenuItem", "eventTypes");
		return EVENT_TYPES_VIEW;
	}

	/**
	 * Maps admin request to edit an event type with the selected id.
	 * 
	 * @param id
	 *            - id of the event type to be edited
	 * 
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 * @return eventTypes.html view with the list of all the event types
	 */

	@GetMapping("/{id}/edit")
	public String editEventType(@PathVariable Long id, Model model) {

		if (!SessionValidation.isSessionAdmin()) {
			return MAIN_VIEW;
		}
		model.addAttribute(EVENT_TYPE_ATTRIBUTE, repoEventType.findOneById(id));
		model.addAttribute(ADD_OR_EDIT_ATTRIBUTE, "edit");
		model.addAttribute("activeMenuItem", "eventTypes");
		return EDIT_EVENT_TYPE_VIEW;
	}

	/**
	 * Maps admin's post request concerning editing a selected event type via input
	 * form on edit EventType.html view
	 * 
	 * @param eventType
	 *            - event type instance parameterized in the input form
	 * @param result
	 *            - binding result errors
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 * @return editEventType.html if there are errors in the input form,
	 *         eventTypes.html view with the updated list of all the events types
	 */

	@PostMapping("/{id}/edit")
	public String editEventTypePost(@Valid EventType eventType, BindingResult result, Model model) {
		if (result.hasErrors()) {
			System.err.println(result);
			return EDIT_EVENT_TYPE_VIEW;
		}
		repoEventType.save(eventType);
		model.addAttribute(ALL_EVENT_TYPES_ATTRIBUTE, repoEventType.findAll());
		model.addAttribute("activeMenuItem", "eventTypes");
		return EVENT_TYPES_VIEW;

	}

}
