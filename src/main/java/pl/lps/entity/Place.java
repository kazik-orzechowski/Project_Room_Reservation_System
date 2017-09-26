package pl.lps.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Place entity class serves to define sites in which rooms are located This
 * class refers to places table in application database.
 * 
 * @author kaz
 *
 */
@Entity
@Table(name = "places")
public class Place {
	/**
	 * Auto generated place id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * Unique place name
	 */
	@Column(unique = true)
	@NotEmpty
	private String name;
	/**
	 * Street and street number of place
	 */
	@NotEmpty
	private String street;
	/**
	 * City of place
	 */
	@NotEmpty
	private String city;
	/**
	 * Description of place
	 */
	@NotNull
	private String description;
	/**
	 * Reference to rooms located in place
	 */
	@OneToMany(mappedBy = "place")
	private List<Room> rooms = new ArrayList<Room>();

	/**
	 * Empty constructor of Place class object
	 */
	public Place() {
		super();

	}

	/**
	 * Parameterized constructor of Place class object
	 * 
	 * @param name
	 * @param street
	 * @param city
	 * @param description
	 */
	public Place(String name, String street, String city, String description) {
		super();
		this.name = name;
		this.street = street;
		this.city = city;
		this.description = description;
	}

	/**
	 * Gets the id of this place
	 * 
	 * @return id of this place
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id of this place
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the name of this place
	 * 
	 * @return name of this place
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of this place
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the street and number of this place
	 * 
	 * @return street and number of this place
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * Sets the street and number of this place
	 * 
	 * @param street
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * Gets the city of this place
	 * 
	 * @return city of this place
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Setter sets city of this place
	 * 
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Gets the description of this place
	 * 
	 * @return description of this place
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of this place
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the list of rooms located at this place
	 * 
	 * @return list of rooms located at this place
	 */
	public List<Room> getRooms() {
		return rooms;
	}

	/**
	 * Sets the list of rooms located at this place
	 * 
	 * @param rooms
	 */
	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	/**
	 * Place object to String method, returns place general description
	 */
	@Override
	public String toString() {
		return "Place [id=" + id + ", name=" + name + ", street=" + street + ", city=" + city + ", description="
				+ description + "]";
	}

}
