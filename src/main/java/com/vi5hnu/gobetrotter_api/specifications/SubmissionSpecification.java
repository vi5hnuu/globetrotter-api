package com.vi5hnu.gobetrotter_api.specifications;

import com.vi5hnu.gobetrotter_api.Entity.user.SubmissionModel;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class SubmissionSpecification {
    public static Specification<SubmissionModel> hasUserIdAndQuestionIds(String userId, List<String> questionIds) {
        return (Root<SubmissionModel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate userPredicate = criteriaBuilder.equal(root.get("userId"), userId);
            Predicate questionPredicate = root.get("questionId").in(questionIds);
            return criteriaBuilder.and(userPredicate, questionPredicate);
        };
    }

    public static Specification<SubmissionModel> findByUserIdQuestionId(String userId,String questionId) {
        return (Root<SubmissionModel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> criteriaBuilder.and(criteriaBuilder.equal(root.get("userId"), userId), criteriaBuilder.equal(root.get("questionId"), questionId));
    }

    public static Specification<SubmissionModel> findByUserId(String userId) {
        return (Root<SubmissionModel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> criteriaBuilder.equal(root.get("userId"), userId);
    }
}