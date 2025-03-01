package com.vi5hnu.gobetrotter_api.Dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PlaceDto {
    public final static String TABLE_NAME = "places";
    private String id;
    private String alias;
    private SubmissionResultDto submissionResult;
    private List<String> clues;
    private List<String> options;
}