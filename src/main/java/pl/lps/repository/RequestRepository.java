package pl.lps.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.lps.entity.Request;
import pl.lps.entity.Series;
import pl.lps.entity.Status;

/**
 * Request class object Jpa Repository. Contains request search methods.
 * 
 * @author kaz
 *
 */

public interface RequestRepository extends JpaRepository<Request, Long>{

	/**
	 * Request search method
	 * 
	 * @param id
	 * @return request by specified request id
	 */
	Request findOneById (Long id);
}


