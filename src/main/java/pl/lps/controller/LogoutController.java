package pl.lps.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/logout")
public class LogoutController extends SessionedController {
	
	@GetMapping("")
	public String logout(Model model) {
		session().setAttribute("user", null);
		model.addAttribute("message", "Wylogowano ");
		model.addAttribute("username", "");
		return "main";
	}

}
