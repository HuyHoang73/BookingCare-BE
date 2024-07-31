package com.project.services;

import com.project.models.Major;
import com.project.responses.MajorResponse;

import java.util.List;

public interface MajorService {
    Major createMajor(Major major);
    List<MajorResponse> getAllMajors();
}
