package com.vi5hnu.gobetrotter_api.services.user;

import com.vi5hnu.gobetrotter_api.Entity.user.VerificationTokenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
public interface VerificationTokenRepository extends JpaRepository<VerificationTokenModel,Long>, JpaSpecificationExecutor<VerificationTokenModel> {
}
