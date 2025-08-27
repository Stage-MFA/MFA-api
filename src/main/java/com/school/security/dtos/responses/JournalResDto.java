package com.school.security.dtos.responses;

import java.time.LocalDateTime;

public record JournalResDto(
        Long journalId,
        LocalDateTime dateJournal,
        String user,
        String technician,
        String action,
        int time) {}
