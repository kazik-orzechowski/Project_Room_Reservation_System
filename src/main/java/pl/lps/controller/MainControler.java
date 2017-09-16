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
import pl.lps.model.LoginData;
import pl.lps.repository.UserRepository;


@Controller
@RequestMapping("main")

public class MainControler extends SessionedController{
	
	@GetMapping("")
		public String home(Model model) {
		LoginData loginData = (LoginData) session().getAttribute("user");
		
		if(session().getAttribute("user")!=null) {
			model.addAttribute("message","#{user.logged.in}");
			model.addAttribute("username"," "+loginData.getLogin());
		}
		
		model.addAttribute("message","");
		model.addAttribute("username","");
		
		return "main";
		} 
}
