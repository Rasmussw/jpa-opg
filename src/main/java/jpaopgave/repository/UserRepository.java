package jpaopgave.repository;

import jpaopgave.model.Band;
import jpaopgave.model.User;
import jpaopgave.service.IUserService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findUsersByUsername(String name);
}
