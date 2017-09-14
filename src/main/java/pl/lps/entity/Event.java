package pl.lps.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "events")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date date;
	@NotNull
	@DateTimeFormat(pattern = "HH:mm")
	private Date hour;
	@DateTimeFormat(pattern = "HH:mm")
	private Date endHour;
	@NotNull
	private Long eventDuration;
	@NotNull
	@Range(min=1, max = 999)
	private Long eventSeats;
	@ManyToOne
	private Series series;
	@ManyToOne
	private Room room;
	
	public Event() {
		super();

	}

	public Event(Date date, Date hour, Date endHour, Long eventDuration, Long eventSeats, Series series,
			Room room) {
		super();
		this.date = date;
		this.hour = hour;
		this.endHour = endHour;
		this.eventDuration = eventDuration;
		this.eventSeats = eventSeats;
		this.series = series;
		this.room = room;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getHour() {
		return hour;
	}

	public void setHour(Date hour) {
		this.hour = hour;
	}

	public Date getEndHour() {
		return endHour;
	}

	public void setEndHour(Date endHour) {
		this.endHour = endHour;
	}

	public Long getEventDuration() {
		return eventDuration;
	}

	public void setEventDuration(Long eventDuration) {
		this.eventDuration = eventDuration;
	}

	public Long getEventSeats() {
		return eventSeats;
	}

	public void setEventSeats(Long eventSeats) {
		this.eventSeats = eventSeats;
	}

	public Series getSeries() {
		return series;
	}

	public void setSeries(Series series) {
		this.series = series;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	@Override
	public String toString() {
		return "Event [date=" + date + ", hour=" + hour + ", endHour=" + endHour + ", eventDuration=" + eventDuration
				+ ", eventSeats=" + eventSeats + ", series=" + series + ", room=" + room + "]";
	}

	

}
