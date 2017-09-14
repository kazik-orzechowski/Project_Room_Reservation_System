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

@Entity
@Table(name = "series")
public class Series {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToOne
	private User user;
	@OneToMany(mappedBy = "series", cascade = CascadeType.ALL)
	private List<Event> events = new ArrayList<Event>();
	@NotNull
	private String client = "";
	@ManyToOne
	private EventType eventType;

	public Series() {
		super();

	}

	public Series(User user, String client, EventType eventType) {
		super();
		this.user = user;
		this.client = client;
		this.eventType = eventType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	public List<Event> getEvents() {
		return events;
	}



	public void setEvents(List<Event> events) {
		this.events = events;
	}



	public String getClient() {
		return client;
	}



	public void setClient(String client) {
		this.client = client;
	}



	public EventType getEventType() {
		return eventType;
	}



	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}



	@Override
	public String toString() {
		return "Series [id=" + id + ", user=" + user + ",  client=" + client + ", eventType="
				+ eventType + "]";
	}

	
}
