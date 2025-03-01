package com.vi5hnu.gobetrotter_api.services.places;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vi5hnu.gobetrotter_api.Dto.PlaceDto;
import com.vi5hnu.gobetrotter_api.Dto.RegisterRequestDto;
import com.vi5hnu.gobetrotter_api.Dto.UserRole;
import com.vi5hnu.gobetrotter_api.Dto.user.UserDto;
import com.vi5hnu.gobetrotter_api.Entity.user.PlaceModel;
import com.vi5hnu.gobetrotter_api.Entity.user.UserModel;
import com.vi5hnu.gobetrotter_api.exceptions.ApiException;
import com.vi5hnu.gobetrotter_api.exceptions.UserAlreadyExistsException;
import com.vi5hnu.gobetrotter_api.specifications.UserSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PlacesService {
    private final PlacesRepository placesRepository;

    @Transactional(readOnly = true)
    public com.vi5hnu.gobetrotter_api.commons.Pageable<PlaceModel> getPlaces(int pageNo, int limit) {
        PageRequest pageable = PageRequest.of(pageNo - 1, limit); // Page index is 0-based in Spring Data
        final var page=placesRepository.findAll(pageable);
        return new com.vi5hnu.gobetrotter_api.commons.Pageable<>(page.get().toList(),pageNo,page.getTotalPages());
    }

    public PlaceModel getPlace(String placeId) throws ApiException {
        return this.placesRepository.findById(placeId).orElseThrow(()->new ApiException(HttpStatus.BAD_REQUEST,"Invalid placeId"));
    }

    public List<String> selectRandomDestinations(List<String> destinations, String correctDestination) {
        List<String> filtered = new ArrayList<>(destinations);
        filtered.remove(correctDestination); // Exclude the correct one
        Collections.shuffle(filtered); // Shuffle to get randomness
        final var listedDestinatioon=filtered.subList(0, Math.min(3, filtered.size()));
        listedDestinatioon.add(correctDestination);
        return listedDestinatioon;
    }
}
