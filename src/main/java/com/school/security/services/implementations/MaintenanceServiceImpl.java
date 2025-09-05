package com.school.security.services.implementations;

import com.school.security.dtos.requests.MaintenanceReqDto;
import com.school.security.dtos.responses.MaintenanceResDto;
import com.school.security.dtos.responses.MaintenancesStatisticsResDto;
import com.school.security.entities.Maintenance;
import com.school.security.enums.StatusType;
import com.school.security.exceptions.EntityException;
import com.school.security.mappers.MaintenanceMapper;
import com.school.security.repositories.InterventionRepository;
import com.school.security.repositories.MaintenanceRepository;
import com.school.security.services.contracts.MaintenanceService;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final MaintenanceMapper maintenanceMapper;
    private final InterventionRepository interventionRepository;

    @Override
    public MaintenanceResDto update(MaintenanceReqDto maintenanceReqDto, Long id) {

        Maintenance existing =
                maintenanceRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new EntityException("Maintenance not found with id " + id));

        existing.setStartDate(maintenanceReqDto.startDate());
        existing.setEndDate(maintenanceReqDto.endDate());
        existing.setDescription(maintenanceReqDto.description());
        existing.setStatus(maintenanceReqDto.status());
        existing.setIntervention(maintenanceMapper.fromDto(maintenanceReqDto).getIntervention());

        Maintenance updated = maintenanceRepository.save(existing);
        return maintenanceMapper.toDto(updated);
    }

    @Override
    public MaintenancesStatisticsResDto getMaintenanceStatistic() {
        Long maintenancesTotal = (long) this.maintenanceRepository.findAll().size();
        Long progress =
                this.maintenanceRepository.findAll().stream()
                        .filter(status -> status.getStatus() == StatusType.IN_PROGRESS)
                        .count();
        Long finish =
                this.maintenanceRepository.findAll().stream()
                        .filter(status -> status.getStatus() == StatusType.FINISH)
                        .count();

        return new MaintenancesStatisticsResDto(maintenancesTotal, progress, finish);
    }

    @Override
    public Map<String, Object> getVariationParAnnee(int year) {
        List<Object[]> minMaxList = this.maintenanceRepository.findMinMaxDateByYear(year);
        LocalDateTime minDate = null;
        LocalDateTime maxDate = null;

        if (!minMaxList.isEmpty()) {
            Object[] minMax = minMaxList.get(0);
            if (minMax[0] != null) minDate = (LocalDateTime) minMax[0];
            if (minMax[1] != null) maxDate = (LocalDateTime) minMax[1];
        }

        List<Object[]> results = this.maintenanceRepository.countByStatusAndDateByYear(year);
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
        return this.maintenanceRepository.findAllYearsWithRequests();
    }

    @Override
    public MaintenanceResDto createOrUpdate(MaintenanceReqDto toSave) {
        Maintenance maintenance = maintenanceMapper.fromDto(toSave);
        Maintenance saved = maintenanceRepository.save(maintenance);
        return maintenanceMapper.toDto(saved);
    }

    @Override
    public List<MaintenanceResDto> findAll() {
        return maintenanceRepository.findAll().stream().map(maintenanceMapper::toDto).toList();
    }

    @Override
    public MaintenanceResDto findById(Long id) {
        Maintenance maintenance =
                maintenanceRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new EntityException("Maintenance not found with id " + id));
        return maintenanceMapper.toDto(maintenance);
    }

    @Override
    public MaintenanceResDto deleteById(Long id) {
        Maintenance maintenance =
                maintenanceRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new EntityException("Maintenance not found with id " + id));
        maintenanceRepository.delete(maintenance);
        return maintenanceMapper.toDto(maintenance);
    }
}
