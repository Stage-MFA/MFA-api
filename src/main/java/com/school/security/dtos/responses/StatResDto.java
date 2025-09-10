package com.school.security.dtos.responses;

import java.time.LocalDate;

public record StatResDto(Long id, LocalDate date, RapportResDto stat) {}
