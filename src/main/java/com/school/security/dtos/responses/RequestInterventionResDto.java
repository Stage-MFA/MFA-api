package com.school.security.dtos.responses;

import com.school.security.entities.Material;
import com.school.security.enums.Priority;
import com.school.security.enums.StatusType;
import java.time.LocalDateTime;
import java.util.List;

public record RequestInterventionResDto(
        Long interventionRequestId,
        LocalDateTime requestDate,
        StatusType status,
        Priority priority,
        List<Material> materials,
        String description,
        Long idUser) {}
