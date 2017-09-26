package pl.lps.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * EventType entity class concerns type of events that are possible to be used
 * in the reservation system by its users. EventType class refers to event_types
 * table in application database.
 * 
 * @author kaz
 *
 */
@Entity
@Table(name = "event_types")
public class EventType {

	/**
	 * Auto generated EventType unique id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * Unique name of event type
	 */
	@Column(unique = true)
	@NotEmpty
	private String name;

	/**
	 * Description of event type
	 */
	@NotEmpty
	private String description;

	/**
	 * EventType object to String method, returns event type general description
	 */
	@Override
	public String toString() {
		return "EventType [id=" + id + ", name=" + name + ", description=" + description + "]";
	}

	/**
	 * Empty constructor of event type object
	 */
	public EventType() {
		super();

	}

	/**
	 * Parameterized constructor of event type object
	 * 
	 * @param name
	 * @param description
	 */
	public EventType(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	/**
	 * Gets the id of this event type
	 * 
	 * @return id of this event type
	 */
	public Long getId() {
		return id;
	}

	/**
	 *  Sets the id of this event type
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the name of this event type
	 * 
	 * @return name of this event type
	 */
	public String getName() {
		return name;
	}

	/**
	 *  Sets the name of this event type
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the description of this event type
	 * 
	 * @return description of this event type
	 */
	public String getDescription() {
		return description;
	}

	/**
	 *  Sets the description of this event type
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
