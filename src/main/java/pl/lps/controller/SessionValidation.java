package pl.lps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.lps.entity.User;
import pl.lps.repository.UserRepository;

@Component
public class SessionValidation extends SessionedController {

	@Autowired
	UserRepository repoUser;

	public static boolean isSessionUser(Long id) {
		User u = (User) session().getAttribute("user");
		if (u.getId() == id) {

			return true;
		}
		return false;
	}


	public static boolean isSessionValid() {
		User u = (User) session().getAttribute("user");
		if (u == null) {
			return false;
		}
		return true;
	}

	public static boolean isSessionAdmin() {
		User u = (User) session().getAttribute("user");
		if (!u.getUserName().equals("admin")) {
			return false;
		}
		return true;
	}
}
