package com.project.services;

import com.project.models.Major;
import com.project.responses.MajorResponse;

import java.util.List;

public interface MajorService {
    boolean checkMajorExistence(String majorName);
    Major createMajor(Major major, String urlImage, String idImage);
    List<MajorResponse> getAllMajors();
}
