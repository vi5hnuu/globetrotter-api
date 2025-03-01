package com.vi5hnu.gobetrotter_api.Entity.user;

import com.vi5hnu.gobetrotter_api.Dto.PlaceDto;
import com.vi5hnu.gobetrotter_api.Dto.SubmissionDto;
import com.vi5hnu.gobetrotter_api.Dto.SubmissionResultDto;
import com.vi5hnu.gobetrotter_api.constants.Constants;
import com.vi5hnu.gobetrotter_api.utils.IdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = PlaceModel.TABLE_NAME)
public class PlaceModel {
    public final static String TABLE_NAME = "places";
    @Id
    private String id;

    @Column(name = "alias") private String alias;
    @Column(name = "name") private String name;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "clues",columnDefinition = "json")
    private List<String> clues;


    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "fun_facts",columnDefinition = "json")
    private List<String> funFacts;

    public static PlaceDto toDto(PlaceModel placeModel, SubmissionResultDto submissionResultDto){
        return PlaceDto.builder()
                .id(placeModel.getId())
                .alias(placeModel.getAlias())
                .clues(placeModel.getClues())
                .submissionResult(submissionResultDto)
                .build();
    }

    @PrePersist()
    private void beforeSave(){
        if(getId()==null) setId(IdGenerators.generateIdWithPrefix(Constants.USER_ID_PREFIX));
    }
}