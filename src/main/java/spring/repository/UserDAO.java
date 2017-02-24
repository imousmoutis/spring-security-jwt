package spring.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import spring.model.User;

public interface UserDAO extends GenericRepository<User>{
	
	@Query(value="SELECT * FROM User WHERE username = :username", nativeQuery = true)
	User findByUsername(@Param("username") String username);

}
