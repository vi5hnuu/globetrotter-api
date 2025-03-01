package com.vi5hnu.gobetrotter_api.auth;

import com.vi5hnu.gobetrotter_api.Dto.*;
import com.vi5hnu.gobetrotter_api.Entity.user.*;
import com.vi5hnu.gobetrotter_api.commons.Pageable;
import com.vi5hnu.gobetrotter_api.exceptions.ApiException;
import com.vi5hnu.gobetrotter_api.services.places.PlacesRepository;
import com.vi5hnu.gobetrotter_api.services.places.PlacesService;
import com.vi5hnu.gobetrotter_api.services.submission.SubmissionRepository;
import com.vi5hnu.gobetrotter_api.services.submission.SubmissionService;
import com.vi5hnu.gobetrotter_api.specifications.PlaceSpecification;
import com.vi5hnu.gobetrotter_api.specifications.SubmissionSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1/places")
@RequiredArgsConstructor
public class PlacesController {
    final PlacesService placesService;
    final SubmissionService submissionService;
    final SubmissionRepository submissionRepository;
    private final PlacesRepository placesRepository;

    @GetMapping(path = "all/info")
    ResponseEntity<Map<String,Object>> getPlacesInfo(
            Principal principal,
            @RequestParam(name = "pageNo",required = false,defaultValue = "1") int pageNo,
            @RequestParam(name = "pageSize",required = false,defaultValue = "20") int pageSize) {
        final var places=this.placesService.getPlaces(pageNo,pageSize);
        final var placeIds=places.getData().stream().map(PlaceModel::getId).toList();
        final var submissions=submissionService.getSubmissionsFor(principal.getName(),placeIds)
                .stream()
                .collect(Collectors.toMap(SubmissionDto::getQuestionId, submission -> submission));

        //all destinations
        final var destionations=places.getData().stream().map(PlaceModel::getName).toList();
        //place dtos with submission results if any
        final var placesDto=places.getData().stream().map(placeModel -> {
            final var submission=submissions.get(placeModel.getId());
            if(submission!=null) submission.setWasCorrect(submission.getChoice().equals(placeModel.getName()));

            final var submissionResult=SubmissionResultDto.builder()
                    .submission(submission)
                    .funFacts(submission!=null ? placeModel.getFunFacts():null)
                    .build();
            final var dto=PlaceModel.toDto(placeModel,submissionResult);
            dto.setOptions(placesService.selectRandomDestinations(destionations, placeModel.getName()));
            return dto;
        }).toList();


        return ResponseEntity.status(200).body(Map.of("success",true,"data",new Pageable<>(placesDto,places.getPageNo(),places.getTotalItems())));
    }

    @GetMapping(path = "submit/{placeId}")
    ResponseEntity<Map<String,Object>> makeSubmittion(
            Principal principal,
            @PathVariable(name = "placeId") String placeId,
            @RequestParam(name = "choice",defaultValue = "") String choice) throws ApiException {
        final var place=placesService.getPlace(placeId);

        var exSubmission=submissionRepository.findOne(SubmissionSpecification.findByUserIdQuestionId(principal.getName(),placeId)).orElse(null);

        if(exSubmission==null){
            exSubmission=SubmissionModel.builder()
                    .questionId(placeId)
                    .userId(principal.getName())
                    .choice(choice)
                    .build();
        }

        exSubmission.setChoice(choice);
        submissionRepository.save(exSubmission);

        final SubmissionDto submissionDto=SubmissionModel.toDto(exSubmission);
        submissionDto.setWasCorrect(choice.equals(place.getName()));

        final var submissionResult=SubmissionResultDto.builder()
                .submission(submissionDto)
                .funFacts(place.getFunFacts())
                .build();
        return ResponseEntity.status(200).body(Map.of("success",true,"data",submissionResult));
    }

    @GetMapping(path = "score")
    ResponseEntity<Map<String,Object>> makeSubmittion(Principal principal) throws ApiException {
        final var submissions=submissionRepository.findAll(SubmissionSpecification.findByUserId(principal.getName()))
                .stream().collect(Collectors.toMap(SubmissionModel::getQuestionId, SubmissionModel::getChoice));
        if(submissions.isEmpty()){
            return ResponseEntity.status(200).body(Map.of("success",true,"data",0));
        }
        final var allPlaces=placesRepository.findAll(PlaceSpecification.placeIn(submissions.keySet().stream().toList()))
                .stream().collect(Collectors.toMap(PlaceModel::getId, PlaceModel::getName));

        var score=0;
        for(final var submission : submissions.entrySet()){
            if(allPlaces.get(submission.getKey()).equals(submission.getValue())) score+=1;
        }
        return ResponseEntity.status(200).body(Map.of("success",true,"data",score));
    }
}