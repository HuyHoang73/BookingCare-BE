package com.project.controllers;

import com.project.models.Major;
import com.project.models.User;
import com.project.responses.MajorResponse;
import com.project.services.MajorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/majors")
public class MajorController {
    private final MajorService majorService;

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
    public ResponseEntity<?> creeateMajor(@RequestBody Major major) {
        try{
            Major newMajor = majorService.createMajor(major);
            return ResponseEntity.ok().body("Thành công");
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage()); //rule 5
        }
    }
}
