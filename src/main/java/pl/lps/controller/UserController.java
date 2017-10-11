
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
import pl.lps.entity.User;
import pl.lps.model.SessionValidation;
import pl.lps.model.SessionedController;
import pl.lps.repository.EventRepository;
import pl.lps.repository.EventTypeRepository;
import pl.lps.repository.UserRepository;

/**
 * UserController is a class used to map and process all admin and user requests
 * regarding users. All methods are mapped at base "/users" browser path. This
 * controller returns and feeds admin panel view and all user object related
 * views: users.html (admin's view responsible for display of all users), as
 * well as admin's and user's view editUser.html responsible for editing user.
 * 
 * @author kaz
 *
 */

@Controller
@RequestMapping("/users")
public class UserController extends SessionedController {

	/**
	 * Passes the name of home page of this application.
	 */
	private static final String MAIN_VIEW = ControllerData.getMainView();

	/**
	 * Id of the series that should be displayed (0 for all series)
	 */
	private static final String SERIES_DISPLAYED_ATTRIBUTE = ControllerAttributesData.getSeriesDisplayedAttribute();

	/**
	 * Name of model attribute passing an information to the user panel view
	 * regarding the current series (all or name} being displayed
	 */
	private static final String SERIES_DISPLAYED_INFO_ATTRIBUTE = ControllerAttributesData
			.getSeriesDisplayedInfoAttribute();

	/**
	 * Name of model attribute passing result of add / edit event to event views.
	 */

	/**
	 * Name of model attribute passing user as User class object to login view.
	 */
	private static final String LOGIN_USER_ATTRIBUTE = ControllerAttributesData.getLoginUserAttribute();

	protected static final String ADD_EVENT_INFO_ATTRIBUTE = ControllerAttributesData.getAddEventInfoAttribute();

	private static final String USER_ATTRIBUTE = ControllerAttributesData.getUserAttribute();

	private static final String ALL_USERS_ATTRIBUTE = ControllerAttributesData.getAllUsersAttribute();

	private static final String ALL_EVENTS_ATTRIBUTE = ControllerAttributesData.getAllEventsAttribute();

	private static final String USERS_VIEW = ControllerData.getUsersView();

	private static final String EVENTS_VIEW = ControllerData.getEventsView();

	private static final String EDIT_USER_VIEW = ControllerData.getEditUserView();

	private static final String USER_PANEL_VIEW = ControllerData.getUserPanelView();

	private static final String ADMIN_PANEL_VIEW = ControllerData.getAdminPanelView();

	private static final String SIGNUP_VIEW = ControllerData.getSignupView();

	/**
	 * Name of model attribute passing an to the user panel view information
	 * regarding the series of events added to the event repository.
	 */
	private static final String REQUESTED_EVENT_SERIES_ATTRIBUTE = ControllerAttributesData
			.getRequestedEventSeriesAttribute();

	/**
	 * Name of model attribute passing a list of all or selected events to user
	 * panel view.
	 */
	private static final String USER_PANEL_EVENTS_ATTRIBUTE = ControllerAttributesData.getUserPanelEventsAttribute();

	@Autowired
	UserRepository repoUser;

	@Autowired
	EventRepository repoEvent;

	@Autowired
	EventTypeRepository repoEventType;

	/**
	 * Maps admin request to display admin panel.
	 * 
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 * @return adminPanel.html view.
	 */

	@RequestMapping("/adminPanel")
	public String adminPanel(Model model) {

		if (!SessionValidation.isSessionAdmin()) {
			return MAIN_VIEW;
		}
		model.addAttribute(USER_ATTRIBUTE, repoUser.findOneByUserName("admin"));
		model.addAttribute("activeMenuItem", "home");

		return ADMIN_PANEL_VIEW;
	}

	/**
	 * Maps admin's request to display list of all users.
	 * 
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 * @return users.html view
	 */

	@RequestMapping("")
	public String allUsers(Model model) {

		if (!SessionValidation.isSessionValid()) {
			return MAIN_VIEW;
		}

		model.addAttribute(ALL_USERS_ATTRIBUTE, repoUser.findAll());
		System.out.println(repoUser.findAll().toString());
		model.addAttribute("activeMenuItem", "users");
		return USERS_VIEW;
	}

