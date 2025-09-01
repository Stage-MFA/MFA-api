package com.school.security.dtos.responses;

import java.util.List;

public record TechnicianStatisticResDto(
        Long technicianTotal,
        Long maleUsers,
        Long femaleUsers,
        List<Object> topPerformingTechnicianIntervention,
        List<Object> topPerformingTechnicianMaintenances) {}
