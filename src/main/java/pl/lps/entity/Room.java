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

/**
 * Room entity class concerns rooms that may be an object of reservation. Room
 * class refers to event_types table in application database.
 * 
 * @author kaz
 *
 */
@Entity
@Table(name = "rooms")
public class Room {

	/**
	 * Auto generated room id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * Room number
	 */
	@NotNull
	@Range(min = 1, max = 999)
	private Long number;
	/**
	 * Room floor
	 */
	@NotEmpty
	private String floor;
	/**
	 * Room description
	 */
	@NotNull
	private String description;
	/**
	 * Reference to site in which room is located
	 */
	@ManyToOne
	private Place place;
	/**
	 * Number of seats in the room
	 */
	@NotNull
	@Range(min = 1, max = 999)
	private Long seats;
	/**
	 * Reference to events assigned to room
	 */
	@OneToMany(mappedBy = "room")
	private List<Event> events = new ArrayList<Event>();

	/**
	 * Empty room constructor
	 */

	public Room() {
		super();

	}

	/**
	 * Parameterized Room class object constructor
	 * 
	 * @param number
	 * @param floor
	 * @param description
	 * @param place
	 * @param seats
	 */
	public Room(Long number, String floor, String description, Place place, Long seats) {
		super();
		this.number = number;
		this.floor = floor;
		this.description = description;
		this.place = place;
		this.seats = seats;
	}

	/**
	 * Room object to String method, returns room general description
	 */
	@Override
	public String toString() {
		return "Room [id=" + id + ", number=" + number + ", floor=" + floor + ", description=" + description
				+ ", place=" + place + ", seats=" + seats + "]";
	}

	/**
	 * Gets this room's id
	 * 
	 * @return this room's id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets this room's id
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets this room's number
	 * 
	 * @return this room's number
	 */
	public Long getNumber() {
		return number;
	}

	/**
	 * Sets this room's number
	 * 
	 * @param number
	 */
	public void setNumber(Long number) {
		this.number = number;
	}

	/**
	 * Gets this room's floor
	 * 
	 * @return this room's floor
	 */
	public String getFloor() {
		return floor;
	}

	/**
	 * Sets this room's floor
	 * 
	 * @param floor
	 */
	public void setFloor(String floor) {
		this.floor = floor;
	}

	/**
	 * Gets this room's description
	 * 
	 * @return this room's description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets this room's description
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets this room's place
	 * 
	 * @return this room's place
	 */
	public Place getPlace() {
		return place;
	}

	/**
	 * Sets this room's place
	 * 
	 * @param place
	 */
	public void setPlace(Place place) {
		this.place = place;
	}

	/**
	 * Gets this room's seats number
	 * 
	 * @return this room's seats number
	 */
	public Long getSeats() {
		return seats;
	}

	/**
	 * Sets this room's seats number
	 * 
	 * @param seats
	 */
	public void setSeats(Long seats) {
		this.seats = seats;
	}

	/**
	 * Gets the list of events assigned to this room
	 * 
	 * @return events assigned to this room
	 */
	public List<Event> getEvents() {
		return events;
	}

	/**
	 * Sets the events assigned to this room
	 * 
	 * @param events
	 */
	public void setEvents(List<Event> events) {
		this.events = events;
	}

}