	/**
	 * Maps admin's request to delete a user.
	 * 
	 * @param id
	 *            - id of user to be deleted
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 * @return users.html view
	 */
	@GetMapping("/{id}/delete")
	public String delUser(@PathVariable Long id, Model model) {

		if (!SessionValidation.isSessionAdmin()) {
			return MAIN_VIEW;
		}
		repoUser.deleteById(id);
		model.addAttribute(ALL_USERS_ATTRIBUTE, repoUser.findAll());
		model.addAttribute("activeMenuItem", "users");

		return USERS_VIEW;
	}

	/**
	 * Maps admin's and user's request to edit a user.
	 * 
	 * @param id
	 *            - id of user to be edited
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 * @return editUser.html view
	 */

	@GetMapping("/{id}/edit")
	public String editUser(@PathVariable Long id, Model model) {

		if (!SessionValidation.isSessionValid()) {
			return MAIN_VIEW;
		}

		model.addAttribute(USER_ATTRIBUTE, repoUser.findOneById(id));

		if (repoUser.findOneById(id).getUserName().equals("admin")) {
			model.addAttribute("returnUrl", "/" + USERS_VIEW);
			model.addAttribute("activeMenuItem", "users");
		} else {
			model.addAttribute("returnUrl", "/" + EVENTS_VIEW + "/" + id);
		}
		model.addAttribute("activeMenuItem", "editUser");
System.err.println("before post" + repoUser.findOneById(id).getPassword());
		return EDIT_USER_VIEW;
	}

	/**
	 * Maps post request concerning editing of user made by admin or user via input
	 * form on editUser.html view
	 * 
	 * @param id
	 *            - id of user to be edited
	 * @param result
	 *            - binding result errors
	 * 
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 * @return editUser.html if there are errors in the input form, users.html view
	 *         for admin and userPanel.html view for all other users
	 */

	@PostMapping("/{id}/edit")
	public String editUserPost(@Valid User editedUser, @PathVariable Long id, BindingResult result, Model model) {
		User user = (User) session().getAttribute(LOGIN_USER_ATTRIBUTE);

		if (result.hasErrors() && !user.isPasswordCorrect(editedUser.getPassword())) {
			return EDIT_USER_VIEW;
		}
		// Sets the original password back for edited user. Password in the editedUser
		// binded object for admin's edition is admin's password

System.err.println("before change" + editedUser.getPassword());

		editedUser.passHashedPassword(repoUser.findOneById(editedUser.getId()).getPassword());

System.err.println("after change" + editedUser.getPassword());		
		
		repoUser.save(editedUser);

		if (user.getUserName().equals("admin")) {
			model.addAttribute(ALL_USERS_ATTRIBUTE, repoUser.findAll());
			model.addAttribute("activeMenuItem", "users");
			return USERS_VIEW;
		} else {
			model.addAttribute(ALL_EVENTS_ATTRIBUTE, repoEvent.findAllBySeriesUserId(id));
			model.addAttribute(USER_ATTRIBUTE, repoUser.findOneById(id));
			model.addAttribute(SERIES_DISPLAYED_INFO_ATTRIBUTE, " - wszystkie serie");

			model.addAttribute("eventType", repoEventType.findAll());
			model.addAttribute(USER_PANEL_EVENTS_ATTRIBUTE, repoEvent.findAllBySeriesUserId(id));
			model.addAttribute(REQUESTED_EVENT_SERIES_ATTRIBUTE, "null");
			model.addAttribute(ADD_EVENT_INFO_ATTRIBUTE, "null");
			model.addAttribute("displayedSeriesId", 0);
			model.addAttribute("activeMenuItem", "home");

			return USER_PANEL_VIEW;
		}

	}

	/**
	 * Maps admin's request to add a new user.
	 * 
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 * @return signup.html view
	 */

	@GetMapping("/add")
	public String addUser(Model model) {

		if (!SessionValidation.isSessionAdmin()) {
			return MAIN_VIEW;
		}

		User addedUser = new User();
		model.addAttribute(USER_ATTRIBUTE, addedUser);
		return SIGNUP_VIEW;
	}

	/**
	 * Maps post request concerning adding of new user made by admin via input form
	 * on addUser.html view
	 * 
	 * @param id
	 *            - id of user to be added
	 * @param result
	 *            - binding result errors
	 * 
	 * @param model
	 *            - instance of Model class used to pass attributes to the views
	 * @return users.html view
	 */

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
