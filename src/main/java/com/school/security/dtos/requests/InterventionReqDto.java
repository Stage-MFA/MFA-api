package com.school.security.dtos.requests;

import com.school.security.enums.StatusType;
import java.time.LocalDateTime;

public record InterventionReqDto(
        LocalDateTime dateIntervention,
        StatusType status,
        String description,
        Long idUser,
        Long interventionRequestId) {}
