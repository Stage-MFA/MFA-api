package com.school.security.mappers;

import com.school.security.dtos.requests.JournalReqDto;
import com.school.security.dtos.responses.JournalResDto;
import com.school.security.entities.Intervention;
import com.school.security.entities.InterventionRequest;
import com.school.security.entities.Journal;
import com.school.security.entities.Maintenance;
import com.school.security.entities.User;
import com.school.security.exceptions.EntityException;
import com.school.security.repositories.InterventionRepository;
import com.school.security.repositories.MaintenanceRepository;
import com.school.security.repositories.RequestInterventionRepository;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JournalMapper implements Mapper<JournalReqDto, Journal, JournalResDto> {

    private final RequestInterventionRepository requestInterventionRepository;
    private final InterventionRepository interventionRepository;
    private final MaintenanceRepository maintenanceRepository;

    @Override
    public Journal fromDto(JournalReqDto dto) {
        Maintenance maintenance =
                maintenanceRepository
                        .findById(dto.idMaintenance())
                        .orElseThrow(
                                () ->
                                        new EntityException(
                                                "Maintenance not found with id: "
                                                        + dto.idMaintenance()));

        Journal journal = new Journal();
        journal.setDateJournal(LocalDateTime.now());
        journal.setAction(dto.description());

        long durationHours =
                Duration.between(maintenance.getStartDate(), maintenance.getEndDate()).toHours();

        journal.setDurationHour((int) durationHours);
        journal.setMaintenance(maintenance);

        return journal;
    }

    @Override
    public JournalResDto toDto(Journal entity) {
        Maintenance maintenance =
                maintenanceRepository
                        .findById(entity.getMaintenance().getMaintenanceId())
                        .orElseThrow(
                                () ->
                                        new EntityException(
                                                "Maintenance not found with id: "
                                                        + entity.getMaintenance()
                                                                .getMaintenanceId()));

        Intervention intervention =
                interventionRepository
                        .findById(maintenance.getIntervention().getInterventionId())
                        .orElseThrow(
                                () ->
                                        new EntityException(
                                                "Intervention not found with id: "
                                                        + maintenance
                                                                .getIntervention()
                                                                .getInterventionId()));

        InterventionRequest request =
                requestInterventionRepository
                        .findById(intervention.getInterventionRequest().getInterventionRequestId())
                        .orElseThrow(
                                () ->
                                        new EntityException(
                                                "InterventionRequest not found for userId: "
                                                        + intervention.getUser().getUsersId()));

        return new JournalResDto(
                entity.getJournalId(),
                entity.getDateJournal(),
                getFullName(request.getUser()),
                getFullName(intervention.getUser()),
                entity.getAction(),
                entity.getDurationHour());
    }

    private String getFullName(User user) {
        return user.getFirstname() + " " + user.getLastname();
    }
}
