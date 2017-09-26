package pl.lps.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.lps.entity.User;

/**
 * User class object Jpa Repository.
 * 
 * @author kaz
 *
 */

public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * User search method
	 * 
	 * @param id
	 * @return user by given {id}
	 */

	User findOneById(Long id);

	/**
	 * User search method
	 * 
	 * @param username
	 * @return user by given username
	 */
	User findOneByUserName(String username);

	/**
	 * User search method
	 * 
	 * @param login
	 * @return user by given login !!!!! Method duplication - needs refactoring !!!!!
	 */
	User findByUserName(String username);
}
