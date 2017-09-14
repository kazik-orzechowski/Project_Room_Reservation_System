package pl.lps.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import pl.lps.entity.Series;



public interface SeriesRepository extends JpaRepository<Series, Long>{

	Series findOneById (Long id);
}


