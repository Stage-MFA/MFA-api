package com.school.security.dtos.requests;

import com.school.security.enums.RoleType;

public record AttachRoleRegDto(String email, RoleType role) {}
