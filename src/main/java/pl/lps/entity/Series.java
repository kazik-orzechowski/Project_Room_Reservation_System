package pl.lps.entity;

import java.util.ArrayList;
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
 * Series entity class is used to define series of events and is created during
 * successful reservation attempt made by user. Series is the only indirect
 * reference of user and event type to event. Series class refers to series
 * table in application database.
 * 
 * @author kaz
 */
@Entity
@Table(name = "series")
public class Series {

	/**
	 * Auto generated series id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * Reference to series user (this is the only /indirect/ reference of user to
	 * event)
	 */
	@OneToOne
	private User user;
	/**
	 * Reference to events in series
	 */
	@OneToMany(mappedBy = "series", cascade = CascadeType.ALL)
	private List<Event> events = new ArrayList<Event>();
	/**
	 * Client of series
	 */
	@NotNull
	private String client = "";
	/**
	 * Event type of series (this is the only /indirect/ reference of event type to
	 * event)
	 */
	@ManyToOne
	private EventType eventType;

	/**
	 * Series class object empty constructor
	 */
	public Series() {
		super();

	}

	/**
	 * Series object class parameterized constructor
	 * 
	 * @param user
	 * @param client
	 * @param eventType
	 */
	public Series(User user, String client, EventType eventType) {
		super();
		this.user = user;
		this.client = client;
		this.eventType = eventType;
	}

	/**
	 * Gets this series id
	 * 
	 * @return this series' id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets this series id
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets this series' user
	 * 
	 * @return this series' user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets this series' user
	 * 
	 * @param user
	 */

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Gets the list of events of this series
	 * 
	 * @return list of events of this series
	 */

	public List<Event> getEvents() {
		return events;
	}

	/**
	 * Sets this series' list of event
	 * 
	 * @param events
	 */

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	/**
	 * Gets this series' client
	 * 
	 * @return this series' client
	 */

	public String getClient() {
		return client;
	}

	/**
	 * Sets the client of this series
	 * 
	 * @param this series' client
	 *            
	 */

	public void setClient(String client) {
		this.client = client;
	}

	/**
	 * Gets this series' event type
	 * 
	 * @return this series' event type
	 */
	public EventType getEventType() {
		return eventType;
	}

	/**
	 * Sets this series' event type
	 * 
	 * @param eventType
	 */
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	/**
	 * Request object to String method, returns request general description
	 */

	@Override
	public String toString() {
		return "Series [id=" + id + ", user=" + user + ",  client=" + client + ", eventType=" + eventType + "]";
	}

}
