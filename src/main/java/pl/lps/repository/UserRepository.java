package pl.lps.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.lps.entity.User;


public interface UserRepository extends JpaRepository<User, Long>{

	User findOneById (Long id);
	User findOneByUserName (String username);
}
