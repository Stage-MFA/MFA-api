package com.school.security.dtos.responses;

public record InterventionStatisticsResDto(
        Long interventionTotal, Long pending, Long progress, Long finish) {}
