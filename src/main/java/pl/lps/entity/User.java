package pl.lps.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.mindrot.jbcrypt.BCrypt;

/**
 * User entity class is used to define users. Series class refers to user table
 * in application database.
 * 
 * @author kaz
 */

@Entity
@Table(name = "users")
public class User {
	/**
	 * Auto generated user id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * Username used for login purposes
	 */
	@NotNull
	@Length(min = 1, max = 20)
	private String userName;
	/**
	 * User first name
	 */
	@NotNull
	@Length(min = 1, max = 20)
	private String firstName;
	/**
	 * User last name
	 */
	@NotNull
	@Length(min = 1, max = 30)
	private String lastName;
	/**
	 * User unique email address
	 */
	@NotNull
	@Column(unique = true)
	@Email
	private String email;
	/**
	 * User password !!!!! Higher security requirements to be developed !!!!!
	 */
	@NotNull
	@Length(min = 6)
	private String password;
	/**
	 * Reference to series (indirect and the only reference to user events)
	 */
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Series> series = new ArrayList<Series>();
	/**
	 * Reference to requests
	 */
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Request> requests = new ArrayList<Request>();

	/**
	 * User empty constructor
	 */
	public User() {
		super();

	}

	/**
	 * User parameterized constructor
	 * 
	 * @param userName
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param password
	 */
	public User(String userName, String firstName, String lastName, String email, String password) {
		super();
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	/**
	 * Gets this user's id
	 * 
	 * @return this user's id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets this user's id
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets this user's first name
	 * 
	 * @return this user's first name
	 */

	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets this user's first name
	 * 
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets this user's email address
	 * 
	 * @return this user's email address
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Gets this user's username
	 * 
	 * @return this user's username
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets this user's user name
	 * 
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Gets this user's last name
	 * 
	 * @return this user's last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets this user's last name
	 * 
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Sets this user's email
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets this user's password
	 * 
	 * @return this user's password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets this user's password encrypted with BCrypt
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = BCrypt.hashpw(password, BCrypt.gensalt());
	}

	/**
	 * Password verification method of this user (BCrypt)
	 * 
	 * @param password
	 * @return
	 */
	public Boolean isPasswordCorrect(String password) {
		return BCrypt.checkpw(password, this.password);
	}

	/**
	 * Gets this user's series (of events)
	 * 
	 * @return this user's series (of events)
	 */
	public List<Series> getSeries() {
		return series;
	}

	/**
	 * Sets this user's series (of events)
	 * 
	 * @param series
	 */
	public void setSeries(List<Series> series) {
		this.series = series;
	}

	/**
	 * Gets this user's requests
	 * 
	 * @return this user's requests
	 */

	public List<Request> getRequests() {
		return requests;
	}

	/**
	 * Sets this user's requests
	 * 
	 * @param requests
	 */
	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}

	/**
	 * User object to String method, returns user general description
	 */
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + userName + ", firstname=" + firstName + ", lastname=" + lastName
				+ ", email=" + email + ", password=" + password + "]";
	}

}
