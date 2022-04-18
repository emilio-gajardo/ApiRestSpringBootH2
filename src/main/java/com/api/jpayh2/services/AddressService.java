package com.api.jpayh2.services;

import com.api.jpayh2.repositories.AddressRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.jpayh2.entities.Address;
import com.api.jpayh2.entities.Profile;
import com.api.jpayh2.repositories.ProfileRepository;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ProfileRepository profileRepository;

    public List<Address> findAddressByUserIdAndProfileId(Integer userId, Integer profileId) {
        return addressRepository.findByUserIdAndProfileId(userId, profileId);
    }

    public Address createAddress(Integer userId, Integer profileId, Address address) {

        Optional<Profile> result = profileRepository.findByUserIdAndProfileId(userId, profileId);

        if (result.isPresent()) {
            address.setProfile(result.get());
            return addressRepository.save(address);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("ProfileId %d and userId %d not found", userId, profileId));
        }
    }
}




