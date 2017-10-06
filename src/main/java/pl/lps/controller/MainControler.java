package pl.lps.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.lps.data.ControllerAttributesData;
import pl.lps.data.ControllerData;
import pl.lps.model.LoginData;

/**
 * LoginController is a controller class used to map and process user requests
 * regarding browsing sign up and log in views. This controller services logout
 * and invalid session events to redirect user to tha main.html home page view
 * of this application.
 * 
 * @author kaz
 *
 */
@Controller
@RequestMapping("main")

public class MainControler extends SessionedController {

	/**
	 * Passes the name of home page of this application.
	 */
	private static final String MAIN_VIEW = ControllerData.getMainView();

	/**
	 * Defines the name of user name attribute used in home page view.
	 */
	private static final String MAIN_USERNAME_ATTRIBUTE = ControllerAttributesData.getMainUsernameAttribute();
	/**
	 * Defines the name of message attribute used in home page view.
	 */
	private static final String MAIN_MESSAGE_ATTRIBUTE = ControllerAttributesData.getMainMessageAttribute();

	/**
	 * Name of User class user attribute used in session.
	 */
	private static final String SESSION_USER_ATTRIBUTE = ControllerAttributesData.getSessionUserAttribute();

	
	/**
	 * Maps user request to get to the home page of the application.
	 * 
	 * @param model
	 * @return home page view - main.html
	 */
	@GetMapping("")
	public String home(Model model) {
		LoginData loginData = (LoginData) session().getAttribute(SESSION_USER_ATTRIBUTE );

		if (session().getAttribute(SESSION_USER_ATTRIBUTE ) != null) {
			model.addAttribute(MAIN_MESSAGE_ATTRIBUTE, "#{user.logged.in}");
			model.addAttribute(MAIN_USERNAME_ATTRIBUTE, " " + loginData.getLogin());
		}

		model.addAttribute(MAIN_MESSAGE_ATTRIBUTE, "");
		model.addAttribute(MAIN_USERNAME_ATTRIBUTE, "");

		return MAIN_VIEW;
	}
}
