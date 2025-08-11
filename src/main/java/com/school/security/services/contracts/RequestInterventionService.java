package com.school.security.services.contracts;

import com.school.security.dtos.requests.RequestInterventionReqDto;
import com.school.security.dtos.responses.RequestInterventionResDto;

public interface RequestInterventionService
        extends Service<RequestInterventionReqDto, RequestInterventionResDto, Long> {
    public RequestInterventionResDto update(RequestInterventionReqDto toUpdate, Long id);
}
