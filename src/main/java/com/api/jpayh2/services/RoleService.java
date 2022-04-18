package com.api.jpayh2.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.api.jpayh2.entities.Role;
import com.api.jpayh2.entities.User;
import com.api.jpayh2.models.AuditDetails;
import com.api.jpayh2.models.MySecurityRule;
import com.api.jpayh2.repositories.RoleRepository;
import com.api.jpayh2.repositories.UserInRoleRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import javax.annotation.security.RolesAllowed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.access.prepost.PostAuthorize;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.access.annotation.Secured;

// directa manipulacion con la BD
@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    @Autowired
    private UserInRoleRepository userInRoleRepository;

    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;

    private ObjectMapper mapper = new ObjectMapper();

    private static final Logger log = LoggerFactory.getLogger(RoleService.class);

    public List<Role> getRoles() {
        return repository.findAll();
    }

    //@RolesAllowed({"ROLE_ADMIN"})
    @MySecurityRule
    public List<User> getUsersByRole(String roleName) {
        log.info("Getting roles by name: {}", roleName);
        return userInRoleRepository.findUsersByRoleName(roleName);
    }

    
    // Creacion de un role
    // Envía el nombre y el rol creado al listener de kafka
    public Role createRole(Role role) {

        Role roleCreated = repository.save(role);

        AuditDetails details = new AuditDetails(SecurityContextHolder.getContext().getAuthentication().getName(), role.getName());

        try {
            kafkaTemplate.send("mi-topic", mapper.writeValueAsString(details));
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error parsing the message");
        }

        return roleCreated;
    }

    public Role updateRole(Integer roleId, Role role) {

        Optional<Role> result = repository.findById(roleId);

        if (result.isPresent()) {
            return repository.save(role);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Role id %d no existe", roleId));
        }
    }

    public void deleteRole(Integer roleId) {
        Optional<Role> result = repository.findById(roleId);
        if (result.isPresent()) {
            repository.delete(result.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Role id %d no existe", roleId));
        }
    }
}
