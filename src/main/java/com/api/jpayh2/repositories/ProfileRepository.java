package com.api.jpayh2.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.jpayh2.entities.Profile;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, Integer> {

    // ?1 = es el primer parametro | ?2 = es el segundo parametro
    @Query("SELECT p FROM Profile p WHERE p.user.id = ?1 AND p.id = ?2")
    public Optional<Profile> findByUserIdAndProfileId(Integer userId, Integer profileId);
}
