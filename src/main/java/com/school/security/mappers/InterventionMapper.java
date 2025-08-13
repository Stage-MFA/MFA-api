package com.school.security.mappers;

import com.school.security.dtos.requests.InterventionReqDto;
import com.school.security.dtos.responses.InterventionResDto;
import com.school.security.entities.Intervention;
import com.school.security.entities.InterventionRequest;
import com.school.security.entities.User;
import com.school.security.exceptions.EntityException;
import com.school.security.repositories.RequestInterventionRepository;
import com.school.security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InterventionMapper
        implements Mapper<InterventionReqDto, Intervention, InterventionResDto> {

    private final UserRepository userRepository;
    private final RequestInterventionRepository requestInterventionRepository;

    @Override
    public Intervention fromDto(InterventionReqDto d) {

        User user =
                userRepository
                        .findById(d.idUser())
                        .orElseThrow(() -> new EntityException("User not found"));
        InterventionRequest interventionRequest =
                requestInterventionRepository
                        .findById(d.interventionRequestId())
                        .orElseThrow(() -> new EntityException("Intervention request not found"));

        Intervention intervention = new Intervention();

        intervention.setDateIntervention(d.dateIntervention());
        intervention.setStatus(d.status());
        intervention.setDescription(d.description());
        intervention.setUser(user);
        intervention.setInterventionRequest(interventionRequest);

        return intervention;
    }

    @Override
    public InterventionResDto toDto(Intervention entity) {
        return new InterventionResDto(
                entity.getInterventionId(),
                entity.getDateIntervention(),
                entity.getStatus(),
                entity.getDescription(),
                entity.getUser().getUsersId(),
                entity.getInterventionRequest().getInterventionRequestId());
    }
}
