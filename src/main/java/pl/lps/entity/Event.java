package pl.lps.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Event entity class is used to define events. Set event is equal to given room
 * reservation, and serve to verify room availability too. Event class refers to
 * events table in application database.
 * 
 * @author kaz
 *
 */
@Entity
@Table(name = "events")
public class Event {

	/**
	 * Auto generated unique id of event
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * Date of event
	 */
	@NotNull
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date date;
	/**
	 * Start hour of event
	 */

	@NotNull
	@DateTimeFormat(pattern = "HH:mm")
	private Date hour;
	/**
	 * End hour of event
	 */
	@DateTimeFormat(pattern = "HH:mm")
	private Date endHour;
	/**
	 * Event duration in hours
	 */
	@NotNull
	private Long eventDuration;
	/**
	 * Number of seats required for event
	 */
	@NotNull
	@Range(min = 1, max = 999)
	private Long eventSeats;

	/**
	 * Reference to event series (each event belongs to series, even if it is one
	 * event series)
	 */
	@ManyToOne
	private Series series;
	/**
	 * Room assigned to event
	 */
	@ManyToOne 
	private Room room;

	/**
	 * Empty constructor of event
	 */
	public Event() {
		super();

	}

	/**
	 * Event constructor with parameters
	 * 
	 * @param date
	 * @param hour
	 * @param endHour
	 * @param eventDuration
	 * @param eventSeats
	 * @param series
	 * @param room
	 */

	public Event(Date date, Date hour, Date endHour, Long eventDuration, Long eventSeats, Series series, Room room) {
		super();
		this.date = date;
		this.hour = hour;
		this.endHour = endHour;
		this.eventDuration = eventDuration;
		this.eventSeats = eventSeats;
		this.series = series;
		this.room = room;
	}

	public Event(Date date, Date hour, Date endHour, Long eventDuration, Long eventSeats,
			Series series) {
		super();
		this.date = date;
		this.hour = hour;
		this.endHour = endHour;
		this.eventDuration = eventDuration;
		this.eventSeats = eventSeats;
		this.series = series;
	}

	/**
	 * Gets the id of this event
	 * 
	 * @return id of this event
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id of this event
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the creation date of this event
	 * 
	 * @return creation date of this event
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Sets the date of this event
	 * 
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Gets the start hour of this event
	 * 
	 * @return start hour of this event
	 */
	public Date getHour() {
		return hour;
	}

	/**
	 * Sets the start hour of this event
	 * 
	 * @param hour
	 */
	public void setHour(Date hour) {
		this.hour = hour;
	}

	/**
	 * Gets the end hour of this event
	 * 
	 * @return end hour of this event
	 */
	public Date getEndHour() {
		return endHour;
	}

	/**
	 * Sets the end hour of this event
	 * 
	 * @param endHour
	 */

	public void setEndHour(Date endHour) {
		this.endHour = endHour;
	}

	/**
	 * Gets this event's duration in hours
	 * 
	 * @return this event's duration in hours
	 */
	public Long getEventDuration() {
		return eventDuration;
	}

	/**
	 * Sets the event's duration in hours
	 * 
	 * @param eventDuration
	 */
	public void setEventDuration(Long eventDuration) {
		this.eventDuration = eventDuration;
	}

	/**
	 * Gets the number of seats required for this event
	 * 
	 * @return number of seats required for this event
	 */
	public Long getEventSeats() {
		return eventSeats;
	}

	/**
	 * Sets the number of seats required for this event
	 * 
	 * @param eventSeats
	 */
	public void setEventSeats(Long eventSeats) {
		this.eventSeats = eventSeats;
	}

	/**
	 * Gets the event series of this event
	 * 
	 * @return event series object of this event
	 */
	public Series getSeries() {
		return series;
	}

	/**
	 * Sets the (event) series of this event
	 * 
	 * @param series
	 */
	public void setSeries(Series series) {
		this.series = series;
	}

	/**
	 * Gets the room of this event
	 * 
	 * @return room of this event
	 */
	public Room getRoom() {
		return room;
	}

	/**
	 * Sets the room of this event
	 * 
	 * @param room
	 */
	public void setRoom(Room room) {
		this.room = room;
	}

	/**
	 * Event object to String method, returns event general description
	 */
	@Override
	public String toString() {
		return "Event [date=" + date + ", hour=" + hour + ", endHour=" + endHour + ", eventDuration=" + eventDuration
				+ ", eventSeats=" + eventSeats + ", series=" + series + ", room=" + room + "]";
	}

}
