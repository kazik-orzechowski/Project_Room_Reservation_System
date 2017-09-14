package pl.lps.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.lps.entity.Room;
import pl.lps.entity.User;


public interface RoomRepository extends JpaRepository<Room, Long>{

	
	
	Room findOneById (Long id);
	List<Room> findAllByPlaceName (String name);
	List<Room> findAllByPlaceId (Long id);
	
	@Query ("SELECT r FROM Room r WHERE r.seats >= :minSeats")
	List<Room> findAllByRoomSize (@Param ("minSeats") Long seats);
	@Query ("SELECT r FROM Room r WHERE r.seats >= :minSeats AND r.place.id = :placeId")
	List<Room> findAllByPlaceAndRoomSize (@Param ("placeId")Long id, @Param ("minSeats") Long seats);
}


