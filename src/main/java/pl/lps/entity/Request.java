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

@Entity
@Table(name = "requests")
public class Request {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private Date startdate;
	@NotNull
	private Date hour;
	@NotNull
	private Integer requestDuration;
	@NotNull
	private Integer intervalDays;
	@ManyToOne
	private User user;
	@OneToOne(cascade = CascadeType.ALL)
	private Status status;

	public Request() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Request(Date startdate, Date hour, Integer requestDuration, Integer intervalDays, User user, Status status) {
		super();
		this.startdate = startdate;
		this.hour = hour;
		this.requestDuration = requestDuration;
		this.intervalDays = intervalDays;
		this.user = user;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getHour() {
		return hour;
	}

	public void setHour(Date hour) {
		this.hour = hour;
	}

	public Integer getRequestDuration() {
		return requestDuration;
	}

	public void setRequestDuration(Integer requestDuration) {
		this.requestDuration = requestDuration;
	}

	public Integer getIntervalDays() {
		return intervalDays;
	}

	public void setIntervalDays(Integer intervalDays) {
		this.intervalDays = intervalDays;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Request [id=" + id + ", startdate=" + startdate + ", hour=" + hour + ", requestDuration="
				+ requestDuration + ", intervalDays=" + intervalDays + ", user=" + user + ", status=" + status + "]";
	}

}
