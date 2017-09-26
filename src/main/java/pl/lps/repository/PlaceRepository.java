package pl.lps.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.lps.entity.Event;
import pl.lps.entity.Place;

/**
 * Place class object Jpa Repository. Contains place search methods.
 * 
 * @author kaz
 *
 */

public interface PlaceRepository extends JpaRepository<Place, Long>{

	/**
	 * Place type search method
	 * 
	 * @param id
	 * @return place by specified place id
	 */
	Place findOneById (Long id);
	
	/**
	 * Place type search method
	 * @param placeName
	 * @return place by specified place name
	 */
	Place findOneByName (String placeName);
}


