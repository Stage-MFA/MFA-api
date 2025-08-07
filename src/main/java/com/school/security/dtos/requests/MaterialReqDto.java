package com.school.security.dtos.requests;

import java.time.LocalDateTime;

public record MaterialReqDto(
        String name,
        String type,
        String brand,
        String Model,
        int serialNumber,
        LocalDateTime acquisitionDat,
        String guarantee) {}
