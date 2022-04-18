package com.api.jpayh2.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.jpayh2.entities.Address;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface AddressRepository extends CrudRepository<Address, Integer> {

    @Query("SELECT a FROM Address a WHERE a.profile.user.id = ?1 AND a.profile.id = ?2")
    List<Address> findByUserIdAndProfileId(Integer userId, Integer profileId);
}
