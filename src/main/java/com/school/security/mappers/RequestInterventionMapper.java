package com.school.security.mappers;

import com.school.security.dtos.requests.RequestInterventionReqDto;
import com.school.security.dtos.responses.RequestInterventionResDto;
import com.school.security.entities.InterventionRequest;
import com.school.security.entities.User;
import com.school.security.exceptions.EntityException;
import com.school.security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestInterventionMapper
        implements Mapper<
                RequestInterventionReqDto, InterventionRequest, RequestInterventionResDto> {

    private final UserRepository userRepository;

    @Override
    public InterventionRequest fromDto(RequestInterventionReqDto d) {
        User user =
                userRepository
                        .findById(d.idUser())
                        .orElseThrow(
                                () ->
                                        new EntityException(
                                                "User with ID " + d.idUser() + " not found"));

        InterventionRequest interventionRequest = new InterventionRequest();
        interventionRequest.setRequestDate(d.requestDate());
        interventionRequest.setStatus(d.status());
        interventionRequest.setPriority(d.priority());
        interventionRequest.setMaterials(d.materials());
        interventionRequest.setDescription(d.description());
        interventionRequest.setUser(user);

        return interventionRequest;
    }

    @Override
    public RequestInterventionResDto toDto(InterventionRequest entity) {
        return new RequestInterventionResDto(
                entity.getInterventionRequestId(),
                entity.getRequestDate(),
                entity.getStatus(),
                entity.getPriority(),
                entity.getMaterials(),
                entity.getDescription(),
                entity.getUser().getUsersId());
    }
}
