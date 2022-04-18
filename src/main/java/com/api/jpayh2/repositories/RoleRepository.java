package com.api.jpayh2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.jpayh2.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

}
