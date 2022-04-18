package com.api.jpayh2.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.jpayh2.entities.UserInRole;
import com.api.jpayh2.entities.User;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface UserInRoleRepository extends CrudRepository<UserInRole, Integer>{

    // ?1 = es el roleName
    @Query("SELECT u.user FROM UserInRole u WHERE u.role.name=?1")
    public List<User> findUsersByRoleName(String roleName);

    public List<UserInRole> findByUser(User user);
}
