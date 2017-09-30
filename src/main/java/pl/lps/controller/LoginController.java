package pl.lps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.lps.data.ControllerData;
import pl.lps.entity.User;
import pl.lps.model.LoginData;
import pl.lps.repository.EventRepository;
import pl.lps.repository.EventTypeRepository;
import pl.lps.repository.UserRepository;

@Controller
@RequestMapping("/login")
public class LoginController extends SessionedController implements ControllerData {

	private static final String USER_PANEL_EVENTS_ATTRIBUTE = "allEvents";
	private static final String LOGIN_USERNAME_ATTRIBUTE = "username";
	private static final String LOGIN_MESSAGE_ATTRIBUTE = "message";
	private static final String LOGIN_USER_ATTRIBUTE = "user";

	@Autowired
	UserRepository repoUser;

	@Autowired
	EventRepository repoEvent;

	@Autowired
	EventTypeRepository repoEventType;

	@GetMapping("")
	public String login(Model m) {
		LoginData loginData = new LoginData();
		m.addAttribute(LOGIN_USER_ATTRIBUTE, loginData);

		return LOGIN_VIEW;
	}
	

	@PostMapping("")
	public String loginPost(LoginData user, Model model) {
		User u = this.repoUser.findByUserName(user.getLogin());
		if (u == null) {
			model.addAttribute(LOGIN_MESSAGE_ATTRIBUTE, "Wprowadź prawidłowe dane");
			model.addAttribute(LOGIN_USERNAME_ATTRIBUTE, "");
			return LOGIN_VIEW;
		}

		if (u.isPasswordCorrect(user.getPassword())) {
			session().setAttribute(LOGIN_USER_ATTRIBUTE, u);
		}

		if (u.getUserName().equals("admin")) {
			return ADMIN_PANEL_VIEW;
		} else {
			model.addAttribute("eventType", repoEventType.findAll());
			model.addAttribute(LOGIN_USER_ATTRIBUTE, u);
			model.addAttribute(USER_PANEL_EVENTS_ATTRIBUTE, repoEvent.findAllBySeriesUserId(u.getId()));
		
			return USER_PANEL_VIEW;
		}
	}

}
