package com.school.security.dtos.responses;

public record LoginResDto(
        String accessToken,
        String refreshToken
){
}
