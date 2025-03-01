package com.vi5hnu.gobetrotter_api.services.submission;

import com.vi5hnu.gobetrotter_api.Dto.PlaceDto;
import com.vi5hnu.gobetrotter_api.Dto.SubmissionDto;
import com.vi5hnu.gobetrotter_api.Entity.user.PlaceModel;
import com.vi5hnu.gobetrotter_api.Entity.user.SubmissionModel;
import com.vi5hnu.gobetrotter_api.exceptions.ApiException;
import com.vi5hnu.gobetrotter_api.services.places.PlacesRepository;
import com.vi5hnu.gobetrotter_api.specifications.SubmissionSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubmissionService {
    private final SubmissionRepository submissionRepository;

    @Transactional(readOnly = true)
    public List<SubmissionDto> getSubmissionsFor(String userId, List<String> placeIds) {
        return submissionRepository.findAll(SubmissionSpecification.hasUserIdAndQuestionIds(userId,placeIds)).stream().map(SubmissionModel::toDto).collect(Collectors.toList());
    }
}
