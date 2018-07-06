package com.waggi.metersdata.data.repository;

import com.waggi.metersdata.data.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByUsername(String username);
    List<User> findByUsernameContaining(String username);

    @Query("SELECT u.enabled FROM User u WHERE u.username = :username")
    Boolean findUserEnabledByUsername(@Param("username") String username);
}
