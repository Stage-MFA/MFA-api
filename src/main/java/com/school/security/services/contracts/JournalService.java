package com.school.security.services.contracts;

import com.school.security.dtos.requests.JournalReqDto;
import com.school.security.dtos.responses.JournalResDto;

public interface JournalService extends Service<JournalReqDto, JournalResDto, Long> {}
