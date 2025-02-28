package com.vi5hnu.gobetrotter_api.services.user;

import com.vi5hnu.gobetrotter_api.Entity.user.UserModel;
import com.vi5hnu.gobetrotter_api.constants.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel,String>, JpaSpecificationExecutor<UserModel> {
    boolean existsByUsernameOrEmail(@NotBlank(message = "user name cannot be blank") String userName, @Pattern(regexp = Constants.EMAIL_PATTERN,message = "invalid email id, ex : example@gmail.com") String email);
    Optional<UserModel> findByUsernameOrEmail(@NotBlank(message = "user name cannot be blank") String userName, @Pattern(regexp = Constants.EMAIL_PATTERN,message = "invalid email id, ex : example@gmail.com") String email);
}
