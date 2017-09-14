package pl.lps.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.lps.entity.Request;
import pl.lps.entity.Series;
import pl.lps.entity.Status;



public interface RequestRepository extends JpaRepository<Request, Long>{

	Request findOneById (Long id);
}


