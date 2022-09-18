package jpaopgave.repository;

import jpaopgave.model.User;
import jpaopgave.service.IUserService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
