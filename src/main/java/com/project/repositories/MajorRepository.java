package com.project.repositories;

import com.project.models.Major;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MajorRepository extends JpaRepository<Major, Long> {
    Major findByName(String name);
}
