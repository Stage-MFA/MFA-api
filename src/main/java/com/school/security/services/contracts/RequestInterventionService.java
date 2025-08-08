package com.school.security.services.contracts;

import com.school.security.dtos.requests.RequestInterventionReqDto;
import com.school.security.entities.InterventionRequest;

public interface RequestInterventionService
        extends Service<RequestInterventionReqDto, InterventionRequest, Long> {
    public InterventionRequest update(InterventionRequest toUpdate, Long id);
}
