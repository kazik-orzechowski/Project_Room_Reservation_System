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
import pl.lps.entity.User;
import pl.lps.repository.EventRepository;
import pl.lps.repository.EventTypeRepository;
import pl.lps.repository.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController extends SessionedController {

	/**
	 * Passes the name of home page of this application.
	 */
	private static final String MAIN_VIEW = ControllerData.getMainView();

	
	private static final String USER_ATTRIBUTE = "user";
	
	private static final String ALL_USERS_ATTRIBUTE = "allUsers";
	
	private static final String ALL_EVENTS_ATTRIBUTE = "allEvents";


	private static final String USERS_VIEW = ControllerData.getUsersView();


	private static final String EVENTS_VIEW = ControllerData.getEventsView();


	private static final String EDIT_USER_VIEW = ControllerData.getEditUserView();


	private static final String USER_PANEL_VIEW = ControllerData.getUserPanelView();


	private static final String ADMIN_PANEL_VIEW = ControllerData.getAdminPanelView();


	private static final String SIGNUP_VIEW = ControllerData.getSignupView();
	
	@Autowired
	UserRepository repoUser;

	@Autowired
	EventRepository repoEvent;

	@Autowired
	EventTypeRepository repoEventType;

	@RequestMapping("/adminPanel")
	public String adminPanel(Model model) {

		if (!SessionValidation.isSessionAdmin()) {
			return MAIN_VIEW;
		}

		return ADMIN_PANEL_VIEW;
	}

	@RequestMapping("")
	public String allUsers(Model model) {

		if (!SessionValidation.isSessionValid()) {
			return MAIN_VIEW;
		}

		model.addAttribute(ALL_USERS_ATTRIBUTE, repoUser.findAll());
		System.out.println(repoUser.findAll().toString());
		return USERS_VIEW;
	}

	@GetMapping("/{id}/delete")
	public String delUser(@PathVariable Long id, Model model) {

		if (!SessionValidation.isSessionAdmin()) {
			return MAIN_VIEW;
		}
		repoUser.deleteById(id);
		return "redirect: /" + USERS_VIEW;
	}

	@GetMapping("/{id}/edit")
	public String editUser(@PathVariable Long id, Model model) {

		if (!SessionValidation.isSessionAdmin()) {
			return MAIN_VIEW;
		}

		model.addAttribute(USER_ATTRIBUTE, repoUser.findOneById(id));

		if (repoUser.findOneById(id).getUserName().equals("admin")) {
			model.addAttribute("returnUrl", "/" + USERS_VIEW);
		} else {
			model.addAttribute("returnUrl", "/" + EVENTS_VIEW + "/" + id);
		}

		return EDIT_USER_VIEW;
	}

	@PostMapping("/{id}/edit")
	public String editUserPost(@Valid User editedUser, @PathVariable Long id, BindingResult result, Model model) {
		if (result.hasErrors()) {
			System.out.println(result);
			return EDIT_USER_VIEW;
		}
		repoUser.save(editedUser);

		model.addAttribute("eventType", repoEventType.findAll());

		if (repoUser.findOneById(id).getUserName().equals("admin")) {
			model.addAttribute(ALL_EVENTS_ATTRIBUTE, repoEvent.findAll());
			return USERS_VIEW;
		} else {
			model.addAttribute(ALL_EVENTS_ATTRIBUTE, repoEvent.findAllBySeriesUserId(id));
			return USER_PANEL_VIEW;
		}

	}

	@GetMapping("/add")
	public String addUser(Model model) {

		if (!SessionValidation.isSessionAdmin()) {
			return MAIN_VIEW;
		}

		User addedUser = new User();
		model.addAttribute(USER_ATTRIBUTE, addedUser);
		return SIGNUP_VIEW;
	}

	@PostMapping("/add")
	public String addUserPost(@Valid User addedUser, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return SIGNUP_VIEW;
		}
		repoUser.save(addedUser);
		model.addAttribute(USER_ATTRIBUTE, addedUser);
		model.addAttribute(ALL_USERS_ATTRIBUTE, repoUser.findAll());
		return USERS_VIEW;

	}

}
