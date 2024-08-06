package com.project.services.impl;

import com.project.converters.MajorConverter;
import com.project.models.Major;
import com.project.repositories.MajorRepository;
import com.project.responses.MajorResponse;
import com.project.services.MajorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MajorServiceImpl implements MajorService {
    private final MajorRepository majorRepository;
    private final MajorConverter majorConverter;

    @Override
    public List<MajorResponse> getAllMajors() {
        return majorRepository.findAll().stream()
                .map(majorConverter::fromMajorToMajorResponse) //Chuyển từng Major thành MajorResponse
                .collect(Collectors.toList()); //Tạo List mới
    }

    @Override
    public boolean checkMajorExistence(String majorName) {
        return majorRepository.findByName(majorName) != null;
    }

    @Override
    @Transactional
    public Major createMajor(Major major, String urlImage, String idImage) {
        if(idImage != null && urlImage != null) {
            major.setIdimage(idImage);
            major.setImage(urlImage);
        }
        return majorRepository.save(major);
    }


}
