package com.school.security.services.implementations;

import com.school.security.controllers.api.InvitationSseController;
import com.school.security.dtos.requests.InterventionReqDto;
import com.school.security.dtos.responses.InterventionResDto;
import com.school.security.entities.Intervention;
import com.school.security.enums.StatusType;
import com.school.security.exceptions.EntityException;
import com.school.security.mappers.InterventionMapper;
import com.school.security.repositories.InterventionRepository;
import com.school.security.services.contracts.InterventionService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class InterventionServiceImplement implements InterventionService {

    private final InterventionMapper interventionMapper;
    private final InterventionRepository interventionRepository;
    private InvitationSseController invitationSseController;

    @Override
    public InterventionResDto update(InterventionReqDto toUpdate, Long id) {
        Intervention existing =
                interventionRepository
                        .findById(id)
                        .orElseThrow(() -> new EntityException("Intervention not found"));

        Intervention updated = interventionMapper.fromDto(toUpdate);
        updated.setInterventionId(existing.getInterventionId());

        updated = interventionRepository.save(updated);
        Long countIntervention = this.getCountIntervention();
        invitationSseController.sendCountIntervention(countIntervention);

        return interventionMapper.toDto(updated);
    }

    @Override
    public Long getCountIntervention() {
        return this.findAll().stream().filter(s -> s.status() == StatusType.PENDING).count();
    }

    @Override
    public InterventionResDto createOrUpdate(InterventionReqDto toSave) {
        Intervention intervention = interventionMapper.fromDto(toSave);
        intervention = interventionRepository.save(intervention);
        Long countIntervention = this.getCountIntervention();
        invitationSseController.sendCountIntervention(countIntervention);
        return interventionMapper.toDto(intervention);
    }

    @Override
    public List<InterventionResDto> findAll() {
        return interventionRepository.findAll().stream().map(interventionMapper::toDto).toList();
    }

    @Override
    public InterventionResDto findById(Long id) {
        Intervention intervention =
                interventionRepository
                        .findById(id)
                        .orElseThrow(() -> new EntityException("Intervention not found"));
        return interventionMapper.toDto(intervention);
    }

    @Override
    public InterventionResDto deleteById(Long id) {
        Intervention intervention =
                interventionRepository
                        .findById(id)
                        .orElseThrow(() -> new EntityException("Intervention not found"));

        interventionRepository.delete(intervention);
        Long countIntervention = this.getCountIntervention();
        this.invitationSseController.sendCountIntervention(countIntervention);
        return interventionMapper.toDto(intervention);
    }
}
