package com.vi5hnu.gobetrotter_api.specifications;

import com.vi5hnu.gobetrotter_api.Entity.user.PlaceModel;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;

public class PlaceSpecification {
    public static Specification<PlaceModel> placeIn(List<String> placeIds) {
        return (Root<PlaceModel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> root.get("id").in(placeIds);
    }
}
