package com.school.security.mappers;

import com.school.security.dtos.requests.RequestInterventionReqDto;
import com.school.security.entities.InterventionRequest;
import org.springframework.stereotype.Component;

@Component
public class RequestInterventionMapper
        implements Mapper<RequestInterventionReqDto, InterventionRequest, InterventionRequest> {

    @Override
    public InterventionRequest fromDto(RequestInterventionReqDto d) {

        InterventionRequest interventionRequest = new InterventionRequest();
        interventionRequest.setRequestDate(d.requestDate());
        interventionRequest.setStatus(d.status());
        interventionRequest.setPriority(d.priority());
        interventionRequest.setMaterials(d.materials());
        interventionRequest.setUser(d.user());

        return interventionRequest;
    }

    @Override
    public InterventionRequest toDto(InterventionRequest entity) {

        return new InterventionRequest(
                entity.getInterventionRequestId(),
                entity.getRequestDate(),
                entity.getStatus(),
                entity.getPriority(),
                entity.getMaterials(),
                entity.getUser());
    }
}
