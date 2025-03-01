package com.vi5hnu.gobetrotter_api.Entity.user;

import com.vi5hnu.gobetrotter_api.Dto.PlaceDto;
import com.vi5hnu.gobetrotter_api.Dto.SubmissionDto;
import com.vi5hnu.gobetrotter_api.constants.Constants;
import com.vi5hnu.gobetrotter_api.utils.IdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = SubmissionModel.TABLE_NAME)
public class SubmissionModel {
    public final static String TABLE_NAME = "submissions";
    @Id
    private String id;

    @Column(name = "user_id") private String userId;

    @Column(name = "is_deleted",nullable = false)
    private boolean isDeleted;
    @Column(name = "question_id") private String questionId;
    @Column(name = "choice") private String choice;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public static SubmissionDto toDto(SubmissionModel submissionModel){
        return SubmissionDto.builder()
                .id(submissionModel.getId())
                .userId(submissionModel.getUserId())
                .questionId(submissionModel.getQuestionId())
                .choice(submissionModel.getChoice())
                .createdAt(submissionModel.getCreatedAt())
                .wasCorrect(false)
                .updatedAt(submissionModel.getUpdatedAt())
                .build();
    }

    @PrePersist()
    private void beforeSave(){
        if(getId()==null) setId(IdGenerators.generateIdWithPrefix(Constants.SUBMISSION_ID_PREFIX));
    }
}