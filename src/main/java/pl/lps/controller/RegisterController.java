package pl.lps.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.lps.data.ControllerAttributesData;
import pl.lps.data.ControllerData;
import pl.lps.entity.User;
import pl.lps.repository.UserRepository;


@Controller
@RequestMapping("/register")
public class RegisterController {

	@Autowired
	UserRepository repoUser;
	
	@GetMapping("")
	public String register(Model model) {
		User registeringUser=new User();
		model.addAttribute(ControllerAttributesData.getUserAttribute(),registeringUser);
		
		return ControllerData.getSignupView();
	}

	@PostMapping("")
	public String registerPost(@Valid User user, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			System.err.println(result);
			return ControllerData.getSignupView();
		}
		this.repoUser.save(user);
		model.addAttribute(ControllerAttributesData.getMainMessageAttribute(), "Zarejestrowano użytkownika ");
		model.addAttribute(ControllerAttributesData.getMainUsernameAttribute(), user.getUserName());
		
		return ControllerData.getMainView();
	}

}
