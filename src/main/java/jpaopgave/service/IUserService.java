package jpaopgave.service;

import jpaopgave.model.User;

import java.util.List;

public interface IUserService extends ICrudService<User, Long>{
    List<User> findUsersByName(String name);
}
