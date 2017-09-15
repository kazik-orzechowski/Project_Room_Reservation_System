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
import pl.lps.repository.UserRepository;


@Controller
@RequestMapping("")

public class MainControler {

	@Autowired
	UserRepository repoUser;

	
	@GetMapping("/main")
	public String main() {
		return "main";
	}

}
