package com.school.security.dtos.requests;

import com.school.security.entities.Material;
import com.school.security.entities.User;
import com.school.security.enums.Priority;
import com.school.security.enums.StatusType;
import java.time.LocalDateTime;
import java.util.List;

public record RequestInterventionReqDto(
        LocalDateTime requestDate,
        StatusType status,
        Priority priority,
        List<Material> materials,
        User user) {}
