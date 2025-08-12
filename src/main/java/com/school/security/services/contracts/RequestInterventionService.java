package com.school.security.services.contracts;

import com.school.security.dtos.requests.RequestInterventionReqDto;
import com.school.security.dtos.responses.RequestInterventionResDto;
import java.util.List;

public interface RequestInterventionService
        extends Service<RequestInterventionReqDto, RequestInterventionResDto, Long> {
    public RequestInterventionResDto update(RequestInterventionReqDto toUpdate, Long id);

    public List<RequestInterventionResDto> listRequestInterventionByUserId(Long id);
}
