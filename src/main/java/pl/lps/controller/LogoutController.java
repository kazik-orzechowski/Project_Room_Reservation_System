package pl.lps.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/logout")
public class LogoutController extends SessionedController {
	
	private static final String MAIN_MESSAGE_ATTRIBUTE = "message";
	private static final String MAIN_USER_ATTRIBUTE = "user";
	private static final String MAIN_USERNAME_ATTRIBUTE = "username";
	private static final String MAIN_VIEW = "main";

	@GetMapping("")
	public String logout(Model model) {
		session().setAttribute(MAIN_USER_ATTRIBUTE, null);
		model.addAttribute(MAIN_MESSAGE_ATTRIBUTE, "Wylogowano ");
		model.addAttribute(MAIN_USERNAME_ATTRIBUTE, "");
		return MAIN_VIEW;
	}

}
