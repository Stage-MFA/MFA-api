package com.school.security.dtos.responses;

import com.school.security.enums.StatusType;
import java.time.LocalDateTime;

public record InterventionResDto(
        Long interventionId,
        LocalDateTime dateIntervention,
        StatusType status,
        String description,
        Long usersId,
        Long interventionRequestId) {}
