package pl.lps.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.lps.data.ControllerAttributesData;
import pl.lps.data.ControllerData;

@Controller
@RequestMapping("/logout")
public class LogoutController extends SessionedController {
	/**
	 * Name of String class message attribute  used in the home page view.
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
	
	@GetMapping("")
	public String logout(Model model) {
		session().setAttribute(SESSION_USER_ATTRIBUTE, null);
		model.addAttribute(MAIN_MESSAGE_ATTRIBUTE, "Wylogowano ");
		model.addAttribute(MAIN_USERNAME_ATTRIBUTE, "");
		return MAIN_VIEW;
	}

}
