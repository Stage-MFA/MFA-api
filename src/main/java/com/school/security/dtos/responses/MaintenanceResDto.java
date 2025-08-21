package com.school.security.dtos.responses;

import com.school.security.enums.StatusType;
import java.time.LocalDateTime;

public record MaintenanceResDto(
        Long maintenanceId,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String description,
        StatusType status,
        Long interventionId) {}
