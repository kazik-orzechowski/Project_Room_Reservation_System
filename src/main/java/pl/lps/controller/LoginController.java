package pl.lps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.lps.data.ControllerAttributesData;
import pl.lps.data.ControllerData;
import pl.lps.entity.User;
import pl.lps.model.LoginData;
import pl.lps.model.SessionedController;
import pl.lps.repository.EventRepository;
import pl.lps.repository.EventTypeRepository;
import pl.lps.repository.UserRepository;

/**
 * LoginController is a controller class used to map and process users requests
 * regarding logging in to this application. Login data (User class object) are
 * kept in user's session.
 * 
 * @author kaz
 *
 */

@Controller
@RequestMapping("/login")
public class LoginController extends SessionedController {

	/**
	 * Name of model attribute passing an information to the user panel view
	 * regarding the current series (all or name} being displayed
	 */

	private static final String SERIES_DISPLAYED_INFO_ATTRIBUTE = ControllerAttributesData.getSeriesDisplayedInfoAttribute();

	/**
	 * Id of the series that should be displayed (0 for all series)
	 */
	private static final String SERIES_DISPLAYED_ATTRIBUTE = ControllerAttributesData.getSeriesDisplayedAttribute();


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
	/**
	 * Name of model attribute passing user name to login view.
	 */
	private static final String LOGIN_USERNAME_ATTRIBUTE = ControllerAttributesData.getLoginUsernameAttribute();
	/**
	 * Name of model attribute passing login message to login view.
	 */
	private static final String LOGIN_MESSAGE_ATTRIBUTE = ControllerAttributesData.getLoginMessageAttribute();
	/**
	 * Name of model attribute passing user as User class object to login view.
	 */
	private static final String LOGIN_USER_ATTRIBUTE = ControllerAttributesData.getLoginUserAttribute();
	/**
	 * Name of model attribute passing result of add / edit event to event views.
	 */

	protected static final String ADD_EVENT_INFO_ATTRIBUTE = ControllerAttributesData.getAddEventInfoAttribute();

	
	/**
	 * Passes the name of home page of this application.
	 */
	private static final String MAIN_VIEW = ControllerData.getMainView();
	/**
	 * Passes the name of login view of this application.
	 */
	private static final String LOGIN_VIEW = ControllerData.getLoginView();
	/**
	 * Passes the name of login view of this application.
	 */
	private static final String ADMIN_PANEL_VIEW = ControllerData.getAdminPanelView();
	/**
	 * Passes the name of user panel view.
	 */
	private static final String USER_PANEL_VIEW = ControllerData.getUserPanelView();

	
	@Autowired
	UserRepository repoUser;

	@Autowired
	EventRepository repoEvent;

	@Autowired
	EventTypeRepository repoEventType;

	/**
	 * Maps user request to log in this application.
	 * 
	 * @param model
	 *            - instance of Model class used to pass attribute LoginData class
	 *            object used for password verification to the login view
	 * @return login.html view
	 */

	@GetMapping("")
	public String login(Model model) {
		LoginData loginData = new LoginData();
		model.addAttribute(LOGIN_USER_ATTRIBUTE, loginData);

		return LOGIN_VIEW;
	}

	/**
	 * Maps post request concerning adding of new event made by user via input form
	 * on addEvent.html view. Verifies if user login and password are correct and
	 * sets logged in user as session attribute.
	 * 
	 * @param userData
	 *            - binded LoginData object
	 * @param model
	 *            - instance of Model class used to pass message and user name
	 *            attributes to the target views
	 * @return login.html view if entered login data (user name and password) does
	 *         not match these parameters in this application's database
	 */
	@PostMapping("")
	public String loginPost(LoginData userData, Model model) {
		User user = this.repoUser.findByUserName(userData.getLogin());
		if (user == null || !user.isPasswordCorrect(userData.getPassword())) {
			model.addAttribute(LOGIN_MESSAGE_ATTRIBUTE, "Wprowadź prawidłowe dane");
			model.addAttribute(LOGIN_USERNAME_ATTRIBUTE, "null");
			return LOGIN_VIEW;
		}

		if (user.isPasswordCorrect(userData.getPassword())) {
			if (user.isEnabled() == false) {
				model.addAttribute(LOGIN_MESSAGE_ATTRIBUTE, "Użytkownik jest nieaktywny, skontaktuj się z administratorem");
				model.addAttribute(LOGIN_USERNAME_ATTRIBUTE, user.getUserName());
				return LOGIN_VIEW;
			} else {
			session().setAttribute(LOGIN_USER_ATTRIBUTE, user);
			} 
		}
			
		if (user.getUserName().equals("admin")) {
			model.addAttribute(LOGIN_USER_ATTRIBUTE, user);
			model.addAttribute("activeMenuItem", "home");
			return ADMIN_PANEL_VIEW;
		} else {
			model.addAttribute("eventType", repoEventType.findAll());
			model.addAttribute(LOGIN_USER_ATTRIBUTE, user);
			model.addAttribute(USER_PANEL_EVENTS_ATTRIBUTE, repoEvent.findAllBySeriesUserId(user.getId()));
			model.addAttribute(SERIES_DISPLAYED_INFO_ATTRIBUTE, " - wszystkie serie");
			model.addAttribute(REQUESTED_EVENT_SERIES_ATTRIBUTE, "null");
			model.addAttribute(ADD_EVENT_INFO_ATTRIBUTE, "null");

			// 0 for all series to be displayed
			model.addAttribute(SERIES_DISPLAYED_ATTRIBUTE, 0);
			model.addAttribute("activeMenuItem", "home");

			return USER_PANEL_VIEW;
		}
	}

}
