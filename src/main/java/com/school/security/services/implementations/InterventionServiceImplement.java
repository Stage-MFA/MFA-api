package com.school.security.services.implementations;

import com.school.security.controllers.api.InvitationSseController;
import com.school.security.dtos.requests.InterventionReqDto;
import com.school.security.dtos.responses.InterventionResDto;
import com.school.security.dtos.responses.InterventionStatisticsResDto;
import com.school.security.entities.Intervention;
import com.school.security.enums.StatusType;
import com.school.security.exceptions.EntityException;
import com.school.security.mappers.InterventionMapper;
import com.school.security.repositories.InterventionRepository;
import com.school.security.services.contracts.InterventionService;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
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
        Long countIntervention = this.getCountIntervention(id);
        invitationSseController.sendCountIntervention(countIntervention);

        return interventionMapper.toDto(updated);
    }

    @Override
    public Long getCountIntervention(Long id) {
        return this.findAll().stream()
                .filter(s -> s.status() == StatusType.PENDING && Objects.equals(s.usersId(), id))
                .count();
    }

    @Override
    public InterventionStatisticsResDto getInterventionStatistics() {
        Long interventionTotal = (long) this.interventionRepository.findAll().size();
        Long pending =
                this.interventionRepository.findAll().stream()
                        .filter(status -> status.getStatus() == StatusType.PENDING)
                        .count();
        Long progress =
                this.interventionRepository.findAll().stream()
                        .filter(status -> status.getStatus() == StatusType.IN_PROGRESS)
                        .count();
        Long finish =
                this.interventionRepository.findAll().stream()
                        .filter(status -> status.getStatus() == StatusType.FINISH)
                        .count();

        return new InterventionStatisticsResDto(interventionTotal, pending, progress, finish);
    }

    @Override
    public Map<String, Object> getVariationParAnnee(int year) {
        List<Object[]> minMaxList = this.interventionRepository.findMinMaxDateByYear(year);
        LocalDateTime minDate = null;
        LocalDateTime maxDate = null;

        if (!minMaxList.isEmpty()) {
            Object[] minMax = minMaxList.get(0);
            if (minMax[0] != null) minDate = (LocalDateTime) minMax[0];
            if (minMax[1] != null) maxDate = (LocalDateTime) minMax[1];
        }

        List<Object[]> results = this.interventionRepository.countByStatusAndDateByYear(year);
        Map<LocalDate, Map<StatusType, Long>> variation = new LinkedHashMap<>();
        for (Object[] row : results) {
            StatusType status = (StatusType) row[0];
            LocalDate date = ((java.sql.Date) row[1]).toLocalDate();
            Long count = (Long) row[2];

            variation.putIfAbsent(date, new HashMap<>());
            variation.get(date).put(status, count);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("minDate", minDate);
        response.put("maxDate", maxDate);
        response.put("variation", variation);

        return response;
    }

    @Override
    public List<Integer> findAllYearsWithRequest() {
        return this.interventionRepository.findAllYearsWithRequests();
    }

    @Override
    public InterventionResDto createOrUpdate(InterventionReqDto toSave) {
        Intervention intervention = interventionMapper.fromDto(toSave);
        intervention = interventionRepository.save(intervention);
        Long countIntervention = this.getCountIntervention(toSave.idUser());
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
        Long countIntervention = this.getCountIntervention(id);
        this.invitationSseController.sendCountIntervention(countIntervention);
        return interventionMapper.toDto(intervention);
    }
}
