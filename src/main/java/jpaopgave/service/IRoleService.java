package jpaopgave.service;

import jpaopgave.model.Role;

import java.util.List;
import java.util.Set;

public interface IRoleService extends ICrudService<Role, Long>{
    List<Role> findRoleByName(String name);
}
