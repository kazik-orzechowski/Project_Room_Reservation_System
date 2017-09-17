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

import pl.lps.entity.User;
import pl.lps.repository.EventRepository;
import pl.lps.repository.EventTypeRepository;
import pl.lps.repository.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController extends SessionedController {

	@Autowired
	UserRepository repoUser;

	@Autowired
	EventRepository repoEvent;

	@Autowired
	EventTypeRepository repoEventType;

	@RequestMapping("/adminPanel")
	public String adminPanel(Model model) {

		if (!SessionValidation.isSessionAdmin()) {
			return "main";
		}

		return "adminPanel";
	}

	@RequestMapping("")
	public String allUsers(Model model) {

		if (!SessionValidation.isSessionValid()) {
			return "main";
		}

		model.addAttribute("users", repoUser.findAll());
		System.out.println(repoUser.findAll().toString());
		return "users";
	}

	@GetMapping("/{id}/delete")
	public String delUser(@PathVariable Long id, Model model) {

		if (!SessionValidation.isSessionAdmin()) {
			return "main";
		}
		repoUser.deleteById(id);
		return "redirect: /users";
	}

	@GetMapping("/{id}/edit")
	public String editUser(@PathVariable Long id, Model model) {

		if (!SessionValidation.isSessionAdmin()) {
			return "main";
		}

		model.addAttribute("user", repoUser.findOneById(id));

		if (repoUser.findOneById(id).getUserName().equals("admin")) {
			model.addAttribute("returnUrl", "/users");
		} else {
			model.addAttribute("returnUrl", "/events/" + id);
		}

		return "editUser";
	}

	@PostMapping("/{id}/edit")
	public String editUserPost(@Valid User user, @PathVariable Long id, BindingResult result, Model model) {
		if (result.hasErrors()) {
			System.out.println(result);
			return "editUser";
		}
		repoUser.save(user);

		model.addAttribute("eventType", repoEventType.findAll());

		if (repoUser.findOneById(id).getUserName().equals("admin")) {
			model.addAttribute("events", repoEvent.findAll());
			return "users";
		} else {
			model.addAttribute("events", repoEvent.findAllBySeriesUserId(id));
			return "userPanel";
		}

	}

	@GetMapping("/add")
	public String addUser(Model model) {
		
		if(!SessionValidation.isSessionAdmin()) {
			return "main";
		}
		
		User user = new User();
		model.addAttribute("user", user);
		return "signup";
	}

	@PostMapping("/add")
	public String addUserPost(@Valid User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "signup";
		}
		repoUser.save(user);
		model.addAttribute("user", user);
		model.addAttribute("users", repoUser.findAll());
		return "users";

	}

	
	
	
	
}
