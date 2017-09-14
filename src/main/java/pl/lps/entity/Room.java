package pl.lps.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "rooms")
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Range(min=1, max = 999)
	private Long number;
	@NotEmpty
	private String floor;
	@NotNull
	private String description;
	@ManyToOne
	private Place place;
	@NotNull
	@Range(min=1, max = 999)
	private Long seats;
	@OneToMany(mappedBy = "room")
	private List<Event> events = new ArrayList<Event>();

	public Room() {
		super();

	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", number=" + number + ", floor=" + floor + ", description=" + description
				+ ", place=" + place + ", seats=" + seats + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public Long getSeats() {
		return seats;
	}

	public void setSeats(Long seats) {
		this.seats = seats;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public Room(Long number, String floor, String description, Place place, Long seats) {
		super();
		this.number = number;
		this.floor = floor;
		this.description = description;
		this.place = place;
		this.seats = seats;
	}

	

}
