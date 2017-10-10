package pl.lps.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.lps.data.ControllerAttributesData;
import pl.lps.data.ControllerData;
import pl.lps.entity.User;
import pl.lps.model.SessionedController;

/**
 * LogoutController is a controller class used to map and process users requests
 * regarding logging out from this application.
 * 
 * @author kaz
 *
 */

@Controller
@RequestMapping("/logout")
public class LogoutController extends SessionedController {
	/**
	 * Name of String class message attribute used in the home page view.
	 */
	private static final String MAIN_MESSAGE_ATTRIBUTE = ControllerAttributesData.getMainMessageAttribute();
	/**
	 * Name of User class user attribute used in session.
	 */
	private static final String SESSION_USER_ATTRIBUTE = ControllerAttributesData.getSessionUserAttribute();
	/**
	 * Name of the String class attribute used in this application's home page.
	 */
	private static final String MAIN_USERNAME_ATTRIBUTE = ControllerAttributesData.getMainUsernameAttribute();
	/**
	 * Passes the name of home page of this application.
	 */
	private static final String MAIN_VIEW = ControllerData.getMainView();

	/**
	 * Maps user request to log out.
	 * 
	 * @param model
	 *            - instance of Model class used to pass message and user name
	 *            attributes to the target views
	 * @return - home page of this application, i.e. main.html view
	 */

	@GetMapping("")
	public String logout(Model model) {

		User u = (User) session().getAttribute(SESSION_USER_ATTRIBUTE);
		session().setAttribute(SESSION_USER_ATTRIBUTE, null);

		model.addAttribute(MAIN_MESSAGE_ATTRIBUTE, "page.main.logout");
		model.addAttribute(MAIN_USERNAME_ATTRIBUTE, " " + u.getUserName());
		
		return MAIN_VIEW;
	}

}
