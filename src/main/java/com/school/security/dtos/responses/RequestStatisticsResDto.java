package com.school.security.dtos.responses;

public record RequestStatisticsResDto(
        Long requestTotal, Long pending, Long progress, Long finish) {}
