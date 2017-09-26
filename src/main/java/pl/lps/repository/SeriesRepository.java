package pl.lps.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.lps.entity.Series;

/**
 * Series class object Jpa repository. Contains event series search methods.
 * 
 * @author kaz
 *
 */

public interface SeriesRepository extends JpaRepository<Series, Long> {

	/**
	 * Series search method
	 * 
	 * @param id
	 * @return series by specified series id
	 */

	Series findOneById(Long id);

	/**
	 * Series search method
	 * 
	 * @param id
	 * @return series by specified id
	 */

	List<Series> findAllByUserId(Long Id);
	// Series findOneByUserIdWhereEventDateMin (Long Id);
	// Series findOneByUserIdWhereEventDateMax (Long Id);

}
