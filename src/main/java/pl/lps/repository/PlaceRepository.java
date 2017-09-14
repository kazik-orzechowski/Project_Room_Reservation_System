package pl.lps.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.lps.entity.Event;
import pl.lps.entity.Place;



public interface PlaceRepository extends JpaRepository<Place, Long>{

	Place findOneById (Long id);
	Place findOneByName (String Placename);
}


