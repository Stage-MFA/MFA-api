package com.school.security.mappers;

import com.school.security.dtos.requests.MaintenanceReqDto;
import com.school.security.dtos.responses.MaintenanceResDto;
import com.school.security.entities.Intervention;
import com.school.security.entities.Maintenance;
import com.school.security.exceptions.EntityException;
import com.school.security.repositories.InterventionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MaintenanceMapper
        implements Mapper<MaintenanceReqDto, Maintenance, MaintenanceResDto> {
    private final InterventionRepository interventionRepository;

    @Override
    public Maintenance fromDto(MaintenanceReqDto d) {

        Intervention intervention =
                interventionRepository
                        .findById(d.interventionId())
                        .orElseThrow(() -> new EntityException("User not found"));

        Maintenance maintenance = new Maintenance();

        maintenance.setStartDate(d.startDate());
        maintenance.setEndDate(d.endDate());
        maintenance.setDescription(d.description());
        maintenance.setStatus(d.status());
        maintenance.setIntervention(intervention);

        return maintenance;
    }

    @Override
    public MaintenanceResDto toDto(Maintenance entity) {
        return new MaintenanceResDto(
                entity.getMaintenanceId(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getIntervention().getInterventionId());
    }
}
