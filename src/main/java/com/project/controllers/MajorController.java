package com.project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.dto.MajorDTO;
import com.project.models.Major;
import com.project.responses.MajorResponse;
import com.project.services.MajorService;
import com.project.services.impl.CloudinaryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/majors")
public class MajorController {
    private final MajorService majorService;
    private final CloudinaryServiceImpl cloudinaryService;
    private final ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<?> getAllMajors() {
        try{
            List<MajorResponse> majors = majorService.getAllMajors();
            return ResponseEntity.ok().body(majors);
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage()); //rule 5
        }
    }

    @PostMapping
    public ResponseEntity<?> createMajor(@RequestPart("file") MultipartFile multipartFile,
                                         @RequestParam("majordto") String majordtoJson) {
        try {
            // Chuyển đổi chuỗi JSON thành đối tượng Major
            MajorDTO majorDTO = objectMapper.readValue(majordtoJson, MajorDTO.class);
            majorService.createMajor(majorDTO, multipartFile);
            return ResponseEntity.ok().body("Thành công");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> updateMajor(@RequestPart("file") MultipartFile multipartFile,
                                         @RequestParam("majordto") String majordtoJson) {
        try {
            // Chuyển đổi chuỗi JSON thành đối tượng Major
            MajorDTO majorDTO = objectMapper.readValue(majordtoJson, MajorDTO.class);
            majorService.updateMajor(majorDTO, multipartFile);
            return ResponseEntity.ok().body("Thành công");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
