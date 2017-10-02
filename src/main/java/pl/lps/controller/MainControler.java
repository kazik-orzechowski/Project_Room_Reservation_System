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

import pl.lps.data.ControllerData;
import pl.lps.entity.User;
import pl.lps.model.LoginData;
import pl.lps.repository.UserRepository;


@Controller
@RequestMapping("main")

public class MainControler extends SessionedController {
	
	/**
	 * Passes the name of home page of this application.
	 */
	private static final String MAIN_VIEW = ControllerData.getMainView();

	
	private static final String MAIN_USERNAME_ATTRIBUTE = "username";
	private static final String MAIN_MESSAGE_ATTRIBUTE = "message";

	@GetMapping("")
		public String home(Model model) {
		LoginData loginData = (LoginData) session().getAttribute("user");
		
		if(session().getAttribute("user")!=null) {
			model.addAttribute(MAIN_MESSAGE_ATTRIBUTE,"#{user.logged.in}");
			model.addAttribute(MAIN_USERNAME_ATTRIBUTE," "+loginData.getLogin());
		}
		
		model.addAttribute(MAIN_MESSAGE_ATTRIBUTE,"");
		model.addAttribute(MAIN_USERNAME_ATTRIBUTE,"");
		
		return MAIN_VIEW;
		} 
}
