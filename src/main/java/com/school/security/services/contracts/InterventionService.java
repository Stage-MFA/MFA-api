package com.school.security.services.contracts;

import com.school.security.dtos.requests.InterventionReqDto;
import com.school.security.dtos.responses.InterventionResDto;
import com.school.security.dtos.responses.InterventionStatisticsResDto;

public interface InterventionService extends Service<InterventionReqDto, InterventionResDto, Long> {
    public InterventionResDto update(InterventionReqDto toUpdate, Long id);

    public Long getCountIntervention();

    public InterventionStatisticsResDto getInterventionStatistics();
}
