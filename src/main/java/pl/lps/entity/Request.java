package pl.lps.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Request entity class is used to user requests for rooms in case there is no
 * room available at the time of the reservation attempt. Request entity class
 * objects are used to define pending request concerning room reservation. The
 * request turns into event when there is a room available for defined criteria.
 * Request class refers to "requests" table in application database. 
 * !!!!! To be implemented !!!!!
 * 
 * @author kaz
 *
 */

@Entity
@Table(name = "requests")
public class Request {

	/**
	 * Auto generated request id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * Start date of requested event in request
	 */
	@NotNull
	private Date startdate;
	/**
	 * Start hour of requested event in request
	 */
	@NotNull
	private Date hour;
	/**
	 * Duration of requested event in request
	 */
	@NotNull
	private Integer requestDuration;
	/**
	 * Number of events in requested series of events in request
	 */
	@NotNull
	private Integer requestNumberOfEvents;
	/**
	 * Interval of series of requested series of events in request
	 */
	@NotNull
	private Integer intervalDays;
	/**
	 * User submitting request
	 */
	@ManyToOne
	private User user;
	/**
	 * Request status
	 */
	@OneToOne(cascade = CascadeType.ALL)
	private Status status;

	/**
	 * Request class object empty constructor
	 */
	public Request() {
		super();
	}

	/**
	 * Parameterized Request class object constructor
	 * 
	 * @param startdate
	 * @param hour
	 * @param requestDuration
	 * @param intervalDays
	 * @param user
	 * @param status
	 */

	public Request(Date startdate, Date hour, Integer requestDuration, Integer intervalDays, User user, Status status) {
		super();
		this.startdate = startdate;
		this.hour = hour;
		this.requestDuration = requestDuration;
		this.intervalDays = intervalDays;
		this.user = user;
		this.status = status;
	}

	/**
	 * Gets this request's id
	 * 
	 * @return this request's id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Setter sets this request's id
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets this request's start date
	 * 
	 * @return this request's start date
	 */
	public Date getStartdate() {
		return startdate;
	}

	/**
	 * Sets this request's start date
	 * 
	 * @param startdate
	 */
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	/**
	 * Gets this request's start hour
	 * 
	 * @return this request start hour
	 */
	public Date getHour() {
		return hour;
	}

	/**
	 * Sets this request's start hour
	 * 
	 * @param hour
	 */

	public void setHour(Date hour) {
		this.hour = hour;
	}

	/**
	 * Gets this request's duration
	 * 
	 * @return this request's duration
	 */
	public Integer getRequestDuration() {
		return requestDuration;
	}

	/**
	 * Sets this request's duration in hours
	 * 
	 * @param requestDuration
	 */
	public void setRequestDuration(Integer requestDuration) {
		this.requestDuration = requestDuration;
	}

	/**
	 * Gets the request's interval between events
	 * 
	 * @return this request's interval between events in days
	 */

	public Integer getIntervalDays() {
		return intervalDays;
	}

	/**
	 * Sets this request's interval between events in days
	 * 
	 * @param intervalDays
	 */
	public void setIntervalDays(Integer intervalDays) {
		this.intervalDays = intervalDays;
	}

	/**
	 * Gets the user that submitted this request
	 * 
	 * @return user that submitted this request
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets the user that submitted this request
	 * 
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Gets the status of this request
	 * 
	 * @return status of this request
	 */

	public Status getStatus() {
		return status;
	}

	/**
	 * Sets the status of this request
	 * 
	 * @param status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * Request object to String method, returns request general description
	 */
	@Override
	public String toString() {
		return "Request [id=" + id + ", startdate=" + startdate + ", hour=" + hour + ", requestDuration="
				+ requestDuration + ", intervalDays=" + intervalDays + ", user=" + user + ", status=" + status + "]";
	}

}
