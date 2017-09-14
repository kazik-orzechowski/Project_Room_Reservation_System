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
import pl.lps.repository.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserRepository repoUser;
	
	@RequestMapping("/adminPanel")
	public String adminPanel(Model model) {
		return "adminPanel";
	}
	
	@RequestMapping("")
	public String allUsers(Model model) {
		model.addAttribute("users", repoUser.findAll());
		System.out.println(repoUser.findAll().toString());
		return "users";
	}

	@GetMapping("/{id}/delete")
	public String delUser(@PathVariable Long id, Model model) {
		repoUser.deleteById(id);
		return "redirect: /users"; 
	}
	
	@GetMapping("/{id}/edit")
	public String editUser(@PathVariable Long id, Model model) {
		model.addAttribute("user", repoUser.findOneById(id));
		return "editUser"; 
	}
	
	@PostMapping("/{id}/edit")
	public String editUserPost(@Valid User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			System.out.println(result);
			return "editUser";
		}
		repoUser.save(user);
		model.addAttribute("users", repoUser.findAll());
		return "users"; // user.toString();
	
	}
	
	@GetMapping("/add")
	public String addUser(Model model) {
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