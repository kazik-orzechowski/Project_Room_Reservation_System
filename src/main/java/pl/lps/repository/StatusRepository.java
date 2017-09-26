package pl.lps.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import pl.lps.entity.Series;
import pl.lps.entity.Status;

/**
 * Status class object Jpa Repository. Contains status search methods.
 * 
 * @author kaz
 *
 */

public interface StatusRepository extends JpaRepository<Status, Long>{

	/**
	 * Status search method
	 * @param id
	 * @return status by given id
	 */
	
	Status findOneById (Long id);
}


