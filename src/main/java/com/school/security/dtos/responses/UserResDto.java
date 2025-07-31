package com.school.security.dtos.responses;

import com.school.security.enums.Gender;
import java.util.List;

public record UserResDto(
        Long id,
        String firstname,
        String lastname,
        String email,
        Gender gender,
        String direction,
        String speciality,
        List<RoleResDto> roleResDto) {}
