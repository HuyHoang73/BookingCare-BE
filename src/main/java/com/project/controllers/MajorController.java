package com.project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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
            Major major = objectMapper.readValue(majordtoJson, Major.class);

            if (majorService.checkMajorExistence(major.getName())) {
                return ResponseEntity.badRequest().body("Tên khoa đã tồn tại");
            }

            Map<String, Object> data = cloudinaryService.upload(multipartFile);
            majorService.createMajor(major, data.get("url").toString(), data.get("public_id").toString());
            return ResponseEntity.ok().body("Đã tạo thành công");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
