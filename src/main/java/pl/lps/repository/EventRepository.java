package pl.lps.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.lps.entity.Event;
import pl.lps.entity.Room;
import pl.lps.entity.Series;

/**
 * Event class object Jpa Repository. This repository defines main methods used
 * for free rooms search for reservation purposes.
 * 
 * @author kaz
 *
 */

public interface EventRepository extends JpaRepository<Event, Long> {
	/**
	 * Event search method
	 * 
	 * @param id
	 * @return event by specified id
	 */
	Event findOneById(Long id);

	/**
	 * Event search method
	 * 
	 * @param Id
	 * @return list of events with specified user id in series assigned to event
	 */
	List<Event> findAllBySeriesUserId(Long Id);

	/**
	 * Event search method
	 * 
	 * @param series
	 * @return list of events with specified series attribute
	 */
	List<Event> findAllBySeries(Series series);

	/**
	 * Event search method
	 * 
	 * @param date
	 * @param hour
	 * @return event with specified start date - calendar day and hour of a day
	 */
	Event findOneByDateAndHour(Date date, Date hour);

	/**
	 * Event search method
	 * 
	 * @param date
	 * @param hour
	 * @param room
	 * @return event with specified start calendar day date, start hour of a day and
	 *         room
	 */
	Event findOneByDateAndHourAndRoom(Date date, Date hour, Room room);

	/**
	 * Event search method - the first of two main methods used for checking
	 * availability of a room against specified time and place criteria
	 * 
	 * @param date
	 * @param roomId
	 * @param hour
	 * @return list of events colliding with potential event with specified calendar
	 *         date, start hour of a day and room id
	 */
	@Query("SELECT e FROM Event e WHERE e.date IN :date " + "AND e.room.id = :roomId "
			+ "AND :endHour >e.hour AND :endHour <= e.endHour OR " + "e.date = :date AND e.room.id = :roomId AND "
			+ ":startHour >=e.hour AND :startHour < e.endHour")
	List<Event> findCollidingEvents(@Param("date") Date date, @Param("roomId") Long roomId,
			@Param("startHour") Date hour, @Param("endHour") Date endHour);

	/**
	 * Event search method - the second of two main methods used for checking
	 * availability of a room against specified time and place criteria
	 * 
	 * @param dates
	 * @param roomId
	 * @param hour
	 * @return list of events colliding with list of potential events (event series)
	 *         with specified calendar dates, start hour of a day and room id
	 */
	@Query("SELECT e FROM Event e WHERE e.date IN (:dates) " + "AND e.room.id = :roomId "
			+ "AND :endHour >e.hour AND :endHour <= e.endHour OR " + "e.date IN (:dates) AND e.room.id = :roomId AND "
			+ ":startHour >=e.hour AND :startHour < e.endHour")
	List<Event> findManyCollidingEvents(@Param("dates") List<Date> dates, @Param("roomId") Long roomId,
			@Param("startHour") Date hour, @Param("endHour") Date endHour);

}
