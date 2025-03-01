package com.vi5hnu.gobetrotter_api.Dto;

import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SubmissionResultDto {
    private SubmissionDto submission;
    private List<String> funFacts;
}