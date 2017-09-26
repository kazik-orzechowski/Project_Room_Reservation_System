package pl.lps.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.lps.entity.Event;
import pl.lps.entity.EventType;

/**
 * EventType class object Jpa Repository. Contains event type search methods.
 * 
 * @author kaz
 *
 */

public interface EventTypeRepository extends JpaRepository<EventType, Long> {
	
	/**
	 * Event type search method
	 * 
	 * @param id
	 * @return event type by given {id}
	 */
	EventType findOneById(Long id);
}
