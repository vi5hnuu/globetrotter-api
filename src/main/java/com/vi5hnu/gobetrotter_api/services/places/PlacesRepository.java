package com.vi5hnu.gobetrotter_api.services.places;

import com.vi5hnu.gobetrotter_api.Entity.user.PlaceModel;
import com.vi5hnu.gobetrotter_api.Entity.user.UserModel;
import com.vi5hnu.gobetrotter_api.constants.Constants;
import com.vi5hnu.gobetrotter_api.specifications.PlaceSpecification;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface PlacesRepository extends JpaRepository<PlaceModel,String>,JpaSpecificationExecutor<PlaceModel> {
}
