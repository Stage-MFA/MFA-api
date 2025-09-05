package com.school.security.services.contracts;

import com.school.security.dtos.requests.InterventionReqDto;
import com.school.security.dtos.responses.InterventionResDto;
import com.school.security.dtos.responses.InterventionStatisticsResDto;
import java.util.List;
import java.util.Map;

public interface InterventionService extends Service<InterventionReqDto, InterventionResDto, Long> {
    public InterventionResDto update(InterventionReqDto toUpdate, Long id);

    public Long getCountIntervention(Long id);

    public InterventionStatisticsResDto getInterventionStatistics();

    public Map<String, Object> getVariationParAnnee(int year);

    List<Integer> findAllYearsWithRequest();
}
