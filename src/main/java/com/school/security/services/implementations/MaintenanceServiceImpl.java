package com.school.security.services.implementations;

import com.school.security.dtos.requests.MaintenanceReqDto;
import com.school.security.dtos.responses.MaintenanceResDto;
import com.school.security.entities.Maintenance;
import com.school.security.exceptions.EntityException;
import com.school.security.mappers.MaintenanceMapper;
import com.school.security.repositories.InterventionRepository;
import com.school.security.repositories.MaintenanceRepository;
import com.school.security.services.contracts.MaintenanceService;
import jakarta.transaction.Transactional;
import java.util.List;
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
