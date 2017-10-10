package pl.lps.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.lps.data.ControllerAttributesData;
import pl.lps.entity.User;
import pl.lps.repository.UserRepository;

/**
 * SessionValidation class serves to validate if current user is logged in or if
 * current user is admin.
 * 
 * @author kaz
 *
 */

@Component
public class SessionValidation extends SessionedController {

	/**
	 * Name of User class user attribute used in session.
	 */
	private static final String SESSION_USER_ATTRIBUTE = ControllerAttributesData.getSessionUserAttribute();

	@Autowired
	UserRepository repoUser;

	/**
	 * Validates if current request is made by the entitled logged in user using
	 * user object stored in http session.
	 * 
	 * @param id
	 *            - current user id passed in http address
	 * @return - true if the user of the passed id equals user object stored in the
	 *         http session.
	 */
	public static boolean isSessionUser(Long id) {
		User u = (User) session().getAttribute(SESSION_USER_ATTRIBUTE);
		if (u.getId() == id) {

			return true;
		}
		return false;
	}

	/**
	 * Validates if current request is made by one of registered users using user
	 * object stored in the http session.
	 * 
	 * @return - true if there is a user/admin object stored in the http session.
	 */

	public static boolean isSessionValid() {
		User u = (User) session().getAttribute(SESSION_USER_ATTRIBUTE);
		if (u == null) {
			return false;
		}
		return true;
	}

	/**
	 * Validates if current request is made by admin using user object stored in the
	 * http session.
	 * 
	 * @return - true if there admin object stored in the http session equals admin
	 *         object in this application repository.
	 */

	public static boolean isSessionAdmin() {
		User u = (User) session().getAttribute(SESSION_USER_ATTRIBUTE);
		if (!u.getUserName().equals("admin")) {
			return false;
		}
		return true;
	}
}
