package pl.lps.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.lps.entity.Event;
import pl.lps.entity.Room;
import pl.lps.entity.User;

/**
 * Room class object Jpa repository. Contains room search methods.
 * 
 * @author kaz
 *
 */
public interface RoomRepository extends JpaRepository<Room, Long> {

	/**
	 * Room search method
	 * 
	 * @param id
	 * @return room by specified room id
	 */

	Room findOneById(Long id);

	/**
	 * Room search method based on place name
	 * 
	 * @param name
	 * @return list of rooms located in the place with specified place name
	 */
	List<Room> findAllByPlaceName(String name);

	/**
	 * Room search method
	 * @param id
	 * @return list of rooms located in the place with specified by place id
	 */
	List<Room> findAllByPlaceId(Long id);

	

	/**
	 * Room search method
	 * 
	 * @return list of rooms with specified sorted by place name ascending
	 */
	@Query("SELECT r FROM Room r ORDER BY r.place.name DESC")
	List<Room> findAllListOrderByPlaceName();

	
	/**
	 * Room search method
	 * 
	 * @param seats
	 * @return list of rooms with number of seats equal or higher than the specified number
	 *         of seats
	 */
	@Query("SELECT r FROM Room r WHERE r.seats >= :minSeats")
	List<Room> findAllByRoomSize(@Param("minSeats") Long seats);

	/**
	 * Room search method based on the seats number and place
	 * 
	 * @param id
	 * @param seats
	 * @return list of rooms with number of seats equal or higher than the specified number
	 *         of seats in the place pointed by place id
	 */
	@Query("SELECT r FROM Room r WHERE r.seats >= :minSeats AND r.place.id = :placeId")
	List<Room> findAllByPlaceAndRoomSize(@Param("placeId") Long id, @Param("minSeats") Long seats);
}
