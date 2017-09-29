package pl.lps.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.lps.entity.User;
import pl.lps.repository.UserRepository;


@Controller
@RequestMapping("/register")
public class RegisterController {

	private static final String MAIN_USERNAME_ATTRIBUTE = "username";
	private static final String MAIN_MESSAGE_ATTRIBUTE = "message";
	private static final String MAIN_VIEW = "main";
	private static final String REGISTER = "register";
	@Autowired
	UserRepository repoUser;
	
	@GetMapping("")
	public String register(Model model) {
		User registeringUser=new User();
		model.addAttribute("user",registeringUser);
		
		return REGISTER;
	}

	@PostMapping("")
	public String registerPost(@Valid User user, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			System.err.println(result);
			return REGISTER;
		}
		this.repoUser.save(user);
		model.addAttribute(MAIN_MESSAGE_ATTRIBUTE, "Zarejestrowano użytkownika ");
		model.addAttribute(MAIN_USERNAME_ATTRIBUTE, user.getUserName());
		
		return MAIN_VIEW;
	}

}
