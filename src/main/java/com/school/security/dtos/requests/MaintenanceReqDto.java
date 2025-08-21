package com.school.security.dtos.requests;

import com.school.security.enums.StatusType;
import java.time.LocalDateTime;

public record MaintenanceReqDto(
        LocalDateTime startDate,
        LocalDateTime endDate,
        String description,
        StatusType status,
        Long interventionId) {}
