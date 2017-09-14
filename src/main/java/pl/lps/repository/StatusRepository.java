package pl.lps.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import pl.lps.entity.Series;
import pl.lps.entity.Status;



public interface StatusRepository extends JpaRepository<Status, Long>{

	Status findOneById (Long id);
}


