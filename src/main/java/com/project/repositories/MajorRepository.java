package com.project.repositories;

import com.project.models.Major;
import com.project.requests.MajorSearchRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MajorRepository extends JpaRepository<Major, Long> {
    boolean existsByName(String name);

    @Query("SELECT m FROM Major m WHERE " +
            "(:#{#request.name} IS NULL OR LOWER(m.name) LIKE LOWER(CONCAT('%', :#{#request.name}, '%'))) AND " +
            "(:#{#request.minDoctors} IS NULL OR SIZE(m.userEntities) >= :#{#request.minDoctors}) AND " +
            "(:#{#request.maxDoctors} IS NULL OR SIZE(m.userEntities) <= :#{#request.maxDoctors})")
    List<Major> getAllMajors(@Param("request") MajorSearchRequest request);
}
