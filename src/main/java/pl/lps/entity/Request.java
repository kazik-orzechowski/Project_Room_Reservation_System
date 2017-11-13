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
 * Request class refers to "requests" table in application database. !!!!! To be
 * implemented !!!!!
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
	private Long requestDuration;
	/**
	 * Number of events in requested series of events in request
	 */
	@NotNull
	private Long requestNumberOfEvents;
	/**
	 * Interval of series of requested series of events in request
	 */
	@NotNull
	private Long intervalDays;
	/**
	 * User submitting request
	 */
	@ManyToOne
	private User user;

	/**
	 * NUmber of seats required for requested event
	 */
	@NotNull
	private Long requestEventSeats;
	
	/**
	 * Request status (0 - pending default, 1 - accepted, 2 - declined, 3 canceled
	 * by user)
	 */
	@NotNull
	private int status;

	/**
	 * Request class object empty constructor
	 */
	public Request() {
		super();
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
	 * Parameterized Request class object constructor

	 * 
	 * @param id
	 * @param startdate
	 * @param hour
	 * @param requestDuration
	 * @param requestNumberOfEvents
	 * @param intervalDays
	 * @param user
	 * @param requestEventSeats
	 * @param status
	 */
	
	public Request(@NotNull Date startdate, @NotNull Date hour, @NotNull Long requestDuration,
			@NotNull Long requestNumberOfEvents, @NotNull Long intervalDays, User user, @NotNull Long requestEventSeats,
			@NotNull int status) {
		super();
		this.startdate = startdate;
		this.hour = hour;
		this.requestDuration = requestDuration;
		this.requestNumberOfEvents = requestNumberOfEvents;
		this.intervalDays = intervalDays;
		this.user = user;
		this.requestEventSeats = requestEventSeats;
		this.status = status;
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
	public Long getRequestDuration() {
		return requestDuration;
	}

	/**
	 * Sets this request's duration in hours
	 * 
	 * @param requestDuration
	 */
	public void setRequestDuration(Long requestDuration) {
		this.requestDuration = requestDuration;
	}

	/**
	 * Gets the request's interval between events
	 * 
	 * @return this request's interval between events in days
	 */

	public Long getIntervalDays() {
		return intervalDays;
	}

	/**
	 * Sets this request's interval between events in days
	 * 
	 * @param intervalDays
	 */
	public void setIntervalDays(Long intervalDays) {
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

	public Integer getStatus() {
		return status;
	}

	/**
	 * Sets the status of this request
	 * 
	 * @param status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}


	
	public Long getRequestNumberOfEvents() {
		return requestNumberOfEvents;
	}

	public void setRequestNumberOfEvents(Long requestNumberOfEvents) {
		this.requestNumberOfEvents = requestNumberOfEvents;
	}

	public Long getRequestEventSeats() {
		return requestEventSeats;
	}

	public void setRequestEventSeats(Long requestEventSeats) {
		this.requestEventSeats = requestEventSeats;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Request object to String method, returns request general description
	 */
	@Override
	public String toString() {
		return "Request [id=" + id + ", startdate=" + startdate + ", hour=" + hour + ", requestDuration="
				+ requestDuration + ", requestNumberOfEvents=" + requestNumberOfEvents + ", intervalDays="
				+ intervalDays + ", user=" + user + ", requestEventSeats=" + requestEventSeats + ", status=" + status + "]";
	}

	
	
	
	

}
