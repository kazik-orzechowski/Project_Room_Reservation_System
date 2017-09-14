package pl.lps.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.lps.entity.Event;
import pl.lps.entity.Room;
import pl.lps.entity.Series;



public interface EventRepository extends JpaRepository<Event, Long> {

	Event findOneById (Long id);
	List<Event> findAllBySeriesUserId (Long Id) ;
	List<Event> findAllBySeries (Series series) ;
	Event findOneByDateAndHour (Date date, Date hour) ;
	Event findOneByDateAndHourAndRoom (Date date, Date hour, Room room) ;
	
	@Query ("SELECT e FROM Event e WHERE e.date IN :date "
			+ "AND e.room.id = :roomId "
			+ "AND :endHour >e.hour AND :endHour < e.endHour OR "
			+ "e.date = :date AND e.room.id = :roomId AND "
			+ ":startHour >e.hour AND :startHour < e.endHour"
			)
	List<Event> findNotCollidingEvents(@Param("date")Date date
									,@Param("roomId")Long roomId
									, 
									@Param("startHour")Date hour,
									@Param("endHour")Date hour2
									);
	
	@Query ("SELECT e FROM Event e WHERE e.date IN (:dates) "
			+ "AND e.room.id = :roomId "
			+ "AND :endHour >e.hour AND :endHour < e.endHour OR "
			+ "e.date IN (:dates) AND e.room.id = :roomId AND "
			+ ":startHour >e.hour AND :startHour < e.endHour"
			)
	List<Event> findNotCollidingManyEvents(@Param("dates")List<Date> dates
									,@Param("roomId")Long roomId
									, 
									@Param("startHour")Date hour,
									@Param("endHour")Date hour2
									);
		
	
}


