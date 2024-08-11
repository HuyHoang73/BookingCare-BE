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
     *
     * @param userDTO - dưới dạng DTO
     * @return dưới dạng Entity
     */
    public User fromDTOtoUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        Long majorId = userDTO.getMajorId();
        user.setMajorUserEntities(majorRepository.findById(majorId).get());
        String dob = userDTO.getDateOfBirth();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateOfBirth = LocalDate.parse(dob, formatter);
        user.setDateOfBirth(dateOfBirth);
        return user;
    }

    /**
     * Phương thức này dùng để chuyển đổi từ UserEntity thành UserDTO
     * @param user - Dạng entity
     * @return UserDTO
     */
    public UserDTO fromUserToDTO(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        Long majorId = user.getMajorUserEntities().getId();
        userDTO.setMajorId(majorId);
        LocalDate dateOfBirth = user.getDateOfBirth();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dob = dateOfBirth.format(formatter);
        userDTO.setDateOfBirth(dob);
        return userDTO;
    }

    /**
     * Phương thức sau để cập nhật 1 User t DTO
     *
     * @param userDTO - DTO dùng để sao chép
     * @param user    - User cần sao chép
     */
    public void updateUserFromDTO(UserDTO userDTO, User user) {
        modelMapper.map(userDTO, user);

        if (userDTO.getMajorId() != null) {
            Long majorId = userDTO.getMajorId();
            user.setMajorUserEntities(majorRepository.findById(majorId).orElse(null));
        }

        if (userDTO.getDateOfBirth() != null) {
            String dob = userDTO.getDateOfBirth();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dateOfBirth = LocalDate.parse(dob, formatter);
            user.setDateOfBirth(dateOfBirth);
        }
    }

}
