package com.vi5hnu.gobetrotter_api.services.submission;

import com.vi5hnu.gobetrotter_api.Entity.user.OtpModel;
import com.vi5hnu.gobetrotter_api.Entity.user.SubmissionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SubmissionRepository extends JpaRepository<SubmissionModel,String>, JpaSpecificationExecutor<SubmissionModel> {
}
