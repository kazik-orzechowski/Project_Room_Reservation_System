package pl.lps.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.lps.entity.Event;
import pl.lps.entity.EventType;



public interface EventTypeRepository extends JpaRepository<EventType, Long>{

	EventType findOneById (Long id);
}


