package com.project.converters;

import com.project.dto.UserDTO;
import com.project.models.User;
import com.project.repositories.MajorRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class UserConverter {
    private final ModelMapper modelMapper;
    private final MajorRepository majorRepository;

    /**
     * Phương thức này để chuyển đổi từ UserDTO sang UserEntity
     * @param userDTO - dưới dạng DTO
     * @return dưới dạng Entity
     */
    public User fromDTOtoUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        String majorName = userDTO.getMajor();
        user.setMajorUserEntities(majorRepository.findByName(majorName));
        String dob = userDTO.getDateOfBirth();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateOfBirth = LocalDate.parse(dob, formatter);
        user.setDateOfBirth(dateOfBirth);
        return user;
    }
}
