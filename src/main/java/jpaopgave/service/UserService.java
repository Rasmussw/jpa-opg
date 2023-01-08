package jpaopgave.service;

import jpaopgave.configuration.SecurityConfiguration;
import jpaopgave.model.User;
import jpaopgave.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements IUserService{
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Set<User> findAll() {
        Set<User> users = new HashSet<>();
        userRepository.findAll().forEach(users :: add);
        return users;
    }

    @Override
    public User save(User object) {
        //laver et hashcode af det password der skrives ind i frontend
        //og gemmes sådan i databasen
        PasswordEncoder pw = SecurityConfiguration.passwordEncoder();
        object.setPassword(pw.encode(object.getPassword()));

        userRepository.save(object);
        return object;
    }

    @Override
    public void delete(User object) {
        userRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        userRepository.deleteById(aLong);
    }

    @Override
    public Optional<User> findById(Long aLong) {
        return userRepository.findById(aLong);
    }

    @Override
    public List<User> findUsersByUsername(String name) {
        return userRepository.findUsersByUsername(name);
    }
}
