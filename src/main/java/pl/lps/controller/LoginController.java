package pl.lps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.lps.entity.User;
import pl.lps.model.LoginData;
import pl.lps.repository.EventRepository;
import pl.lps.repository.EventTypeRepository;
import pl.lps.repository.UserRepository;

@Controller
@RequestMapping("/login")
public class LoginController extends SessionedController {
	@Autowired
	UserRepository repoUser;

	@Autowired
	EventRepository repoEvent;

	@Autowired
	EventTypeRepository repoEventType;

	@GetMapping("")
	public String login(Model m) {
		LoginData login = new LoginData();
		m.addAttribute("user", login);

		return "login";
	}

	@PostMapping("")
	public String loginPost(LoginData user, Model model) {
		User u = this.repoUser.findByUserName(user.getLogin());
		if (u == null) {
			model.addAttribute("message", "Wprowadź prawidłowe dane");
			model.addAttribute("username", "");
			return "login";
		}

		if (u.isPasswordCorrect(user.getPassword())) {
			session().setAttribute("user", u);
		}

		if (u.getUserName().equals("admin")) {
			return "adminPanel";
		} else {
			model.addAttribute("events", repoEvent.findAllBySeriesUserId(u.getId()));

			model.addAttribute("eventType", repoEventType.findAll());
			model.addAttribute("user", u);
			return "userPanel";
		}
	}

}
