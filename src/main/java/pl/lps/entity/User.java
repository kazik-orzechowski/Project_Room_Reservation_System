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

import net.minidev.json.annotate.JsonIgnore;

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
	
	@Column(unique = true)
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
	@SuppressWarnings("deprecation")
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
	 * Status of this user set by the admin
	 */
	@NotNull
	private boolean enabled;
	
	/**
	 * Reference to series (indirect and the only reference to user events)
	 */
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Series> series = new ArrayList<Series>();
	/**
	 * Reference to requests
	 */
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Request> requests = new ArrayList<Request>();

	/**
	 * User empty constructor, sets user enable access status to false
	 */
	public User() {
		super();
		this.enabled = false;

	}

	
	/**
	 * Parameterized user constructor
	 * @param id
	 * @param userName
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param password
	 * @param enabled
	 */
	public User(Long id, @NotNull @Length(min = 1, max = 20) String userName,
			@NotNull @Length(min = 1, max = 20) String firstName, @NotNull @Length(min = 1, max = 30) String lastName,
			@NotNull @Email String email, @NotNull @Length(min = 6) String password, @NotNull boolean enabled) {
		super();
		this.id = id;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.enabled = false;
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
	 * Passes this user's original hashed password during edit user process.
	 * 
	 * @param password
	 */
	public void passHashedPassword(String password) {
		this.password = password;
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
	 * Gets this user's enabled status
	 * 
	 * @param series
	 */
	
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Sets this user's enabled status
	 * 
	 * @param series
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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
				+ ", email=" + email + ", password=" + password + ", enabled=" + enabled + "]";
	}

}
