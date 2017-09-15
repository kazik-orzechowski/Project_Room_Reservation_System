package pl.lps.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.lps.entity.User;
import pl.lps.repository.EventRepository;
import pl.lps.repository.EventTypeRepository;
import pl.lps.repository.UserRepository;

@Controller
@RequestMapping("/users")

public class RegistrationAndLoginController {

	@Autowired
	UserRepository repoUser;

	@Autowired
	EventRepository repoEvent;

	@Autowired
	EventTypeRepository repoEventType;

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping("/login")
	public String loginVerification(@RequestParam String login, @RequestParam String password, Model model) {
		List<User> users = repoUser.findAll();
		for (User user : users) {
			if (user.getUserName().equals(login) && user.getPassword().equals(password)) {
				Long id = user.getId();
				if (repoUser.findOneById(id).getUserName().equals("admin")) {
					return "adminPanel";
				} else {
					model.addAttribute("events", repoEvent.findAllBySeriesUserId(id));
				}
				model.addAttribute("eventType", repoEventType.findAll());
				model.addAttribute("user", user);
				return "userPanel";
			}
		}

		return "login";
	}

	@GetMapping("/logout")
	public String logout() {
		return "main";
	}

	@GetMapping("/signup")
	public String addUser(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "signup";
	}

	@PostMapping("/signup")
	public String addUserPost(@Valid User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "signup";
		}
		repoUser.save(user);
		model.addAttribute("user", user);
		return "main";

	}

}
