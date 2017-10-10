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

/**
 * RegisterController class maps and processes user's request concerning signing
 * up to this application.
 * 
 * @author kaz
 *
 */

@Controller
@RequestMapping("/register")
public class RegisterController {

	@Autowired
	UserRepository repoUser;

	/**
	 * Name of model attribute passing selected user to event related views.
	 */
	protected static final String USER_ATTRIBUTE = ControllerAttributesData.getUserAttribute();

	/**
	 * Name of String class message attribute used in the home page view.
	 */
	private static final String MAIN_MESSAGE_ATTRIBUTE = ControllerAttributesData.getMainMessageAttribute();
	/**
	 * Name of the String class attribute used in this application's home page.
	 */
	private static final String MAIN_USERNAME_ATTRIBUTE = ControllerAttributesData.getMainUsernameAttribute();

	/**
	 * Name of home page of this application.
	 */
	static final String MAIN_VIEW = ControllerData.getMainView();

	/**
	 * Name of user sign up view.
	 */
	static final String SIGNUP_VIEW = ControllerData.getSignupView();

	/**
	 * Maps user request for signing up to this application.
	 * 
	 * @param model
	 *            - instance of Model class used to pass attributes to the sign up
	 *            view
	 * @return signup.html view
	 */

	@GetMapping("")
	public String register(Model model) {
		User registeringUser = new User();
		model.addAttribute(USER_ATTRIBUTE, registeringUser);

		return SIGNUP_VIEW;
	}

	/**
	 * Maps post user's sign up request made by user via sign up form.
	 * on addEvent.html view
	 * 
	 * @param user - new User entity class instance 
	 * @param result
	 *            - binding result errors
	 * @param model
	 *            - instance of Model class used to pass attributes to the sign up
	 *            view
	 * @return - home page of this application, i.e. main.html view
	 */
	@PostMapping("")
	public String registerPost(@Valid User user, BindingResult result, Model model) {

		if (result.hasErrors()) {
			System.err.println(result);
			return SIGNUP_VIEW;
		}
		this.repoUser.save(user);
		model.addAttribute(MAIN_MESSAGE_ATTRIBUTE, "user.signed.up");
		model.addAttribute(MAIN_USERNAME_ATTRIBUTE, " " + user.getUserName());

		return MAIN_VIEW;
	}

}
