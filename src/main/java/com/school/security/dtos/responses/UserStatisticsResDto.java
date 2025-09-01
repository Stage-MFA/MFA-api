package com.school.security.dtos.responses;

public record UserStatisticsResDto(
        Long totalUsers,
        Long maleUsers,
        Long femaleUsers,
        Long users,
        Long technicians,
        Long managers) {}
