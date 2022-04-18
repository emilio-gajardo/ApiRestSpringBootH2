package com.api.jpayh2.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.api.jpayh2.entities.Profile;
import com.api.jpayh2.entities.User;
import com.api.jpayh2.repositories.ProfileRepository;
import com.api.jpayh2.repositories.UserRepository;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    public Profile create(Integer userId, Profile profile) {
        Optional<User> result = userRepository.findById(userId);

        if (result.isPresent()) {
            profile.setUser(result.get());
            return profileRepository.save(profile);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("User %d not found", userId));
        }
    }

    public Profile getByUserIdAndProfileId(Integer userId, Integer profileId) {
        return profileRepository
                .findByUserIdAndProfileId(userId, profileId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Profile %d not found for user %d", profileId, userId)));
    }

}
