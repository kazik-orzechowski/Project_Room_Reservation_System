package pl.lps.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import pl.lps.entity.Series;



public interface SeriesRepository extends JpaRepository<Series, Long>{

	Series findOneById (Long id);
	List<Series> findAllByUserId (Long Id);
	
}


